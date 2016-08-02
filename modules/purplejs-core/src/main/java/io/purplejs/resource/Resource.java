package io.purplejs.resource;

import com.google.common.io.ByteSource;

public interface Resource
{
    ResourcePath getPath();

    long getSize();

    long getLastModified();

    ByteSource getBytes();
}
