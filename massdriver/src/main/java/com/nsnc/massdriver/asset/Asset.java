package com.nsnc.massdriver.asset;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.chunk.Chunk;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by luna on 8/8/17.
 */
public interface Asset<AI> {
    AI getId();

    Asset<AI> ofString(String input);

    String asString();

    String getName();

    String getUrn();

    String getContentType();

    long getSize();

    Description getDescription();

    List<Description> getChunkInfo();

    Stream<? extends Chunk> stream() throws IOException;


}
