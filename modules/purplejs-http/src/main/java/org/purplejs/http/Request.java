package org.purplejs.http;

public interface Request
{
    String getMethod();

    Parameters getParameters();

    Headers getHeaders();

    Attributes getAttributes();

    Object getRaw();
}
