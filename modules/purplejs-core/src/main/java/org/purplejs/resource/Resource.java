package org.purplejs.resource;

import java.time.Instant;

import com.google.common.io.ByteSource;

public interface Resource
{
    ResourcePath getPath();

    boolean exists();

    long getSize();

    Instant getLastModified();

    ByteSource getBytes();
}
