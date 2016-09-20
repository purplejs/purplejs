/**
 * Thymeleaf template related functions.
 *
 * @example
 * var thymeleaf = require('/lib/thymeleaf');
 *
 * @module thymeleaf
 */

var service = __.getInstance('io.purplejs.thymeleaf.ThymeleafService');

/**
 * This function renders a view using thymeleaf.
 *
 * @param view Location of the view. Use `resolve(..)` to resolve a view.
 * @param {object} model Model that is passed to the view.
 * @returns {string} The rendered output.
 */
exports.render = function (view, model) {
    model = __.toScriptValue(model);
    return service.render(view, model);
};
