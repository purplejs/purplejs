package org.purplejs.value;

import org.purplejs.resource.ResourcePath;

public interface ScriptExports
{
    ResourcePath getResource();

    ScriptValue getValue();

    boolean hasMethod( String name );

    ScriptValue executeMethod( String name, Object... args );
}
