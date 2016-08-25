package io.purplejs.http.itest

import io.purplejs.http.Response
import io.purplejs.http.Status

class RequestParameterTest
    extends AbstractIntegrationTest
{
    private String executeGet()
    {
        script( '''
            exports.get = function (req) {
                return {
                    body: JSON.stringify(req.params)
                };
            };
        ''' );

        final Response res = serve();
        assert res.status == Status.OK;
        return toStringBody( res );
    }

    def "no params"()
    {
        when:
        def res = executeGet();

        then:
        res == '{}';
    }

    def "simple params"()
    {
        setup:
        this.requestBuilder.parameter( 'a', '1' );
        this.requestBuilder.parameter( 'b', '2' );

        when:
        def res = executeGet();

        then:
        res == '{"a":"1","b":"2"}';
    }

    def "multiple values"()
    {
        setup:
        this.requestBuilder.parameter( 'a', '1' );
        this.requestBuilder.parameter( 'b', '2' );
        this.requestBuilder.parameter( 'b', '3' );

        when:
        def res = executeGet();

        then:
        res == '{"a":"1","b":["2","3"]}';
    }
}
