/**
 * GameJFrame：最外层游戏窗口。
 *
 * 负责设置窗口标题、大小、监听器和线程，并启动显示。
 * 由于底部说明栏已经移除，这里同步把窗口高度调整为更紧凑的比例。
 */
package com.tedu.show;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameJFrame extends JFrame {
    public static int GameX = 1280;
    public static int GameY = 720;

    private JPanel jPanel;
    private KeyListener keyListener;
    private MouseMotionListener mouseMotionListener;
    private MouseListener mouseListener;
    private Thread thead;

    public GameJFrame() {
        init();
    }

    public void init() {
        this.setTitle("植物大战僵尸-框架版");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public void start() {
        if (jPanel != null) {
            jPanel.setPreferredSize(new Dimension(GameX, GameY));
            jPanel.setFocusable(true);
            jPanel.setFocusTraversalKeysEnabled(false);
            this.setContentPane(jPanel);
        }
        if (keyListener != null) {
            this.addKeyListener(keyListener);
            if (jPanel != null) {
                jPanel.addKeyListener(keyListener);
            }
        }
        if (mouseListener != null && jPanel != null) {
            jPanel.addMouseListener(mouseListener);
        }
        if (mouseMotionListener != null && jPanel != null) {
            jPanel.addMouseMotionListener(mouseMotionListener);
        }

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        if (jPanel != null) {
            jPanel.requestFocusInWindow();
        }

        if (thead != null) {
            thead.start();
        }
        if (this.jPanel instanceof Runnable) {
            new Thread((Runnable) this.jPanel).start();
        }
    }

    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void setThead(Thread thead) {
        this.thead = thead;
    }
}
