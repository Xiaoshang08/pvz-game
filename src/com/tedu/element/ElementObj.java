/**
 * ElementObj：所有游戏元素的父类。
 * 提供位置、尺寸、存活状态、碰撞矩形以及统一的 model 更新流程。
 * 植物、僵尸、子弹、背景、UI 等对象都继承自它。
 */
package com.tedu.element;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.util.ArrayList;
import java.util.List;

public abstract class ElementObj {
    private int x;
    private int y;
    private int w;
    private int h;
    private ImageIcon icon;
    private boolean live = true;
    private boolean movable = true;

    // level2新增属性：

    private String roleType;

    // 移动相关
    protected boolean left, right;
    protected double vy = 0;
    protected boolean isOnGround = false;
    protected static final double GRAVITY = 0.5;
    protected static final double JUMP_VELOCITY = -9.8;

    // 钻石收集计数
    private int collectedDiamondCount = 0;
    private int requiredDiamondCount = 0;

    protected int moveSpeed = 5;

    protected ElementManager em = ElementManager.getManager();
    protected static final int MAP_WIDTH = 1280;
    protected static final int MAP_HEIGHT = 760;

    // end of level2新增属性

    public ElementObj() {
    }

    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }

    public abstract void showElement(Graphics g);

    public void keyClick(boolean pressed, int keyCode) {
    }

    public void mouseClick(int x, int y) {
    }

    protected void move() {
    }

    public final void model(long gameTime) {
        updateImage(gameTime);
        move();
        add(gameTime);
    }

    protected void updateImage(long time) {
    }

    protected void add(long gameTime) {
    }

    public void die() {
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, w, h);
    }

    public boolean pk(ElementObj obj) {
        return this.getRectangle().intersects(obj.getRectangle());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    // level2新增方法：
    protected void moveX(int dx) {
        int newX = getX() + dx;
        setX(newX);
        if (getX() < 0)
            setX(0);
        if (getX() + getW() > MAP_WIDTH)
            setX(MAP_WIDTH - getW());

        for (ElementObj terrain : getAllTerrains()) {
            if (this.pk(terrain) && terrain.isBlocking()) {
                setX(getX() - dx);
                break;
            }
        }
    }

    protected void moveY(int dy) {
        int newY = getY() + dy;
        setY(newY);
        if (getY() + getH() > MAP_HEIGHT) {
            setY(MAP_HEIGHT - getH());
            if (vy > 0)
                vy = 0;
            isOnGround = true;
        }
        if (getY() < 0) {
            setY(0);
            if (vy < 0)
                vy = 0;
        }

        for (ElementObj terrain : getAllTerrains()) {
            if (this.pk(terrain) && terrain.isBlocking()) {
                if (dy > 0) {
                    setY(terrain.getY() - getH());
                    vy = 0;
                    isOnGround = true;
                } else if (dy < 0) {
                    setY(terrain.getY() + terrain.getH());
                    vy = 0;
                }
                break;
            }
        }
    }

    protected void checkGround() {
        int footY = getY() + getH();
        for (ElementObj t : getAllTerrains()) {
            if (!t.isBlocking())
                continue;
            if (getX() + getW() > t.getX() && getX() < t.getX() + t.getW() &&
                    Math.abs(footY - t.getY()) <= 5) {
                isOnGround = true;
                return;
            }
        }
        isOnGround = false;
    }

    protected void checkPickupDiamond() {
        String targetType = getDiamondRoleType();
        if (targetType.isEmpty())
            return;
        List<ElementObj> diamonds = (targetType.contains("fire")) ? em.getElementsByKey(GameElement.FIRE_DIAMOND)
                : em.getElementsByKey(GameElement.WATER_DIAMOND);
        for (ElementObj diamond : diamonds) {
            if (this.pk(diamond) && diamond.getRoleType().equals(targetType)) {
                collectedDiamondCount++;
                diamond.setLive(false);
                em.getElementsByKey(GameElement.FIRE_DIAMOND).remove(diamond);
                em.getElementsByKey(GameElement.WATER_DIAMOND).remove(diamond);
                break;
            }
        }
    }

    protected void checkOpenDoor() {
        String doorType = getDoorRoleType();
        if (doorType.isEmpty())
            return;
        List<ElementObj> doors = (doorType.contains("fire")) ? em.getElementsByKey(GameElement.FIRE_DOOR)
                : em.getElementsByKey(GameElement.WATER_DOOR);
        for (ElementObj doorObj : doors) {
            if (this.pk(doorObj)) {
                // 检查门是否已打开
                boolean isOpen = false;
                if (doorType.contains("fire") && doorObj instanceof FireDoor) {
                    isOpen = ((FireDoor) doorObj).isOpen();
                } else if (doorType.contains("water") && doorObj instanceof WaterDoor) {
                    isOpen = ((WaterDoor) doorObj).isOpen();
                }
                if (isOpen) {
                    // 门已开，无需提示
                    return;
                }
                // 未集齐钻石且门未开，显示提示
                if (!hasAllDiamonds()) {
                    em.showTip("必须收集满三个钻石才能开门哦", 2000);
                    return;
                }
                // 集齐钻石且门未开，执行开门
                if (doorType.contains("fire") && doorObj instanceof FireDoor) {
                    ((FireDoor) doorObj).setOpen(true);
                    collectedDiamondCount = 0;
                    break;
                } else if (doorType.contains("water") && doorObj instanceof WaterDoor) {
                    ((WaterDoor) doorObj).setOpen(true);
                    collectedDiamondCount = 0;
                    break;
                }
            }
        }
    }

    protected void checkDeathByTerrain() {
        String deathType = getDeathTerrainRoleType();
        if (deathType.isEmpty())
            return;
        List<ElementObj> deathTerrains = (deathType.contains("water")) ? em.getElementsByKey(GameElement.WATER_TERRAIN)
                : em.getElementsByKey(GameElement.FIRE_TERRAIN);
        for (ElementObj terrain : deathTerrains) {
            if (isStandingOn(terrain)) {
                resetToSpawn();
                return;
            }
        }
    }

    protected void checkTrapDeath() {
        for (ElementObj trap : em.getElementsByKey(GameElement.TRAP_TERRAIN)) {
            if (isStandingOn(trap)) {
                resetToSpawn();
                return;
            }
        }
    }

    /** 死亡重置到出生点，但保留已收集的钻石数量 */
    protected void resetToSpawn() {
        setX(getSpawnX());
        setY(getSpawnY());
        vy = 0;
        isOnGround = false;
        // 注意：不清零 collectedDiamondCount
    }

    protected boolean isStandingOn(ElementObj terrain) {
        if (terrain == null)
            return false;
        int footY = this.getY() + this.getH();
        int terrainTop = terrain.getY();
        if (Math.abs(footY - terrainTop) > 5)
            return false;
        return this.getX() + this.getW() > terrain.getX() &&
                this.getX() < terrain.getX() + terrain.getW();
    }

    public ElementObj createElement(String str) {
        return null;
    }

    public boolean hasAllDiamonds() {
        return collectedDiamondCount >= requiredDiamondCount;
    }

    protected int getSpawnX() {
        return 0;
    }

    protected int getSpawnY() {
        return 0;
    }

    protected String getDiamondRoleType() {
        return "";
    }

    protected String getDoorRoleType() {
        return "";
    }

    protected String getDeathTerrainRoleType() {
        return "";
    }

    protected int getRequiredDiamondCount() {
        return 0;
    }

    protected int getMoveSpeed() {
        return moveSpeed;
    }

    public boolean isBlocking() {
        return true;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public void setRequiredDiamondCount(int requiredDiamondCount) {
        this.requiredDiamondCount = requiredDiamondCount;
    }

    // ---------- 辅助方法 ----------
    protected List<ElementObj> getAllTerrains() {
        List<ElementObj> list = new ArrayList<>();
        list.addAll(em.getElementsByKey(GameElement.MAPS));
        list.addAll(em.getElementsByKey(GameElement.FIRE_TERRAIN));
        list.addAll(em.getElementsByKey(GameElement.WATER_TERRAIN));
        list.addAll(em.getElementsByKey(GameElement.TRAP_TERRAIN));
        list.addAll(em.getElementsByKey(GameElement.FIRE_DOOR));
        list.addAll(em.getElementsByKey(GameElement.WATER_DOOR));
        return list;
    }
    // end of level2新增方法
}
