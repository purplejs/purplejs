package io.purplejs.registry;

import java.util.Optional;
import java.util.function.Supplier;

public interface Binding<T>
    extends Iterable<T>, Supplier<T>
{
    Class<T> getType();

    Optional<T> getOptional();
}
