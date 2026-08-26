package com.mingye.model;

public class Grid {

    private final int x;

    private final int y;

    private GridTypeEnum gridType;

    private Piece piece;

    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GridTypeEnum getGridType() {
        return gridType;
    }

    public void setGridType(GridTypeEnum gridType) {
        this.gridType = gridType;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
