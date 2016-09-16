/**
 * Mustache template related functions.
 *
 * @example
 * var mustache = require('/lib/mustache');
 *
 * @module mustache
 */

var service = __.getInstance('io.purplejs.mustache.MustacheService');

/**
 * This function renders a view using mustache.
 *
 * @param view Location of the view. Use `resolve(..)` to resolve a view.
 * @param {object} model Model that is passed to the view.
 * @returns {string} The rendered output.
 */
exports.render = function (view, model) {
    model = __.toScriptValue(model);
    return service.render(view, model);
};
