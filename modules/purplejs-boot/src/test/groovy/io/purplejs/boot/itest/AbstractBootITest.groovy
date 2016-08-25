package io.purplejs.boot.itest

import io.purplejs.boot.MainApp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import spock.lang.Specification

abstract class AbstractBootITest
    extends Specification
{
    def MainApp app;

    def OkHttpClient client;

    def setup()
    {
        this.client = new OkHttpClient();

        this.app = new MainApp();
        this.app.start();
    }

    def cleanup()
    {
        this.app.stop();
    }

    protected final Request.Builder newRequest( final String path )
    {
        return new Request.Builder()
            .url( 'http://localhost:' + this.app.port + '' + path );
    }

    protected final Response execute( final Request.Builder request )
    {
        return this.client.newCall( request.build() ).execute();
    }
}
