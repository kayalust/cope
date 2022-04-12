# cope
A custom placeholder plugin that saves placeholders values to a Mongo Database (also per player support!)
## Commands
- `/cope create <name>` - Creates a new placeholder
- `/cope delete <name>` - Deletes an existing placeholder
- `/cope setdefaultvalue <name> <value>` - Sets the default value of a specific placeholder
- `/cope setplayervalue <name> <player> <value>` - Sets the placeholder value of a specific player
## Dependencies
* A working MongoDB database
* PlaceholderAPI - https://github.com/PlaceholderAPI/PlaceholderAPI/
## Recommended Usage
- This project is recommended to be used in any multi-server (or normal) setup that requires a custom data per player that's saved to the database (Eg. Coins or points system, etc.)
## Source Code
This plugin is developed on Java 8. However, it should work with any Java versions that is not 7 or lower.

Lombok plugin is required in order to build the project using Maven.