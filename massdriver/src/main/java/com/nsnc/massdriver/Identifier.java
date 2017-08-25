package com.nsnc.massdriver;

/**
 * Created by luna on 8/8/17.
 * Abstracts Assets and Chunks from the implementing Datastore.
 */
public interface Identifier<IT> {
    public IT getId();
    public void setId(IT id);

    public String toString();
    public IT fromString(String input);
}
