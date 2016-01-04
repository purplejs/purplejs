package org.purplejs.http;

import java.util.List;
import java.util.Map;

public interface Parameters
{
    Map<String, List<String>> asMap();
}
