package io.purplejs.boot.internal.config

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class ConfigBuilderTest
    extends Specification
{
    @Rule
    TemporaryFolder temporaryFolder = new TemporaryFolder();

    private ConfigBuilder builder;

    def setup()
    {
        this.builder = new ConfigBuilder();
    }

    def "build config"()
    {
        when:
        def settings = this.builder.build();

        then:
        settings != null;
    }

    def "load from file"()
    {
        setup:
        def file = this.temporaryFolder.newFile();
        file << '''
            a = 1
            b = 2
        '''

        when:
        this.builder.load( file );
        def settings = this.builder.build();

        then:
        settings != null;
        settings.get( 'a' ).isPresent()
        settings.get( 'b' ).isPresent()
    }

    def "load from file - no such file"()
    {
        when:
        this.builder.load( this.temporaryFolder.root );
        def settings = this.builder.build();

        then:
        settings != null;
    }
}
