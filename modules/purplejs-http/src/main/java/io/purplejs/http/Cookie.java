package io.purplejs.http;

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
