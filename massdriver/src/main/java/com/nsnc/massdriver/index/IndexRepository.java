package com.nsnc.massdriver.index;

public interface IndexRepository<AT extends Index> extends IndexSink<AT>, IndexSource<AT> {
}
