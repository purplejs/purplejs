package io.purplejs.http.internal.lib;

import java.util.function.Supplier;

import com.google.common.net.MediaType;

import io.purplejs.core.json.JsonSerializable;
import io.purplejs.http.Request;

public final class HttpLibHelper
{
    private Supplier<Request> currentRequest;

    public void setRequestProvider( final Supplier<Request> provider )
    {
        this.currentRequest = provider;
    }

    public Request getRequest()
    {
        return this.currentRequest.get();
    }

    public boolean isJsonBody()
    {
        final MediaType type = getRequest().getContentType();
        return type != null && type.withoutParameters().is( MediaType.JSON_UTF_8.withoutParameters() );
    }

    public boolean isMultipart()
    {
        return getRequest().getMultipart() != null;
    }

    private MultipartLibHelper getMultipartHelper()
    {
        return new MultipartLibHelper( getRequest().getMultipart() );
    }

    public JsonSerializable getMultipartForm()
    {
        return getMultipartHelper().getFormAsJson();
    }

    public JsonSerializable getMultipartItem( final String name, final int index )
    {
        return getMultipartHelper().getItemAsJson( name, index );
    }
}
