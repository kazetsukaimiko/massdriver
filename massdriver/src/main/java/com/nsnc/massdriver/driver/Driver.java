package com.nsnc.massdriver.driver;

import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.asset.AssetRepository;
import com.nsnc.massdriver.chunk.ChunkRepository;


// TODO: Smart Lookups By Trait, URI, etc
public interface Driver<ID> extends AssetRepository<ID, Asset>, ChunkRepository<ID, Chunk> {
}
