package io.purplejs;

public enum RunMode
{
    PROD,
    TEST,
    DEV;

    private static RunMode MODE = RunMode.PROD;

    public static RunMode get()
    {
        return MODE;
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
