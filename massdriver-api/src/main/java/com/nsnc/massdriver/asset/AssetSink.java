package com.nsnc.massdriver.asset;

import com.nsnc.massdriver.Trait;

import java.io.IOException;
import java.util.List;

public interface AssetSink {
    List<Trait> persistAsset(Asset asset) throws IOException;
    List<Trait> removeAsset(Asset asset);
}
