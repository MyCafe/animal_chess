package com.mingye.model;

import com.mingye.constants.PieceConstants;

/**
 * 棋子类
 */
public class Piece implements Comparable<Piece> {

    private String pieceName;

    private String color;

    /**
     * 战斗力
     */
    private int power;

    public Piece() {

    }

    public Piece(String pieceName, String color) {
        this.pieceName = pieceName;
        this.color = color;
    }

    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public int compareTo(Piece o) {
        if (this.getPower() == PieceConstants.MIN_ANIMAL_POWER && o.getPower() == PieceConstants.MAX_ANIMAL_POWER) {
            return 1;
        }
        if (this.getPower() == PieceConstants.MAX_ANIMAL_POWER && o.getPower() == PieceConstants.MIN_ANIMAL_POWER) {
            return -1;
        }
        if (this.getPower() > o.getPower()) {
            return 1;
        } else if (this.getPower() < o.getPower()) {
            return -1;
        }
        return 0;
    }
}
