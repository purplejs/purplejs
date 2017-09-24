package io.purplejs.core.exception

import spock.lang.Specification

class ExceptionHelperTest
    extends Specification
{
    def "new instance"()
    {
        when:
        def instance = new ExceptionHelper()

        then:
        instance != null
    }

    def "toRuntime - checked"()
    {
        setup:
        def cause = new IOException()

        when:
        def ex = ExceptionHelper.toRuntime( cause )

        then:
        ex instanceof RuntimeException
        ex.cause == cause
    }

    def "toRuntime - unchecked"()
    {
        setup:
        def cause = new RuntimeException()

        when:
        def ex = ExceptionHelper.toRuntime( cause )

        then:
        cause == ex
    }

    def "wrap no exception"()
    {
        setup:
        def callable = { return 'hello' }

        when:
        def result = ExceptionHelper.wrap( callable )

        then:
        result == 'hello'
    }

    def "wrap unchecked"()
    {
        setup:
        def cause = new IOException()
        def callable = { throw cause }

        when:
        ExceptionHelper.wrap( callable )

        then:
        RuntimeException ex = thrown()
        ex.cause == cause
    }
}
