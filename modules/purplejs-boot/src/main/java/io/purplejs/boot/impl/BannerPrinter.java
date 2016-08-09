package io.purplejs.boot.impl;

import java.io.IOException;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public final class BannerPrinter
{
    private final static String PATH = "/banner.txt";

    private final String banner;

    public BannerPrinter()
    {
        this.banner = loadBanner();
    }

    private String loadBanner()
    {
        final URL url = getClass().getResource( PATH );
        if ( url == null )
        {
            return null;
        }

        try
        {
            return Resources.toString( url, Charsets.UTF_8 );
        }
        catch ( final IOException e )
        {
            return null;
        }
    }

    public void printBanner()
    {
        if ( this.banner != null )
        {
            System.out.println( this.banner );
        }
    }
}
