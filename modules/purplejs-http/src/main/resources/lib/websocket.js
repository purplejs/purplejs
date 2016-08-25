/* global exports */

var helper = __.newBean('io.purplejs.http.internal.lib.WebSocketLibHelper');
helper.manager = __.getInstance('io.purplejs.http.websocket.WebSocketManager');

exports.send = function (id, message) {
    helper.sendMessage(id, message);
};

exports.sendToGroup = function (group, message, excludeId) {
    helper.sendMessageToGroup(group, message, excludeId || null);
};
