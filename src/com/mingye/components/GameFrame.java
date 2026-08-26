package com.mingye.components;

import com.mingye.constants.GameConstants;
import com.mingye.service.GameService;

import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame {

    /**
     * 棋盘面板
     */
    private BoardPanel boardPanel;

    private GameService service;


    public GameFrame(GameService service) {
        init(service);
    }

    /**
     * 初始化
     */
    private void init(GameService service) {
        this.setTitle(GameConstants.GAME_TITLE);
        this.setLocation(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setResizable(false);

        // 创建菜单栏
        createMenuBar();

        this.service = service;
        // 棋盘面板
        boardPanel = new BoardPanel(service);
        this.add(boardPanel, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // 焦点请求（必须在 setVisible 之后！）
        boardPanel.requestFocusInWindow();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // 一级菜单：游戏
        JMenu gameMenu = new JMenu("游戏");
        gameMenu.setMnemonic('G'); // Alt+G 快捷键（可选）

        // 菜单项：开始/重新开始
        JMenuItem restartItem = new JMenuItem("开始 / 重新开始");
        restartItem.setMnemonic('R'); // Alt+R
        restartItem.addActionListener(e -> {
            service.restartGame();
            boardPanel.repaint();
        });


        // 菜单项：关闭
        JMenuItem exitItem = new JMenuItem("关闭");
        exitItem.setMnemonic('X'); // Alt+X
        exitItem.addActionListener(e -> {
            System.exit(0);
        });

        // 把菜单项加入“游戏”菜单
        gameMenu.add(restartItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);

        // 把“游戏”菜单加入菜单栏
        menuBar.add(gameMenu);

        // 设置到 JFrame
        setJMenuBar(menuBar);
    }

}
