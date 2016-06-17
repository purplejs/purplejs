package io.purplejs.http;

import java.util.Optional;

public interface MultipartForm
    extends Iterable<MultipartItem>
{
    boolean isEmpty();

    int getSize();

    Optional<MultipartItem> get( String name );

    void delete();
}
