package com.nsnc.massdriver.asset;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.cache.DescriptionCache;
import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.ChunkUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FileAsset extends BaseAsset<Path> implements Asset<Path> {
    private final Path id;
    private final DescriptionCache<Path> descriptionCache;

    @Override
    public Path getId() {
        return id;
    }

    @Override
    public FileAsset ofString(String input) {
        return new FileAsset(Paths.get(input), descriptionCache);
    }

    @Override
    public String asString() {
        return Optional.of(id)
                .map(Path::toString)
                .orElse(null);
    }

    @Override
    public List<Description> getChunkInfo() {
        return descriptionCache.get(this);
    }

    @Override
    public Stream<? extends Chunk> stream() throws IOException {
        return ChunkUtils.chunkStream(getId());
    }

    public FileAsset(Path id, DescriptionCache<Path> descriptionCache) {
        this.id = id;
        this.descriptionCache = descriptionCache;
    }
}
