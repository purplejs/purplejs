package io.purplejs.boot.internal.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.MimeTypes;

import com.google.common.io.Resources;
import com.google.common.net.MediaType;

public final class AssetFilter
    implements Filter
{
    private final static String ASSETS_ROOT = "/assets";

    private List<File> devSourceDirs;

    private MimeTypes mimeTypes;

    private boolean devMode;

    public AssetFilter()
    {
        this.mimeTypes = new MimeTypes();
        this.devMode = false;
    }

    public void setDevMode( final boolean devMode )
    {
        this.devMode = devMode;
    }

    public void setDevSourceDirs( final List<File> devSourceDirs )
    {
        this.devSourceDirs = devSourceDirs;
    }

    @Override
    public void init( final FilterConfig config )
        throws ServletException
    {
        // Do nothing
    }

    @Override
    public void doFilter( final ServletRequest request, final ServletResponse response, final FilterChain chain )
        throws IOException, ServletException
    {
        final boolean served = serveResource( (HttpServletRequest) request, (HttpServletResponse) response );
        if ( !served )
        {
            chain.doFilter( request, response );
        }
    }

    @Override
    public void destroy()
    {
        // Do nothing
    }

    private boolean serveResource( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException, ServletException
    {
        final URL url = findResource( request );
        if ( ( url == null ) || url.getPath().endsWith( "/" ) )
        {
            return false;
        }

        serveResource( response, url );
        return true;
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
        if ( !this.devMode )
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
        final String mimeType = this.mimeTypes.getMimeByExtension( path );
        if ( mimeType != null )
        {
            return mimeType;
        }

        return MediaType.OCTET_STREAM.withoutParameters().toString();
    }
}
