package com.nsnc.massdriver.asset;

import com.nsnc.massdriver.Trait;

import java.util.stream.Stream;

public interface AssetSource<AI, AT extends Asset> {
    AT retrieveAsset(AI assetIdentifier);
    Stream<AT> findAssets(String assetName, Trait... traits);
}
