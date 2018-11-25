package com.nsnc.massdriver.asset;

import com.nsnc.massdriver.Trait;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface AssetSource {
    Optional<Asset> retrieveAsset(List<Trait> traits);
    Stream<Asset> findAssetsByTraits(Trait... traits);
    Stream<Asset> allAssets();
}
