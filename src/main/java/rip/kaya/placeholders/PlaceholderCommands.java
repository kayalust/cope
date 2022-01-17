package rip.kaya.placeholders;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/5/2022
*/

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import rip.kaya.placeholders.command.annotation.Command;
import rip.kaya.placeholders.command.annotation.Require;
import rip.kaya.placeholders.command.annotation.Sender;
import rip.kaya.placeholders.utilities.CC;

import java.util.Arrays;
import java.util.List;

public class PlaceholderCommands {

    private final PlaceholderPlugin plugin = PlaceholderPlugin.getInstance();

    private final List<String> helpMessage = Arrays.asList(
            "- /cp create <name> - Creates a new placeholder\n",
            "- /cp delete <name> - Deletes an existing placeholder\n",
            "- /cp setdefaultvalue <name> <value> - Sets the default value of a specific placeholder\n",
            "- /cp setplayervalue <name> <player> <value> - Sets the placeholder value of a specific player\n"
    );

    @Command(name = "", desc = "The main CustomPlaceholders command.")
    @Require("cp.admin")
    public void sendHelp(@Sender CommandSender sender) {
        helpMessage.forEach(sender::sendMessage);
    }

    @Command(name = "create", desc = "Creates a new placeholder", usage = "<name>")
    public void create(@Sender CommandSender sender, String name) {
        Placeholder placeholder = new Placeholder(name);
        placeholder.save();

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Successfully created the placeholder " + name + "!");
            return;
        }

        sender.sendMessage(CC.translate("&aSuccessfully created the placeholder " + name + "!"));
    }

    @Command(name = "delete", desc = "Deletes an existing placeholder", usage = "<name>")
    @Require("cp.admin")
    public void delete(@Sender CommandSender sender, Placeholder name) {
        name.delete();

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Successfully deleted the placeholder " + name.getName() + "!");
            return;
        }

        sender.sendMessage(CC.translate("&aSuccessfully deleted the placeholder " + name.getName() + "!"));
    }

    @Command(name = "setdefaultvalue", desc = "Sets the default value of a placeholder", usage = "<name> <value>")
    @Require("cp.admin")
    public void setDefaultValue(@Sender CommandSender sender, Placeholder name, String value) {
        name.setDefaultValue(value);
        name.save();

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Successfully set " + name.getName() + "'s default value to " + value + "!");
            return;
        }

        sender.sendMessage(CC.translate("&aSuccessfully set " + name.getName() + "'s default value to " + value + "!"));
    }

    @Command(name = "setplayervalue", desc = "Sets the player value of a placeholder", usage = "<name> <player> <value>")
    @Require("cp.admin")
    public void setPlayerValue(@Sender CommandSender sender, Placeholder name, Player player, String value) {
        name.setValue(player, value);
        name.save();

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Successfully set " + name.getName() + "'s value for " + player.getName() + " to " + value + "!");
            return;
        }

        sender.sendMessage(CC.translate("&aSuccessfully set " + name.getName() + "'s value for " + player.getName() + " to " + value + "!"));
    }
}