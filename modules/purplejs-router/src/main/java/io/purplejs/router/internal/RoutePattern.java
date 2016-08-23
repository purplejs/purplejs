package io.purplejs.router.internal;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public final class RoutePattern
{
    private final static Pattern PARAM = Pattern.compile( "\\{(\\w+)(:(.+))?\\}" );

    private final Pattern pattern;

    private final List<String> pathParams;

    private RoutePattern( final Pattern pattern, final List<String> pathParams )
    {
        this.pattern = pattern;
        this.pathParams = pathParams;
    }

    public boolean matches( final String path )
    {
        return this.pattern.matcher( path ).matches();
    }

    public Map<String, String> getPathParams( final String path )
    {
        final Map<String, String> map = Maps.newHashMap();
        final Matcher matcher = this.pattern.matcher( path );

        if ( !matcher.matches() )
        {
            return map;
        }

        for ( int i = 0; i < matcher.groupCount(); i++ )
        {
            map.put( this.pathParams.get( i ), matcher.group( i + 1 ) );
        }

        return map;
    }

    public static RoutePattern compile( final String pattern )
    {
        String regexp = "";
        final List<String> pathParams = Lists.newArrayList();

        for ( final String part : Splitter.on( '/' ).omitEmptyStrings().trimResults().split( pattern ) )
        {
            final Matcher matcher = PARAM.matcher( part );
            if ( !matcher.matches() )
            {
                regexp += "/" + part;
            }
            else
            {
                pathParams.add( matcher.group( 1 ) );
                final String partExpr = matcher.group( 3 );

                regexp += "/(" + ( partExpr != null ? partExpr : "[^/]+" ) + ")";
            }
        }

        return new RoutePattern( Pattern.compile( regexp ), pathParams );
    }
}
