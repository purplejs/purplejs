package io.purplejs.http.itest

import com.google.common.base.Charsets
import io.purplejs.http.Response
import io.purplejs.http.Status

class RequestHeaderTest
    extends AbstractIntegrationTest
{
    private String executeGet()
    {
        script( '''
            exports.get = function (req) {
                return {
                    body: JSON.stringify(req.headers)
                };
            };
        ''' );

        this.request.method = 'GET';

        final Response res = serve();
        assert res.status == Status.OK;
        return res.body.asCharSource( Charsets.UTF_8 ).read();
    }

    def "no headers"()
    {
        when:
        def res = executeGet();

        then:
        res == '{}';
    }

    def "simple headers"()
    {
        setup:
        this.request.headers.put( 'a', '1' );
        this.request.headers.put( 'b', '2' );

        when:
        def res = executeGet();

        then:
        res == '{"a":"1","b":"2"}';
    }
}
