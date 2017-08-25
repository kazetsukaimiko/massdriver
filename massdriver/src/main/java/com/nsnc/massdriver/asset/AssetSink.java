package com.nsnc.massdriver.asset;

import java.io.IOException;

public interface AssetSink<AI, AT extends Asset> {
    AI persistAsset(Asset asset) throws IOException;
    AI persistAsset(AI id, Asset asset) throws IOException;
}
