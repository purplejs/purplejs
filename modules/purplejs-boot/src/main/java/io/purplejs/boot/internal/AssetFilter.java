package io.purplejs.boot.internal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.common.net.MediaType;

import io.purplejs.core.RunMode;

public final class AssetFilter
    implements Filter
{
    private final static String ASSETS_ROOT = "/assets";

    private ServletContext context;

    private List<File> devSourceDirs;

    @Override
    public void init( final FilterConfig config )
        throws ServletException
    {
        this.context = config.getServletContext();
        this.devSourceDirs = findDevSourceDirs( config.getInitParameter( "devSourceDirs" ) );
    }

    private List<File> findDevSourceDirs( final String prop )
    {
        if ( prop == null )
        {
            return Lists.newArrayList();
        }

        final Iterable<String> items = Splitter.on( ',' ).omitEmptyStrings().trimResults().split( prop );
        return Lists.newArrayList( items ).stream().map( File::new ).collect( Collectors.toList() );
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
        if ( ( url == null ) || url.getPath().endsWith( "/" ) )
        {
            chain.doFilter( req, res );
            return;
        }

        serveResource( res, url );
    }

    @Override
    public void destroy()
    {
        // Do nothing
    }

    private URL findResource( final HttpServletRequest req )
        throws IOException
    {
        final String path = req.getPathInfo();

        final URL fromDev = findResourceInFolders( ASSETS_ROOT + path );
        return fromDev != null ? fromDev : getClass().getResource( ASSETS_ROOT + path );
    }

    private URL findResourceInFolders( final String path )
        throws IOException
    {
        if ( RunMode.get() != RunMode.DEV )
        {
            return null;
        }

        for ( final File folder : this.devSourceDirs )
        {
            final File file = new File( folder, path );
            if ( file.exists() )
            {
                return file.toURI().toURL();
            }
        }

        return null;
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
