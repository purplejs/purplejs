package io.purplejs.core.resource;

/**
 * TODO:
 *
 * Maybe it's best to let this be internal and have some sort of resolver-settings
 * (io.purplejs.resource.*). The settings could include the following:
 *
 * - search-paths (/lib) for used when the resource is specified like "a/b/c".
 * - root-paths (/) for used with /site etc.
 *
 * If specified by "a/b/c" and not found, it could revert back to "./a/b/c" by default.
 */
public interface ResourceResolver
{
    ResourceResult resolve( ResourcePath base, String path );

    ResourceResult resolveRequire( ResourcePath base, String path );
}
