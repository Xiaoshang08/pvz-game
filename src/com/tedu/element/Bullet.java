/**
 * Bullet：豌豆射手发出的子弹对象。
 * 负责向右移动、显示自己、提供行号与伤害值。
 */
package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;

public class Bullet extends ElementObj {
    private static final int SPEED = 6;
    private static final int DAMAGE = 1;

    private final int row;

    public Bullet(int row, int x, int y) {
        super(x, y, 18, 16, null);
        this.row = row;
    }

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        int drawX = board == null ? getX() : board.toScreenX(getX());
        if (PeaBulletSprite.draw(g, drawX, getY(), getW(), getH())) {
            return;
        }
        g.setColor(new Color(55, 218, 48));
        g.fillOval(drawX, getY(), getW(), getH());
    }

    @Override
    protected void move() {
        setX(getX() + SPEED);
        if (getX() > GameJFrame.GameX) {
            setLive(false);
        }
    }

    public static void spawnImpact(int centerX, int centerY) {
        ElementManager.getManager().addElement(new PeaImpactEffect(centerX, centerY), GameElement.EFFECT);
    }

    public int getDamage() { return DAMAGE; }
    public int getRow() { return row; }
}
