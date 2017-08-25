package com.nsnc.massdriver.asset;

import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.Description;
import sun.security.krb5.internal.crypto.Des;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by luna on 8/8/17.
 */
public interface Asset {
    public String getName();

    public String getUrn();

    public String getContentType();

    public long getSize();

    public Description getDescription();

    public List<Description> getChunkInfo();

    public Stream<? extends Chunk> stream() throws IOException;
}
