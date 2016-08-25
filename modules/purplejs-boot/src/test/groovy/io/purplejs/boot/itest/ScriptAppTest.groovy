package io.purplejs.boot.itest

import okhttp3.ws.WebSocketCall
import okhttp3.ws.WebSocketListener

class ScriptAppTest
    extends AbstractBootITest
{
    def "simple get"()
    {
        when:
        def res = execute( newRequest( '/' ) );

        then:
        res != null;
        res.body().contentType().toString() == 'text/plain;charset=utf-8';
        res.body().string().trim() == 'Hello Script!';
    }

    def "test websocket"()
    {
        setup:
        def call = WebSocketCall.create( this.client, newRequest( '/' ).build() );
        def listener = Mock( WebSocketListener.class );

        when:
        call.enqueue( listener );
        Thread.sleep( 1000L );
        // call.cancel();

        then: true;
    }
}
