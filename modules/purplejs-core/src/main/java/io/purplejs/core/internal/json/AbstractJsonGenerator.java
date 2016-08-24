package io.purplejs.core.internal.json;

import java.util.Stack;

import io.purplejs.core.json.JsonGenerator;
import io.purplejs.core.json.JsonSerializable;
import io.purplejs.core.value.ScriptValue;

public abstract class AbstractJsonGenerator
    implements JsonGenerator
{
    private final Stack<Object> stack;

    private Object current;

    private Object root;

    public AbstractJsonGenerator()
    {
        this.stack = new Stack<>();
    }

    public final Object getRoot()
    {
        return this.root;
    }

    protected abstract Object newMap();

    protected abstract Object newArray();

    protected abstract boolean isMap( Object value );

    protected abstract boolean isArray( Object value );

    private void checkIfMap()
    {
        if ( !isMap( this.current ) )
        {
            throw new IllegalArgumentException( "Current object should be of type map" );
        }
    }

    private void checkIfArray()
    {
        if ( !isArray( this.current ) )
        {
            throw new IllegalArgumentException( "Current object should be of type array" );
        }
    }

    private Object putInMap( final String key, final Object value )
    {
        checkIfMap();
        putInMap( this.current, key, value );
        return value;
    }

    protected abstract void putInMap( Object map, String key, Object value );

    private Object addToArray( final Object value )
    {
        checkIfArray();
        addToArray( this.current, value );
        return value;
    }

    protected abstract void addToArray( Object array, Object value );

    private void setCurrent( final Object object )
    {
        this.stack.push( this.current );
        this.current = object;

        if ( this.root == null )
        {
            this.root = this.current;
        }

    }

    @Override
    public final JsonGenerator array()
    {
        if ( this.current == null )
        {
            setCurrent( newArray() );
        }
        else
        {
            setCurrent( addToArray( newArray() ) );
        }

        return this;
    }

    @Override
    public final JsonGenerator array( final String key )
    {
        setCurrent( putInMap( key, newArray() ) );
        return this;
    }

    @Override
    public final JsonGenerator map()
    {
        if ( this.current == null )
        {
            setCurrent( newMap() );
        }
        else
        {
            setCurrent( addToArray( newMap() ) );
        }

        return this;
    }

    @Override
    public final JsonGenerator map( final String key )
    {
        setCurrent( putInMap( key, newMap() ) );
        return this;
    }

    @Override
    public final JsonGenerator value( final Object value )
    {
        addToArray( convertValue( value ) );
        return this;
    }

    @Override
    public final JsonGenerator value( final String key, final Object value )
    {
        putInMap( key, convertValue( value ) );
        return this;
    }

    @Override
    public final JsonGenerator end()
    {
        if ( !this.stack.isEmpty() )
        {
            this.current = this.stack.pop();
        }

        return this;
    }

    private Object convertValue( final Object value )
    {
        if ( value == null )
        {
            return null;
        }

        if ( value instanceof JsonSerializable )
        {
            return convertValue( (JsonSerializable) value );
        }

        if ( value instanceof ScriptValue )
        {
            return convertValue( ( (ScriptValue) value ).getRaw() );
        }

        return value;
    }

    private Object convertValue( final JsonSerializable value )
    {
        final AbstractJsonGenerator generator = newGenerator();
        value.serialize( generator );
        return generator.getRoot();
    }

    protected abstract AbstractJsonGenerator newGenerator();
}
