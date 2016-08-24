// var http = require('/lib/http');

var ByteSource = Java.type('com.google.common.io.ByteSource');


exports.get = function (req) {

    return {
        body: {
            m: req.peer.raw.remoteAddr
        }
    };

};

/*
 exports.post = function (req) {



 return {
 body: http.bodyAsJson()
 };
 };
 */
