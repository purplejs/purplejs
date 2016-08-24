package io.purplejs.http.internal.lib;

import java.util.List;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.io.ByteSource;

import io.purplejs.core.json.JsonGenerator;
import io.purplejs.core.json.JsonSerializable;
import io.purplejs.http.MultipartForm;
import io.purplejs.http.MultipartItem;

final class MultipartLibHelper
{
    private final MultipartForm form;

    MultipartLibHelper( final MultipartForm form )
    {
        this.form = form;
    }

    JsonSerializable getFormAsJson()
    {
        return gen -> serializeForm( gen, getItemsAsMap() );
    }

    JsonSerializable getItemAsJson( final String name, final int index )
    {
        final MultipartItem item = findItem( name, index );
        return itemToJson( item );
    }

    ByteSource getItemStream( final String name, final int index )
    {
        final MultipartItem item = findItem( name, index );
        return item != null ? item.getBytes() : ByteSource.empty();
    }

    private MultipartItem findItem( final String name, final int index )
    {
        final List<MultipartItem> items = getItemsAsMap().get( name );
        if ( !items.isEmpty() && ( items.size() > index ) )
        {
            return items.get( index );
        }

        return null;
    }

    private ListMultimap<String, MultipartItem> getItemsAsMap()
    {
        final ListMultimap<String, MultipartItem> items = LinkedListMultimap.create();
        if ( form == null )
        {
            return items;
        }

        for ( final MultipartItem item : form )
        {
            items.put( item.getName(), item );
        }

        return items;
    }

    private JsonSerializable itemToJson( final MultipartItem item )
    {
        if ( item == null )
        {
            return emptyJson();
        }

        return gen ->
        {
            gen.map();
            serializeItem( gen, item );
            gen.end();
        };
    }

    private JsonSerializable emptyJson()
    {
        return gen ->
        {
            gen.map();
            gen.end();
        };
    }

    private void serializeForm( final JsonGenerator gen, final ListMultimap<String, MultipartItem> form )
    {
        gen.map();

        for ( final String name : form.keySet() )
        {
            final List<MultipartItem> values = form.get( name );
            if ( values.size() == 1 )
            {
                gen.map( name );
                serializeItem( gen, values.get( 0 ) );
                gen.end();
            }
            else
            {
                gen.array( name );
                values.forEach( ( item ) ->
                                {
                                    gen.map();
                                    serializeItem( gen, item );
                                    gen.end();
                                } );
                gen.end();
            }
        }

        gen.end();
    }

    private void serializeItem( final JsonGenerator gen, final MultipartItem item )
    {
        gen.value( "name", item.getName() );
        gen.value( "fileName", item.getFileName() );
        gen.value( "contentType", item.getContentType() );
        gen.value( "size", item.getSize() );
        gen.value( "stream", item.getBytes() );
    }
}
