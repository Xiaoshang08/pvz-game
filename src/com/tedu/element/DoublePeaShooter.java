package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.util.GameImage;

public class DoublePeaShooter extends Plant {
    private static final String IMAGE_PATH = "images/plants/Double Peashooter.png";
    private static final BufferedImage IMAGE = GameImage.get(IMAGE_PATH);
    private static final double REFERENCE_ASPECT_RATIO = 713.0 / 790.0;
    private static final int SHOOT_INTERVAL = 80;

    private int shootCounter = 0;

    public DoublePeaShooter(int row, int col, int x, int y, int w, int h) {
        super(row, col, x, y, w, h, 4);
    }

    @Override
    protected void drawPlant(Graphics g, int x, int y) {
        if (IMAGE != null) {
            int drawH = getH() - 4;
            int maxDrawW = getW() - 8;
            int drawW = Math.min(maxDrawW, (int) Math.round(drawH * REFERENCE_ASPECT_RATIO));
            int drawX = x + (getW() - drawW) / 2;
            GameImage.draw(g, IMAGE, drawX, y + 2, drawW, drawH);
            return;
        }

        g.setColor(new Color(36, 138, 40));
        g.fillRect(x + 34, y + 24, 8, 34);

        g.setColor(new Color(56, 181, 77));
        g.fillOval(x + 16, y + 42, 22, 12);
        g.fillOval(x + 40, y + 42, 22, 12);

        g.setColor(new Color(12, 178, 48));
        g.fillOval(x + 18, y + 10, 28, 28);
        g.fillOval(x + 34, y + 18, 28, 28);

        g.setColor(new Color(15, 153, 32));
        g.fillOval(x + 42, y + 14, 20, 16);
        g.fillOval(x + 48, y + 26, 20, 16);

        g.setColor(Color.BLACK);
        g.fillOval(x + 28, y + 19, 4, 4);
        g.fillOval(x + 40, y + 27, 4, 4);
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
        int centerY = getY() + getH() / 2 - 6;
        ElementManager manager = ElementManager.getManager();
        manager.addElement(new Bullet(getRow(), bulletX, centerY - 8), GameElement.BULLET);
        manager.addElement(new Bullet(getRow(), bulletX + 6, centerY + 4), GameElement.BULLET);
    }
}
