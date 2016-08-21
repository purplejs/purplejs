package io.purplejs.servlet.internal

import spock.lang.Specification

import javax.servlet.http.Part

class MultipartItemImplTest
    extends Specification
{
    def Part part;

    def MultipartItemImpl item;

    def setup()
    {
        this.part = Mock( Part.class );
        this.item = new MultipartItemImpl( this.part );
    }

    def "getName"()
    {
        setup:
        this.part.name >> 'item';

        when:
        def value = this.item.name;

        then:
        value == 'item';
    }

    def "getFileName"()
    {
        setup:
        this.part.submittedFileName >> 'file.txt';

        when:
        def value = this.item.fileName;

        then:
        value == 'file.txt';
    }

    def "getSize"()
    {
        setup:
        this.part.size >> 10L;

        when:
        def value = this.item.size;

        then:
        value == 10L;
    }

    def "getContentType"()
    {
        setup:
        this.part.contentType >> 'text/plain';

        when:
        def value = this.item.contentType;

        then:
        value != null;
        value.toString() == 'text/plain';
    }

    def "getContentType is null"()
    {
        when:
        def value = this.item.contentType;

        then:
        value == null;
    }

    def "getBytes"()
    {
        setup:
        def input = new ByteArrayInputStream( new byte[2] );
        this.part.inputStream >> input;
        this.part.size >> 2L;

        when:
        def value = this.item.bytes;

        then:
        value != null;
        value.size() == 2L;
    }

    def "getAsString"()
    {
        setup:
        def input = new ByteArrayInputStream( 'hello'.bytes );
        this.part.inputStream >> input;
        this.part.size >> 2L;

        when:
        def value = this.item.asString;

        then:
        value != null;
        value == 'hello';
    }

    def "getAsString error"()
    {
        setup:
        this.part.inputStream >> { throw new IOException() };

        when:
        this.item.asString;

        then:
        thrown IOException;
    }

    def "test delete"()
    {
        when:
        this.item.delete();

        then:
        1 * this.part.delete();
    }

    def "test delete error"()
    {
        setup:
        this.part.delete() >> { throw new IOException() };

        when:
        this.item.delete();

        then:
        1 * this.part.delete();
    }
}
