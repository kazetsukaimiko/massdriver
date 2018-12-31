package com.nsnc.massdriver.web;

import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.asset.AssetSource;
import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.ChunkMetadata;
import com.nsnc.massdriver.chunk.ChunkSource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Path(WebSource.ROOT)
public interface WebSource extends AssetSource, ChunkSource {

    String ROOT = "/source";

    String BY_PATH = "/by";

    String CHUNK_PATH = "/chunks";
    String CHUNK_BY_DESC_PATH = CHUNK_PATH+ BY_PATH + "/description";
    String CHUNKS_BY_TRAITS_PATH = CHUNK_PATH + BY_PATH + "/traits";

    String ASSET_PATH = "/assets";
    String ASSET_BY_DESC_PATH = ASSET_PATH + BY_PATH + "/description";
    String ASSETS_BY_TRAITS_PATH = ASSET_PATH + BY_PATH + "/traits";


    @POST
    @Path(ASSET_BY_DESC_PATH)
    @Override
    Optional<Asset> retrieveAsset(List<Trait> description);

    @POST
    @Path(ASSETS_BY_TRAITS_PATH)
    @Override
    Stream<Asset> findAssetsByTraits(Trait... traits);

    @POST
    @Path(CHUNK_BY_DESC_PATH)
    @Override
    Optional<Chunk> retrieveChunk(ChunkMetadata chunkMetadata);

    /*
    @POST
    @Path(CHUNKS_BY_TRAITS_PATH)
    @Override
    Stream<Chunk> findChunksByTraits(Trait... traits);
    */
}
