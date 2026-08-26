package com.mingye.components;

import com.mingye.constants.GameConstants;
import com.mingye.listener.BoardClickListener;
import com.mingye.model.Grid;
import com.mingye.model.Piece;
import com.mingye.service.GameService;
import com.mingye.utils.ImageUtils;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 棋盘面板
 */
public class BoardPanel extends JPanel {

    private GameService service;

    private BoardClickListener boardClickListener;

    private BufferedImage riverImg;

    private BufferedImage trapImg;

    private BufferedImage caveImg;

    public BoardPanel(GameService service) {
        this.setPreferredSize(new Dimension(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT));
        this.service = service;
        boardClickListener = new BoardClickListener(this);
        this.addMouseListener(boardClickListener);

        // 加载图片元素
        riverImg = ImageUtils.loadRiverImage();
        trapImg = ImageUtils.loadTrapImage();
        caveImg = ImageUtils.loadCaveImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int gridWidthHeight = GameConstants.GRID_WIDTH_HEIGHT;
        int margin = GameConstants.MARGIN;

        setBackground(new Color(240, 220, 180)); // 木纹色背景
        Grid[][] boardArr = service.getBoardArr();
        // 绘制线条
        g.setColor(Color.BLACK);

        for (int i = 0; i <= boardArr.length; i++) {
            int x2 = GameConstants.BOARD_WIDTH;
            g.drawLine(margin, i * gridWidthHeight + margin, x2 + margin,
                    i * gridWidthHeight + margin);
        }
        for (int i = 0; i <= boardArr[0].length; i++) {
            int y2 = GameConstants.BOARD_HEIGHT;
            g.drawLine(i * gridWidthHeight + margin, margin,
                    i * gridWidthHeight + margin, y2 + margin);
        }

        // 绘制小河
        drawRivers(g);

        // 绘制陷阱
        drawTraps(g);

        // 绘制兽穴
        drawCaves(g);

        // 绘制棋子
        drawPieces(g, boardArr);
        // 绘制选中边框
        drawSelectedGrid(g);
    }

    private void drawRivers(Graphics g) {
        if (riverImg != null) {
            int riverWidth = GameConstants.GRID_WIDTH_HEIGHT * 2;
            int riverHeight = GameConstants.GRID_WIDTH_HEIGHT * 3;
            int margin = GameConstants.MARGIN;
            g.drawImage(riverImg, 1 * GameConstants.GRID_WIDTH_HEIGHT + margin,
                    3 * GameConstants.GRID_WIDTH_HEIGHT + margin,
                    riverWidth, riverHeight, null);
            g.drawImage(riverImg, 4 * GameConstants.GRID_WIDTH_HEIGHT + margin,
                    3 * GameConstants.GRID_WIDTH_HEIGHT + margin, riverWidth, riverHeight, null);
        }
    }

    private void drawTraps(Graphics g) {
        int widthHeight = GameConstants.TRAP_WIDTH_HEIGHT;
        int margin = GameConstants.MARGIN;

        List<Grid> trapGridList = service.getTrapGridList();
        for (Grid grid : trapGridList) {
            if (trapImg != null) {
                int trapX = grid.getX() * GameConstants.GRID_WIDTH_HEIGHT + margin + 5;
                int trapY = grid.getY() * GameConstants.GRID_WIDTH_HEIGHT + margin + 5;
                g.drawImage(trapImg, trapX, trapY, widthHeight, widthHeight, null);
            }
        }
    }

    private void drawCaves(Graphics g) {
        int widthHeight = GameConstants.CAVE_WIDTH_HEIGHT;
        int margin = GameConstants.MARGIN;

        List<Grid> caveGridList = service.getCaveGridList();
        for (Grid grid : caveGridList) {
            if (caveImg != null) {
                int caveX = grid.getX() * GameConstants.GRID_WIDTH_HEIGHT + margin + 5;
                int caveY = grid.getY() * GameConstants.GRID_WIDTH_HEIGHT + margin + 5;
                g.drawImage(caveImg, caveX, caveY, widthHeight, widthHeight, null);
            }
        }
    }

    private void drawPieces(Graphics g, Grid[][] boardArr) {
        int radius = GameConstants.PIECE_RADIUS;
        int margin = GameConstants.MARGIN;
        for (int i = 0; i < boardArr.length; i++) {
            for (int j = 0; j < boardArr[i].length; j++) {
                Grid grid = boardArr[i][j];
                Piece piece = grid.getPiece();
                if (piece != null) {
                    BufferedImage image = ImageUtils.loadPieceImageByValue(piece);
                    if (image != null) {
                        int pieceX = grid.getX() * GameConstants.GRID_WIDTH_HEIGHT + margin + 10;
                        int pieceY = grid.getY() * GameConstants.GRID_WIDTH_HEIGHT + margin + 10;
                        g.drawImage(image, pieceX, pieceY, radius, radius, null);
                    }
                }
            }
        }
    }

    private void drawSelectedGrid(Graphics g) {
        Grid selected = service.getGridOfSelectedPiece();
        if (selected == null) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(3.0f));
            int margin = GameConstants.MARGIN;
            int gridWH = GameConstants.GRID_WIDTH_HEIGHT;
            int x = selected.getX() * gridWH + margin;
            int y = selected.getY() * gridWH + margin;
            g2d.drawRect(x, y, gridWH, gridWH);
        } finally {
            // 必释放画笔
            g2d.dispose();
        }
    }


    public GameService getService() {
        return service;
    }

    /**
     * 弹出游戏结束的对话框
     */
    public void showWinDialog(String winPlayerColor) {
        new javax.swing.Timer(200, e -> {
            ((javax.swing.Timer) e.getSource()).stop();

            String message;
            if (GameService.RED_PIECE.equals(winPlayerColor)) {
                message = "红棋胜利!";
            } else if (GameService.BLACK_PIECE.equals(winPlayerColor)) {
                message = "黑棋胜利!";
            } else {
                message = "游戏结束!";
            }
            String[] options = {"再来一局"};

            int result = JOptionPane.showOptionDialog(
                    SwingUtilities.getWindowAncestor(this),
                    message,
                    "游戏结束",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (result == 0) {
                service.restartGame();
                repaint();
            }
        }).start();
    }
}
