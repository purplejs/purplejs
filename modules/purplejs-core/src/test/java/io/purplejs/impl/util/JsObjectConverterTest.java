package io.purplejs.impl.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.purplejs.impl.nashorn.NashornHelper;
import io.purplejs.impl.nashorn.NashornRuntime;
import io.purplejs.impl.nashorn.NashornRuntimeFactory;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import static org.junit.Assert.*;

public class JsObjectConverterTest
{
    private JsObjectConverter converter;

    private NashornRuntime runtime;

    @Before
    public void setUp()
    {
        final ClassLoader classLoader = getClass().getClassLoader();
        this.runtime = new NashornRuntimeFactory().newRuntime( classLoader );
        this.converter = new JsObjectConverter( this.runtime );
    }

    @Test
    public void toJs_simple()
    {
        final Object value = this.converter.toJs( 11 );
        assertNotNull( value );
        assertEquals( 11, value );
    }

    @Test
    public void toJs_date()
    {
        final Date date = new Date();
        final Object value = this.converter.toJs( date );

        assertNotNull( value );
        assertEquals( true, NashornHelper.isDateType( value ) );

        final Date other = NashornHelper.toDate( value );
        assertEquals( date, other );
    }

    @Test
    public void toJs_list()
    {
        final List<Object> list = Lists.newArrayList( 1 );

        final Object jsList1 = this.converter.toJs( list );
        assertNotNull( jsList1 );
        assertEquals( ScriptObjectMirror.class, jsList1.getClass() );
        assertEquals( "[1]", this.runtime.toJsonString( jsList1 ) );

        list.clear();
        list.add( 1 );
        list.add( Maps.newHashMap() );

        final Object jsList2 = this.converter.toJs( list );
        assertNotNull( jsList2 );
        assertEquals( ScriptObjectMirror.class, jsList2.getClass() );
        assertEquals( "[1,{}]", this.runtime.toJsonString( jsList2 ) );

        list.clear();
        list.add( 1 );
        list.add( new SimpleJsonObject() );

        final Object jsList3 = this.converter.toJs( list );
        assertNotNull( jsList3 );
        assertEquals( ScriptObjectMirror.class, jsList3.getClass() );
        assertEquals( "[1,{\"a\":1,\"b\":2}]", this.runtime.toJsonString( jsList3 ) );

        list.clear();
        list.add( 1 );
        list.add( Lists.newArrayList() );

        final Object jsList4 = this.converter.toJs( list );
        assertNotNull( jsList4 );
        assertEquals( ScriptObjectMirror.class, jsList4.getClass() );
        assertEquals( "[1,[]]", this.runtime.toJsonString( jsList4 ) );
    }

    @Test
    public void toJs_map()
    {
        final Map<String, Object> map = Maps.newHashMap();
        map.put( "a", 1 );

        final Object jsMap1 = this.converter.toJs( map );
        assertNotNull( jsMap1 );
        assertEquals( ScriptObjectMirror.class, jsMap1.getClass() );
        assertEquals( "{\"a\":1}", this.runtime.toJsonString( jsMap1 ) );

        map.clear();
        map.put( "a", 1 );
        map.put( "b", Maps.newHashMap() );

        final Object jsMap2 = this.converter.toJs( map );
        assertNotNull( jsMap2 );
        assertEquals( ScriptObjectMirror.class, jsMap2.getClass() );
        assertEquals( "{\"a\":1,\"b\":{}}", this.runtime.toJsonString( jsMap2 ) );

        map.clear();
        map.put( "a", 1 );
        map.put( "b", new SimpleJsonObject() );

        final Object jsMap3 = this.converter.toJs( map );
        assertNotNull( jsMap3 );
        assertEquals( ScriptObjectMirror.class, jsMap3.getClass() );
        assertEquals( "{\"a\":1,\"b\":{\"a\":1,\"b\":2}}", this.runtime.toJsonString( jsMap3 ) );

        map.clear();
        map.put( "a", 1 );
        map.put( "b", Lists.newArrayList() );

        final Object jsMap4 = this.converter.toJs( map );
        assertNotNull( jsMap4 );
        assertEquals( ScriptObjectMirror.class, jsMap4.getClass() );
        assertEquals( "{\"a\":1,\"b\":[]}", this.runtime.toJsonString( jsMap4 ) );
    }

    @Test
    public void toJs_serializable()
    {
        final Object value = this.converter.toJs( new SimpleJsonObject() );
        assertNotNull( value );
        assertEquals( ScriptObjectMirror.class, value.getClass() );
        assertEquals( "{\"a\":1,\"b\":2}", this.runtime.toJsonString( value ) );
    }
}
