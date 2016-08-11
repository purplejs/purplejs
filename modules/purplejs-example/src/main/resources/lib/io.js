var Charsets = Java.type('com.google.common.base.Charsets');

// Returns text from stream.
module.exports.streamAsText = function (stream) {
    return stream.asCharSource(Charsets.UTF_8).read();
};
