package io.purplejs.http.handler;

import io.purplejs.resource.ResourcePath;

public interface HttpHandlerFactory
{
    HttpHandler newHandler( ResourcePath resource );
}
