package io.purplejs.http.itest.lib

import io.purplejs.http.Status
import io.purplejs.http.internal.websocket.WebSocketRegistry
import io.purplejs.http.itest.AbstractHttpITest
import io.purplejs.http.websocket.WebSocketSession

class WebSocketLibScriptTest
    extends AbstractHttpITest
{
    def WebSocketRegistry webSocketRegistry;

    def setup()
    {
        this.webSocketRegistry = this.engine.getInstance( WebSocketRegistry.class );
    }

    private WebSocketSession addSession( final String id, final String group )
    {
        final WebSocketSession session = Mock( WebSocketSession.class );
        session.id >> id;
        session.group >> group;

        this.webSocketRegistry.add( session );
        return session;
    }

    def "test send"()
    {
        setup:
        def session = addSession( '1', 'somewhere' );

        script( '''
            var websocket = require('/lib/websocket');

            exports.get = function() {
                websocket.send('1', 'text');
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
        res.status == Status.OK;
        1 * session.send( 'text' );
    }

    def "test sendToGroup"()
    {
        setup:
        def session1 = addSession( '1', 'somewhere' );
        def session2 = addSession( '2', 'somewhere' );

        script( '''
            var websocket = require('/lib/websocket');

            exports.get = function() {
                websocket.sendToGroup('somewhere', 'message 1');
                websocket.sendToGroup('somewhere', 'message 2', '1');
            };
        ''' );

        when:
        def res = serve();

        then:
        res != null;
        res.status == Status.OK;
        1 * session1.send( 'message 1' );
        1 * session2.send( 'message 1' );
        1 * session2.send( 'message 2' );
    }
}
