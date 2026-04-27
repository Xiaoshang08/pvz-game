package com.tedu.element;

/**
 * 可被玩家子弹或植物子弹伤害的敌人统一接口。
 */
public interface DamageableEnemy {
    void takeDamage(int damage);
}
