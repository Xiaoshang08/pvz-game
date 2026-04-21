package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyBullet extends ElementObj {
    private static final int SPEED = 9;
    private static final int DAMAGE = 1;

    public EnemyBullet(int x, int y) {
        super(x, y, 18, 6, null);
    }

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        int drawX = board == null ? getX() : board.toScreenX(getX());
        g.setColor(new Color(255, 190, 66));
        g.fillRoundRect(drawX, getY(), getW(), getH(), 4, 4);
        g.setColor(new Color(255, 238, 130));
        g.fillRect(drawX + 12, getY() + 1, 8, 4);
    }

    @Override
    protected void move() {
        setX(getX() - SPEED);
        GameBoard board = GameBoard.getInstance();
        int leftLimit = board == null ? -80 : board.getContraCameraX() - 120;
        if (getX() < leftLimit) {
            setLive(false);
        }
    }

    public int getDamage() {
        return DAMAGE;
    }
}
