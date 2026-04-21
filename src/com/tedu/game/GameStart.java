/**
 * GameStart：程序入口。
 *
 * 从这里创建窗口、主面板、监听器、游戏线程，并正式启动整个游戏。
 */
package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

public class GameStart {
    public static void main(String[] args) {
        GameJFrame frame = new GameJFrame();
        GameMainJPanel panel = new GameMainJPanel();
        GameListener listener = new GameListener();
        GameThread thread = new GameThread();

        frame.setjPanel(panel);
        frame.setMouseListener(listener);
        frame.setKeyListener(listener);
        frame.setThead(thread);
        frame.start();
    }
}
