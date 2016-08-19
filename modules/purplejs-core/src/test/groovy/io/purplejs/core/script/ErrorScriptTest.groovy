package io.purplejs.core.script

import io.purplejs.core.ScriptTestSupport
import io.purplejs.core.exception.ProblemException

class ErrorScriptTest
    extends ScriptTestSupport
{
    def "compile error"()
    {
        setup:
        file( '/test.js', '''
            %
        ''' );

        when:
        run( '/test.js' );

        then:
        ProblemException ex = thrown();
        ex.lineNumber == 1;
        ex.path.path == '/test.js';
    }

    def "runtime error"()
    {
        setup:
        file( '/test.js', '''
            exports.hello = function () {
                throw "error";
            };
        ''' );

        when:
        run( '/test.js' ).executeMethod( 'hello' );

        then:
        ProblemException ex = thrown();
        ex.lineNumber == 2;
        ex.path.path == '/test.js';
    }

    def "compile error in require"()
    {
        setup:
        file( '/error.js', '''
            unknown.variable;
        ''' );

        file( '/test.js', '''
            exports.hello = function () {
                require('./error');
            };
        ''' );

        when:
        run( '/test.js' ).executeMethod( 'hello' );

        then:
        ProblemException ex = thrown();
        ex.lineNumber == 1;
        ex.path.path == '/error.js';
    }

    def "error in require json"()
    {
        setup:
        file( '/error.json', '''
            {...
        ''' );

        file( '/test.js', '''
            require('./error.json');
        ''' );

        when:
        run( '/test.js' );

        then:
        ProblemException ex = thrown();
        ex.lineNumber == 1;
        ex.path.path == '/test.js';
    }
}

