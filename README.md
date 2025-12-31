# Mini IDE 

A lightweight IDE for running Kotlin and Swift scripts, built with **Compose Multiplatform** targeting **desktop**.

## Features

- **Multi-language Support**: Run Kotlin (`.kts`), Swift (`.swift`), Rust(`.rs`) and C(`.c`) directly from the editor.
- **Syntax Highlighting**: Real-time keyword highlighting.
- **Integrated Runner**: Toolbar with Run and Stop controls, language selection, and exit code status.
- **Live Output**: Console output window for stdout and stderr.

## Requirements

To run scripts within the IDE, ensure the following are installed and available in your `PATH`:
- **Kotlin**: `kotlinc` (for Kotlin scripts)
- **Swift**: `swift` (for Swift scripts)
- **Rust**: `cargo` (for Rust binaries)
- **C**: `clang` (for C binaries)

## 🚀 Getting Started
   ```bash
   git clone https://github.com/alukic7/mini-ide.git
   cd mini-ide
   ./gradlew run
   ```
## Usage

1. Select your target language from the dropdown menu in the toolbar.
2. Write your code in the editor pane.
3. Click **Run** to execute. 
4. The output or potential errors will appear in the bottom panel.
5. Click **Stop** if you need to terminate a running process.
