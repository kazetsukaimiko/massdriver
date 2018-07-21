package com.nsnc.massdriver.cache;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.asset.FileAsset;

import java.util.List;

public interface DescriptionCache<AI> {
    List<Description> get(Asset<AI> asset);
}
