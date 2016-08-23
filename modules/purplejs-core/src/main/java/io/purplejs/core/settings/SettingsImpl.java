package io.purplejs.core.settings;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

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
        return get( type, key, null );
    }

    @Override
    public <T> Optional<T> get( final Class<T> type, final String key, final Function<String, T> converter )
    {
        final Optional<String> value = get( key );
        return value.flatMap( str -> Optional.ofNullable( convert( type, str, converter ) ) );
    }

    @Override
    public List<String> getAsArray( final String key )
    {
        final String value = get( key ).orElse( "" );
        return Lists.newArrayList( Splitter.on( ',' ).omitEmptyStrings().trimResults().split( value ) );
    }

    @Override
    public <T> List<T> getAsArray( final Class<T> type, final String key )
    {
        return getAsArray( type, key, null );
    }

    @Override
    public <T> List<T> getAsArray( final Class<T> type, final String key, final Function<String, T> converter )
    {
        return getAsArray( key ).stream().
            map( str -> convert( type, str, converter ) ).
            filter( value -> value != null ).
            collect( Collectors.toList() );
    }

    private <T> T convert( final Class<T> type, final String value, final Function<String, T> converter )
    {
        try
        {
            if ( converter != null )
            {
                return converter.apply( value );
            }

            return ConvertHelper.INSTANCE.convert( value, type );
        }
        catch ( final Exception e )
        {
            return null;
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
