package com.nsnc.massdriver.index;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Identifier;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.PathAsset;
import org.dizitart.no2.objects.Id;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Indexes are existing, local, binary data that doesn't need to be chunked.
 * We can already fetch chunks-
 */
public interface Index extends Asset, Identifier<Path> {

    @Override
    public Path getId();

    @Override
    public void setId(Path id);

    @Override
    public Path fromString(String input);

    @Override
    public String toString();
}
