package io.purplejs.servlet;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.servlet.ServletConfig;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.purplejs.resource.ResourcePath;

final class ScriptServletConfig
{
    private final static String CONFIG_PREFIX = "config.";

    private final static String DEV_MODE_PROP = "devMode";

    private final static String DEV_SOURCE_DIRS_PROP = "devSourceDirs";

    private final static String RESOURCE_PROP = "resource";

    private final ServletConfig config;

    ScriptServletConfig( final ServletConfig config )
    {
        this.config = config;
    }

    boolean isDevMode()
    {
        return getValue( DEV_MODE_PROP ).map( Boolean::parseBoolean ).orElse( false );
    }

    List<File> getDevSourceDirs()
    {
        final String value = getValue( DEV_SOURCE_DIRS_PROP ).orElse( "" );

        final List<File> result = Lists.newArrayList();
        Splitter.on( ',' ).omitEmptyStrings().trimResults().split( value ).forEach( ( str ) -> result.add( new File( str ) ) );
        return result;
    }

    ResourcePath getResource()
    {
        return ResourcePath.from( getRequiredValue( RESOURCE_PROP ) );
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

    private Optional<String> getValue( final String name )
    {
        final String value = this.config.getInitParameter( name );
        return Optional.ofNullable( Strings.emptyToNull( value ) );
    }

    private String getRequiredValue( final String name )
    {
        return getValue( name ).orElseThrow( (Supplier<RuntimeException>) () -> new IllegalArgumentException(
            String.format( "Required init-parameter [%s] not set.", name ) ) );
    }
}

