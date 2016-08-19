package io.purplejs.http

import spock.lang.Specification

class StatusTest
    extends Specification
{
    def "getCode"()
    {
        when:
        def status = Status.OK;

        then:
        status.code == 200;
    }

    def "getReasonPhrase"()
    {
        when:
        def status = Status.OK;

        then:
        status.reasonPhrase == 'OK';
    }

    def "from"()
    {
        when:
        def status = Status.from( 200 );

        then:
        status != null;
        status == Status.OK;
    }

    def "from unknown"()
    {
        when:
        Status.from( 600 );

        then:
        thrown IllegalArgumentException;
    }

    def "status type"()
    {
        expect:
        status.isInformational() == informational;
        status.isSuccessful() == successful;
        status.isRedirection() == redirection;
        status.isClientError() == clientError;
        status.isServerError() == serverError;

        where:
        status                       | informational | successful | redirection | clientError | serverError
        Status.CONTINUE              | true          | false      | false       | false       | false
        Status.OK                    | false         | true       | false       | false       | false
        Status.SEE_OTHER             | false         | false      | true        | false       | false
        Status.BAD_REQUEST           | false         | false      | false       | true        | false
        Status.INTERNAL_SERVER_ERROR | false         | false      | false       | false       | true
    }
}
