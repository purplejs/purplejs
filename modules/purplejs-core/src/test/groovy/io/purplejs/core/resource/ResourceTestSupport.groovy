package io.purplejs.core.resource

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

abstract class ResourceTestSupport
    extends Specification
{
    @Rule
    def TemporaryFolder temporaryFolder = new TemporaryFolder();

    protected final File newFolder( final String... path )
    {
        return this.temporaryFolder.newFolder( path );
    }

    protected final File writeFile( final String name, final String text )
    {
        return writeFile( null, name, text );
    }

    protected final File writeFile( final File folder, final String name, final String text )
    {
        def current = folder != null ? folder : this.temporaryFolder.root;
        return writeFile( new File( current, name ), text );
    }

    protected static final File writeFile( final File file, final String text )
    {
        file << text;
        return file;
    }
}
