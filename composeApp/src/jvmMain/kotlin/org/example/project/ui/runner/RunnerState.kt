package org.example.project.ui.runner

enum class RuntimeConfiguration(val desc: String) {
    KOTLIN("kotlinc in PATH"),
    SWIFT("swift in PATH"),
    RUST("cargo in PATH"),
    C("clang in PATH"),
}

data class RunnerState(
    val isRunning: Boolean = false,
    val lastExitCode: Int? = null,
    val lastRunHadNonZeroExit: Boolean = false,
    val lastErrorMessage: String? = null,
    val runtimeConfiguration: RuntimeConfiguration = RuntimeConfiguration.KOTLIN
)