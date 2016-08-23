package io.purplejs.core.settings;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import io.purplejs.core.internal.util.ConvertHelper;

final class SettingsImpl
    implements Settings
{
    private final ImmutableMap<String, String> map;

    SettingsImpl( final ImmutableMap<String, String> map )
    {
        this.map = map;
    }

    @Override
    public String get( final String key )
    {
        return this.map.get( key );
    }

    @Override
    public String get( final String key, final String defValue )
    {
        return this.map.getOrDefault( key, defValue );
    }

    @Override
    public <T> T get( final Class<T> type, final String key )
    {
        return get( type, key, null );
    }

    @Override
    public <T> T get( final Class<T> type, final String key, final T defValue )
    {
        final String value = get( key );
        if ( value == null )
        {
            return defValue;
        }

        try
        {
            return ConvertHelper.INSTANCE.convert( value, type );
        }
        catch ( final Exception e )
        {
            return defValue;
        }
    }

    @Override
    public Settings getAsSettings( final String key )
    {
        final String prefix = key + ".";
        final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        this.map.forEach( ( oldKey, value ) ->
                          {
                              if ( oldKey.startsWith( prefix ) )
                              {
                                  builder.put( oldKey.substring( prefix.length() ), value );
                              }
                          } );

        return new SettingsImpl( builder.build() );
    }

    @Override
    public Map<String, String> asMap()
    {
        return this.map;
    }
}
