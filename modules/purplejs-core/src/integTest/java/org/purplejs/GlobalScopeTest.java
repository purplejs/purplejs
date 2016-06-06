package org.purplejs;

import org.junit.Test;
import org.purplejs.resource.ResourcePath;

public class GlobalScopeTest
    extends AbstractScriptTest
{
    @Test
    public void testScope()
    {
        run( ResourcePath.from( "/global/main.js" ) );
    }
}
