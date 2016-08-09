package io.purplejs.boot.impl.config;

import java.util.Map;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;

public final class Configuration
    extends ForwardingMap<String, String>
{
    private final Map<String, String> map;

    public Configuration()
    {
        this.map = Maps.newHashMap();
    }

    @Override
    protected Map<String, String> delegate()
    {
        return this.map;
    }

    public Configuration subConfig( final String prefix )
    {
        final Configuration config = new Configuration();
        for ( final Map.Entry<String, String> entry : this.entrySet() )
        {
            final String key = entry.getKey();
            if ( key.startsWith( prefix ) )
            {
                config.put( key.substring( prefix.length() ), entry.getValue() );
            }
        }

        return config;
    }
}
