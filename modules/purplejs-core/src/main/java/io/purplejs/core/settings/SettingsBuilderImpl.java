package io.purplejs.core.settings;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

final class SettingsBuilderImpl
    implements SettingsBuilder
{
    private final static Pattern INTERPOLATE = Pattern.compile( "(\\$\\{(\\S+)})", Pattern.MULTILINE );

    private final Map<String, String> map;

    SettingsBuilderImpl()
    {
        this.map = Maps.newLinkedHashMap();
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
    public SettingsBuilder put( final String key, final int value )
    {
        return put( key, String.valueOf( value ) );
    }

    @Override
    public SettingsBuilder put( final String key, final long value )
    {
        return put( key, String.valueOf( value ) );
    }

    @Override
    public SettingsBuilder put( final String key, final float value )
    {
        return put( key, String.valueOf( value ) );
    }

    @Override
    public SettingsBuilder put( final String key, final double value )
    {
        return put( key, String.valueOf( value ) );
    }

    @Override
    public SettingsBuilder putArray( final String key, final String... values )
    {
        return put( key, Joiner.on( ',' ).join( values ) );
    }

    public SettingsBuilder interpolate()
    {
        this.map.forEach( this::interpolate );
        return this;
    }

    private void interpolate( final String key, final String value )
    {
        this.map.put( key, interpolateValue( key, value ) );
    }

    private String interpolateValue( final String key, final String value )
    {
        final Matcher matcher = INTERPOLATE.matcher( value );

        final StringBuffer str = new StringBuffer();
        while ( matcher.find() )
        {
            final String replaceKey = matcher.group( 2 );
            if ( key.equals( replaceKey ) )
            {
                continue;
            }

            final String replaceValue = this.map.get( replaceKey );
            if ( replaceValue != null )
            {
                final String interpolated = interpolateValue( replaceKey, replaceValue );
                matcher.appendReplacement( str, interpolated.replace( "$", "\\$" ) );
            }
        }

        matcher.appendTail( str );
        return str.toString();
    }

    @Override
    public Settings build()
    {
        return new SettingsImpl( ImmutableMap.copyOf( this.map ) );
    }
}
