/**
 * GridCell：草地中的单个格子显示对象。
 * 首页不显示格子，进入战斗后再显示。
 */
package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

public class GridCell extends ElementObj {
    private final int row;
    private final int col;

    public GridCell(int row, int col) {
        this.row = row;
        this.col = col;
        GameBoard board = GameBoard.getInstance();
        setX(board.getCellX(col));
        setY(board.getCellY(row));
        setW(board.getCellW());
        setH(board.getCellH());
    }

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        if (board == null || !board.isInBattleStage()) {
            return;
        }
        int drawX = board.toScreenX(getX());
        int drawY = getY();

        if ((row + col) % 2 == 0) {
            g.setColor(new Color(145, 199, 91, 90));
        } else {
            g.setColor(new Color(129, 182, 78, 90));
        }
        g.fillRoundRect(drawX, drawY, getW(), getH(), 10, 10);
        g.setColor(new Color(82, 134, 49));
        g.drawRoundRect(drawX, drawY, getW(), getH(), 10, 10);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
}
