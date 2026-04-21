/**
 * Bullet：豌豆射手发出的子弹对象。
 * 负责向右移动、显示自己、提供行号与伤害值。
 */
package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

import com.tedu.show.GameJFrame;

public class Bullet extends ElementObj {
    private final int row;
    private final int speed = 6;
    private final int damage = 1;

    public Bullet(int row, int x, int y) {
        super(x, y, 12, 12, null);
        this.row = row;
    }

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        int drawX = board == null ? getX() : board.toScreenX(getX());
        g.setColor(new Color(55, 218, 48));
        g.fillOval(drawX, getY(), getW(), getH());
    }

    @Override
    protected void move() {
        setX(getX() + speed);
        if (getX() > GameJFrame.GameX) {
            setLive(false);
        }
    }

    public int getDamage() { return damage; }
    public int getRow() { return row; }
}
