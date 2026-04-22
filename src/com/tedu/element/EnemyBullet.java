package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyBullet extends ElementObj {
    private static final int SPEED = 9;
    private static final int DEFAULT_DAMAGE = 1;
    private double preciseX;
    private double preciseY;
    private double speedX;
    private double speedY;
    private int damage;

    public EnemyBullet(int x, int y) {
        super(x, y, 18, 6, null);
        initMotion(x, y, -SPEED, 0, DEFAULT_DAMAGE);
    }

    public EnemyBullet(int x, int y, double speedX, double speedY) {
        super(x, y, 12, 12, null);
        initMotion(x, y, speedX, speedY, DEFAULT_DAMAGE);
    }

    public EnemyBullet(int x, int y, double speedX, double speedY, int damage) {
        super(x, y, 12, 12, null);
        initMotion(x, y, speedX, speedY, damage);
    }

    private void initMotion(int x, int y, double speedX, double speedY, int damage) {
        preciseX = x;
        preciseY = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.damage = damage;
    }

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        int drawX = board == null ? getX() : board.toScreenX(getX());
        g.setColor(new Color(255, 190, 66));
        g.fillOval(drawX, getY(), getW(), getH());
        g.setColor(new Color(255, 238, 130));
        g.fillOval(drawX + getW() / 3, getY() + getH() / 3, Math.max(3, getW() / 3), Math.max(3, getH() / 3));
    }

    @Override
    protected void move() {
        preciseX += speedX;
        preciseY += speedY;
        setX((int) Math.round(preciseX));
        setY((int) Math.round(preciseY));
        GameBoard board = GameBoard.getInstance();
        int leftLimit = board == null ? -80 : board.getContraCameraX() - 120;
        int rightLimit = board == null ? 1400 : board.getContraCameraX() + board.getW() + 120;
        if (getX() < leftLimit || getX() > rightLimit || getY() < -80 || getY() > 780) {
            setLive(false);
        }
    }

    public int getDamage() {
        return damage;
    }
}
