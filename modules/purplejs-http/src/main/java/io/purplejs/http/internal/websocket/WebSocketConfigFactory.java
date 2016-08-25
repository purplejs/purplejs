package io.purplejs.http.internal.websocket;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import io.purplejs.core.value.ScriptValue;
import io.purplejs.http.websocket.WebSocketConfig;

public final class WebSocketConfigFactory
{
    private final static long DEFAULT_TIMEOUT = TimeUnit.SECONDS.toMillis( 30 );

    public WebSocketConfig create( final ScriptValue value )
    {
        if ( value == null )
        {
            return null;
        }

        final WebSocketConfigImpl config = new WebSocketConfigImpl();
        config.subProtocols = parseSubProtocols( value.getMember( "subProtocols" ) );
        config.attributes = value.getMember( "attributes" );
        config.group = getString( value.getMember( "group" ), "default" );
        config.timeout = getLong( value.getMember( "timeout" ), DEFAULT_TIMEOUT );
        return config;
    }

    private Set<String> parseSubProtocols( final ScriptValue value )
    {
        final Set<String> set = Sets.newHashSet();
        if ( value == null )
        {
            return set;
        }

        if ( value.isValue() )
        {
            set.add( value.getValue().toString() );
        }
        else if ( value.isArray() )
        {
            set.addAll( value.getArray().stream().
                filter( ScriptValue::isValue ).
                map( item -> item.getValue().toString() ).
                collect( Collectors.toList() ) );
        }

        return set;
    }

    private String getString( final ScriptValue value, final String defValue )
    {
        return ( value != null && value.isValue() ) ? value.getValue().toString() : defValue;
    }

    private Long getLong( final ScriptValue value, final long defValue )
    {
        if ( value != null && value.isValue() )
        {
            try
            {
                return value.getValue( Long.class );
            }
            catch ( final Exception e )
            {
                return defValue;
            }
        }

        return defValue;
    }
}
