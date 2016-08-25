package io.purplejs.http.itest

class WebSocketAcceptTest
    extends AbstractIntegrationTest
{
    def "webSocket accept"()
    {
        setup:
        script( '''
            exports.get = function (req) {
                return {
                    webSocket: {
                        group: 'mygroup',
                        timeout: 1000,
                        subProtocols: ['text', 'binary'],
                        attributes: {
                            a: 1,
                            b: 2
                        }
                    }
                };
            };
        ''' );
        this.requestBuilder.webSocket( true );

        when:
        def res = serve();

        then:
        res.webSocket != null;
        res.webSocket.group == 'mygroup';
        res.webSocket.timeout == 1000;
        res.webSocket.subProtocols.toString() == '[binary, text]';
        res.webSocket.attributes != null;
    }
}
