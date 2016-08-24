package io.purplejs.http.internal.websocket;

import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import io.purplejs.http.websocket.WebSocketSession;

public final class WebSocketRegistryImpl
    implements WebSocketRegistry
{
    private final Map<String, WebSocketSession> idToSession;

    private final Multimap<String, WebSocketSession> groupToId;

    public WebSocketRegistryImpl()
    {
        this.idToSession = Maps.newConcurrentMap();
        this.groupToId = Multimaps.synchronizedMultimap( HashMultimap.create() );
    }

    @Override
    public WebSocketSession getSession( final String id )
    {
        return this.idToSession.get( id );
    }

    @Override
    public Stream<WebSocketSession> getByGroup( final String group )
    {
        return this.groupToId.get( group ).stream();
    }

    @Override
    public void add( final WebSocketSession session )
    {
        this.idToSession.put( session.getId(), session );
        this.groupToId.put( session.getGroup(), session );
    }

    @Override
    public void remove( final WebSocketSession session )
    {
        this.idToSession.remove( session.getId() );
        this.groupToId.remove( session.getGroup(), session );
    }
}
