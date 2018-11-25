package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.crypt.KeyPairPool;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ChunkSource {
    Optional<Location> chunkLocation(List<Trait> chunkTraits);
    Optional<Chunk> retrieveChunk(ChunkMetadata chunkMetadata);
    default Chunk retrieveChunkOrInterrupt(ChunkMetadata chunkMetadata) throws UnresolvableChunkException {
        try {
            Optional<Chunk> local = retrieveChunk(chunkMetadata);
            if (!local.isPresent()) {
                throw new IOException("No local chunk");
            }
            return local.get();
        } catch (IOException ioe) {
            throw new UnresolvableChunkException(chunkMetadata, ioe);
        }
    }

    Stream<Location> allLocations();

    // TODO: Explain
    //Stream<Chunk> findChunksByTraits(Trait... traits);

    // TODO: ChunkMetadata, not Chunk.
    Chunk encrypt(Chunk chunk, KeyPairPool KeyPairPool);
    Chunk decrypt(Chunk encryptedChunk, KeyPairPool KeyPairPool);
}
