package io.purplejs.impl.resource;

import java.io.File;

import org.junit.rules.TemporaryFolder;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ResourceTestFixture
{
    private final TemporaryFolder temporaryFolder;

    public ResourceTestFixture( final TemporaryFolder temporaryFolder )
    {
        this.temporaryFolder = temporaryFolder;
    }

    public File getRootDir()
    {
        return this.temporaryFolder.getRoot();
    }

    public void createFiles()
        throws Exception
    {
        final File dir1 = createFolder( "a" );
        createFile( dir1, "b.txt", "hello" );
    }

    private File createFolder( final String... elements )
        throws Exception
    {
        return this.temporaryFolder.newFolder( elements );
    }

    private File createFile( final File dir, final String name, final String content )
        throws Exception
    {
        final File file = new File( dir, name );
        Files.write( content, file, Charsets.UTF_8 );
        return file;
    }

    public File createFile( final String name, final String content )
        throws Exception
    {
        return createFile( getRootDir(), name, content );
    }
}
