package io.purplejs.http;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.common.base.Splitter;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.net.MediaType;

import static com.google.common.net.HttpHeaders.ACCEPT;

// TODO: Support multiple headers with same key!
public final class Headers
    extends ForwardingMap<String, String>
{
    private final Map<String, String> map;

    public Headers()
    {
        this.map = Maps.newHashMap();
    }

    @Override
    protected Map<String, String> delegate()
    {
        return this.map;
    }

    public List<MediaType> getAccept()
    {
        return parseList( ACCEPT, MediaType::parse );
    }

    private <T> List<T> parseList( final String key, final Function<String, T> parser )
    {
        final String value = getOrDefault( key, "" );
        final Iterable<String> values = Splitter.on( ',' ).trimResults().omitEmptyStrings().split( value );

        final List<T> list = Lists.newArrayList();
        for ( final String item : values )
        {
            list.add( parser.apply( item ) );
        }

        return list;
    }
}
