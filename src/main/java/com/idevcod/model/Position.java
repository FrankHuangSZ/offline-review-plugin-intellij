package com.idevcod.model;

public class Position {
    private int row = 0;
    private int column = 0;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position(String pos) {
        String posArray[] = pos.split(":");
        if (posArray.length == 2) {
            row = Integer.valueOf(posArray[0]);
            column = Integer.valueOf(posArray[1]);
        }
    }

    public String getPosition() {
        return String.format("%s:%s", row, column);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Position{" +
            "row=" + row +
            ", column=" + column +
            '}';
    }
}
