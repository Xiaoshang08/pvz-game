package com.tedu.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * GameInfoPanel：顶部双排功能栏。
 * 本次把说明性长文字移除，改成更清爽的两列布局，避免后续继续扩展时拥挤。
 */
public class GameInfoPanel extends ElementObj {
    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        if (board == null || !board.isInBattleStage()) {
            return;
        }

        if (board.isBattleIntroPlaying()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = board.getStatusBarX();
        int y = board.getStatusBarY();
        int w = board.getStatusBarW();
        int h = board.getStatusBarH();

        g2.setColor(new Color(255, 248, 220));
        g2.fillRoundRect(x, y, w, h, 18, 18);
        g2.setColor(new Color(144, 108, 50));
        g2.drawRoundRect(x, y, w, h, 18, 18);

        g2.setColor(new Color(255, 210, 66));
        g2.fillOval(x + 18, y + 18, 30, 30);
        g2.setColor(new Color(230, 160, 30));
        g2.drawOval(x + 18, y + 18, 30, 30);

        g2.setColor(new Color(92, 66, 24));
        g2.setFont(new Font("SansSerif", Font.BOLD, 22));
        g2.drawString(String.valueOf(board.getCurrentSun()), x + 60, y + 42);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g2.drawString("阳光", x + 62, y + 66);

        drawPlantCard(g2, board, board.getPeaCardX(), board.getPeaCardY(),
                "豌豆射手", board.getPeaShooterCost(),
                board.getSelectedPlantType() == GameBoard.PlantType.PEA_SHOOTER && !board.isShovelMode());
        drawPlantCard(g2, board, board.getSunflowerCardX(), board.getSunflowerCardY(),
                "向日葵", board.getSunflowerCost(),
                board.getSelectedPlantType() == GameBoard.PlantType.SUNFLOWER && !board.isShovelMode());
        drawShovelButton(g2, board);
        drawMenuButton(g2, board);

        g2.setColor(new Color(96, 66, 24));
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2.drawString("击败数：" + board.getTotalKills() + " / " + board.getMaxZombies(), x + 560, y + 34);
        g2.drawString("当前模式：" + getModeLabel(board), x + 560, y + 62);
        g2.drawString("当前植物：" + getSelectedPlantLabel(board), x + 840, y + 34);
        g2.drawString("僵尸波次：" + board.getSpawnedZombies() + " / " + board.getMaxZombies(), x + 840, y + 62);
    }

    private String getSelectedPlantLabel(GameBoard board) {
        return board.getSelectedPlantType() == GameBoard.PlantType.SUNFLOWER ? "向日葵" : "豌豆射手";
    }

    private String getModeLabel(GameBoard board) {
        if (board.isPaused()) {
            return "暂停中";
        }
        if (board.isShovelMode()) {
            return "铲除";
        }
        return "种植";
    }

    private void drawPlantCard(Graphics2D g, GameBoard board, int x, int y, String name, int cost, boolean selected) {
        g.setColor(selected ? new Color(97, 171, 96) : new Color(196, 221, 154));
        g.fillRoundRect(x, y, board.getPlantCardW(), board.getPlantCardH(), 16, 16);
        g.setColor(selected ? Color.WHITE : new Color(120, 120, 120));
        g.drawRoundRect(x, y, board.getPlantCardW(), board.getPlantCardH(), 16, 16);

        g.setColor(new Color(92, 66, 24));
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString(name, x + 14, y + 17);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString(cost + " 阳光", x + 14, y + 32);
    }

    private void drawShovelButton(Graphics2D g, GameBoard board) {
        int x = board.getShovelButtonX();
        int y = board.getShovelButtonY();
        int w = board.getShovelButtonW();
        int h = board.getShovelButtonH();

        Color btnColor = board.isShovelMode() ? new Color(223, 119, 67) : new Color(116, 163, 210);
        g.setColor(btnColor);
        g.fillRoundRect(x, y, w, h, 16, 16);
        g.setColor(Color.WHITE);
        g.drawRoundRect(x, y, w, h, 16, 16);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString(board.isShovelMode() ? "铲子模式: ON" : "铲子", x + 42, y + 21);
    }

    private void drawMenuButton(Graphics2D g, GameBoard board) {
        int x = board.getMenuButtonX();
        int y = board.getMenuButtonY();
        int w = board.getMenuButtonW();
        int h = board.getMenuButtonH();

        g.setColor(new Color(104, 109, 224));
        g.fillRoundRect(x, y, w, h, 16, 16);
        g.setColor(Color.WHITE);
        g.drawRoundRect(x, y, w, h, 16, 16);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("菜单  ≡", x + 48, y + 21);
    }

    @Override
    public void mouseClick(int x, int y) {
        GameBoard board = GameBoard.getInstance();
        if (board == null || !board.isInBattleStage() || board.isGameOver() || board.isGameWin() || board.isPaused()) {
            return;
        }

        if (board.isInPeaCard(x, y)) {
            board.selectPeaShooter();
            return;
        }
        if (board.isInSunflowerCard(x, y)) {
            board.selectSunflower();
            return;
        }
        if (board.isInShovelButton(x, y)) {
            board.toggleShovelMode();
        }
    }
}
