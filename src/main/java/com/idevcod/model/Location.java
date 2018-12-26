package com.idevcod.model;

public class Location {
    private Position start = new Position(0, 0);
    private Position end = new Position(0, 0);

    public Location(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public Location(String location) {
        String pos[] = location.split("-");
        if (pos.length == 2) {
            start = new Position(pos[0]);
            end = new Position(pos[1]);
        }
    }


    public String getLocation() {
        return String.format("%s:%s-%s:%s", start.getRow(), start.getColumn(), end.getRow(), end.getColumn());
    }


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
