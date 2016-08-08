package io.purplejs.http;

import org.junit.Test;

import static org.junit.Assert.*;

public class CookieTest
{
    @Test
    public void defaultValues()
    {
        final Cookie cookie = new Cookie( "test" );
        assertEquals( "test", cookie.getName() );
        assertNull( cookie.getValue() );
        assertNull( cookie.getDomain() );
        assertNull( cookie.getPath() );
        assertNull( cookie.getComment() );
        assertEquals( false, cookie.isSecure() );
        assertEquals( false, cookie.isHttpOnly() );
        assertEquals( -1, cookie.getMaxAge() );

        assertEquals( "Cookie{name=test, value=null, domain=null, path=null, comment=null, secure=false, httpOnly=false, maxAge=-1}",
                      cookie.toString() );
    }

    @Test
    public void getters_setters()
    {
        final Cookie cookie = new Cookie( "test" );
        assertEquals( "test", cookie.getName() );

        cookie.setValue( "value" );
        assertEquals( "value", cookie.getValue() );

        cookie.setDomain( "domain" );
        assertEquals( "domain", cookie.getDomain() );

        cookie.setPath( "path" );
        assertEquals( "path", cookie.getPath() );

        cookie.setComment( "comment" );
        assertEquals( "comment", cookie.getComment() );

        cookie.setSecure( true );
        assertEquals( true, cookie.isSecure() );

        cookie.setHttpOnly( true );
        assertEquals( true, cookie.isHttpOnly() );

        cookie.setMaxAge( 3600 );
        assertEquals( 3600, cookie.getMaxAge() );

        assertEquals( "Cookie{name=test, value=value, domain=domain, path=path, comment=comment, secure=true, httpOnly=true, maxAge=3600}",
                      cookie.toString() );
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_null_name()
    {
        new Cookie( null );
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_empty_name()
    {
        new Cookie( "" );
    }
}
