package com.nsnc.massdriver.asset;

import java.io.IOException;
import java.util.UUID;

public interface AssetRepository<AI, AT extends Asset> extends AssetSink<AI, AT>, AssetSource<AI, AT> {
    AT renameAsset(UUID assetId1, String baseContent1) throws IOException;
}
