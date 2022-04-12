package rip.kaya.placeholders.command.api.provider.spigot;

import rip.kaya.placeholders.command.api.argument.CommandArg;
import rip.kaya.placeholders.command.api.exception.CommandExitMessage;
import rip.kaya.placeholders.command.api.parametric.DrinkProvider;
import org.bukkit.command.ConsoleCommandSender;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

public class ConsoleCommandSenderProvider extends DrinkProvider<ConsoleCommandSender> {
    public static final ConsoleCommandSenderProvider INSTANCE = new ConsoleCommandSenderProvider();

    @Override
    public boolean doesConsumeArgument() {
        return false;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Nullable
    @Override
    public ConsoleCommandSender provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        if (arg.isSenderPlayer()) {
            throw new CommandExitMessage("This is a console-only command.");
        }

        return null;
    }

    @Override
    public String argumentDescription() {
        return "console sender";
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        return Collections.emptyList();
    }
}
