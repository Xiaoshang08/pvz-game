package com.tedu.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tedu.util.GameImage;

/**
 * Sun：阳光对象。
 *
 * 同时支持两种来源：
 * 1. 天空掉落阳光：缓慢落到地面后自动收集；
 * 2. 向日葵产出的阳光：停留片刻后自动收集。
 */
public class Sun extends ElementObj {
    private static final int SIZE = 36;
    private static final int FALL_SPEED = 2;
    private static final String IMAGE_PATH = "images/items/sun.png";
    private static final BufferedImage IMAGE = GameImage.get(IMAGE_PATH);

    private final int value;
    private final int targetY;
    private final boolean falling;
    private int autoCollectDelay;

    private Sun(int x, int y, int targetY, int value, boolean falling, int autoCollectDelay) {
        super(x, y, SIZE, SIZE, null);
        this.targetY = targetY;
        this.value = value;
        this.falling = falling;
        this.autoCollectDelay = autoCollectDelay;
    }

    public static Sun createFallingSun(int x, int startY, int targetY, int value) {
        return new Sun(x, startY, targetY, value, true, 0);
    }

    public static Sun createProducedSun(int x, int y, int value, int autoCollectDelay) {
        return new Sun(x, y, y, value, false, autoCollectDelay);
    }

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        int x = board == null ? getX() : board.toScreenX(getX());
        int y = getY();

        if (IMAGE != null) {
            GameImage.draw(g, IMAGE, x, y, getW(), getH());
            g.setColor(new Color(130, 80, 0));
            g.setFont(new Font("SansSerif", Font.BOLD, 12));
            g.drawString(String.valueOf(value), x + 10, y + 22);
            return;
        }

        g.setColor(new Color(255, 212, 59));
        g.fillOval(x, y, getW(), getH());

        g.setColor(new Color(255, 236, 130));
        g.fillOval(x + 6, y + 6, getW() - 12, getH() - 12);

        g.setColor(new Color(225, 158, 22));
        g.drawOval(x, y, getW(), getH());
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString(String.valueOf(value), x + 8, y + 23);
    }

    @Override
    protected void move() {
        if (falling) {
            if (getY() < targetY) {
                setY(Math.min(getY() + FALL_SPEED, targetY));
                return;
            }
            collectAndDisappear();
            return;
        }

        if (autoCollectDelay > 0) {
            autoCollectDelay--;
            return;
        }
        collectAndDisappear();
    }

    private void collectAndDisappear() {
        if (!isLive()) {
            return;
        }
        GameBoard board = GameBoard.getInstance();
        if (board != null) {
            board.addSun(value);
        }
        setLive(false);
    }
}
