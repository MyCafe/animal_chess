package com.mingye.service;

import com.mingye.constants.GameConstants;
import com.mingye.constants.PieceConstants;
import com.mingye.model.Grid;
import com.mingye.model.GridTypeEnum;
import com.mingye.model.Piece;
import com.mingye.utils.BoardArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameService {

    /**
     * 红棋类型
     */
    public static final String RED_PIECE = "red";

    /**
     * 黑棋类型
     */
    public static final String BLACK_PIECE = "black";

    /**
     * 游戏是否已结束
     */
    private boolean gameOverFlag;

    private Grid[][] boardArr;

    private List<Piece> pieceList;

    private List<Grid> trapGridList;

    private List<Grid> caveGridList;

    /**
     * 当前玩家
     */
    private String currentPlayerColor;

    private Piece selectedPiece;

    private Grid gridOfSelectedPiece;


    public GameService() {
        this.currentPlayerColor = RED_PIECE;
        initBoardArr();
        initRiver();
        initTrap();
        initCave();
        initPiece();
    }

    /**
     * 初始化棋盘
     */
    private void initBoardArr() {
        final int rowCount = GameConstants.ROW_COUNT;
        final int colCount = GameConstants.COL_COUNT;
        boardArr = new Grid[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                int x = j;
                int y = i;
                Grid grid = new Grid(x, y);
                // 默认都是陆地
                grid.setGridType(GridTypeEnum.LAND);
                boardArr[i][j] = grid;
            }
        }
    }

    /**
     * 初始化小河
     */
    private void initRiver() {
        // 左侧河流 (列 1-2)
        for (int i = 3; i <= 5; i++) {
            for (int j = 1; j <= 2; j++) {
                boardArr[i][j].setGridType(GridTypeEnum.RIVER);
            }
        }
        // 右侧河流 (列 4-5)
        for (int i = 3; i <= 5; i++) {
            for (int j = 4; j <= 5; j++) {
                boardArr[i][j].setGridType(GridTypeEnum.RIVER);
            }
        }
    }

    /**
     * 初始化陷阱
     */
    private void initTrap() {
        trapGridList = new ArrayList<>();

        // 黑方陷阱
        List<Grid> blackTrapGridList = Arrays.asList(
                boardArr[0][2], boardArr[0][4], boardArr[1][3]
        );
        for (Grid grid : blackTrapGridList) {
            grid.setGridType(GridTypeEnum.BLACK_TRAP);
        }
        trapGridList.addAll(blackTrapGridList);

        // 红方陷阱
        List<Grid> redTrapGridList = Arrays.asList(
                boardArr[8][2], boardArr[8][4], boardArr[7][3]
        );
        for (Grid grid : redTrapGridList) {
            grid.setGridType(GridTypeEnum.RED_TRAP);
        }
        trapGridList.addAll(redTrapGridList);
    }

    /**
     * 初始化兽穴
     */
    private void initCave() {
        caveGridList = new ArrayList<>();
        // 黑方兽穴
        Grid blackCaveGrid = boardArr[0][3];
        blackCaveGrid.setGridType(GridTypeEnum.BLACK_CAVE);
        caveGridList.add(blackCaveGrid);

        Grid redCaveGrid = boardArr[8][3];
        redCaveGrid.setGridType(GridTypeEnum.RED_CAVE);
        caveGridList.add(redCaveGrid);
    }

    /**
     * 初始化棋子
     */
    private void initPiece() {
        pieceList = new ArrayList<>(16);
        // 红方
        Piece redMouse = new Piece(PieceConstants.MOUSE, RED_PIECE);
        pieceList.add(redMouse);
        Piece redCat = new Piece(PieceConstants.CAT, RED_PIECE);
        pieceList.add(redCat);
        Piece redDog = new Piece(PieceConstants.DOG, RED_PIECE);
        pieceList.add(redDog);
        Piece redWolf = new Piece(PieceConstants.WOLF, RED_PIECE);
        pieceList.add(redWolf);
        Piece redLeopard = new Piece(PieceConstants.LEOPARD, RED_PIECE);
        pieceList.add(redLeopard);
        Piece redTiger = new Piece(PieceConstants.TIGER, RED_PIECE);
        pieceList.add(redTiger);
        Piece redLion = new Piece(PieceConstants.LION, RED_PIECE);
        pieceList.add(redLion);
        Piece redElephant = new Piece(PieceConstants.ELEPHANT, RED_PIECE);
        pieceList.add(redElephant);

        // 黑方
        Piece blackMouse = new Piece(PieceConstants.MOUSE, BLACK_PIECE);
        pieceList.add(blackMouse);
        Piece blackCat = new Piece(PieceConstants.CAT, BLACK_PIECE);
        pieceList.add(blackCat);
        Piece blackDog = new Piece(PieceConstants.DOG, BLACK_PIECE);
        pieceList.add(blackDog);
        Piece blackWolf = new Piece(PieceConstants.WOLF, BLACK_PIECE);
        pieceList.add(blackWolf);
        Piece blackLeopard = new Piece(PieceConstants.LEOPARD, BLACK_PIECE);
        pieceList.add(blackLeopard);
        Piece blackTiger = new Piece(PieceConstants.TIGER, BLACK_PIECE);
        pieceList.add(blackTiger);
        Piece blackLion = new Piece(PieceConstants.LION, BLACK_PIECE);
        pieceList.add(blackLion);
        Piece blackElephant = new Piece(PieceConstants.ELEPHANT, BLACK_PIECE);
        pieceList.add(blackElephant);

        Map<String, Integer> animalPowerMap = PieceConstants.getAnimalPowerMap();
        for (Piece piece : pieceList) {
            piece.setPower(animalPowerMap.get(piece.getPieceName()));
        }

        // 将棋子放在指定的网格中
        boardArr[0][0].setPiece(blackLion);
        boardArr[0][6].setPiece(blackTiger);
        boardArr[1][1].setPiece(blackDog);
        boardArr[1][5].setPiece(blackCat);
        boardArr[2][0].setPiece(blackMouse);
        boardArr[2][2].setPiece(blackLeopard);
        boardArr[2][4].setPiece(blackWolf);
        boardArr[2][6].setPiece(blackElephant);

        boardArr[6][0].setPiece(redElephant);
        boardArr[6][2].setPiece(redWolf);
        boardArr[6][4].setPiece(redLeopard);
        boardArr[6][6].setPiece(redMouse);
        boardArr[7][1].setPiece(redCat);
        boardArr[7][5].setPiece(redDog);
        boardArr[8][0].setPiece(redTiger);
        boardArr[8][6].setPiece(redLion);

    }

    public boolean isGameOverFlag() {
        return gameOverFlag;
    }

    public Grid[][] getBoardArr() {
        return boardArr;
    }

    public String getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    public List<Piece> getPieceList() {
        return pieceList;
    }

    public List<Grid> getTrapGridList() {
        return trapGridList;
    }

    public List<Grid> getCaveGridList() {
        return caveGridList;
    }

    public Grid getGridOfSelectedPiece() {
        return gridOfSelectedPiece;
    }

    /**
     * 切换棋子类型
     */
    public void changePlayer() {
        if (RED_PIECE.equals(currentPlayerColor)) {
            currentPlayerColor = BLACK_PIECE;
        } else {
            currentPlayerColor = RED_PIECE;
        }
        selectedPiece = null;
        gridOfSelectedPiece = null;
        System.out.println("=============");
        System.out.println("当前玩家：" + (RED_PIECE.equals(currentPlayerColor) ? "红棋" : "黑棋"));
    }

    /**
     * 检查游戏是否结束
     *
     * @return
     */
    public String checkWin() {
        List<Piece> redPieceList = pieceList.stream().filter(x -> RED_PIECE.equals(x.getColor())).collect(Collectors.toList());
        if (redPieceList.isEmpty()) {
            gameOverFlag = true;
            return BLACK_PIECE;
        }
        List<Piece> blackPieceList = pieceList.stream().filter(x -> BLACK_PIECE.equals(x.getColor())).collect(Collectors.toList());
        if (blackPieceList.isEmpty()) {
            gameOverFlag = true;
            return RED_PIECE;
        }

        // 判断双方兽穴上有没有棋子
        for (Grid grid : caveGridList) {
            if (GridTypeEnum.RED_CAVE.equals(grid.getGridType())
                    && grid.getPiece() != null) {
                gameOverFlag = true;
                return BLACK_PIECE;
            }

            if (GridTypeEnum.BLACK_CAVE.equals(grid.getGridType())
                && grid.getPiece() != null) {
                gameOverFlag = true;
                return RED_PIECE;
            }
        }
        return null;
    }


    /**
     * 重开游戏
     */
    public void restartGame() {
        // 初始化棋盘
        for (Grid[] grids : boardArr) {
            for (Grid grid : grids) {
                grid.setPiece(null);
            }
        }

        // 初始化棋子
        pieceList.clear();
        initPiece();

        currentPlayerColor = RED_PIECE;
        selectedPiece = null;
        gridOfSelectedPiece = null;
        gameOverFlag = false;
    }

    /**
     * 游戏事件
     *
     * @param targetGrid
     * @return true 表示当前玩家结束，需切换玩家
     * false 表示当前玩家没结束，继续操作
     */
    public boolean doPlay(Grid targetGrid) {
        // 原地无效点击的判断
        if (gridOfSelectedPiece != null && gridOfSelectedPiece == targetGrid) {
            return false;
        }

        Piece targetPiece = targetGrid.getPiece();
        // 目标格子上有棋子
        if (targetPiece != null) {
            // 是己方棋子的情况
            if (targetPiece.getColor().equals(currentPlayerColor)) {
                selectedPiece = targetPiece;
                gridOfSelectedPiece = targetGrid;
                return false;
            }

            // 是对方棋子的情况
            if (selectedPiece != null) {
                // 判断当前棋子是否可达对方棋子位置 且 战斗力比目标棋子强
                if (checkGridCanMove(gridOfSelectedPiece, targetGrid) && selectedPiece.compareTo(targetPiece) >= 0) {
                    // 击杀对方
                    pieceList.remove(targetPiece);
                    // 移动当前棋子
                    BoardArrayUtils.movePiece(selectedPiece, gridOfSelectedPiece, targetGrid);
                    return true;
                } else {
                    System.out.println("己方棋子战斗力低或位置不可达");
                    return false;
                }
            } else {
                System.out.println("不能选择对方的棋子");
                return false;
            }
        }

        // 目标格子上没有棋子
        // 如果自己选中了棋子并可达目标位置
        if (selectedPiece != null && checkGridCanMove(gridOfSelectedPiece, targetGrid)) {
            // 移动自己的棋子
            BoardArrayUtils.movePiece(selectedPiece, gridOfSelectedPiece, targetGrid);
            return true;
        }

        System.out.println("当前玩家未选择棋子或目标位置不可达");
        return false;
    }

    /**
     * 判断棋盘中两个格子是否相邻
     *
     * @param srcGrid
     * @param targetGrid
     * @return
     */
    private boolean checkGridCanMove(Grid srcGrid, Grid targetGrid) {
        int x1 = srcGrid.getX();
        int y1 = srcGrid.getY();

        int x2 = targetGrid.getX();
        int y2 = targetGrid.getY();

        boolean isNearFlag = Math.abs(x1 - x2) + Math.abs(y1 - y2) == 1;
        if (isNearFlag) {
            // 判断是否己方兽穴
            if (GridTypeEnum.RED_CAVE.equals(targetGrid.getGridType()) && RED_PIECE.equals(currentPlayerColor)
                    || GridTypeEnum.BLACK_CAVE.equals(targetGrid.getGridType()) && BLACK_PIECE.equals(currentPlayerColor)) {
                System.out.println("不能够进入自己的兽穴");
                return false;
            }

            // 如果是老鼠，可以游泳
            if (PieceConstants.MOUSE.equals(selectedPiece.getPieceName())) {
                // 判断水陆移动
                if ((GridTypeEnum.RIVER.equals(srcGrid.getGridType()) && GridTypeEnum.LAND.equals(targetGrid.getGridType()))
                        || (GridTypeEnum.LAND.equals(srcGrid.getGridType()) && GridTypeEnum.RIVER.equals(targetGrid.getGridType()))) {
                    if (targetGrid.getPiece() != null) {
                        System.out.println("老鼠不能在水陆之间吃子");
                        return false;
                    }
                }
            } else {
                // 不是老鼠，不能站在水里
                if (GridTypeEnum.RIVER.equals(targetGrid.getGridType())) {
                    System.out.println("当前棋子不能过河");
                    return false;
                }
            }
            return true;
        } else {
            // 不相邻的情况，判断老虎或狮子是否能跳河
            if (!PieceConstants.TIGER.equals(selectedPiece.getPieceName())
                    && !PieceConstants.LION.equals(selectedPiece.getPieceName())) {
                return false;
            }

            if (GridTypeEnum.RIVER.equals(targetGrid.getGridType())) {
                return false;
            }

            // 必须是直线（同一行 或 同一列）
            if (x1 != x2 && y1 != y2) {
                return false;
            }

            // 检查两点之间是不是全是河
            if (!BoardArrayUtils.isAllRiverBetween(srcGrid, targetGrid, boardArr)) {
                System.out.println("只能跳过小河区域");
                return false;
            }

            // 检查河里有没有老鼠（有就不能跳）
            if (BoardArrayUtils.hasMouseInRiver(srcGrid, targetGrid, boardArr)) {
                System.out.println("河里有老鼠，不能跳河");
                return false;
            }

            // 所有条件满足 → 可以跳
            return true;
        }
    }

}
