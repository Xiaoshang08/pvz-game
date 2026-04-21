/**
 * PeaShooter：豌豆射手，是当前版本的具体植物类型。
 * 负责绘制自己，并在本行存在僵尸时按固定节奏发射子弹。
 */
package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.util.GameImage;

public class PeaShooter extends Plant {
    private static final String IMAGE_PATH = "images/plants/pea_shooter.png";
    private static final BufferedImage IMAGE = GameImage.get(IMAGE_PATH);

    private int shootCounter = 0;
    /**
     * 攻速说明：
     * 本项目1个 tick = 30ms，所以每 SHOOT_INTERVAL 个 tick 才攻击一次
     */
    private static final int SHOOT_INTERVAL = 80;

    public PeaShooter(int row, int col, int x, int y, int w, int h) {
        super(row, col, x, y, w, h, 4);
    }

    // 绘制豌豆射手：优先使用图片；如果没找到图片，则回退到手绘图形
    @Override
    protected void drawPlant(Graphics g, int x, int y) {

        if (IMAGE != null) {
            GameImage.draw(g, IMAGE, x + 4, y + 2, getW() - 8, getH() - 4);
            return;
        }

        g.setColor(new Color(36, 138, 40));
        g.fillRect(x + 34, y + 28, 8, 30);

        g.setColor(new Color(56, 181, 77));
        g.fillOval(x + 18, y + 42, 22, 12);
        g.fillOval(x + 38, y + 42, 22, 12);

        g.setColor(new Color(12, 178, 48));
        g.fillOval(x + 18, y + 12, 34, 34);

        g.setColor(new Color(15, 153, 32));
        g.fillOval(x + 42, y + 20, 18, 14);

        g.setColor(Color.BLACK);
        g.fillOval(x + 28, y + 22, 5, 5);
    }

    @Override
    protected void add(long gameTime) {
        shootCounter++;
        if (shootCounter < SHOOT_INTERVAL) {
            return;
        }
        shootCounter = 0;

        GameBoard board = GameBoard.getInstance();
        if (board == null || !board.hasZombieInRow(getRow())) {
            return;
        }

        int bulletX = getX() + getW() - 8;
        int bulletY = getY() + getH() / 2 - 6;
        ElementManager.getManager().addElement(new Bullet(getRow(), bulletX, bulletY), GameElement.BULLET);
    }
}
