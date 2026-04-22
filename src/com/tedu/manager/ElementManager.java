/**
 * ElementManager：元素总管理器。
 *
 * 统一保存并分类管理背景、格子、植物、僵尸、子弹、UI 等对象。
 */
package com.tedu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tedu.element.ElementObj;

public class ElementManager {
    private Map<GameElement, List<ElementObj>> gameElements;
    private static ElementManager EM = null;

    public static synchronized ElementManager getManager() {
        if (EM == null) {
            EM = new ElementManager();
        }
        return EM;
    }

    private ElementManager() {
        init();
    }

    public void init() {
        gameElements = new HashMap<>();
        for (GameElement ge : GameElement.values()) {
            gameElements.put(ge, new ArrayList<>());
        }
    }

    public void addElement(ElementObj obj, GameElement ge) {
        if (obj != null) {
            gameElements.get(ge).add(obj);
        }
    }

    public List<ElementObj> getElementsByKey(GameElement ge) {
        return gameElements.get(ge);
    }

    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }

    // level2新增:
    // 提示消息相关
    private String tipMessage = null;
    private long tipExpireTime = 0;
    private int tipX = 0;
    private int tipY = 0;

    // 一次性提示标志（触发后不再重复显示）
    private boolean firstMoveTipShown = false;
    private boolean trapTipShown = false;

    /**
     * 在指定位置显示一条临时提示消息
     * 
     * @param msg        提示内容
     * @param x          显示x坐标，-1表示居中
     * @param y          显示y坐标
     * @param durationMs 持续时间（毫秒）
     */
    public void showTip(String msg, int x, int y, int durationMs) {
        this.tipMessage = msg;
        this.tipX = x;
        this.tipY = y;
        this.tipExpireTime = System.currentTimeMillis() + durationMs;
    }

    /**
     * 显示第一次移动的提示（只显示一次）
     */
    public void showFirstMoveTip(String msg, int x, int y, int durationMs) {
        if (!firstMoveTipShown) {
            firstMoveTipShown = true;
            showTip(msg, x, y, durationMs);
        }
    }

    /**
     * 显示毒液区域警告（只显示一次）
     */
    public void showTrapTip(String msg, int x, int y, int durationMs) {
        if (!trapTipShown) {
            trapTipShown = true;
            showTip(msg, x, y, durationMs);
        }
    }

    /**
     * 重置一次性提示标志（游戏重新开始时调用）
     */
    public void resetTipFlags() {
        firstMoveTipShown = false;
        trapTipShown = false;
    }

    /**
     * 获取当前待显示的提示消息（若已过期则返回 null）
     * 
     * @return 提示消息或 null
     */
    public String getTipMessage() {
        if (tipMessage != null && System.currentTimeMillis() < tipExpireTime) {
            return tipMessage;
        }
        tipMessage = null;
        return null;
    }

    /**
     * 获取提示消息的x坐标
     */
    public int getTipX() {
        return tipX;
    }

    /**
     * 获取提示消息的y坐标
     */
    public int getTipY() {
        return tipY;
    }
}
