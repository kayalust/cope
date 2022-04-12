package rip.kaya.placeholders;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/5/2022
*/

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import rip.kaya.placeholders.command.annotation.Command;
import rip.kaya.placeholders.command.annotation.Require;
import rip.kaya.placeholders.command.annotation.Sender;
import rip.kaya.placeholders.utilities.CC;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class PlaceholderCommands {

    private final PlaceholderPlugin plugin;

    private final List<String> helpMessage = Arrays.asList(
            "- /cp create <name> - Creates a new placeholder\n",
            "- /cp delete <name> - Deletes an existing placeholder\n",
            "- /cp setdefaultvalue <name> <value> - Sets the default value of a specific placeholder\n",
            "- /cp setplayervalue <name> <player> <value> - Sets the placeholder value of a specific player\n"
    );

    @Command(name = "", desc = "The main CustomPlaceholders command.")
    public void sendHelp(@Sender CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("cp.admin")) {
                helpMessage.forEach(sender::sendMessage);
            } else {
                player.sendMessage(CC.BLANK_LINE);
                player.sendMessage("&fThis server is currently running &ecope &fv&e" + plugin.getDescription().getVersion());
                player.sendMessage("&fDeveloped by &ekayalust &f@ &eCrackpixel Development Team");
                player.sendMessage(CC.BLANK_LINE);
            }
        } else {
            helpMessage.forEach(sender::sendMessage);
        }
    }

    @Command(name = "create", desc = "Creates a new placeholder", usage = "<name>")
    @Require("cp.admin")
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
    public void setPlayerValue(@Sender CommandSender sender, Placeholder placeholder, String playerName, String value) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (!placeholder.getData().containsKey(player.getUniqueId())) {
            sender.sendMessage("This player has never joined before!");
            return;
        }

        placeholder.setValue(player.getUniqueId(), value);

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Successfully set " + placeholder.getName() + "'s value for " + player.getName() + " to " + value + "!");
            return;
        }

        sender.sendMessage(CC.translate("&aSuccessfully set " + placeholder.getName() + "'s value for " + player.getName() + " to " + value + "!"));
    }

    @Command(name = "addplayervalue", aliases = "add", desc = "Adds a value to a player of a placeholder", usage = "<player> <name> <value>")
    @Require("cp.admin")
    public void addPlayerValue(@Sender CommandSender sender, String playerName, Placeholder placeholder, String value) {
        if (!isNumeric(value)) {
            sender.sendMessage(CC.translate("That is not a number!"));
            return;
        }

        int i = Integer.parseInt(value);

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (!placeholder.getData().containsKey(player.getUniqueId())) {
            sender.sendMessage("This player has never joined before!");
            return;
        }

        placeholder.setValue(player.getUniqueId(), String.valueOf(Integer.parseInt(placeholder.getPlayerValue(player.getUniqueId())) + i));

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Successfully added " + placeholder.getName() + "'s value for " + player.getName() + " to " + value + "!");
            return;
        }

        sender.sendMessage(CC.translate("&aSuccessfully added " + placeholder.getName() + "'s value for " + player.getName() + " to " + value + "!"));
    }

    @Command(name = "removeplayervalue", aliases = "remove", desc = "Removes a value to a player of a placeholder", usage = "<player> <name> <value>")
    @Require("cp.admin")
    public void removePlayerValue(@Sender CommandSender sender, String playerName, Placeholder placeholder, String value) {
        if (!isNumeric(value)) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage("That is not a number!");
            } else {
                sender.sendMessage(CC.translate("&cThat is not a number!"));
            }
            return;
        }

        int i = Integer.parseInt(value);

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (!placeholder.getData().containsKey(player.getUniqueId())) {
            sender.sendMessage("This player has never joined before!");
            return;
        }

        if ((Integer.parseInt(placeholder.getPlayerValue(player.getUniqueId())) - i) <= 0) {
            sender.sendMessage("not setting player balance cuz theyre broke lol cope");
            return;
        }

        placeholder.setValue(player.getUniqueId(), String.valueOf(Integer.parseInt(placeholder.getPlayerValue(player.getUniqueId())) - i));

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Successfully removed " + placeholder.getName() + "'s value for " + player.getName() + " to " + value + "!");
            return;
        }

        sender.sendMessage(CC.translate("&aSuccessfully removed " + placeholder.getName() + "'s value for " + player.getName() + " to " + value + "!"));
    }

    @Command(name = "viewvalue", desc = "Views a player placeholder value", usage = "<name> <player>")
    @Require("cp.admin")
    public void viewValue(@Sender Player sender, Placeholder placeholder, String playerName) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (!placeholder.getData().containsKey(player.getUniqueId())) {
            sender.sendMessage("This player has never joined before!");
            return;
        }

        sender.sendMessage(CC.translate("&aValue is " + placeholder.getToDisplay(player.getUniqueId())) + " for " + player.getName());
    }

    public static boolean isNumeric(String string) {
        if(string == null || string.equals("")) {
            PlaceholderPlugin.getInstance().getLogger().info("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            PlaceholderPlugin.getInstance().getLogger().info("Input String cannot be parsed to Integer.");
            return false;
        }
        return true;
    }
}