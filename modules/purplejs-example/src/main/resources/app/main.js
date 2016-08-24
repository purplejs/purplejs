var http = require('/lib/http');
var websocket = require('/lib/websocket');


exports.get = function (req) {

    if (!req.webSocket) {
        websocket.sendToGroup('default', 'Hello from here!');

        return {
            body: req
        };
    }

    return {
        webSocket: {
            // group: 'test',            // default is "default"
            attributes: {
                a: 3,
                b: 4
            }
            // timeout: 2000
            // subProtocols: ['text'],   // default is []
        }
    };

};

exports.webSocketEvent = function (event) {
    websocket.send(event.session.id, 'Hello World!');
    websocket.sendToGroup('default', 'Hello everyone!', event.session.id);
};

/*
 exports.post = function (req) {



 return {
 body: http.bodyAsJson()
 };
 };
 */
