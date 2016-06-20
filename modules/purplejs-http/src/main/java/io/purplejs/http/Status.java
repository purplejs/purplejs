package io.purplejs.http;

public enum Status
    implements StatusType
{
    OK( 200, "OK" ),
    BAD_REQUEST( 400, "Bad Request" ),
    METHOD_NOT_ALLOWED( 405, "Method Not Allowed" ),
    INTERNAL_SERVER_ERROR( 500, "Internal Server Error" );

    private final int code;

    private final String reasonPhrase;

    Status( final int code, final String reasonPhrase )
    {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public int getCode()
    {
        return this.code;
    }

    @Override
    public String getReasonPhrase()
    {
        return this.reasonPhrase;
    }

    public static Status from( final int value )
    {
        for ( final Status status : Status.values() )
        {
            if ( status.code == value )
            {
                return status;
            }
        }

        return null;
    }
}
