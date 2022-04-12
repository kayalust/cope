package rip.kaya.placeholders.command.api.modifier;

import rip.kaya.placeholders.command.api.parametric.CommandParameter;
import rip.kaya.placeholders.command.api.command.CommandExecution;
import rip.kaya.placeholders.command.api.exception.CommandExitMessage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface DrinkModifier<T> {

    Optional<T> modify(@Nonnull CommandExecution execution, @Nonnull CommandParameter commandParameter, @Nullable T argument) throws CommandExitMessage;

}
