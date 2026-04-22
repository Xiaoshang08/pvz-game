package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

public class ContraPeaBullet extends ElementObj {
    private static final int SPEED = 16;
    private static final int DAMAGE = 1;

    public ContraPeaBullet(int x, int y) {
        super(x, y, 24, 20, null);
    }

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        int drawX = board == null ? getX() : board.toScreenX(getX());
        if (PeaBulletSprite.draw(g, drawX, getY(), getW(), getH())) {
            return;
        }
        g.setColor(new Color(90, 235, 74));
        g.fillOval(drawX, getY(), getW(), getH());
        g.setColor(new Color(28, 126, 35));
        g.drawOval(drawX, getY(), getW(), getH());
    }

    @Override
    protected void move() {
        setX(getX() + SPEED);
        GameBoard board = GameBoard.getInstance();
        int limit = board == null ? 1320 : board.getContraWorldWidth() + 80;
        if (getX() > limit) {
            setLive(false);
        }
    }

    public int getDamage() {
        return DAMAGE;
    }
}
