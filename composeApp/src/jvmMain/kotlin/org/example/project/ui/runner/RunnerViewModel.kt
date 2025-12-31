package org.example.project.ui.runner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.example.project.ui.editor.EditorViewModel
import org.example.project.ui.output.LineType
import org.example.project.ui.output.OutputViewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.writeText

class RunnerViewModel(
    private val editorViewModel: EditorViewModel,
    private val outputViewModel: OutputViewModel
) : ViewModel() {

    private val _state = MutableStateFlow(RunnerState())
    val state: StateFlow<RunnerState> = _state

    private var currProcess: Process? = null
    private var runJob: Job? = null
    private var tempDir: Path? = null
    private var pb: ProcessBuilder? = null

    fun start() {
        if (_state.value.isRunning) return

        outputViewModel.clear()
        _state.update { it.copy(isRunning = true, lastExitCode = null, lastRunHadNonZeroExit = false, lastErrorMessage = null) }

        runJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                tempDir = Files.createTempDirectory("miniide-")
                val extension = when (_state.value.runtimeConfiguration) {
                    RuntimeConfiguration.KOTLIN -> "kts"
                    RuntimeConfiguration.SWIFT -> "swift"
                    RuntimeConfiguration.RUST -> "rs"
                    RuntimeConfiguration.C -> "c"
                }
                val scriptFile = tempDir?.resolve("foo.$extension")
                val outputFile = tempDir?.resolve("foo.out")
                scriptFile?.writeText(editorViewModel.state.value.text)

                when(_state.value.runtimeConfiguration) {
                    RuntimeConfiguration.KOTLIN -> {
                        pb = ProcessBuilder(
                            "/usr/bin/env",
                            "kotlinc",
                            "-script",
                            scriptFile?.
                            toAbsolutePath().
                            toString()
                        )
                    }
                    RuntimeConfiguration.SWIFT -> {
                        pb = ProcessBuilder(
                            "/usr/bin/env",
                            "swift",
                            scriptFile?.
                            toAbsolutePath().
                            toString()
                        )
                    }

                    RuntimeConfiguration.RUST -> {
                        // Using rustc for single-file compilation and execution
                        pb = ProcessBuilder(
                            "/bin/sh", "-c",
                            "rustc ${scriptFile?.toAbsolutePath()} -o ${outputFile?.toAbsolutePath()} && ${outputFile?.toAbsolutePath()}"
                        )
                    }
                    RuntimeConfiguration.C -> {
                        // Using clang (or gcc) for C
                        pb = ProcessBuilder(
                            "/bin/sh", "-c",
                            "clang ${scriptFile?.toAbsolutePath()} -o ${outputFile?.toAbsolutePath()} && ${outputFile?.toAbsolutePath()}"
                        )
                    }
                }

                pb?.redirectErrorStream(false)
                val p = pb?.start()
                currProcess = p

                val stdout = BufferedReader(InputStreamReader(p?.inputStream))
                val stderr = BufferedReader(InputStreamReader(p?.errorStream))

                val outJob = launch {
                    var line: String?
                    while (stdout.readLine().also { line = it } != null) {
                        outputViewModel.append(line!!, LineType.TEXT)
                    }
                }
                val errJob = launch {
                    var line: String?
                    while (stderr.readLine().also { line = it } != null) {
                        outputViewModel.append("[Error]: ${line!!}", type = LineType.ERROR)
                    }
                }

                val exitCode = p?.waitFor()
                outJob.cancel()
                errJob.cancel()

                withContext(Dispatchers.Main) {
                    _state.update {
                        it.copy(
                            isRunning = false,
                            lastExitCode = exitCode,
                            lastRunHadNonZeroExit = exitCode != 0
                        )
                    }
                }
            } catch (t: Throwable) {
                withContext(Dispatchers.Main) {
                    _state.update {
                        it.copy(isRunning = false, lastErrorMessage = t.message ?: t.toString())
                    }
                }
            } finally {
                tempDir?.toFile()?.deleteRecursively()
                currProcess = null
                pb = null
            }
        }
    }

    fun setRuntimeConfiguration(config: RuntimeConfiguration) {
        _state.update { it.copy(runtimeConfiguration = config) }
    }

    fun stop() {
        val p = currProcess ?: return
        try {
            p.destroyForcibly()
        } finally {
            currProcess = null
            pb = null
            runJob?.cancel()
            _state.update {
                it.copy(isRunning = false)
            }
            outputViewModel.append("Stopped script execution.", LineType.TEXT)
        }
    }

    fun clearError() {
        _state.update {
            it.copy(lastErrorMessage = null)
        }
    }
}
