package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.crypt.KeyPairPool;

import java.util.Optional;
import java.util.stream.Stream;

public interface ChunkSource {
    Optional<Chunk> retrieveChunk(Description chunkDescription);
    Stream<Chunk> findChunksByTraits(Trait... traits);

    //Chunk encrypt(Chunk chunk, KeyPairPool KeyPairPool);
    //Chunk decrypt(Chunk encryptedChunk, KeyPairPool KeyPairPool);
}
