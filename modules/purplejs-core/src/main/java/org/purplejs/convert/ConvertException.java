package org.purplejs.convert;

public final class ConvertException
    extends RuntimeException
{
    public ConvertException( final String message )
    {
        super( message );
    }

    public ConvertException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public static ConvertException noSuchConverter( final Class<?> source, final Class<?> target )
    {
        return new ConvertException( String.format( "No such converter for %s -> %s", source.getName(), target.getName() ) );
    }

    public static ConvertException convertFailure( final Class<?> source, final Class<?> target, final Throwable cause )
    {
        return new ConvertException( String.format( "Failed to convert %s -> %s", source.getName(), target.getName() ), cause );
    }
}
