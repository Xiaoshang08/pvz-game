/**
 * GameMainJPanel：主绘图面板。
 *
 * 主要职责：
 * 1. 遍历 ElementManager 中的所有元素并绘制；
 * 2. 维持独立的重绘线程；
 * 3. 在所有元素绘制完成后，额外把开始界面 / Game Over 覆盖层画到最上面，
 *    确保按钮、提示文字不会被僵尸、植物、血条压住。
 */
package com.tedu.show;

import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

public class GameMainJPanel extends JPanel implements Runnable {
    private final ElementManager em;

    public GameMainJPanel() {
        em = ElementManager.getManager();
        setPreferredSize(new Dimension(GameJFrame.GameX, GameJFrame.GameY));
        setFocusable(true);
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for (ElementObj obj : list) {
                obj.showElement(g);
            }
        }

        // 最后单独绘制 GameBoard 的覆盖层，保证其处于最高图层。
        com.tedu.element.GameBoard board = com.tedu.element.GameBoard.getInstance();
        if (board != null) {
            board.drawOverlay(g);
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!isDisplayable()) {
                return;
            }
            SwingUtilities.invokeLater(this::repaint);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
