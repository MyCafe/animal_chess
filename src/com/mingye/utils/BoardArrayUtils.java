package com.mingye.utils;

import com.mingye.constants.GameConstants;
import com.mingye.constants.PieceConstants;
import com.mingye.model.Grid;
import com.mingye.model.GridTypeEnum;
import com.mingye.model.Piece;
import com.mingye.service.GameService;

public final class BoardArrayUtils {

    /**
     * 根据点击的坐标获取对应的格子
     *
     * @param gridArr
     * @param x
     * @param y
     * @return
     */
    public static Grid getGridByLocation(Grid[][] gridArr, int x, int y) {
        int margin = GameConstants.MARGIN;
        // 获取行数和列数
        int rows = gridArr.length;
        int cols = gridArr[0].length;

        Grid resultGrid = null;
        l: for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int gridX1 = j * GameConstants.GRID_WIDTH_HEIGHT + margin;
                int gridY1 = i * GameConstants.GRID_WIDTH_HEIGHT + margin;
                int gridX2 = gridX1 + GameConstants.GRID_WIDTH_HEIGHT;
                int gridY2 = gridY1 + GameConstants.GRID_WIDTH_HEIGHT;
                if (x >= gridX1 && x < gridX2 && y >= gridY1 && y < gridY2) {
                    resultGrid = gridArr[i][j];
                    break l;
                }
            }
        }
        return resultGrid;
    }

    /**
     * 移动棋子并设置战斗力
     *
     * @param piece
     * @param srcGird
     * @param targetGird
     */
    public static void movePiece(Piece piece, Grid srcGird, Grid targetGird) {
        targetGird.setPiece(piece);
        srcGird.setPiece(null);

        GridTypeEnum targetGridType = targetGird.getGridType();
        if (GridTypeEnum.BLACK_TRAP.equals(targetGridType) && GameService.RED_PIECE.equals(piece.getColor())
                || GridTypeEnum.RED_TRAP.equals(targetGridType) && GameService.BLACK_PIECE.equals(piece.getColor())) {
            // 进入敌方陷阱
            System.out.println("进入敌方陷阱，战斗力变为0");
            piece.setPower(PieceConstants.NO_POWER);
        } else {
            if (piece.getPower() == PieceConstants.NO_POWER) {
                // 走出陷阱后，恢复战斗力
                System.out.println("已走出陷阱后，恢复战斗力");
                piece.setPower(PieceConstants.getAnimalPowerMap().get(piece.getPieceName()));
            }
        }
    }

    public static boolean isAllRiverBetween(Grid src, Grid target, Grid[][] gridArr) {
        int x1 = src.getX();
        int y1 = src.getY();
        int x2 = target.getX();
        int y2 = target.getY();

        // 同一行
        if (y1 == y2) {
            int minX = Math.min(x1, x2);
            int maxX = Math.max(x1, x2);
            for (int x = minX + 1; x < maxX; x++) {
                Grid grid = gridArr[y1][x];
                if (!GridTypeEnum.RIVER.equals(grid.getGridType())) {
                    return false;
                }
            }
            return true;
        }

        // 同一列
        if (x1 == x2) {
            int minY = Math.min(y1, y2);
            int maxY = Math.max(y1, y2);
            for (int y = minY + 1; y < maxY; y++) {
                Grid grid = gridArr[y][x1];
                if (!GridTypeEnum.RIVER.equals(grid.getGridType())) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    /**
     * 校验两点之间河流内是否存在老鼠（有老鼠则狮虎不能跳河）
     *
     * @param src
     * @param target
     * @param gridArr
     * @return
     */
    public static boolean hasMouseInRiver(Grid src, Grid target, Grid[][] gridArr) {
        int x1 = src.getX();
        int y1 = src.getY();
        int x2 = target.getX();
        int y2 = target.getY();

        if (y1 == y2) {
            int minX = Math.min(x1, x2);
            int maxX = Math.max(x1, x2);
            for (int x = minX + 1; x < maxX; x++) {
                Grid grid = gridArr[y1][x];
                if (grid.getPiece() != null) {
                    return true;
                }
            }
        }

        if (x1 == x2) {
            int minY = Math.min(y1, y2);
            int maxY = Math.max(y1, y2);
            for (int y = minY + 1; y < maxY; y++) {
                Grid grid = gridArr[y][x1];
                if (grid.getPiece() != null) {
                    return true;
                }
            }
        }

        return false;
    }
}
