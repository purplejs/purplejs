package io.purplejs.http.internal.websocket

import io.purplejs.core.internal.nashorn.NashornRuntime
import io.purplejs.core.internal.nashorn.NashornRuntimeFactory
import io.purplejs.core.internal.value.ScriptValueFactory
import io.purplejs.core.internal.value.ScriptValueFactoryImpl
import io.purplejs.core.value.ScriptValue
import io.purplejs.http.websocket.WebSocketConfig
import spock.lang.Specification

import javax.script.ScriptEngine

class WebSocketConfigFactoryTest
    extends Specification
{
    def ScriptValueFactory valueFactory;

    def ScriptEngine engine;

    def WebSocketConfigFactory configFactory;

    def setup()
    {
        final NashornRuntime runtime = new NashornRuntimeFactory().newRuntime( getClass().getClassLoader() );
        this.engine = runtime.getEngine();
        this.valueFactory = new ScriptValueFactoryImpl( runtime );

        this.configFactory = new WebSocketConfigFactory();
    }

    private WebSocketConfig newConfig( final String script )
    {
        final Object result = this.engine.eval( script );
        final ScriptValue value = this.valueFactory.newValue( result );
        return this.configFactory.create( value );
    }

    def "test null"()
    {
        when:
        def config = newConfig( '' );

        then:
        config == null;
    }

    def "test illegal"()
    {
        when:
        def config = newConfig( 'var result = []; result;' );

        then:
        assertDefaults( config );
    }

    def "test empty"()
    {
        when:
        def config = newConfig( 'var result = {}; result;' );

        then:
        assertDefaults( config );
    }

    def "test illegal values"()
    {
        when:
        def config = newConfig( '''
            var result = {
                group: {},
                timeout: 'abc',
                subProtocols: {}
            };

            result;
        ''' );

        then:
        assertDefaults( config );

        when:
        config = newConfig( '''
            var result = {
                group: {},
                timeout: {},
                subProtocols: {}
            };

            result;
        ''' );

        then:
        assertDefaults( config );
    }

    def "test subProtocols"()
    {
        when:
        def config = newConfig( '''
            var result = {
                subProtocols: 'text'
            };

            result;
        ''' );

        then:
        config != null;
        config.getSubProtocols().toString() == '[text]';

        when:
        config = newConfig( '''
            var result = {
                subProtocols: ['text', 'binary']
            };

            result;
        ''' );

        then:
        config != null;
        config.getSubProtocols().toString() == '[binary, text]';
    }

    def "test timeout"()
    {
        when:
        def config = newConfig( '''
            var result = {
                timeout: 1000
            };

            result;
        ''' );

        then:
        config != null;
        config.getTimeout() == 1000;
    }

    def "test attributes"()
    {
        when:
        def config = newConfig( '''
            var result = {
                attributes: {
                    a: 1,
                    b: 2
                }
            };

            result;
        ''' );

        then:
        config != null;
        config.getAttributes() != null;
    }

    def "test group"()
    {
        when:
        def config = newConfig( '''
            var result = {
                group: 'other'
            };

            result;
        ''' );

        then:
        config != null;
        config.getGroup() == 'other';
    }

    def "test combined"()
    {
        when:
        def config = newConfig( '''
            var result = {
                group: 'other',
                timeout: 1000,
                attributes: {
                    a: 1,
                    b: 2
                },
                subProtocols: ['text', 'binary']
            };

            result;
        ''' );

        then:
        config != null;
        config.getGroup() == 'other';
        config.getTimeout() == 1000;
        config.getAttributes() != null;
        config.getSubProtocols().toString() == '[binary, text]';
    }

    private static void assertDefaults( WebSocketConfig config )
    {
        assert config != null;
        assert config.getGroup() == 'default';
        assert config.getAttributes() == null;
        assert config.getSubProtocols().isEmpty();
        assert config.getTimeout() == 30000;
    }
}
