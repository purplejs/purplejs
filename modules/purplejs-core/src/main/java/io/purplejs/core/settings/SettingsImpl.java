package io.purplejs.core.settings;

import java.util.Map;
import java.util.Optional;

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
    public Optional<String> get( final String key )
    {
        return Optional.ofNullable( this.map.get( key ) );
    }

    @Override
    public <T> Optional<T> get( final Class<T> type, final String key )
    {
        final Optional<String> value = get( key );
        return value.flatMap( str -> convert( type, str ) );
    }

    private <T> Optional<T> convert( final Class<T> type, final String value )
    {
        try
        {
            return Optional.ofNullable( ConvertHelper.INSTANCE.convert( value, type ) );
        }
        catch ( final Exception e )
        {
            return Optional.empty();
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
