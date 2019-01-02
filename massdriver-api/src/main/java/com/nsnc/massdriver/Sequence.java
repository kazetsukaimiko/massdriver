package com.nsnc.massdriver;

import java.util.List;

public class Sequence {
    private long position;
    private List<Trait> last;
    private List<Trait> current;
    private List<Trait> next;

    public Sequence(long position, List<Trait> last, List<Trait> current, List<Trait> next) {
        this.position = position;
        this.last = last;
        this.current = current;
        this.next = next;
    }

    public Sequence() {
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public List<Trait> getLast() {
        return last;
    }

    public void setLast(List<Trait> last) {
        this.last = last;
    }

    public List<Trait> getCurrent() {
        return current;
    }

    public void setCurrent(List<Trait> current) {
        this.current = current;
    }

    public List<Trait> getNext() {
        return next;
    }

    public void setNext(List<Trait> next) {
        this.next = next;
    }
}
