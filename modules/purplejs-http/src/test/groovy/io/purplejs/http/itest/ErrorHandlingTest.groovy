package io.purplejs.http.itest

import io.purplejs.core.resource.ResourcePath
import io.purplejs.http.Response
import io.purplejs.http.ResponseBuilder
import io.purplejs.http.Status
import io.purplejs.http.handler.HttpHandler

class ErrorHandlingTest
    extends AbstractHttpITest
{
    def "test errorIfNeeded"()
    {
        setup:
        script( '''
            exports.handleError = function(error) {
                error.request = error.request != undefined;

                return {
                    body: error
                };
            };
        ''' );

        when:
        def response = ResponseBuilder.newBuilder().status( Status.INTERNAL_SERVER_ERROR ).build();
        def result = errorIfNeeded( response );

        then:
        result != null;
        prettifyJson( toStringBody( result ) ) == prettifyJson( '''
            {
                "status": 500,
                "message": "Internal Server Error",
                "request": true
            }
        ''' );
    }

    def "test handleException"()
    {
        setup:
        script( '''
            exports.handleError = function(error) {
                error.request = error.request != undefined;

                return {
                    body: error
                };
            };
        ''' );

        when:
        def cause = new IOException( 'Some message' );
        def result = handleException( cause );

        then:
        result != null;
        prettifyJson( toStringBody( result ) ) == prettifyJson( '''
            {
                "status": 500,
                "message": "Some message",
                "exception": "java.io.IOException: Some message",
                "request": true
            }
        ''' );
    }

    private HttpHandler newHandler()
    {
        return this.handlerFactory.newHandler( ResourcePath.from( '/test.js' ) );
    }

    private Response handleException( final Throwable cause )
    {
        return newHandler().handleException( this.requestBuilder.build(), cause );
    }

    private Response errorIfNeeded( final Response response )
    {
        return newHandler().errorIfNeeded( this.requestBuilder.build(), response );
    }
}
