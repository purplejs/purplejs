package io.purplejs.http;

public enum Status
    implements StatusType
{
    OK( 200, "OK" ),
    BAD_REQUEST( 400, "Bad Request" );

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
}
