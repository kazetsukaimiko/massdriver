package com.nsnc.massdriver.asset;

import com.jcabi.urn.URN;
import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;

import java.io.IOException;
import java.util.List;

/**
 * Created by luna on 8/8/17.
 */
public interface Asset {
    // A human readable representation of an Asset.
    String getName();

    // Generate a URN for the asset
    String getUrn();

    // The Content Type of the Asset Data.
    String getContentType();

    // The size of the asset.
    long getSize();

    // A Cumulation of Traits that make up a unique identifier.
    Description getDescription();

    // This is the context-agnostic logical connection of the Asset to its Chunks.
    List<Description> getChunkInfo() throws IOException;

    default String makeUrn(String name, String contentType, Description description) {
        if (description == null) {
            return null;
        }
        URN urn = new URN("massdriver", "asset");
        if (name != null) {
            urn = urn.param("xt", name);
        }
        if (contentType != null) {
            urn = urn.param("contentType", contentType);
        }
        for (Trait trait : description.getTraits()) {
            urn = urn.param(String.valueOf(trait.getName()).replaceAll("-", "_"), trait.getContent());
        }
        return urn.toString();
    }
}
