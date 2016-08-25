package io.purplejs.http.internal.websocket

import io.purplejs.http.websocket.WebSocketSession
import spock.lang.Specification

import java.util.stream.Collectors

class WebSocketRegistryImplTest
    extends Specification
{
    def WebSocketRegistryImpl registry;

    def setup()
    {
        this.registry = new WebSocketRegistryImpl();
    }

    private WebSocketSession mockSession( final String id, final String group )
    {
        def session = Mock( WebSocketSession.class );
        session.getId() >> id;
        session.getGroup() >> group;
        return session;
    }

    def "test getSession"()
    {
        setup:
        def session = mockSession( '1', 'group' );
        this.registry.add( session );

        when:
        def result = this.registry.getSession( 'unknown' );

        then:
        result == null;

        when:
        result = this.registry.getSession( '1' );

        then:
        result == session;
    }

    def "test getByGroup"()
    {
        setup:
        def session1 = mockSession( '1', 'group1' );
        def session2 = mockSession( '2', 'group1' );
        def session3 = mockSession( '3', 'group2' );

        this.registry.add( session1 );
        this.registry.add( session2 );
        this.registry.add( session3 );

        when:
        def result = this.registry.getByGroup( 'unknown' );

        then:
        result.count() == 0;

        when:
        result = this.registry.getByGroup( 'group1' );
        def list = result.collect( Collectors.toList() );

        then:
        list.size() == 2;
        list.contains( session1 );
        list.contains( session2 );

        when:
        this.registry.remove( session2 );
        result = this.registry.getByGroup( 'group1' );
        list = result.collect( Collectors.toList() );

        then:
        list.size() == 1;
        list.contains( session1 );
    }
}
