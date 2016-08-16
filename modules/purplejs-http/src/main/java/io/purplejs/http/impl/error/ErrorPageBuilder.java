package io.purplejs.http.impl.error;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.core.exception.ProblemException;

final class ErrorPageBuilder
{
    private final static int NUM_DELTA_LINES = 3;

    private final static int NUM_STACK_ELEMENTS = 140;

    private int statusCode;

    private String title;

    private String description;

    private Throwable cause;

    private List<String> lines;

    private ProblemException problem;

    private final static class LineInfo
    {
        private final int line;

        private final boolean mark;

        private final String text;

        public LineInfo( final int line, final String text )
        {
            this( line, text, false );
        }

        public LineInfo( final int line, final String text, final boolean mark )
        {
            this.line = line;
            this.mark = mark;
            this.text = text;
        }

        public int getLine()
        {
            return this.line;
        }

        public boolean isMark()
        {
            return this.mark;
        }

        public String getText()
        {
            return this.text;
        }
    }

    public ErrorPageBuilder status( final int value )
    {
        this.statusCode = value;
        return this;
    }

    public ErrorPageBuilder title( final String value )
    {
        this.title = value;
        return this;
    }

    public ErrorPageBuilder description( final String value )
    {
        this.description = value;
        return this;
    }

    public ErrorPageBuilder cause( final Throwable cause )
    {
        this.cause = cause;
        return this;
    }

    public ErrorPageBuilder lines( final List<String> lines )
    {
        this.lines = lines;
        return this;
    }

    public ErrorPageBuilder problem( final ProblemException problem )
    {
        this.problem = problem;
        return this;
    }

    public String build()
    {
        final HtmlBuilder html = new HtmlBuilder();
        html.text( "<!DOCTYPE html>" );
        html.open( "html" );
        buildHead( html );
        buildBody( html );
        html.close();
        return html.toString();
    }

    private void buildHead( final HtmlBuilder html )
    {
        html.open( "head" );
        html.open( "title" ).escapedText( this.statusCode + " " + this.title ).close();
        html.open( "style" );

        html.text( "html, body, pre {" );
        html.text( " margin: 0;" );
        html.text( " padding: 0;" );
        html.text( " font-family: Monaco, 'Lucida Console', monospace;" );
        html.text( " background: #ECECEC;" );
        html.text( "}" );

        html.text( "h1 {" );
        html.text( " margin: 0;" );
        html.text( " background: #A31012;" );
        html.text( " padding: 20px 45px;" );
        html.text( " color: #fff;" );
        html.text( " text-shadow: 1px 1px 1px rgba(0, 0, 0, .3);" );
        html.text( " border-bottom: 1px solid #690000;" );
        html.text( " font-size: 28px;" );
        html.text( "}" );

        html.text( "p#detail {" );
        html.text( " margin: 0;" );
        html.text( " padding: 15px 45px;" );
        html.text( " background: #F5A0A0;" );
        html.text( " border-top: 4px solid #D36D6D;" );
        html.text( " color: #730000;" );
        html.text( " text-shadow: 1px 1px 1px rgba(255, 255, 255, .3);" );
        html.text( " font-size: 14px;" );
        html.text( " border-bottom: 1px solid #BA7A7A;" );
        html.text( "}" );

        html.text( "h2 {" );
        html.text( " margin: 0;" );
        html.text( " padding: 5px 45px;" );
        html.text( " font-size: 12px;" );
        html.text( " background: #333;" );
        html.text( " color: #fff;" );
        html.text( " text-shadow: 1px 1px 1px rgba(0, 0, 0, .3);" );
        html.text( " border-top: 4px solid #2a2a2a;" );
        html.text( "}" );

        html.text( "pre {" );
        html.text( " margin: 0;" );
        html.text( " border-bottom: 1px solid #DDD;" );
        html.text( " text-shadow: 1px 1px 1px rgba(255, 255, 255, .5);" );
        html.text( " position: relative;" );
        html.text( " font-size: 12px;" );
        html.text( " overflow: hidden;" );
        html.text( "}" );

        html.text( "span.line {" );
        html.text( " text-align: right;" );
        html.text( " display: inline-block;" );
        html.text( " padding: 5px 5px;" );
        html.text( " width: 30px;" );
        html.text( " background: #D6D6D6;" );
        html.text( " color: #8B8B8B;" );
        html.text( " text-shadow: 1px 1px 1px rgba(255, 255, 255, .5);" );
        html.text( " font-weight: bold;" );
        html.text( "}" );

        html.text( "span.code {" );
        html.text( " padding: 5px 5px;" );
        html.text( " position: absolute;" );
        html.text( " right: 0;" );
        html.text( " left: 40px;" );
        html.text( "}" );

        html.text( "pre:first-child span.code {" );
        html.text( " border-top: 4px solid #CDCDCD;" );
        html.text( "}" );

        html.text( "pre:first-child span.line {" );
        html.text( " border-top: 4px solid #B6B6B6;" );
        html.text( "}" );

        html.text( "pre.error span.line {" );
        html.text( " background: #A31012;" );
        html.text( " color: #fff;" );
        html.text( " text-shadow: 1px 1px 1px rgba(0, 0, 0, .3);" );
        html.text( "}" );

        html.text( "pre.error {" );
        html.text( " color: #A31012;" );
        html.text( "}" );

        html.text( "pre.error span.marker {" );
        html.text( " background: #A31012;" );
        html.text( " color: #fff;" );
        html.text( " text-shadow: 1px 1px 1px rgba(0, 0, 0, .3);" );
        html.text( "}" );

        html.close();
        html.close();
    }

    private void buildBody( final HtmlBuilder html )
    {
        html.open( "body" );
        html.open( "h1" ).escapedText( this.statusCode + " " + this.title ).close();
        html.open( "p" ).attribute( "id", "detail" ).escapedText( this.description ).close();
        buildSourceInfo( html );
        buildCauseInfo( html );
        html.close();
    }

    private void buildSourceInfo( final HtmlBuilder html )
    {
        if ( this.problem == null )
        {
            return;
        }

        if ( this.problem.getPath() != null )
        {
            html.open( "h2" );
            html.escapedText( "In " + this.problem.getPath().toString() + " at line " + this.problem.getLineNumber() );
            html.close();
        }

        html.open( "div" ).attribute( "id", "source-code" );
        buildLineInfo( html, findSourceLines( this.problem ) );
        html.close();

        final List<LineInfo> callStack = getCallStack( this.problem );
        if ( !callStack.isEmpty() )
        {
            html.open( "h2" );
            html.text( "Here is the script calling stack:" );
            html.close();

            html.open( "div" );
            buildLineInfo( html, callStack );
            html.close();
        }
    }

    private void buildCauseInfo( final HtmlBuilder html )
    {
        if ( this.cause == null )
        {
            return;
        }

        html.open( "h2" ).text( "Here is the stack trace:" ).close();
        html.open( "div" );
        buildLineInfo( html, findTrace( this.cause ) );
        html.close();
    }

    private void buildLineInfo( final HtmlBuilder html, final Iterable<LineInfo> lines )
    {
        for ( final LineInfo line : lines )
        {
            buildLineInfo( html, line );
        }
    }

    private void buildLineInfo( final HtmlBuilder html, final LineInfo line )
    {
        html.open( "pre" );
        if ( line.isMark() )
        {
            html.attribute( "class", "error" );
        }

        html.open( "span" );
        html.attribute( "class", "line" );
        html.escapedText( String.valueOf( line.getLine() ) );
        html.close();

        html.open( "span" );
        html.attribute( "class", "code" );
        html.escapedText( line.getText() );
        html.close();

        html.close();
    }

    private static List<LineInfo> findTrace( final Throwable cause )
    {
        final List<LineInfo> list = Lists.newArrayList();
        final StackTraceElement[] trace = cause.getStackTrace();

        for ( int i = 0; i < Math.min( trace.length, NUM_STACK_ELEMENTS ); i++ )
        {
            list.add( new LineInfo( i + 1, trace[i].toString() ) );
        }

        if ( trace.length > NUM_STACK_ELEMENTS )
        {
            list.add( new LineInfo( NUM_STACK_ELEMENTS + 1, "..." ) );
        }

        return list;
    }

    private List<LineInfo> findSourceLines( final ProblemException cause )
    {
        final int errorLine = cause.getLineNumber();
        final List<String> allLines = this.lines;
        final List<String> subList = sliceLines( errorLine, allLines );

        int currentLine = Math.max( 0, errorLine - NUM_DELTA_LINES ) + 1;

        final List<LineInfo> list = Lists.newArrayList();
        for ( final String line : subList )
        {
            final String str = line.replaceAll( "\t", "    " );
            list.add( new LineInfo( currentLine, str, ( errorLine == currentLine ) ) );
            currentLine++;
        }

        return list;
    }

    private static List<String> sliceLines( final int line, final List<String> all )
    {
        final int firstLine = Math.max( 0, line - NUM_DELTA_LINES );
        final int lastLine = Math.min( all.size(), line + NUM_DELTA_LINES );
        return all.subList( firstLine, lastLine );
    }

    private static List<LineInfo> getCallStack( final ProblemException cause )
    {
        final List<LineInfo> list = Lists.newArrayList();
        final List<String> callStack = cause.getCallStack();

        for ( int i = 0; i < callStack.size(); i++ )
        {
            list.add( new LineInfo( i, callStack.get( i ) ) );
        }

        return list;
    }
}
