package io.purplejs.boot.internal.websocket

import com.google.common.io.ByteSource
import io.purplejs.core.value.ScriptValue
import org.eclipse.jetty.websocket.api.RemoteEndpoint
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

import java.nio.ByteBuffer

class WebSocketSessionImplTest
    extends Specification
{
    def WebSocketSessionImpl session;

    def Session raw;

    def RemoteEndpoint endpoint;

    def setup()
    {
        this.raw = Mock( Session.class );
        this.session = new WebSocketSessionImpl();
        this.session.raw = this.raw;
        this.endpoint = Mock( RemoteEndpoint.class );
    }

    def "test id"()
    {
        when:
        def other = new WebSocketSessionImpl();

        then:
        session.id != null;
        other.id != null;
        session.id != other.id;
    }

    def "test group"()
    {
        when:
        this.session.group = 'test';

        then:
        this.session.getGroup() == 'test';
    }

    def "test attributes"()
    {
        when:
        session.attributes = Mock( ScriptValue.class );

        then:
        session.getAttributes() != null;
    }

    def "test close"()
    {
        when:
        this.session.close();
        this.session.close( 1001, 'no reason' );

        then:
        1 * this.raw.close();
        1 * this.raw.close( 1001, 'no reason' );
    }

    def "test isOpen"()
    {
        when:
        this.session.isOpen();

        then:
        1 * this.raw.isOpen();
    }

    def "test getSubProtocol"()
    {
        setup:
        def res = newServletUpgradeResponse();
        this.raw.getUpgradeResponse() >> res;

        when:
        res.acceptedSubProtocol = 'text';

        then:
        this.session.getSubProtocol() == 'text';
    }

    def "send text"()
    {
        when:
        this.session.send( 'text' );

        then: true;

        when:
        this.raw.getRemote() >> this.endpoint;
        this.session.send( 'text' );

        then:
        1 * this.endpoint.sendString( 'text' );
    }

    def "send binary"()
    {
        setup:
        def bytes = ByteSource.empty();

        when:
        this.session.send( bytes );

        then: true;

        when:
        this.raw.getRemote() >> this.endpoint;
        this.session.send( bytes );

        then:
        1 * this.endpoint.sendBytes( _ as ByteBuffer );
    }

    def "getRemote error"()
    {
        when:
        this.raw.getRemote() >> { throw new RuntimeException() };
        this.session.send( 'text' );

        then: true;
    }

    def "test getRaw"()
    {
        when:
        def other = this.session.getRaw();

        then:
        other == this.raw;
    }

    def "send text - error"()
    {
        setup:
        this.raw.getRemote() >> this.endpoint;
        this.endpoint.sendString( _ as String ) >> { throw new IOException( "Some error" ); }

        when:
        this.session.send( 'text' );

        then: true;
    }

    def "send binary - error"()
    {
        setup:
        this.raw.getRemote() >> this.endpoint;
        this.endpoint.sendBytes( _ as ByteBuffer ) >> { throw new IOException( "Some error" ); }

        when:
        this.session.send( ByteSource.empty() );

        then: true;
    }

    private static ServletUpgradeResponse newServletUpgradeResponse()
    {
        return new ServletUpgradeResponse( new MockHttpServletResponse() );
    }
}
