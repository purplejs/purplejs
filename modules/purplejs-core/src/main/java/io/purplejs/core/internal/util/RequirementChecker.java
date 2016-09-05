package io.purplejs.core.internal.util;

import java.util.Properties;

public final class RequirementChecker
{
    private final static int JAVA_UPDATE_MIN = 92;

    private final Properties properties;

    RequirementChecker( final Properties properties )
    {
        this.properties = properties;
    }

    void doCheck()
    {
        checkJavaVersion();
    }

    private void checkJavaVersion()
    {
        final JavaVersion version = new JavaVersion( this.properties );
        if ( version.isJava9() )
        {
            return;
        }

        if ( !version.isJava8() )
        {
            throw throwJavaVersionRequirements( version );
        }

        if ( version.getUpdate() < JAVA_UPDATE_MIN )
        {
            throw throwJavaVersionRequirements( version );
        }
    }

    private RuntimeException throwJavaVersionRequirements( final JavaVersion version )
    {
        return new RuntimeException(
            String.format( "Java 1.8 update %s and above is required. You are running %s.", JAVA_UPDATE_MIN, version ) );
    }

    public static void check()
    {
        new RequirementChecker( System.getProperties() ).doCheck();
    }
}
