package org.purplejs.engine;

import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptExports;

public interface Engine
{
    // TODO: Name this require
    ScriptExports execute( ResourcePath path );

    // ScriptValue execute(ResourcePath path);

    // Response execute( ScriptExports exports, Request request );
    // Request filter( ScriptExports exports, Request request );
    // Response filter( ScriptExports exports, Request request, Response response );

    // void dispose();   -> Trigger all finalizers
}
