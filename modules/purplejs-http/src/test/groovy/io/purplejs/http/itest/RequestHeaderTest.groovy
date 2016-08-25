package io.purplejs.http.itest

import io.purplejs.http.Response
import io.purplejs.http.Status

class RequestHeaderTest
    extends AbstractHttpITest
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

        final Response res = serve();
        assert res.status == Status.OK;
        return toStringBody( res );
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
        this.requestBuilder.header( 'a', '1' );
        this.requestBuilder.header( 'b', '2' );

        when:
        def res = executeGet();

        then:
        res == '{"a":"1","b":"2"}';
    }

    def "ignore headers"()
    {
        setup:
        this.requestBuilder.header( 'Cookie', 'a=1' );

        when:
        def res = executeGet();

        then:
        res == '{}';
    }
}
