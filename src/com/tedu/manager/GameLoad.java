/**
 * GameLoad：游戏加载器。
 *
 * 负责在开局时创建并注册背景、格子等基础元素。
 * 原先底部的信息栏已移除，后续可以把图鉴等功能放到主界面中继续扩展。
 */
package com.tedu.manager;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.tedu.element.GameBoard;
import com.tedu.element.GridCell;
import com.tedu.element.GameInfoPanel;

public class GameLoad {
    private static final ElementManager em = ElementManager.getManager();
    public static final Map<String, ImageIcon> imgMap = new HashMap<>();

    private GameLoad() {}

    public static void loadGame() {
        loadMap();
    }

    private static void loadMap() {
        em.addElement(new GameBoard(), GameElement.BACKGROUND);
        GameBoard board = GameBoard.getInstance();
        em.addElement(new GameInfoPanel(), GameElement.UI);
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                em.addElement(new GridCell(row, col), GameElement.GRID);
            }
        }
    }
}
