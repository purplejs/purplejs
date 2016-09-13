package io.purplejs.boot.internal;

import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public final class BannerPrinter
{
    private final static String PATH = "/banner.txt";

    private String loadBanner()
        throws Exception
    {
        final URL url = getClass().getResource( PATH );
        return Resources.toString( url, Charsets.UTF_8 );
    }

    public void printBanner()
        throws Exception
    {
        System.out.println( loadBanner() );
    }
}
