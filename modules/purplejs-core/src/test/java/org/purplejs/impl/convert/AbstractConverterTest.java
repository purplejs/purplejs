package org.purplejs.impl.convert;

import org.junit.Before;
import org.purplejs.convert.Converter;

public abstract class AbstractConverterTest<V, C extends Converter<V>>
{
    protected C converter;

    @Before
    public final void setup()
    {
        this.converter = newConverter();
    }

    protected abstract C newConverter();
}
