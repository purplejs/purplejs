package io.purplejs.http.internal.lib

import com.google.common.collect.Lists
import com.google.common.io.ByteSource
import io.purplejs.http.websocket.WebSocketManager
import io.purplejs.http.websocket.WebSocketSession
import spock.lang.Specification

class WebSocketLibHelperTest
    extends Specification
{
    def WebSocketLibHelper helper;

    def WebSocketManager manager;

    def setup()
    {
        this.manager = Mock( WebSocketManager.class );
        this.helper = new WebSocketLibHelper();
        this.helper.setManager( this.manager );
    }

    def "sendMessage no socket"()
    {
        when:
        this.helper.sendMessage( '1', 'text message' );

        then:
        true;
    }

    def "sendMessage with null"()
    {
        setup:
        def session = addSession( '1' );

        when:
        this.helper.sendMessage( '1', null );

        then:
        0 * session.send( _ as String );
    }

    def "sendMessage as text"()
    {
        setup:
        def session = addSession( '1' );

        when:
        this.helper.sendMessage( '1', 'text message' );

        then:
        1 * session.send( 'text message' );
    }

    def "sendMessage as binary"()
    {
        setup:
        def session = addSession( '1' );
        def binary = ByteSource.wrap( 'hello'.bytes );

        when:
        this.helper.sendMessage( '1', binary );

        then:
        1 * session.send( binary );
    }

    def "sendMessageToGroup as text"()
    {
        setup:
        def session1 = addSession( '1' );
        def session2 = addSession( '2' );
        addToGroup( 'group', session1, session2 );

        when:
        this.helper.sendMessageToGroup( 'group', 'text message', null );

        then:
        1 * session1.send( 'text message' );
        1 * session2.send( 'text message' );
    }

    def "sendMessageToGroup as binary"()
    {
        setup:
        def session1 = addSession( '1' );
        def session2 = addSession( '2' );
        addToGroup( 'group', session1, session2 );

        def binary = ByteSource.wrap( 'hello'.bytes );

        when:
        this.helper.sendMessageToGroup( 'group', binary, null );

        then:
        1 * session1.send( binary );
        1 * session2.send( binary );
    }

    def "sendMessageToGroup except id"()
    {
        setup:
        def session1 = addSession( '1' );
        def session2 = addSession( '2' );
        addToGroup( 'group', session1, session2 );

        when:
        this.helper.sendMessageToGroup( 'group', 'text message', '2' );

        then:
        1 * session1.send( 'text message' );
        0 * session2.send( 'text message' );
    }

    private WebSocketSession addSession( final String id )
    {
        final WebSocketSession session = Mock( WebSocketSession.class );
        session.getId() >> id;
        this.manager.getSession( id ) >> session;
        return session;
    }

    private void addToGroup( final String group, final WebSocketSession... sessions )
    {
        this.manager.getByGroup( group ) >> Lists.newArrayList( sessions ).stream();
    }
}
