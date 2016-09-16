package io.purplejs.mustache;

import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptValue;

public interface MustacheService
{
    String render( ResourcePath view, ScriptValue model );
}
