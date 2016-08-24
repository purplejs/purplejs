package io.purplejs.core.internal.resource;

import java.io.File;

import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;

final class FileResourceLoader
    implements ResourceLoader
{
    private final File dir;

    FileResourceLoader( final File dir )
    {
        this.dir = dir;
    }

    @Override
    public Resource loadOrNull( final ResourcePath path )
    {
        final File file = new File( this.dir, path.getPath() );
        if ( !file.isFile() )
        {
            return null;
        }

        return new FileResource( path, file );
    }
}
