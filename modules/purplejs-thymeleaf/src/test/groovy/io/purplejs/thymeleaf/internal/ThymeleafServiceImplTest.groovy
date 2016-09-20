package io.purplejs.thymeleaf.internal

import io.purplejs.core.Engine
import io.purplejs.core.exception.ProblemException
import io.purplejs.core.mock.MockResourceLoader
import io.purplejs.core.resource.ResourcePath
import org.thymeleaf.exceptions.TemplateProcessingException
import spock.lang.Specification

class ThymeleafServiceImplTest
    extends Specification
{
    private ThymeleafServiceImpl service;

    private MockResourceLoader resourceLoader;

    def setup()
    {
        this.service = new ThymeleafServiceImpl();
        this.resourceLoader = new MockResourceLoader();

        def engine = Mock( Engine.class );
        engine.getResourceLoader() >> this.resourceLoader;

        this.service.initialize( engine );
    }

    def "template not found"()
    {
        when:
        this.service.render( ResourcePath.from( '/test.html' ), null );

        then:
        thrown ProblemException;
    }

    def "handleError - RuntimeException"()
    {
        setup:
        def ex = new RuntimeException();

        when:
        def result = ThymeleafServiceImpl.handleError( ex );

        then:
        result == ex;
    }

    def "handleError - TemplateProcessingException"()
    {
        setup:
        def ex = new TemplateProcessingException( 'failure', '/test.html', null );
        ex.setLineAndCol( 10, 0 );

        when:
        def result = ThymeleafServiceImpl.handleError( ex );

        then:
        result instanceof ProblemException;

        when:
        def problem = (ProblemException) result;

        then:
        problem.path.toString() == '/test.html';
        problem.lineNumber == 10;
    }

    def "handleError - TemplateProcessingException no path or line"()
    {
        setup:
        def ex = new TemplateProcessingException( 'failure' );

        when:
        def result = ThymeleafServiceImpl.handleError( ex );

        then:
        result instanceof ProblemException;
    }
}
