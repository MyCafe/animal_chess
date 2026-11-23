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

    /**
     * 鼠标按键释放时触发，处理玩家的一次落子操作。
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        GameService service = boardPanel.getService();
        // 对局已经结束，忽略后续的所有点击
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

        // 像素坐标换算为棋盘格；坐标未精确落在任一格内时返回 null
        Grid[][] boardArr = boardPanel.getService().getBoardArr();
        Grid currentGrid = BoardArrayUtils.getGridByLocation(boardArr, x, y);

        // 走子逻辑统一由 GameService 判定；返回 true 表示本次是有效操作，需要换手
        boolean changePlayerFlag = service.doPlay(currentGrid);
        boardPanel.repaint();

        // 已分出胜负则直接结算；未分胜负且本次操作有效时，才切换到对方行动
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
