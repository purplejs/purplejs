package io.purplejs.servlet;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletConfig;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import io.purplejs.resource.ResourcePath;

final class ScriptServletConfig
{
    private final static String CONFIG_PREFIX = "config.";

    private final ServletConfig config;

    ScriptServletConfig( final ServletConfig config )
    {
        this.config = config;
    }

    ResourcePath getResource()
    {
        return ResourcePath.from( getRequiredValue( "resource" ) );
    }

    Map<String, String> getConfig()
    {
        final Map<String, String> result = Maps.newHashMap();

        final Enumeration<String> keys = this.config.getInitParameterNames();
        while ( keys.hasMoreElements() )
        {
            final String key = keys.nextElement();
            if ( key.startsWith( CONFIG_PREFIX ) )
            {
                result.put( key.substring( CONFIG_PREFIX.length() ), this.config.getInitParameter( key ) );
            }
        }

        return result;
    }

    private String getRequiredValue( final String name )
    {
        final String value = this.config.getInitParameter( name );
        if ( !Strings.isNullOrEmpty( value ) )
        {
            return value;
        }

        throw new IllegalArgumentException( String.format( "Required init-parameter [%s] not set.", name ) );
    }
}
