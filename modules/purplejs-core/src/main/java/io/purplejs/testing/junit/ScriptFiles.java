package io.purplejs.testing.junit;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptFiles
{
    String[] value();
}
