package io.purplejs.thymeleaf.internal

import com.google.common.io.ByteSource
import io.purplejs.core.resource.Resource
import io.purplejs.core.resource.ResourcePath
import org.thymeleaf.templateresource.ITemplateResource
import spock.lang.Specification

class TemplateResourceImplTest
    extends Specification
{
    private TemplateResourceResolver resolver;

    private TemplateResourceImpl templateResource;

    private Resource resource;

    private ResourcePath path;

    def setup()
    {
        this.path = ResourcePath.from( '/test.html' );
        this.resolver = Mock( TemplateResourceResolver.class );

        this.resource = Mock( Resource.class );
        this.resource.getPath() >> this.path;

        this.templateResource = new TemplateResourceImpl( this.resource, this.resolver );
    }

    def "getDescription"()
    {
        when:
        def result = this.templateResource.getDescription();

        then:
        result == '/test.html';
    }

    def "getBaseName"()
    {
        when:
        def result = this.templateResource.getBaseName();

        then:
        result == 'test';
    }

    def "exists"()
    {
        when:
        def result = this.templateResource.exists();

        then:
        result;
    }

    def "relative"()
    {
        setup:
        def other = Mock( ITemplateResource.class );
        this.resolver.resolve( this.path, '../other.html' ) >> other;

        when:
        def result = this.templateResource.relative( '../other.html' );

        then:
        result == other;
    }

    def "reader"()
    {
        setup:
        this.resource.getBytes() >> ByteSource.empty();

        when:
        def result = this.templateResource.reader();

        then:
        result != null;
    }
}
