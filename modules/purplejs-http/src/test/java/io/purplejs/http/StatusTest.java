package io.purplejs.http;

import org.junit.Test;

import static org.junit.Assert.*;

public class StatusTest
{
    @Test
    public void getCode()
    {
        assertEquals( 200, Status.OK.getCode() );
    }

    @Test
    public void getReasonPhrase()
    {
        assertEquals( "OK", Status.OK.getReasonPhrase() );
    }

    @Test
    public void from()
    {
        final Status status = Status.from( 200 );
        assertNotNull( status );
        assertSame( Status.OK, status );
    }

    @Test(expected = IllegalArgumentException.class)
    public void from_unknown()
    {
        Status.from( 600 );
    }

    @Test
    public void isInformational()
    {
        assertFalse( Status.OK.isInformational() );
        assertTrue( Status.CONTINUE.isInformational() );
    }

    @Test
    public void isSuccessful()
    {
        assertFalse( Status.CONTINUE.isSuccessful() );
        assertFalse( Status.SEE_OTHER.isSuccessful() );
        assertTrue( Status.OK.isSuccessful() );
    }

    @Test
    public void isRedirection()
    {
        assertFalse( Status.OK.isRedirection() );
        assertFalse( Status.BAD_REQUEST.isRedirection() );
        assertTrue( Status.SEE_OTHER.isRedirection() );
    }

    @Test
    public void isClientError()
    {
        assertFalse( Status.SEE_OTHER.isClientError() );
        assertFalse( Status.INTERNAL_SERVER_ERROR.isClientError() );
        assertTrue( Status.BAD_REQUEST.isClientError() );
    }

    @Test
    public void isServerError()
    {
        assertFalse( Status.BAD_REQUEST.isServerError() );
        assertTrue( Status.INTERNAL_SERVER_ERROR.isServerError() );
    }
}
