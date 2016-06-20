package io.purplejs.http;

import java.util.List;

import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;

import io.purplejs.value.ScriptValue;

public interface Response
{
    Status getStatus();

    ByteSource getBody();

    MediaType getContentType();

    Headers getHeaders();

    List<Cookie> getCookies();

    ScriptValue getValue();
}
