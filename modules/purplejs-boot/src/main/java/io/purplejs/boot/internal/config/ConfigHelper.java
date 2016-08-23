package io.purplejs.boot.internal.config;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import io.purplejs.core.settings.Settings;

public final class ConfigHelper
{
    private final static String SYS_PROP_PREFIX = "io.purplejs";

    private final static String DEV_SOURCE_DIRS_KEY = "devSourceDirs";

    private final Settings settings;

    public ConfigHelper( final Settings settings )
    {
        this.settings = settings;
    }

    public List<File> getDevSourceDirs()
    {
        final String value = this.settings.get( DEV_SOURCE_DIRS_KEY ).orElse( null );
        return findDevSourceDirs( value );
    }

    private List<File> findDevSourceDirs( final String value )
    {
        if ( value == null )
        {
            return Lists.newArrayList();
        }

        final Iterable<String> items = Splitter.on( ',' ).omitEmptyStrings().trimResults().split( value );
        return Lists.newArrayList( items ).stream().map( File::new ).collect( Collectors.toList() );
    }
}
