package com.nsnc.massdriver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by luna on 8/2/17.
 * Chunk Identifier-
 * Contains three indexes (order, length, previous sum)
 * and a list of Trait identifiers.
 *
 * This class determines the "identity" of a Chunk via hashing.
 * When a FileAsset changes size, a new Partial FileAsset is created.
 */
public class Description { // implements Comparable<Description> {

    private List<Trait> traits = new ArrayList<>();

    public List<Trait> getTraits() {
        return traits;
    }

    public void setTraits(List<Trait> traits) {
        this.traits = traits;
    }

    public Description() {

    }

    public Description(Collection<Trait> traits) {
        this.traits = new ArrayList<>(traits);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        if (that.getTraits() == null || that.getTraits().size() ==0) {
            return false;
        }
        if (getTraits() == null || getTraits().size() ==0) {
            return false;
        }
        for (Trait trait : traits) {
            if (that.getTraits() == null || !that.getTraits().contains(trait)) {
                return false;
            }
        }
        for (Trait trait : that.getTraits()) {
            if (getTraits() == null || !getTraits().contains(trait)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(traits);
    }

}
