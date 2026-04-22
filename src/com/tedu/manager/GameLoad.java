/**
 * GameLoad：游戏加载器。
 *
 * 负责在开局时创建并注册背景、格子等基础元素。
 * 原先底部的信息栏已移除，后续可以把图鉴等功能放到主界面中继续扩展。
 */

/**
 * 新增：同时支持冰火人模式的地图、角色、地形等资源的反射加载。
 */

package com.tedu.manager;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.tedu.element.GameBoard;
import com.tedu.element.GridCell;
import com.tedu.element.GameInfoPanel;

//新增导包：
import com.tedu.element.ElementObj;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class GameLoad {
    private static final ElementManager em = ElementManager.getManager();
    public static final Map<String, ImageIcon> imgMap = new HashMap<>();

    // ========== 冰火人模式资源加载相关 ==========
    private static Properties pro = new Properties();
    private static Map<String, Class<?>> objMap = new HashMap<>();

    static {
        loadObj();
        loadImg();
    }

    private static void loadObj() {
        String path = "com/tedu/text/obj.pro";
        ClassLoader loader = GameLoad.class.getClassLoader();
        try (InputStream is = loader.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("找不到 obj.pro");
                return;
            }
            pro.load(is);
            Enumeration<?> names = pro.propertyNames();
            while (names.hasMoreElements()) {
                String key = names.nextElement().toString();
                String className = pro.getProperty(key);
                objMap.put(key, Class.forName(className));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void loadImg() {
        String configPath = "com/tedu/text/GameData.pro";
        ClassLoader loader = GameLoad.class.getClassLoader();
        Properties prop = new Properties();

        try (InputStream is = loader.getResourceAsStream(configPath)) {
            if (is == null) {
                System.err.println("找不到配置文件: " + configPath);
                return;
            }
            prop.load(is);
            Set<Object> keys = prop.keySet();

            // 获取项目根目录下的 assets/images/icefire 文件夹
            File imgDir = new File("assets/images/icefire");
            if (!imgDir.exists() || !imgDir.isDirectory()) {
                System.err.println("assets/images/icefire 目录不存在，请确认路径：" + imgDir.getAbsolutePath());
                return;
            }

            for (Object keyObj : keys) {
                String key = keyObj.toString();
                String imgRelativePath = prop.getProperty(key);
                String fileName = new File(imgRelativePath).getName();
                File imgFile = new File(imgDir, fileName);
                if (imgFile.exists()) {
                    imgMap.put(key, new ImageIcon(imgFile.getAbsolutePath()));
                } else {
                    System.err.println("图片不存在: " + imgFile.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ElementObj getObj(String key) {
        Class<?> clazz = objMap.get(key);
        if (clazz == null)
            return null;
        try {
            return (ElementObj) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加载指定ID的地图（冰火人模式）
     * 
     * @param mapId 地图编号（1）
     */
    public static void MapLoad(int mapId) {
        String mapPath = "com/tedu/text/" + mapId + ".map";
        ClassLoader loader = GameLoad.class.getClassLoader();
        try (InputStream is = loader.getResourceAsStream(mapPath)) {
            if (is == null) {
                System.err.println("地图文件不存在: " + mapPath);
                return;
            }
            pro.clear();
            pro.load(is);
            Enumeration<?> names = pro.propertyNames();
            while (names.hasMoreElements()) {
                String terrainType = names.nextElement().toString();
                String coordStr = pro.getProperty(terrainType);
                String[] coords = coordStr.split(";");
                for (String coord : coords) {
                    ElementObj obj = getObj(terrainType);
                    if (obj != null) {
                        ElementObj element = obj.createElement(coord);
                        if (terrainType.contains("fire_door")) {
                            em.addElement(element, GameElement.FIRE_DOOR);
                        } else if (terrainType.contains("water_door")) {
                            em.addElement(element, GameElement.WATER_DOOR);
                        } else if (terrainType.contains("fire_diamond")) {
                            em.addElement(element, GameElement.FIRE_DIAMOND);
                        } else if (terrainType.contains("water_diamond")) {
                            em.addElement(element, GameElement.WATER_DIAMOND);
                        } else if (terrainType.contains("trap_terrain")) {
                            em.addElement(element, GameElement.TRAP_TERRAIN);
                        } else if (terrainType.contains("fire_terrain")) {
                            em.addElement(element, GameElement.FIRE_TERRAIN);
                        } else if (terrainType.contains("water_terrain")) {
                            em.addElement(element, GameElement.WATER_TERRAIN);
                        } else {
                            em.addElement(element, GameElement.MAPS);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载冰火人角色（火人、水人）
     */
    public static void loadFireWaterMan() {
        ElementObj fireMan = getObj("fire_man");
        if (fireMan != null) {
            fireMan.createElement("64,544");
            em.addElement(fireMan, GameElement.FIRE_MAN);
        }
        ElementObj waterMan = getObj("water_man");
        if (waterMan != null) {
            waterMan.createElement("64,448");
            em.addElement(waterMan, GameElement.WATER_MAN);
        }
        // 显示控制提示（持续5秒）
        em.showTip("冰仔移动：↑（上）、←（左）、→（右）\n火仔移动：W（上）、A（左）、D（右）", 192, 472, 5000);
    }

    // ========== 普通模式（植物大战僵尸）原有加载逻辑 ==========

    private GameLoad() {
    }

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
