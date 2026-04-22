package com.tedu.element;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * GameInfoPanel：顶部双排功能栏。 本次把说明性长文字移除，改成更清爽的两列布局，避免后续继续扩展时拥挤。
 */
public class GameInfoPanel extends ElementObj {

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        if (board == null || !board.isInBattleStage()) {
            return;
        }

        if (board.isFireIceMode()) {
            // 冰火人模式不显示植物大战僵尸的信息面板
            return;
        }

        if (board.isBattleIntroPlaying()) {
            return;
        }

        if (board.isContraMode()) {
            drawContraInfo(g, board);
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        // 增强抗锯齿，提升文字/图形顺滑度
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

        int x = board.getStatusBarX();
        int y = board.getStatusBarY();
        int w = board.getStatusBarW();
        int h = board.getStatusBarH();

        // 1. 主背景：渐变+圆角，替代纯色填充，增加层次感
        GradientPaint bgGradient = new GradientPaint(x, y, new Color(252, 242, 210), x, y + h,
                new Color(248, 236, 195));
        g2.setPaint(bgGradient);
        g2.fillRoundRect(x, y, w, h, 22, 22);
        // 边框：更细腻的颜色+轻微描边，避免粗重感
        g2.setColor(new Color(168, 132, 75));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(x, y, w, h, 22, 22);

        // 2. 阳光图标：双层渐变+高光，更立体
        GradientPaint sunGradient = new GradientPaint(x + 18, y + 14, new Color(255, 225, 88), x + 48, y + 44,
                new Color(255, 210, 66));
        g2.setPaint(sunGradient);
        g2.fillOval(x + 18, y + 14, 30, 30);
        // 边框：更暖的色调，增加质感
        g2.setColor(new Color(220, 145, 20));
        g2.drawOval(x + 18, y + 14, 30, 30);
        // 阳光高光点：模拟光泽
        g2.setColor(new Color(255, 255, 255, 180));
        g2.fillOval(x + 25, y + 20, 8, 8);

        // 3. 阳光数值：优化字体+颜色，更易读
        g2.setColor(new Color(80, 58, 20));
        g2.setFont(new Font("Microsoft YaHei", Font.BOLD, 22));
        g2.drawString(String.valueOf(board.getCurrentSun()), x + 58, y + 38);
        g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        g2.drawString("阳光", x + 60, y + 57);

        // 4. 植物卡片：优化配色+选中态，增加交互感
        drawPlantCard(g2, board, board.getPeaCardX(), board.getPeaCardY(),
                "豌豆射手", board.getPeaShooterCost(),
                board.getSelectedPlantType() == GameBoard.PlantType.PEA_SHOOTER && !board.isShovelMode());
        drawPlantCard(g2, board, board.getSunflowerCardX(), board.getSunflowerCardY(),
                "向日葵", board.getSunflowerCost(),
                board.getSelectedPlantType() == GameBoard.PlantType.SUNFLOWER && !board.isShovelMode());

        // 5. 功能按钮：优化配色+描边
        drawShovelButton(g2, board);
        drawMenuButton(g2, board);

        // 6. 右侧信息：优化字体层级+颜色，区分主次
        g2.setColor(new Color(85, 58, 20));
        g2.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        g2.drawString("击败数：" + board.getTotalKills() + " / " + board.getMaxZombies(), x + 490, y + 27);
        g2.drawString("当前模式：" + getModeLabel(board), x + 490, y + 50);
        // 选中植物标红，突出显示
        g2.setColor(board.isShovelMode() ? new Color(180, 60, 30) : new Color(85, 58, 20));
        g2.drawString("当前植物：" + getSelectedPlantLabel(board), x + 760, y + 27);
        g2.setColor(new Color(85, 58, 20));
        g2.drawString("僵尸波次：" + board.getSpawnedZombies() + " / " + board.getMaxZombies(), x + 760, y + 50);
    }

    private void drawContraInfo(Graphics g, GameBoard board) {
        Graphics2D g2 = (Graphics2D) g;
        // 增强抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        int x = board.getStatusBarX();
        int y = board.getStatusBarY();
        int w = board.getStatusBarW();
        int h = board.getStatusBarH();

        // 魂斗罗模式背景：深色渐变+半透明，更贴合主题
        GradientPaint contraBg = new GradientPaint(x, y, new Color(25, 40, 35, 230), x, y + h,
                new Color(31, 48, 42, 220));
        g2.setPaint(contraBg);
        g2.fillRoundRect(x, y, w, h, 18, 18);
        // 边框：更亮的绿色，增加科技感
        g2.setColor(new Color(170, 240, 130));
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawRoundRect(x, y, w, h, 18, 18);

        // 文字优化：字体+颜色分层
        g2.setFont(new Font("Consolas", Font.BOLD, 22));
        g2.setColor(new Color(240, 255, 230));
        g2.drawString("Contra PVZ", x + 24, y + 31);

        // HP数值高亮（低血量变红）
        g2.setFont(new Font("Consolas", Font.BOLD, 16));
        Color hpColor = board.getContraPlayerHealth() < 30 ? new Color(255, 100, 100) : new Color(150, 226, 117);
        g2.setColor(hpColor);
        g2.drawString("HP: " + board.getContraPlayerHealth(), x + 220, y + 29);

        g2.setColor(new Color(150, 226, 117));
        g2.drawString("Boss: " + (board.isContraBossDefeated() ? "Defeated" : "Hong Yuexian"), x + 340, y + 29);
        g2.drawString("A/D move    W double jump    S drop    Space shoot    P/Esc pause", x + 520, y + 29);

        g2.setFont(new Font("Consolas", Font.PLAIN, 13));
        g2.setColor(new Color(200, 240, 180));
        g2.drawString("Defeat Hong Yuexian, avoid hazards, then enter the blue base passage.", x + 24, y + 56);
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
        // 植物卡片：选中态渐变+未选中态浅绿，更自然
        GradientPaint cardBg = selected
                ? new GradientPaint(x, y, new Color(85, 160, 85), x + board.getPlantCardW(), y + board.getPlantCardH(),
                        new Color(109, 171, 16))
                : new GradientPaint(x, y, new Color(210, 235, 170), x + board.getPlantCardW(),
                        y + board.getPlantCardH(), new Color(196, 221, 154));

        g.setPaint(cardBg);
        g.fillRoundRect(x, y, board.getPlantCardW(), board.getPlantCardH(), 14, 14);

        // 边框：选中态白色高亮，未选中态浅灰
        g.setColor(selected ? new Color(255, 255, 255, 200) : new Color(130, 140, 120));
        g.setStroke(new BasicStroke(1f));
        g.drawRoundRect(x, y, board.getPlantCardW(), board.getPlantCardH(), 14, 14);

        // 文字：优化字体+颜色，增加可读性
        g.setColor(new Color(80, 58, 20));
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        g.drawString(name, x + 12, y + 17);
        g.setFont(new Font("Microsoft YaHei", Font.PLAIN, 11));
        g.drawString(cost + " 阳光", x + 12, y + 33);
    }

    private void drawShovelButton(Graphics2D g, GameBoard board) {
        int x = board.getShovelButtonX();
        int y = board.getShovelButtonY();
        int w = board.getShovelButtonW();
        int h = board.getShovelButtonH();

        // 铲子按钮：选中态渐变+未选中态浅蓝，更有交互感
        GradientPaint btnColor = board.isShovelMode()
                ? new GradientPaint(x, y, new Color(230, 125, 70), x + w, y + h, new Color(223, 119, 67))
                : new GradientPaint(x, y, new Color(130, 180, 230), x + w, y + h, new Color(116, 163, 210));

        g.setPaint(btnColor);
        g.fillRoundRect(x, y, w, h, 16, 16);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(1f));
        g.drawRoundRect(x, y, w, h, 16, 16);

        // 文字：加粗+优化位置，更醒目
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        g.drawString(board.isShovelMode() ? "铲子 ON" : "铲子", x + 24, y + 27);
    }

    private void drawMenuButton(Graphics2D g, GameBoard board) {
        int x = board.getMenuButtonX();
        int y = board.getMenuButtonY();
        int w = board.getMenuButtonW();
        int h = board.getMenuButtonH();

        // 菜单按钮：渐变背景+细边框，更精致
        GradientPaint menuGradient = new GradientPaint(x, y, new Color(115, 120, 230), x + w, y + h,
                new Color(104, 109, 224));
        g.setPaint(menuGradient);
        g.fillRoundRect(x, y, w, h, 16, 16);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(1f));
        g.drawRoundRect(x, y, w, h, 16, 16);

        // 文字优化
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        g.drawString("菜单 ≡", x + 22, y + 27);
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
