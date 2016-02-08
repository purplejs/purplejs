package org.purplejs.impl.resource;

import java.io.File;

import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourceNotFoundException;
import org.purplejs.resource.ResourcePath;
import org.purplejs.resource.ResourceResolver;

final class FileResourceResolver
    implements ResourceResolver
{
    private final File dir;

    public FileResourceResolver( final File dir )
    {
        this.dir = dir;
    }

    @Override
    public Resource resolve( final ResourcePath path )
    {
        final File file = new File( this.dir, path.getPath() );
        if ( file.isFile() && file.exists() )
        {
            return new FileResource( path, file );
        }

        throw new ResourceNotFoundException( path );
    }
}
