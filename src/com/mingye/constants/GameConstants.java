package com.mingye.constants;

public interface GameConstants {

    /**
     * 游戏名称
     */
    String GAME_TITLE = "斗兽棋";

    /**
     * 菜单宽度
     */
    int WINDOW_WIDTH = 800;

    /**
     * 菜单高度
     */
    int WINDOW_HEIGHT = 800;

    /**
     * 网格的宽度或高度
     */
    int GRID_WIDTH_HEIGHT = 80;

    /**
     * 棋子的半径
     */
    int PIECE_RADIUS = 60;

    /**
     * 陷阱的宽度或高度
     */
    int TRAP_WIDTH_HEIGHT = 70;

    /**
     * 兽穴的宽度或高度
     */
    int CAVE_WIDTH_HEIGHT = 70;

    /**
     * 棋盘行数
     */
    int ROW_COUNT = 9;

    /**
     * 棋盘列数
     */
    int COL_COUNT = 7;

    /**
     * 棋盘的宽度
     */
    int BOARD_WIDTH = COL_COUNT * GRID_WIDTH_HEIGHT;

    /**
     * 棋盘的高度
     */
    int BOARD_HEIGHT = ROW_COUNT * GRID_WIDTH_HEIGHT;

    int MARGIN = 40;

}
