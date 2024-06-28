# Demo Plugin

## Overview

The Demo Plugin is an IntelliJ IDEA plugin that enhances the IDE with a new tool window. Upon clicking this tool window, users are presented with animal images and can provide feedback on the image they see. The plugin stores all feedback in a SQLite database.
# Build Instructions

## Terminal Build

1. Clone the Repository:

```
git clone https://github.com/hosein97/intelij-plugin-userfeedback.git
cd demo-plugin
```

2.  Configure Database

    By default, the plugin stores user feedback in an in-memory SQLite database. To persist data or use a specific database location, set the `DEMO_PLUGIN_DB_URL` environment variable:
```
export DEMO_PLUGIN_DB_URL=jdbc:sqlite:/path/to/your/database.db
```
Note: To persist this setting across terminal sessions, add it to your shell configuration file (e.g., `~/.bashrc` or `~/.bash_profile`).
3. Build the Plugin:
```dtd
./gradlew build
```

4. Run the Plugin:
```dtd
./gradlew runIde
```

This command will start IntelliJ IDEA with the Demo plugin installed.

5. Build the Plugin ZIP File:
   Run the buildPlugin task to generate the ZIP file:
```dtd
./gradlew buildPlugin
```
This task generates a ZIP file containing the built plugin in the `build/distributions` directory.

## Installing the Plugin in IntelliJ IDEA
1. Install the Plugin:
   - Open IntelliJ IDEA
   - Go to File -> Settings -> Plugins.
   - Click on the ⚙️ icon and select Install Plugin from Disk....
   - Navigate to the location of the ZIP file (demo-plugin-1.0.0.zip) and select it.
   - Click OK to install the plugin.
2. Activate the Plugin:
   - Restart IntelliJ IDEA to load the installed plugin.
   - Use the plugin's features as described in the overview.