package io.purplejs.http;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.net.MediaType;

import static com.google.common.net.HttpHeaders.ACCEPT;

public final class Headers
{
    private final Map<String, String> map;

    public Headers()
    {
        this.map = Maps.newHashMap();
    }

    public Optional<String> get( final String key )
    {
        final String value = this.map.get( key );
        return Optional.ofNullable( value );
    }

    public void set( final String key, final String value )
    {
        this.map.put( key, value );
    }

    public void remove( final String key )
    {
        this.map.remove( key );
    }

    public List<MediaType> getAccept()
    {
        return parseList( ACCEPT, MediaType::parse );
    }

    private <T> List<T> parseList( final String key, final Function<String, T> parser )
    {
        final String value = get( key ).orElse( "" );
        final Iterable<String> values = Splitter.on( ',' ).trimResults().omitEmptyStrings().split( value );

        final List<T> list = Lists.newArrayList();
        for ( final String item : values )
        {
            list.add( parser.apply( item ) );
        }

        return list;
    }

    public Map<String, String> asMap()
    {
        return this.map;
    }
}
