package io.purplejs.http.impl.error;

import java.util.Stack;

import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;

final class HtmlBuilder
{
    private final Escaper escaper;

    private final StringBuilder str;

    private final Stack<String> openTags;

    private boolean addedInner;

    HtmlBuilder()
    {
        this.escaper = HtmlEscapers.htmlEscaper();
        this.str = new StringBuilder();
        this.openTags = new Stack<>();
        this.addedInner = false;
    }

    private void closeIfNeeded()
    {
        if ( !this.addedInner && !this.openTags.isEmpty() )
        {
            this.str.append( '>' );
        }
    }

    HtmlBuilder open( final String name )
    {
        closeIfNeeded();

        this.str.append( '<' );
        this.str.append( name );

        this.openTags.push( name );
        this.addedInner = false;

        return this;
    }

    HtmlBuilder close()
    {
        this.str.append( "</" );
        this.str.append( this.openTags.pop() );
        this.str.append( '>' );
        return this;
    }

    HtmlBuilder closeEmpty()
    {
        this.str.append( "/>" );
        this.openTags.pop();
        return this;
    }

    HtmlBuilder attribute( final String name, final String value )
    {
        this.str.append( ' ' );
        this.str.append( name );
        this.str.append( "=\"" );
        this.str.append( value );
        this.str.append( '"' );
        this.addedInner = false;
        return this;
    }

    HtmlBuilder escapedText( final String text )
    {
        return text( this.escaper.escape( text ) );
    }

    HtmlBuilder text( final String text )
    {
        closeIfNeeded();
        this.str.append( text );
        this.addedInner = true;
        return this;
    }

    @Override
    public String toString()
    {
        return this.str.toString();
    }
}