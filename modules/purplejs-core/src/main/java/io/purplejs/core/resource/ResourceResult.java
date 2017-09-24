package io.purplejs.core.resource;

import java.util.List;

import javax.xml.ws.Provider;

// TODO: Implement as mutable class.
public interface ResourceResult
    extends Provider<ResourcePath>
{
    ResourcePath getFound();

    List<ResourcePath> getScanned();
}
