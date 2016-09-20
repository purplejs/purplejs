package io.purplejs.thymeleaf.internal;

import java.util.Map;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.cache.ICacheEntryValidity;
import org.thymeleaf.cache.NonCacheableCacheEntryValidity;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;

import com.google.common.io.Files;

import io.purplejs.core.RunMode;
import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;

final class TemplateResolverImpl
    extends AbstractConfigurableTemplateResolver
    implements TemplateResourceResolver
{
    private final ResourceLoader loader;

    TemplateResolverImpl( final ResourceLoader loader )
    {
        this.loader = loader;
    }

    @Override
    protected ITemplateResource computeTemplateResource( final IEngineConfiguration configuration, final String ownerTemplate,
                                                         final String template, final String resourceName, final String characterEncoding,
                                                         final Map<String, Object> templateResolutionAttributes )
    {
        return resolve( null, ownerTemplate, resourceName );
    }

    private boolean isDisableCache()
    {
        return RunMode.get() == RunMode.DEV;
    }

    @Override
    protected ICacheEntryValidity computeValidity( final IEngineConfiguration configuration, final String ownerTemplate,
                                                   final String template, final Map<String, Object> templateResolutionAttributes )
    {
        if ( isDisableCache() )
        {
            return new NonCacheableCacheEntryValidity();
        }

        return super.computeValidity( configuration, ownerTemplate, template, templateResolutionAttributes );
    }

    private ITemplateResource resolve( final ResourcePath base, final String ownerTemplate, final String location )
    {
        final ResourcePath parent = findParent( base, ownerTemplate );
        return resolve( parent, location );
    }

    private ResourcePath findParent( final ResourcePath base, final String ownerTempalte )
    {
        return ownerTempalte != null ? ResourcePath.from( ownerTempalte ) : base;
    }

    @Override
    public ITemplateResource resolve( final ResourcePath base, final String location )
    {
        final ResourcePath resolved = resolvePath( base, location );
        final Resource resource = this.loader.loadOrNull( resolved );
        return resource != null ? new TemplateResourceImpl( resource, this ) : null;
    }

    private ResourcePath resolvePath( final ResourcePath base, final String location )
    {
        if ( Files.getFileExtension( location ).equals( "" ) )
        {
            return resolvePath( base, location + ".html" );
        }

        return base != null ? base.resolve( ".." ).resolve( location ) : ResourcePath.from( location );
    }
}
