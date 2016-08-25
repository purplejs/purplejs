package io.purplejs.boot.internal.request;

import javax.servlet.http.Part;

import io.purplejs.http.multipart.MultipartForm;

final class MultipartFormFactory
{
    MultipartForm create( final Iterable<Part> parts )
    {
        final MultipartForm form = new MultipartForm();
        for ( final Part part : parts )
        {
            final MultipartItemImpl item = new MultipartItemImpl( part );
            form.add( item );
        }

        return form;
    }
}
