package org.purplejs.resource;

import java.net.URL;

import com.google.common.io.ByteSource;

public interface Resource
{
    ResourcePath getPath();

    URL getUrl();

    long getSize();

    long getLastModified();

    ByteSource getBytes();
}
