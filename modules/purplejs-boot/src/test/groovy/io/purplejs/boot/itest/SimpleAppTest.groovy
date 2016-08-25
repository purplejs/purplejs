package io.purplejs.boot.itest

class SimpleAppTest
    extends AbstractBootITest
{
    def "startup"()
    {
        when:
        def port = this.app.getPort();

        then:
        port > 0;
    }
}
