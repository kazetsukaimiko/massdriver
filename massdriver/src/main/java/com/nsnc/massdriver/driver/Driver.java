package com.nsnc.massdriver.driver;

import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.asset.AssetSink;
import com.nsnc.massdriver.asset.AssetSource;
import com.nsnc.massdriver.chunk.ChunkSink;
import com.nsnc.massdriver.chunk.ChunkSource;

import java.io.IOException;
import java.nio.file.Path;

public interface Driver extends AssetSink, AssetSource, ChunkSink, ChunkSource {
    Asset persist(Path path) throws IOException;
}
