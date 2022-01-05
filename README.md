# CustomPlaceholders
A custom placeholder plugin that saves placeholders values to a Mongo Database (also per player support!)
## Commands
- `/cp create <name>` - Creates a new placeholder
- `/cp delete <name>` - Deletes an existing placeholder
- `/cp setdefaultvalue <name> <value>` - Sets the default value of a specific placeholder
- `/cp setplayervalue <name> <player> <value>` - Sets the placeholder value of a specific player
## Dependencies
* A working MongoDB database (Required)
* PlaceholderAPI (Required) - https://github.com/PlaceholderAPI/PlaceholderAPI/
## Source Code
This plugin is developed on Java 8. However, it should work with any Java versions that is not 7 or lower.

Lombok plugin is required in order to build the plugin using Maven.