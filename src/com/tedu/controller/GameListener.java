/**
 * GameListener：统一接收鼠标与键盘输入，并把输入分发给游戏元素。
 * 这里相当于玩家操作进入游戏逻辑的入口。
 */
package com.tedu.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

public class GameListener implements MouseListener, KeyListener {
    private final ElementManager em = ElementManager.getManager();

    @Override
    public void mouseClicked(MouseEvent e) {
        e.getComponent().requestFocusInWindow();
        List<ElementObj> uiList = em.getElementsByKey(GameElement.UI);
        for (ElementObj obj : uiList) {
            obj.mouseClick(e.getX(), e.getY());
        }

        List<ElementObj> backgroundList = em.getElementsByKey(GameElement.BACKGROUND);
        for (ElementObj obj : backgroundList) {
            obj.mouseClick(e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        dispatchKey(true, e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        dispatchKey(false, e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void dispatchKey(boolean pressed, int key) {
        for (ElementObj obj : em.getElementsByKey(GameElement.BACKGROUND)) {
            obj.keyClick(pressed, key);
        }
        for (ElementObj obj : em.getElementsByKey(GameElement.PLAYER)) {
            obj.keyClick(pressed, key);
        }
    }
}
