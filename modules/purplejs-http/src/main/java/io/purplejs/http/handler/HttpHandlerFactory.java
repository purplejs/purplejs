package io.purplejs.http.handler;

import io.purplejs.core.resource.ResourcePath;

public interface HttpHandlerFactory
{
    HttpHandler newHandler( ResourcePath resource );
}
