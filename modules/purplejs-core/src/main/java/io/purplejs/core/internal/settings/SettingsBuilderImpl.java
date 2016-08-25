package io.purplejs.core.internal.settings;

import java.util.Map;
import java.util.Properties;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import io.purplejs.core.settings.Settings;
import io.purplejs.core.settings.SettingsBuilder;

public final class SettingsBuilderImpl
    implements SettingsBuilder
{
    private final Map<String, String> map;

    public SettingsBuilderImpl()
    {
        this.map = Maps.newTreeMap();
    }

    @Override
    public SettingsBuilder put( final Properties properties )
    {
        return put( null, properties );
    }

    @Override
    public SettingsBuilder put( final Map<String, String> map )
    {
        return put( null, map );
    }

    @Override
    public SettingsBuilder put( final Settings settings )
    {
        return put( null, settings );
    }

    @Override
    public SettingsBuilder put( final String key, final Properties properties )
    {
        return put( key, Maps.fromProperties( properties ) );
    }

    @Override
    public SettingsBuilder put( final String key, final Map<String, String> map )
    {
        map.forEach( ( oldKey, value ) -> put( keyFromPrefix( key, oldKey ), value ) );
        return this;
    }

    @Override
    public SettingsBuilder put( final String key, final Settings settings )
    {
        return put( key, settings.asMap() );
    }

    @Override
    public SettingsBuilder put( final String key, final String value )
    {
        this.map.put( key.trim(), value.trim() );
        return this;
    }

    private String keyFromPrefix( final String prefix, final String key )
    {
        return prefix != null ? ( prefix + "." + key ) : key;
    }

    @Override
    public SettingsBuilder put( final String key, final boolean value )
    {
        return put( key, String.valueOf( value ) );
    }

    @Override
    public SettingsBuilder put( final String key, final Number value )
    {
        return put( key, String.valueOf( value ) );
    }

    @Override
    public SettingsBuilder putArray( final String key, final String... values )
    {
        return put( key, Joiner.on( ',' ).join( values ) );
    }

    @Override
    public Settings build()
    {
        return new SettingsImpl( ImmutableMap.copyOf( this.map ) );
    }
}
