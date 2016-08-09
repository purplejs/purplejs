package io.purplejs.boot.impl;

import java.io.IOException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.io.Resources;
import com.google.common.net.MediaType;

public final class AssetFilter
    implements Filter
{
    private final static String ASSETS_ROOT = "/assets";

    private ServletContext context;

    @Override
    public void init( final FilterConfig config )
        throws ServletException
    {
        this.context = config.getServletContext();
    }

    @Override
    public void doFilter( final ServletRequest req, final ServletResponse res, final FilterChain chain )
        throws IOException, ServletException
    {
        doFilter( (HttpServletRequest) req, (HttpServletResponse) res, chain );
    }

    private void doFilter( final HttpServletRequest req, final HttpServletResponse res, final FilterChain chain )
        throws IOException, ServletException
    {
        final URL url = findResource( req );
        if ( url == null )
        {
            chain.doFilter( req, res );
            return;
        }

        serveResource( res, url );
    }

    @Override
    public void destroy()
    {
    }

    private URL findResource( final HttpServletRequest req )
    {
        final String path = req.getPathInfo();
        return getClass().getResource( ASSETS_ROOT + path );
    }

    private void serveResource( final HttpServletResponse res, final URL url )
        throws IOException
    {
        final String mimeType = findMimeType( url.getPath() );
        res.setContentType( mimeType );

        final byte[] bytes = Resources.toByteArray( url );
        res.setContentLength( bytes.length );

        res.getOutputStream().write( bytes );
    }

    private String findMimeType( final String path )
    {
        final String mimeType = this.context.getMimeType( path );
        if ( mimeType != null )
        {
            return mimeType;
        }

        return MediaType.OCTET_STREAM.withoutParameters().toString();
    }
}
