package io.purplejs.impl.executor;

import java.net.URL;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Resources;

final class InitScriptReader
{
    private final static InitScriptReader INSTANCE = new InitScriptReader();

    private final String init;

    private InitScriptReader()
    {
        try
        {
            this.init = loadScript();
        }
        catch ( final Exception e )
        {
            throw new RuntimeException( "Failed to load init.js", e );
        }
    }

    static String getScript( final String script )
    {
        return INSTANCE.init.replace( "__script__;", script );
    }

    private String loadScript()
        throws Exception
    {
        final URL url = this.getClass().getResource( "init.js" );
        final List<String> lines = Resources.readLines( url, Charsets.UTF_8 );
        return Joiner.on( "" ).join( lines );
    }
}
