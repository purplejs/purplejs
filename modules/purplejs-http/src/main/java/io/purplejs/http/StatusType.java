package io.purplejs.http;

public interface StatusType
{
    int getCode();

    String getReasonPhrase();

    default boolean isInformational()
    {
        return ( getCode() / 100 ) == 1;
    }

    default boolean isSuccessful()
    {
        return ( getCode() / 100 ) == 2;
    }

    default boolean isRedirection()
    {
        return ( getCode() / 100 ) == 3;
    }

    default boolean isClientError()
    {
        return ( getCode() / 100 ) == 4;
    }

    default boolean isServerError()
    {
        return ( getCode() / 100 ) == 5;
    }
}
