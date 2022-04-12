package rip.kaya.placeholders.command.api.command;

import org.bukkit.command.CommandSender;

public interface HelpFormatter {

    void sendHelpFor(CommandSender sender, DrinkCommandContainer container);
}
