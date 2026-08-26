package com.mingye.listener;

import com.mingye.components.BoardPanel;
import com.mingye.constants.GameConstants;
import com.mingye.model.Grid;
import com.mingye.service.GameService;
import com.mingye.utils.BoardArrayUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardClickListener extends MouseAdapter {

    private BoardPanel boardPanel;

    public BoardClickListener(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        GameService service = boardPanel.getService();
        if (service.isGameOverFlag()) {
            return;
        }

        int margin = GameConstants.MARGIN;
        int x = e.getX();
        int y = e.getY();
        System.out.println("x= " + x + " y=" + y);

        // 判断是否点击到了棋盘外面
        if (x < margin || x > GameConstants.BOARD_WIDTH + margin
                || y < margin || y > GameConstants.BOARD_HEIGHT + margin) {
            System.out.println("点击了棋盘外部");
            return;
        }

        Grid[][] boardArr = boardPanel.getService().getBoardArr();
        Grid currentGrid = BoardArrayUtils.getGridByLocation(boardArr, x, y);

        boolean changePlayerFlag = service.doPlay(currentGrid);
        boardPanel.repaint();

        String winPlayerColor = service.checkWin();
        if (winPlayerColor != null) {
            boardPanel.showWinDialog(winPlayerColor);
        } else {
            if (changePlayerFlag) {
                service.changePlayer();
            }
        }
    }

}
