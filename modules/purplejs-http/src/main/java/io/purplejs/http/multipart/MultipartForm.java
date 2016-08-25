package io.purplejs.http.multipart;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;

public final class MultipartForm
    implements Iterable<MultipartItem>
{
    private final ListMultimap<String, MultipartItem> map;

    public MultipartForm()
    {
        this.map = LinkedListMultimap.create();
    }

    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }

    public int getSize()
    {
        return this.map.size();
    }

    public Set<String> getNames()
    {
        return this.map.keySet();
    }

    public MultipartItem get( final String name )
    {
        return get( name, 0 );
    }

    public List<MultipartItem> getAll( final String name )
    {
        return this.map.get( name );
    }

    public MultipartItem get( final String name, final int index )
    {
        final List<MultipartItem> list = getAll( name );
        if ( !list.isEmpty() && ( list.size() > index ) )
        {
            return list.get( index );
        }

        return null;
    }

    public void add( final MultipartItem item )
    {
        this.map.put( item.getName(), item );
    }

    @Override
    public Iterator<MultipartItem> iterator()
    {
        return this.map.values().iterator();
    }
}
