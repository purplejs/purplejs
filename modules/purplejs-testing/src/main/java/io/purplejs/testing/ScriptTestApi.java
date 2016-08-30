package io.purplejs.testing;

import java.util.function.Consumer;

public interface ScriptTestApi
{
    void before( Consumer<Object[]> callback );

    void after( Consumer<Object[]> callback );

    void test( String name, Consumer<Object[]> callback );

    void assertEquals( Object expected, Object actual, String message );

    void assertNotEquals( Object expected, Object actual, String message );

    void assertTrue( boolean actual, String message );

    void assertFalse( boolean actual, String message );
}
