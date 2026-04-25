package com.tedu.element;

import com.tedu.manager.GameLoad;
import javax.swing.ImageIcon;
import java.awt.Graphics;

public class WaterMan extends ElementObj {

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public void keyClick(boolean pressed, int keyCode) {
        if (pressed) {
            switch (keyCode) {
                case 37:
                    left = pressed;
                    break;
                case 39:
                    right = pressed;
                    break;
                case 38:
                    if (isOnGround) {
                        vy = JUMP_VELOCITY;
                        isOnGround = false;
                    }
                    break;
            }
            // 检测第一次移动（全局只触发一次）
            if (keyCode == 37 || keyCode == 38 || keyCode == 39) {
                em.showFirstMoveTip("注意：冰仔不能靠近火地，火仔不能落入冰面哦！\n别忘了收集冰之精魄和火之精魄", 388, 500, 5000);
            }
        } else {
            switch (keyCode) {
                case 37:
                    left = pressed;
                    break;
                case 39:
                    right = pressed;
                    break;
                case 38:
                    break;
            }
        }
    }

    @Override
    protected void move() {
        int dx = 0;
        if (left) {
            dx = -getMoveSpeed();
            // 向左走时切换为 waterman_left.png
            ImageIcon leftIcon = GameLoad.imgMap.get("waterman_left");
            if (leftIcon != null && (this.getIcon() == null || this.getIcon() != leftIcon)) {
                setIcon(leftIcon);
                setW(leftIcon.getIconWidth());
                setH(leftIcon.getIconHeight());
            }
        } else if (right) {
            dx = getMoveSpeed();
            // 向右走时切换为 waterman_right.png
            ImageIcon rightIcon = GameLoad.imgMap.get("waterman_right");
            if (rightIcon != null && (this.getIcon() == null || this.getIcon() != rightIcon)) {
                setIcon(rightIcon);
                setW(rightIcon.getIconWidth());
                setH(rightIcon.getIconHeight());
            }
        } else {
            // 静止时显示 water_man.png
            ImageIcon defaultIcon = GameLoad.imgMap.get("water_man");
            if (defaultIcon != null && (this.getIcon() == null || this.getIcon() != defaultIcon)) {
                setIcon(defaultIcon);
                setW(defaultIcon.getIconWidth());
                setH(defaultIcon.getIconHeight());
            }
        }
        if (dx != 0)
            moveX(dx);

        vy += GRAVITY;
        moveY((int) vy);
        checkGround();

        checkPickupDiamond();
        checkOpenDoor();
        checkDeathByTerrain();
        checkTrapDeath();

        // 检测是否到达危险区域（x>832, y<256）（全局只触发一次）
        if (getX() > 832 && getY() < 256) {
            em.showTrapTip("小心毒液！冰仔和火仔落入毒液都会死亡", 896, 128, 5000);
        }
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        setX(Integer.parseInt(split[0]));
        setY(Integer.parseInt(split[1]));
        ImageIcon icon = GameLoad.imgMap.get("water_man");
        if (icon == null) {
            System.err.println("WaterMan: water_man 图片加载失败");
        } else {
            setIcon(icon);
            setW(icon.getIconWidth());
            setH(icon.getIconHeight());
        }
        setRoleType("water");
        moveSpeed = 5;
        setRequiredDiamondCount(getRequiredDiamondCount());
        return this;
    }

    @Override
    protected int getSpawnX() {
        return 64;
    }

    @Override
    protected int getSpawnY() {
        return 448;
    }

    @Override
    protected String getDiamondRoleType() {
        return "water_diamond";
    }

    @Override
    protected String getDoorRoleType() {
        return "water_door";
    }

    @Override
    protected String getDeathTerrainRoleType() {
        return "fire_terrain";
    }

    @Override
    protected int getRequiredDiamondCount() {
        return 3;
    }
}