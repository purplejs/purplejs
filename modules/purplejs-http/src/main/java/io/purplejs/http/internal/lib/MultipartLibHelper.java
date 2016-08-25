package io.purplejs.http.internal.lib;

import java.util.List;

import com.google.common.net.MediaType;

import io.purplejs.core.json.JsonGenerator;
import io.purplejs.core.json.JsonSerializable;
import io.purplejs.http.multipart.MultipartForm;
import io.purplejs.http.multipart.MultipartItem;

final class MultipartLibHelper
{
    private final MultipartForm form;

    MultipartLibHelper( final MultipartForm form )
    {
        this.form = form != null ? form : new MultipartForm();
    }

    JsonSerializable getFormAsJson()
    {
        return this::serializeForm;
    }

    JsonSerializable getItemAsJson( final String name, final int index )
    {
        final MultipartItem item = this.form.get( name, index );
        return item != null ? itemToJson( item ) : null;
    }

    private JsonSerializable itemToJson( final MultipartItem item )
    {
        return gen ->
        {
            gen.map();
            serializeItem( gen, item );
            gen.end();
        };
    }

    private void serializeForm( final JsonGenerator gen )
    {
        gen.map();

        for ( final String name : this.form.getNames() )
        {
            final List<MultipartItem> values = this.form.getAll( name );
            if ( values.size() == 1 )
            {
                gen.map( name );
                serializeItem( gen, values.get( 0 ) );
                gen.end();
            }
            else
            {
                gen.array( name );
                values.forEach( ( item ) -> itemToJson( item ).serialize( gen ) );
                gen.end();
            }
        }

        gen.end();
    }

    private void serializeItem( final JsonGenerator gen, final MultipartItem item )
    {
        gen.value( "name", item.getName() );
        gen.value( "fileName", item.getFileName() );
        gen.value( "contentType", toString( item.getContentType() ) );
        gen.value( "size", item.getSize() );
        gen.value( "stream", item.getBytes() );
    }

    private String toString( final MediaType type )
    {
        return type != null ? type.withoutParameters().toString() : null;
    }
}
