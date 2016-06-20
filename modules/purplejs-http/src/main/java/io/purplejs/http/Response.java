package io.purplejs.http;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.value.ScriptValue;

public interface Response
{
    Status getStatus();

    // TODO: Check if we can use OK IO or some other means of streaming the body
    ByteSource getBody();

    MediaType getContentType();

    Headers getHeaders();

    Attributes getAttributes();

    ScriptValue getValue();
}
