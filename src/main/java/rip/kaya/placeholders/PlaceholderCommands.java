package rip.kaya.placeholders;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/5/2022
*/

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import rip.kaya.placeholders.command.annotation.Command;
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
    public void sendHelp(@Sender CommandSender sender) {
        helpMessage.forEach(sender::sendMessage);
    }

    @Command(name = "create", desc = "Creates a new placeholder", usage = "<name>")
    public void create(@Sender CommandSender sender, String name) {
        Placeholder placeholder = new Placeholder(name);
        placeholder.save();

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("&aSuccessfully created the placeholder " + name + "!");
            return;
        }

        sender.sendMessage(CC.translate("&aSuccessfully created the placeholder " + name + "!"));
    }

    @Command(name = "delete", desc = "Deletes an existing placeholder", usage = "<name>")
    public void delete(@Sender CommandSender sender, Placeholder name) {
        name.delete();

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("&aSuccessfully deleted the placeholder " + name + "!");
            return;
        }

        sender.sendMessage(CC.translate("&aSuccessfully deleted the placeholder " + name + "!"));
    }
}