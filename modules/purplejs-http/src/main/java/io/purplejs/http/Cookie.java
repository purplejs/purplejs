package io.purplejs.http;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public final class Cookie
{
    private String name;

    private String value;

    private String domain;

    private String path;

    private String comment;

    private boolean secure = false;

    private boolean httpOnly = false;

    private int maxAge = -1;

    public Cookie( final String name )
    {
        Preconditions.checkArgument( !Strings.isNullOrEmpty( name ), "name is required" );
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( final String value )
    {
        this.value = value;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain( final String domain )
    {
        this.domain = domain;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath( final String path )
    {
        this.path = path;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment( final String comment )
    {
        this.comment = comment;
    }

    public boolean isSecure()
    {
        return secure;
    }

    public void setSecure( final boolean secure )
    {
        this.secure = secure;
    }

    public boolean isHttpOnly()
    {
        return httpOnly;
    }

    public void setHttpOnly( final boolean httpOnly )
    {
        this.httpOnly = httpOnly;
    }

    public int getMaxAge()
    {
        return maxAge;
    }

    public void setMaxAge( final int maxAge )
    {
        this.maxAge = maxAge;
    }
}
