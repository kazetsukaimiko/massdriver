package com.nsnc.massdriver.asset;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;

import java.util.Optional;
import java.util.stream.Stream;

public interface AssetSource {
    Optional<Asset> retrieveAsset(Description description);
    Stream<Asset> findAssetsByTraits(Trait... traits);
}
