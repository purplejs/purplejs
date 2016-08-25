package io.purplejs.boot.itest

class AssetAppTest
    extends AbstractBootITest
{
    def "serve asset"()
    {
        when:
        def res = execute( newRequest( '/test.txt' ) );

        then:
        res != null;
        res.body().contentType().toString() == 'text/plain';
        res.body().string().trim() == 'Hello World!';
    }
}
