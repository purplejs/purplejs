package io.purplejs.mustache.internal;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.MustacheException;
import com.samskivert.mustache.Template;

import io.purplejs.core.Engine;
import io.purplejs.core.exception.ProblemException;
import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.util.IOHelper;
import io.purplejs.core.value.ScriptValue;
import io.purplejs.mustache.MustacheService;

final class MustacheServiceImpl
    implements MustacheService
{
    private final Mustache.Compiler compiler;

    private ResourceLoader resourceLoader;

    MustacheServiceImpl()
    {
        this.compiler = Mustache.compiler();
    }

    void initialize( final Engine engine )
    {
        this.resourceLoader = engine.getResourceLoader();
    }

    @Override
    public String render( final ResourcePath view, final ScriptValue model )
    {
        try
        {
            final Resource resource = this.resourceLoader.load( view );
            return doRender( resource, model.toJavaObject() );
        }
        catch ( final RuntimeException e )
        {
            throw handleError( view, e );
        }
    }

    private String doRender( final Resource view, final Object model )
    {
        final String str = IOHelper.readString( view.getBytes() );
        final Template template = this.compiler.compile( str );
        return template.execute( model );
    }

    static RuntimeException handleError( final ResourcePath view, final RuntimeException e )
    {
        if ( e instanceof MustacheException.Context )
        {
            return handleError( view, (MustacheException.Context) e );
        }

        return e;
    }

    private static RuntimeException handleError( final ResourcePath view, final MustacheException.Context e )
    {
        return ProblemException.newBuilder().
            lineNumber( e.lineNo ).
            path( view ).
            cause( e ).
            message( e.getMessage() ).
            build();
    }
}
