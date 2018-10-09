package com.nsnc.massdriver.asset;

import com.nsnc.massdriver.Description;

import java.io.IOException;

public interface AssetSink {
    Description persistAsset(Asset asset) throws IOException;
}
