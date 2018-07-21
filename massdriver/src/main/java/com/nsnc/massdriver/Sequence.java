package com.nsnc.massdriver;

public class Sequence {
    private long position;
    private Description last;
    private Description current;
    private Description next;

    public Sequence(long position, Description last, Description current, Description next) {
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

    public Description getLast() {
        return last;
    }

    public void setLast(Description last) {
        this.last = last;
    }

    public Description getCurrent() {
        return current;
    }

    public void setCurrent(Description current) {
        this.current = current;
    }

    public Description getNext() {
        return next;
    }

    public void setNext(Description next) {
        this.next = next;
    }
}
