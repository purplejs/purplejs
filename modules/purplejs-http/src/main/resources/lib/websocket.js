/* global exports */

/**
 * WebSocket javascript functions.
 *
 * @example
 * // Require websocket functions.
 * var websocket = require('/lib/websocket');
 *
 * @module websocket
 */

// Create a new helper instance and set the manager instance.
var helper = __.newBean('io.purplejs.http.internal.lib.WebSocketLibHelper');
helper.manager = __.getInstance('io.purplejs.http.websocket.WebSocketManager');

/**
 * Send a message to the specified session id. The message can either be a string or a stream.
 *
 * @param {string} id Session id to send the message to.
 * @param {string|*} message Text (or stream) message to send.
 */
exports.send = function (id, message) {
    helper.sendMessage(id, message);
};

/**
 * Send a message to the specified session group. The message can either be a string or a stream. It
 * can also have an optional excludeId to exclude a specific session id.
 *
 * @param {string} group Session group to send the message to.
 * @param {string|*} message Text (or stream) message to send.
 * @param {string} excludeId Session id to exclude from message sending.
 */
exports.sendToGroup = function (group, message, excludeId) {
    helper.sendMessageToGroup(group, message, excludeId || null);
};
