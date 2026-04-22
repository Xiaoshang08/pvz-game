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

    /**
     * 显示一条临时提示消息（在屏幕上停留 durationMs 毫秒）
     * 
     * @param msg        提示内容
     * @param durationMs 持续时间（毫秒）
     */
    public void showTip(String msg, int durationMs) {
        this.tipMessage = msg;
        this.tipExpireTime = System.currentTimeMillis() + durationMs;
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
}
