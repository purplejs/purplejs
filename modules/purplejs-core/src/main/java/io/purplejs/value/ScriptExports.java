package io.purplejs.value;

import io.purplejs.resource.ResourcePath;

public interface ScriptExports
{
    ResourcePath getResource();

    ScriptValue getValue();

    boolean hasMethod( String name );

    ScriptValue executeMethod( String name, Object... args );
}
