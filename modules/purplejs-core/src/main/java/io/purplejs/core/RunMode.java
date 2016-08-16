package io.purplejs.core;

public enum RunMode
{
    PROD,
    TEST,
    DEV;

    private static RunMode MODE;

    static
    {
        init();
    }

    public static RunMode get()
    {
        return MODE;
    }

    public static void init()
    {
        try
        {
            final String value = System.getProperty( "io.purplejs.runMode" );
            valueOf( value.toUpperCase() ).set();
        }
        catch ( final Exception e )
        {
            PROD.set();
        }
    }

    public static boolean isDevMode()
    {
        return get() == DEV;
    }

    public static boolean isTestMode()
    {
        return get() == TEST;
    }

    public static boolean isProdMode()
    {
        return get() == PROD;
    }

    public void set()
    {
        MODE = this;
    }
}
