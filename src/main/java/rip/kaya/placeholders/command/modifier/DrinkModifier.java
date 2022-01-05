package rip.kaya.placeholders.command.modifier;

import rip.kaya.placeholders.command.parametric.CommandParameter;
import rip.kaya.placeholders.command.command.CommandExecution;
import rip.kaya.placeholders.command.exception.CommandExitMessage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface DrinkModifier<T> {

    Optional<T> modify(@Nonnull CommandExecution execution, @Nonnull CommandParameter commandParameter, @Nullable T argument) throws CommandExitMessage;

}
