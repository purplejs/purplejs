package io.purplejs.core.exception

import spock.lang.Specification

class ExceptionHelperTest
    extends Specification
{
    def "new instance"()
    {
        when:
        def instance = new ExceptionHelper();

        then:
        instance != null;
    }

    def "throw unchecked"()
    {
        setup:
        def cause = new IOException();

        when:
        ExceptionHelper.unchecked( cause );

        then:
        def IOException ex = thrown();
        ex == cause;
    }

    def "wrap no exception"()
    {
        setup:
        def callable = { return 'hello'; };

        when:
        def result = ExceptionHelper.wrap( callable );

        then:
        result == 'hello';
    }

    def "wrap unchecked"()
    {
        setup:
        def cause = new IOException();
        def callable = { throw cause; };

        when:
        ExceptionHelper.wrap( callable );

        then:
        def IOException ex = thrown();
        ex == cause;
    }
}
