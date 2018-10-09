package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;

import java.util.Optional;
import java.util.stream.Stream;

public interface ChunkSource {
    Optional<Chunk> retrieveChunk(Description chunkDescription);
    Stream<Chunk> findChunksByTraits(Trait... traits);
}
