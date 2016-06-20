package io.purplejs.http.impl.response;

import io.purplejs.http.Cookie;

final class CookieImpl
    implements Cookie
{
    String name;

    String value;

    String domain;

    String path;

    String comment;

    boolean secure;

    boolean httpOnly;

    long maxAge;

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getValue()
    {
        return this.value;
    }

    @Override
    public String getDomain()
    {
        return this.domain;
    }

    @Override
    public String getComment()
    {
        return this.comment;
    }

    @Override
    public boolean isHttpOnly()
    {
        return this.httpOnly;
    }

    @Override
    public boolean isSecure()
    {
        return this.secure;
    }

    @Override
    public long getMaxAge()
    {
        return this.maxAge;
    }

    @Override
    public String getPath()
    {
        return this.path;
    }
}
