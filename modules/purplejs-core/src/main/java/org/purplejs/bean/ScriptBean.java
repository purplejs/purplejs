package org.purplejs.bean;

public interface ScriptBean
{
    void init( BeanContext context );

    void destroy();
}

/**
 *
 * This should be done in JS instead:
 *
 *
 *    var bean = __.newBean('...');
 *    bean.initialize(__.context);
 *
 *    __.finalizer(bean.destroy);
 *
 *
 *
 *
 */
