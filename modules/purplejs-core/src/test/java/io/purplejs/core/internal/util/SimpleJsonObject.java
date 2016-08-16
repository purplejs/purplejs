package io.purplejs.core.internal.util;

import io.purplejs.core.json.JsonGenerator;
import io.purplejs.core.json.JsonSerializable;

public final class SimpleJsonObject
    implements JsonSerializable
{
    @Override
    public void serialize( final JsonGenerator gen )
    {
        gen.map();
        gen.value( "a", 1 );
        gen.value( "b", 2 );
        gen.end();
    }
}
