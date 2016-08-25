package io.purplejs.http.itest.lib

import com.google.common.io.ByteSource
import com.google.common.net.MediaType
import io.purplejs.http.Status
import io.purplejs.http.itest.AbstractIntegrationTest
import io.purplejs.http.multipart.MultipartForm
import io.purplejs.http.multipart.MultipartItem

class HttpLibScript_multipartTest
    extends AbstractIntegrationTest
{
    def "test isMultipart"()
    {
        setup:
        this.requestBuilder.method( 'POST' );
        mockForm();

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                t.assertEquals(true, http.isMultipart());
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
        res.status == Status.OK;
    }

    def "test isMultipart, no multipart"()
    {
        setup:
        this.requestBuilder.method( 'POST' );

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                t.assertEquals(false, http.isMultipart());
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
        res.status == Status.OK;
    }

    def "test getMultipartForm, no multipart"()
    {
        setup:
        this.requestBuilder.method( 'POST' );

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                return {
                    body: JSON.stringify(http.getMultipartForm())
                };
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
        res.status == Status.OK;
        toStringBody( res ) == '{}';
    }

    def "test getMultipartForm"()
    {
        setup:
        this.requestBuilder.method( 'POST' );
        mockForm( mockItem( 'item1', 'test.txt', 'hello', MediaType.PLAIN_TEXT_UTF_8 ), mockItem( 'item2', 'other.txt', 'world', null ) );

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                return {
                    body: JSON.stringify(http.getMultipartForm())
                };
            };
        ''' );

        when:
        def res = serve();
        def text = toStringBody( res );

        then:
        res != null;
        res.status == Status.OK;
        prettifyJson( text ) == prettifyJson( '''
            {
              "item1": {
                "name": "item1",
                "fileName": "test.txt",
                "contentType": "text/plain",
                "size": 5
              },
              "item2": {
                "name": "item2",
                "fileName": "other.txt",
                "size": 5
              }
            }
        ''' );
    }

    def "test getMultipartForm, list of items"()
    {
        setup:
        this.requestBuilder.method( 'POST' );
        mockForm( mockItem( 'item1', 'test.txt', 'hello', MediaType.PLAIN_TEXT_UTF_8 ), mockItem( 'item1', 'other.txt', 'world', null ) );

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                return {
                    body: JSON.stringify(http.getMultipartForm())
                };
            };
        ''' );

        when:
        def res = serve();
        def text = toStringBody( res );

        then:
        res != null;
        res.status == Status.OK;
        prettifyJson( text ) == prettifyJson( '''
            {
              "item1": [
                {
                  "name": "item1",
                  "fileName": "test.txt",
                  "contentType": "text/plain",
                  "size": 5
                },
                {
                  "name": "item1",
                  "fileName": "other.txt",
                  "size": 5
                }
              ]
            }
        ''' );
    }

    def "test getMultipartItem"()
    {
        setup:
        this.requestBuilder.method( 'POST' );
        mockForm( mockItem( 'item1', 'test.txt', 'hello', null ) );

        script( '''
            var http = require('/lib/http');

            exports.post = function() {
                t.assertEquals(null, http.getMultipartItem('unknown'));
                t.assertEquals(null, http.getMultipartItem('item1', 2));

                var item = http.getMultipartItem('item1', 0);
                t.assertEquals(5, item.stream.size());

                return {
                    body: JSON.stringify(item)
                };
            };
        ''' );

        when:
        def res = serve();
        def text = toStringBody( res );

        then:
        res != null;
        res.status == Status.OK;
        prettifyJson( text ) == prettifyJson( '''
            {
              "name": "item1",
              "fileName": "test.txt",
              "size": 5
            }
        ''' );
    }

    private void mockForm( final MultipartItem... items )
    {
        final MultipartForm form = new MultipartForm();
        for ( final MultipartItem item : items )
        {
            form.add( item );
        }

        this.requestBuilder.multipart( form );
    }

    private MultipartItem mockItem( final String name, final String fileName, final String content, final MediaType type )
    {
        final MultipartItem item = Mock( MultipartItem.class );
        item.getName() >> name;
        item.getFileName() >> fileName;
        item.getSize() >> content.length();
        item.getAsString() >> content;
        item.getBytes() >> ByteSource.wrap( content.bytes );
        item.getContentType() >> type;
        return item;
    }
}
