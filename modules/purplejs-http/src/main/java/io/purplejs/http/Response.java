package io.purplejs.http;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.value.ScriptValue;

public interface Response
{
    Status getStatus();

    ByteSource getBody();

    MediaType getContentType();

    Headers getHeaders();

    ScriptValue getValue();
}
