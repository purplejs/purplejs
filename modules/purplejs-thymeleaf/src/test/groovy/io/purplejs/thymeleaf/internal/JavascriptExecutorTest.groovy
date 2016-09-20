package io.purplejs.thymeleaf.internal

import spock.lang.Specification

import java.util.function.Function

class JavascriptExecutorTest
    extends Specification
{
    private JavascriptExecutor executor;

    def setup()
    {
        this.executor = new JavascriptExecutor();
    }

    def "exec - zero args"()
    {
        when:
        this.executor.exec();

        then:
        thrown IllegalArgumentException;
    }

    def "exec - args no function"()
    {
        when:
        this.executor.exec( 1, 2 );

        then:
        thrown IllegalArgumentException;
    }

    def "exec - one arg"()
    {
        setup:
        def func = new Function<Object[], Object>() {
            @Override
            Object apply( final Object[] args )
            {
                return args.length;
            }
        };

        when:
        def result = this.executor.exec( func );

        then:
        result == 0;
    }

    def "exec - multiple arg"()
    {
        setup:
        def func = new Function<Object[], Object>() {
            @Override
            Object apply( final Object[] args )
            {
                return args[0] + ' ' + args[1];
            }
        };

        when:
        def result = this.executor.exec( func, 'hello', 'world' );

        then:
        result == 'hello world';
    }
}
