package rip.kaya.placeholders.command.provider;

import rip.kaya.placeholders.command.argument.CommandArg;
import rip.kaya.placeholders.command.argument.CommandArgs;
import rip.kaya.placeholders.command.exception.CommandExitMessage;
import rip.kaya.placeholders.command.parametric.DrinkProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

public class CommandArgsProvider extends DrinkProvider<CommandArgs> {

    public static final CommandArgsProvider INSTANCE = new CommandArgsProvider();

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
    public CommandArgs provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        return arg.getArgs();
    }

    @Override
    public String argumentDescription() {
        return "args";
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        return Collections.emptyList();
    }
}
