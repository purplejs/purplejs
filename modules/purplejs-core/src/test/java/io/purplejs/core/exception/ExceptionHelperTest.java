package io.purplejs.core.exception;

import java.io.IOException;

import org.junit.Test;

public class ExceptionHelperTest
{
    @Test(expected = IOException.class)
    public void throwUnchecked()
    {
        throw ExceptionHelper.unchecked( new IOException() );
    }
}
