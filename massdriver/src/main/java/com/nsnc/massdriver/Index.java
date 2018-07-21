package com.nsnc.massdriver;

import java.nio.file.Path;
import java.util.List;

public class Index {
    private Path aPath;
    private List<Sequence> sequences;



    public List<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }
}
