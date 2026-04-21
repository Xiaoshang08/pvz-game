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
    ZOMBIE,
    BULLET,
    SUN,
    UI,
    EFFECT;
}
