package io.purplejs.core;

import org.junit.Test;

import io.purplejs.core.RunMode;

import static org.junit.Assert.*;

public class RunModeTest
{
    @Test
    public void values()
    {
        final RunMode[] values = RunMode.values();
        assertEquals( 3, values.length );
    }

    @Test
    public void valueOf()
    {
        assertEquals( RunMode.DEV, RunMode.valueOf( "DEV" ) );
        assertEquals( RunMode.PROD, RunMode.valueOf( "PROD" ) );
        assertEquals( RunMode.TEST, RunMode.valueOf( "TEST" ) );
    }

    @Test
    public void getCurrent()
    {
        RunMode.PROD.set();

        final RunMode mode = RunMode.get();
        assertEquals( RunMode.PROD, mode );
    }

    @Test
    public void isDevMode()
    {
        RunMode.PROD.set();
        assertFalse( RunMode.isDevMode() );

        RunMode.DEV.set();
        assertTrue( RunMode.isDevMode() );
    }

    @Test
    public void isTestMode()
    {
        RunMode.PROD.set();
        assertFalse( RunMode.isTestMode() );

        RunMode.TEST.set();
        assertTrue( RunMode.isTestMode() );
    }

    @Test
    public void isProdMode()
    {
        RunMode.DEV.set();
        assertFalse( RunMode.isProdMode() );

        RunMode.PROD.set();
        assertTrue( RunMode.isProdMode() );
    }

    @Test
    public void setFromProp()
    {
        System.setProperty( "io.purplejs.runMode", "d" );
        RunMode.init();
        assertEquals( RunMode.PROD, RunMode.get() );

        System.setProperty( "io.purplejs.runMode", "dev" );
        RunMode.init();
        assertEquals( RunMode.DEV, RunMode.get() );
    }
}
