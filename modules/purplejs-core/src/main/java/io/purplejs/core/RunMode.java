package io.purplejs.core;

/**
 * Run-mode is used to indicate which environment the engine is running in. The engine
 * can be configured differently depending on the run-mode.
 *
 * The run-mode can also be set using a system property (io.purplejs.runMode).
 */
public enum RunMode
{
    /**
     * Production mode. This is the default mode.
     */
    PROD,

    /**
     * Development mode. When using this the script-cache will be refreshed if scripts are changed.
     */
    DEV;

    private static RunMode MODE;

    static
    {
        init();
    }

    /**
     * Returns the current run-mode.
     *
     * @return current run-mode.
     */
    public static RunMode get()
    {
        return MODE;
    }

    /**
     * Initializes the run-mode based on system-properties.
     */
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

    /**
     * Set this mode as the current run-mode.
     */
    public void set()
    {
        MODE = this;
    }
}
