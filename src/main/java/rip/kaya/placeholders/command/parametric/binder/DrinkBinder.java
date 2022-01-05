package rip.kaya.placeholders.command.parametric.binder;

import com.google.common.base.Preconditions;
import rip.kaya.placeholders.command.parametric.DrinkProvider;
import rip.kaya.placeholders.command.provider.InstanceProvider;
import rip.kaya.placeholders.command.command.DrinkCommandService;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class DrinkBinder<T> {

    private final DrinkCommandService commandService;
    private final Class<T> type;
    private final Set<Class<? extends Annotation>> classifiers = new HashSet<>();
    private DrinkProvider<T> provider;

    public DrinkBinder(DrinkCommandService commandService, Class<T> type) {
        this.commandService = commandService;
        this.type = type;
    }

    public DrinkBinder<T> annotatedWith(@Nonnull Class<? extends Annotation> annotation) {
        Preconditions.checkState(commandService.getModifierService().isClassifier(annotation), "Annotation " + annotation.getSimpleName() + " must have @Classifer to be bound");
        classifiers.add(annotation);
        return this;
    }

    public void toInstance(@Nonnull T instance) {
        Preconditions.checkNotNull(instance, "Instance cannot be null for toInstance during binding for " + type.getSimpleName());
        this.provider = new InstanceProvider<>(instance);
        finish();
    }

    public void toProvider(@Nonnull DrinkProvider<T> provider) {
        Preconditions.checkNotNull(provider, "Provider cannot be null for toProvider during binding for " + type.getSimpleName());
        this.provider = provider;
        finish();
    }

    private void finish() {
        commandService.bindProvider(type, classifiers, provider);
    }

}
