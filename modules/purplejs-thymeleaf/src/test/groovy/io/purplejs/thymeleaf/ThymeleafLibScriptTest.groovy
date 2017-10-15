package io.purplejs.thymeleaf

import io.purplejs.core.exception.ProblemException
import io.purplejs.core.itest.AbstractCoreITest

class ThymeleafLibScriptTest
    extends AbstractCoreITest
{
    private void template( final String template )
    {
        file( '/test.html', template );
    }

    private void model( final String model )
    {
        file( '/test.js', """
            var thymeleaf = require('/lib/thymeleaf');

            exports.render = function() {
                var model = ${model};
                return thymeleaf.render('/test.html', model);
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

    def "template not found"()
    {
        setup:
        model( '{}' );

        when:
        render();

        then:
        thrown ProblemException;
    }

    def "model not a map"()
    {
        setup:
        template( '' );
        model( '11' );

        when:
        def result = render();

        then:
        result != null;
        result == '';
    }

    def "template error"()
    {
        setup:
        template( '<div data-th-each="fruit ; ${fruits}">' );
        model( '{}' );

        when:
        render();

        then:
        thrown ProblemException;
    }

    def "simple template"()
    {
        setup:
        template( '''
            <div>
                <div data-th-each="fruit : ${fruits}">
                    Name:
                    <div data-th-text="${fruit.name}">Name</div>
                    Color:
                    <div data-th-text="${fruit.color}">Color</div>
                </div>
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

    def "dates template"()
    {
        setup:
        def timezone = TimeZone.default;
        TimeZone.setDefault( TimeZone.getTimeZone( "GMT" ) );

        template( '''
            <div>
                <div data-th-text="${#dates.year(date)}">date</div>
                <div data-th-text="${#dates.month(date)}">date</div>
                <div data-th-text="${#dates.day(date)}">date</div>
                <div data-th-text="${#dates.format(date, 'yyyy-MM-dd HH:mm:ssZ')}">date</div>
            </div>
        ''' );
        model( '''
            {
                date: new Date(Date.parse('1995-11-12T22:24:25Z'))
            }
        ''' );

        when:
        def result = render();

        then:
        result != null;
        trimLines( result ) == trimLines( '''
            <div>
                <div>1995</div>
                <div>11</div>
                <div>12</div>
                <div>1995-11-12 22:24:25+0000</div>
            </div>
        ''' );

        cleanup:
        TimeZone.setDefault( timezone );
    }

    def "jsexec template"()
    {
        setup:
        template( '''
            <div>
                <div data-th-text="${#js.exec(func1)}">Func1</div>
                <div data-th-text="${#js.exec(func2, 'a')}">Func2</div>
                <div data-th-text="${#js.exec(func3, 1, 2)}">Func3</div>
            </div>
        ''' );
        model( '''
            {
                func1: function () {
                    return "Hello";
                },
                func2: function (arg1) {
                    return "Hello " + arg1;
                },
                func3: function (arg1, arg2) {
                    return "Hello " + arg1 + " and " + arg2;
                }
            }
        ''' );

        when:
        def result = render();

        then:
        result != null;
        trimLines( result ) == trimLines( '''
            <div>
                <div>Hello</div>
                <div>Hello a</div>
                <div>Hello 1 and 2</div>
            </div>
        ''' );
    }

    def "inline fragment template"()
    {
        setup:
        template( '''
            <div>
                <div data-th-fragment="copy" data-th-remove="all">
                    &copy; 2011 The Good Thymes Virtual Grocery
                </div>
                <div data-th-include="::copy"></div>
            </div>
        ''' );
        model( '{}' );

        when:
        def result = render();

        then:
        result != null;
        trimLines( result ) == trimLines( '''
            <div>
                <div>
                    &copy; 2011 The Good Thymes Virtual Grocery
                </div>
            </div>
        ''' );
    }

    def "external fragment template"()
    {
        setup:
        template( '''
            <div>
                <div data-th-include="fragment1::part1"></div>
                <div data-th-include="./fragment1.html::part2"></div>
                <div data-th-include="../fragment2::part1"></div>
                <div data-th-include="/fragment2.html::part2"></div>
            </div>
        ''' );

        file( '/fragment1.html', '''
            <div>
                <div data-th-fragment="part1" data-th-remove="all">
                    Fragment1.Part1
                </div>
                <div data-th-fragment="part2" data-th-remove="all">
                    Fragment.1Part2
                </div>
            </div>
        ''' );

        file( '/fragment2.html', '''
            <div>
                <div data-th-fragment="part1" data-th-remove="all">
                    Fragment2.Part1
                </div>
                <div data-th-fragment="part2" data-th-remove="all">
                    <div data-th-include="./fragment3.html::part"></div>
                </div>
            </div>
        ''' );

        file( '/fragment3.html', '''
            <div>
                <div data-th-fragment="part" data-th-remove="all">
                    Fragment3.Part1
                </div>
            </div>
        ''' );

        model( '{}' );

        when:
        def result = render();

        then:
        result != null;
        trimLines( result ) == trimLines( '''
            <div>
                <div>
                    Fragment1.Part1
                </div>
                <div>
                    Fragment.1Part2
                </div>
                <div>
                    Fragment2.Part1
                </div>
                <div>
                    <div>
                        Fragment3.Part1
                    </div>
                </div>
            </div>
        ''' );
    }
}
