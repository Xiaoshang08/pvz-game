/**
 * Plant：植物父类。
 *
 * 统一管理植物的行列位置、生命值、受伤与死亡后的棋盘清理逻辑。
 */
package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Plant extends ElementObj {
    private final int row;
    private final int col;
    private int health;

    public Plant(int row, int col, int x, int y, int w, int h, int health) {
        super(x, y, w, h, null);
        this.row = row;
        this.col = col;
        this.health = health;
    }

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        int drawX = board == null ? getX() : board.toScreenX(getX());
        int drawY = getY();

        drawPlant(g, drawX, drawY);
        g.setColor(new Color(145, 47, 47));
        g.drawString("HP:" + health, drawX + 2, drawY - 4);
    }

    protected abstract void drawPlant(Graphics g, int x, int y);

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            setLive(false);
        }
    }

    @Override
    public void die() {
        GameBoard board = GameBoard.getInstance();
        if (board != null) {
            board.removePlant(row, col);
        }
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getHealth() { return health; }
}
