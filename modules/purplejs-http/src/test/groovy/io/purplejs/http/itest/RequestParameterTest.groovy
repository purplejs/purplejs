package io.purplejs.http.itest

import com.google.common.base.Charsets
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

        this.request.method = 'GET';

        final Response res = serve();
        assert res.status == Status.OK;
        return res.body.asCharSource( Charsets.UTF_8 ).read();
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
        this.request.parameters.put( 'a', '1' );
        this.request.parameters.put( 'b', '2' );

        when:
        def res = executeGet();

        then:
        res == '{"a":"1","b":"2"}';
    }

    def "multiple values"()
    {
        setup:
        this.request.parameters.put( 'a', '1' );
        this.request.parameters.put( 'b', '2' );
        this.request.parameters.put( 'b', '3' );

        when:
        def res = executeGet();

        then:
        res == '{"a":"1","b":["2","3"]}';
    }
}
