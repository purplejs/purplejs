package org.purplejs.servlet.impl;

import java.util.Iterator;
import java.util.Optional;

import javax.servlet.http.Part;

import org.purplejs.http.MultipartForm;
import org.purplejs.http.MultipartItem;

import com.google.common.collect.ImmutableMap;

final class MultipartFormImpl
    implements MultipartForm
{
    private final ImmutableMap<String, MultipartItem> map;

    public MultipartFormImpl( final Iterable<Part> parts )
    {
        final ImmutableMap.Builder<String, MultipartItem> builder = ImmutableMap.builder();
        for ( final Part part : parts )
        {
            final MultipartItemImpl item = new MultipartItemImpl( part );
            builder.put( item.getName(), item );
        }

        this.map = builder.build();
    }

    @Override
    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }

    @Override
    public int getSize()
    {
        return this.map.size();
    }

    @Override
    public Optional<MultipartItem> get( final String name )
    {
        return Optional.ofNullable( this.map.get( name ) );
    }

    @Override
    public void delete()
    {
        this.map.values().forEach( this::delete );
    }

    @Override
    public Iterator<MultipartItem> iterator()
    {
        return this.map.values().iterator();
    }

    private void delete( final MultipartItem item )
    {
        ( (MultipartItemImpl) item ).delete();
    }
}
