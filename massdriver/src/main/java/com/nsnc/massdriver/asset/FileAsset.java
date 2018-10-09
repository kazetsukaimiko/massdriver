package com.nsnc.massdriver.asset;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.ChunkUtils;
import com.nsnc.massdriver.chunk.MemoryChunk;
import com.nsnc.massdriver.crypt.CryptUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileAsset extends BaseAsset implements Asset {
    private final Path id;

    public FileAsset(Path id) throws IOException {
        this.id = id;
        setSize(Files.size(id));
        setName(id.getFileName().toString());
        setContentType(Files.probeContentType(id));
        setDescription(CryptUtils.makeDescription(id));
        setUrn(makeUrn(
                getName(),
                getContentType(),
                getDescription()
        ));
    }

    @Override
    public List<Description> getChunkInfo() throws IOException {
        return ChunkUtils.chunkStream(id)
                .map(Chunk::getDescription)
                .collect(Collectors.toList());
    }
}
