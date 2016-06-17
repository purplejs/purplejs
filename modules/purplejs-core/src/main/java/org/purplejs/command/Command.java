package org.purplejs.command;

import java.util.Map;

import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptExports;

public interface Command<R>
{
    ResourcePath getResource();

    Map<String, Object> getVariables();

    R execute( ScriptExports exports );
}
