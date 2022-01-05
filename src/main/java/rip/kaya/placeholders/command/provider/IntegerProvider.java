package rip.kaya.placeholders.command.provider;

import rip.kaya.placeholders.command.argument.CommandArg;
import rip.kaya.placeholders.command.exception.CommandExitMessage;
import rip.kaya.placeholders.command.parametric.DrinkProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

public class IntegerProvider extends DrinkProvider<Integer> {

    public static final IntegerProvider INSTANCE = new IntegerProvider();

    @Override
    public boolean doesConsumeArgument() {
        return true;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean allowNullArgument() {
        return false;
    }

    @Nullable
    @Override
    public Integer defaultNullValue() {
        return 0;
    }

    @Override
    @Nullable
    public Integer provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        String s = arg.get();
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException ex) {
            throw new CommandExitMessage("Required: Integer, Given: '" + s + "'");
        }
    }

    @Override
    public String argumentDescription() {
        return "integer";
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        return Collections.emptyList();
    }
}
