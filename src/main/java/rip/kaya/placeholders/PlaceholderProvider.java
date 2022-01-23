package rip.kaya.placeholders;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/5/2022
*/

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rip.kaya.placeholders.command.argument.CommandArg;
import rip.kaya.placeholders.command.exception.CommandExitMessage;
import rip.kaya.placeholders.command.parametric.DrinkProvider;
import rip.kaya.placeholders.utilities.CC;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public class PlaceholderProvider extends DrinkProvider<Placeholder> {

    private final PlaceholderPlugin plugin = PlaceholderPlugin.getInstance();

    @Override
    public boolean doesConsumeArgument() {
        return true;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Nullable
    @Override
    public Placeholder provide(@NotNull CommandArg arg, @NotNull List<? extends Annotation> annotations) throws CommandExitMessage {
        String name = arg.get();
        Placeholder placeholder = plugin.getPlaceholderManager().getPlaceholderByName(name);

        if (name == null || placeholder == null) {
            throw new CommandExitMessage(CC.translate("&cThe placeholder " + name + " was not found!"));
        }

        return placeholder;
    }

    @Override
    public String argumentDescription() {
        return "placeholder";
    }

    @Override
    public List<String> getSuggestions(@NotNull String prefix) {
        String fp = prefix.toLowerCase();
        return plugin.getPlaceholderManager().getPlaceholders().values().stream().map(Placeholder::getName).filter(s -> fp.length() == 0 || s.startsWith(fp)).collect(Collectors.toList());
    }
}
