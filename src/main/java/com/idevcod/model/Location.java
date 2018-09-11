package com.idevcod.model;

public class Location {
    private Position start;
    private Position end;

    public Position getStart() {
        return start;
    }

    public void setStart(Position start) {
        this.start = start;
    }

    public Position getEnd() {
        return end;
    }

    public void setEnd(Position end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Location{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
