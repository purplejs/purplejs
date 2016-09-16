package io.purplejs.mustache

import io.purplejs.core.exception.ProblemException
import io.purplejs.core.itest.AbstractCoreITest

class MustacheLibScriptTest
    extends AbstractCoreITest
{
    private void template( final String template )
    {
        file( '/test.html', template );
    }

    private void model( final String model )
    {
        file( '/test.js', """
            var mustache = require('/lib/mustache');

            exports.render = function() {
                var view = resolve('/test.html');
                var model = ${model};

                return mustache.render(view, model);
            };
        """ );
    }

    private String render()
    {
        return run( '/test.js', 'render' ).getValue();
    }

    def "empty template"()
    {
        setup:
        template( '' );
        model( '{}' );

        when:
        def result = render();

        then:
        result != null;
        result == '';
    }

    def "simple template"()
    {
        setup:
        template( '''
            <div>
                {{#fruits}}
                <div>
                    Name:
                    <div>{{name}}</div>
                    Color:
                    <div>{{color}}</div>
                </div>
                {{/fruits}}
            </div>
        ''' );
        model( '''
            {
                fruits: [
                    {
                        name: 'Apple',
                        color: 'Red'
                    },
                    {
                        name: 'Pear',
                        color: 'Green'
                    }
                ]
            }
        ''' );

        when:
        def result = render();

        then:
        result != null;
        trimLines( result ) == trimLines( '''
            <div>
                <div>
                    Name:
                    <div>Apple</div>
                    Color:
                    <div>Red</div>
                </div>
                <div>
                    Name:
                    <div>Pear</div>
                    Color:
                    <div>Green</div>
                </div>
            </div>
        ''' );
    }

    def "template error"()
    {
        setup:
        template( '''
            <div>
                <div>{{other}}</div>
            </div>
        ''' );
        model( '{}' );

        when:
        render();

        then:
        def ex = thrown ProblemException;
        ex.lineNumber == 2;
        ex.message == "No method or field with name 'other' on line 2";
    }

    def "template not found"()
    {
        setup:
        model( '{}' );

        when:
        render();

        then:
        thrown ProblemException;
    }
}
