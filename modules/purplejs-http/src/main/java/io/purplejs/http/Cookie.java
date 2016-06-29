package io.purplejs.http;

// See io.netty.handler.codec.http.cookie.Cookie
public interface Cookie
{
    String getName();

    String getValue();

    String getDomain();

    String getComment();

    boolean isHttpOnly();

    boolean isSecure();

    int getMaxAge();

    String getPath();
}
