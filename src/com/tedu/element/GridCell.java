/**
 * GridCell：草地中的单个格子显示对象。
 * 首页不显示格子，进入战斗后再显示。
 */
package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (board.hasBackgroundSceneImage()) {
            g2.setColor(new Color(255, 255, 255, 6));
            g2.fillRect(drawX, drawY, getW(), getH());
            g2.setColor(new Color(34, 120, 42, row == 2 ? 168 : 138));
            g2.drawRect(drawX, drawY, getW(), getH());
            return;
        }

        if ((row + col) % 2 == 0) {
            g2.setColor(new Color(145, 199, 91, 90));
        } else {
            g2.setColor(new Color(129, 182, 78, 90));
        }
        g2.fillRoundRect(drawX, drawY, getW(), getH(), 10, 10);
        g2.setColor(new Color(82, 134, 49));
        g2.drawRoundRect(drawX, drawY, getW(), getH(), 10, 10);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
}
