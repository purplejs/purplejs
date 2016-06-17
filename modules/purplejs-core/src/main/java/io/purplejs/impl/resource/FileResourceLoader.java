package io.purplejs.impl.resource;

import java.io.File;

import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;

final class FileResourceLoader
    implements ResourceLoader
{
    private final File dir;

    public FileResourceLoader( final File dir )
    {
        this.dir = dir;
    }

    @Override
    public Resource loadOrNull( final ResourcePath path )
    {
        final File file = new File( this.dir, path.getPath() );
        if ( file.isFile() && file.exists() )
        {
            return new FileResource( path, file );
        }

        return null;
    }
}
