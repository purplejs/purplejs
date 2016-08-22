package io.purplejs.http.internal.error

import com.google.common.collect.Lists
import io.purplejs.core.resource.ResourcePath
import io.purplejs.http.Status
import io.purplejs.http.mock.MockRequest
import spock.lang.Specification

class ErrorInfoImplTest
    extends Specification
{
    def "accessors"()
    {
        setup:
        def info = new ErrorInfoImpl();

        when:
        info.lines = Lists.newArrayList( '1', '2' );
        info.path = ResourcePath.from( '/test.js' );
        info.cause = new IOException();
        info.request = new MockRequest();
        info.status = Status.NOT_FOUND;

        then:
        info.lines != null;
        info.path != null;
        info.cause != null;
        info.request != null;
        info.status == Status.NOT_FOUND;
    }
}
