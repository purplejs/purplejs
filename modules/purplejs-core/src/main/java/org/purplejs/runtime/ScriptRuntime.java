package org.purplejs.runtime;

import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptExports;

public interface ScriptRuntime
{
    ScriptExports execute( ResourcePath path );
}
