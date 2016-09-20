package io.purplejs.thymeleaf;

import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptValue;

public interface ThymeleafService
{
    String render( ResourcePath view, ScriptValue model );
}
