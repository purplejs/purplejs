package io.purplejs.core.exception

import spock.lang.Specification

class ExceptionHelperTest
    extends Specification
{
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
}
