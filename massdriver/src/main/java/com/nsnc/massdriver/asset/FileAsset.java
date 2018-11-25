package com.nsnc.massdriver.asset;

import com.nsnc.massdriver.chunk.ChunkMetadata;
import com.nsnc.massdriver.chunk.ChunkUtils;
import com.nsnc.massdriver.crypt.CryptUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileAsset extends BaseAsset implements Asset {
    private static final Logger LOGGER = Logger.getLogger(FileAsset.class.getName());
    private final Path path;

    public FileAsset(Path path) throws IOException {
        this.path = path;
        setSize(Files.size(path));
        setName(path.getFileName().toString());
        setContentType(Files.probeContentType(path));
        setTraits(CryptUtils.makeDescription(path));
        setUrn(makeUrn(
                getName(),
                getContentType(),
                getTraits()
        ));
    }

    @Override
    public List<ChunkMetadata> getChunkMetadata() {
        if (super.getChunkMetadata() == null) {
            try {
                setChunkMetadata(ChunkUtils.chunkStream(path)
                        .map(ChunkMetadata.class::cast)
                        .collect(Collectors.toList()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return super.getChunkMetadata();
    }

    public Path getPath() {
        return path;
    }
}
