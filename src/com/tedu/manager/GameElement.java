/**
 * GameElement：元素类别枚举。
 *
 * 用来标记不同类型的游戏对象，方便 ElementManager 分类存放与读取。
 */
package com.tedu.manager;

public enum GameElement {
    BACKGROUND,
    GRID,
    PLANT,
    PLAYER,
    ZOMBIE,
    BULLET,
    ENEMY_BULLET,
    SUN,
    UI,
    EFFECT,
    // level2新增枚举值
    MAPS,
    FIRE_TERRAIN,
    WATER_TERRAIN,
    WATER_MAN,
    FIRE_MAN,
    FIRE_DOOR,
    WATER_DOOR,
    FIRE_DIAMOND,
    WATER_DIAMOND,
    TRAP_TERRAIN
}
