package io.purplejs.thymeleaf.internal;

import java.io.IOException;
import java.io.Reader;

import org.thymeleaf.templateresource.ITemplateResource;

import com.google.common.base.Charsets;

import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourcePath;

final class TemplateResourceImpl
    implements ITemplateResource
{
    private final Resource resource;

    private final ResourcePath path;

    private final TemplateResourceResolver resolver;

    TemplateResourceImpl( final Resource resource, final TemplateResourceResolver resolver )
    {
        this.resource = resource;
        this.path = this.resource.getPath();
        this.resolver = resolver;
    }

    @Override
    public String getDescription()
    {
        return this.path.toString();
    }

    @Override
    public String getBaseName()
    {
        return this.path.getNameWithoutExtension();
    }

    @Override
    public boolean exists()
    {
        return true;
    }

    @Override
    public Reader reader()
        throws IOException
    {
        return this.resource.getBytes().asCharSource( Charsets.UTF_8 ).openBufferedStream();
    }

    @Override
    public ITemplateResource relative( final String relativeLocation )
    {
        return this.resolver.resolve( this.path, relativeLocation );
    }
}
