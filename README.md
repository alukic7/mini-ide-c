# Mini IDE 

A lightweight IDE for running Kotlin and Swift scripts, built with **Compose Multiplatform** targeting **desktop**.

## Features

- **Multi-language Support**: Run Kotlin (`.kts`) and Swift (`.swift`) scripts directly from the editor.
- **Syntax Highlighting**: Real-time keyword highlighting for both Kotlin and Swift.
- **Integrated Runner**: Toolbar with Run and Stop controls, language selection, and exit code status.
- **Live Output**: Console output window for stdout and stderr (with error highlighting).

## Requirements

To run scripts within the IDE, ensure the following are installed and available in your `PATH`:
- **Kotlin**: `kotlinc` (for Kotlin scripts)
- **Swift**: `swift` (for Swift scripts)

## 🚀 Getting Started
   ```bash
   git clone https://github.com/alukic7/mini-ide.git
   cd mini-ide
   ./gradlew run
   ```
## Usage

1. Select your target language (Kotlin or Swift) from the dropdown menu in the toolbar.
2. Write your script in the editor pane.
3. Click **Run** to execute. 
4. The output or potential errors will appear in the bottom panel.
5. Click **Stop** if you need to terminate a running script.
