package com.tedu.element;

import com.tedu.manager.GameLoad;
import javax.swing.ImageIcon;
import java.awt.Graphics;

public class FireMan extends ElementObj {

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public void keyClick(boolean pressed, int keyCode) {
        switch (keyCode) {
            case 65: left = pressed; break;
            case 68: right = pressed; break;
            case 87:
                if (pressed && isOnGround) {
                    vy = JUMP_VELOCITY;
                    isOnGround = false;
                }
                break;
        }
    }

    @Override
    protected void move() {
        int dx = 0;
        if (left)
            dx = -getMoveSpeed();
        if (right)
            dx = getMoveSpeed();
        if (dx != 0)
            moveX(dx);

        vy += GRAVITY;
        moveY((int) vy);
        checkGround();

        checkPickupDiamond();
        checkOpenDoor();
        checkDeathByTerrain();
        checkTrapDeath();
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        setX(Integer.parseInt(split[0]));
        setY(Integer.parseInt(split[1]));
        ImageIcon icon = GameLoad.imgMap.get("fire_man");
        setIcon(icon);
        setW(icon.getIconWidth());
        setH(icon.getIconHeight());
        setRoleType("fire");
        moveSpeed = 5;
        setRequiredDiamondCount(getRequiredDiamondCount());
        return this;
    }

    @Override
    protected int getSpawnX() { return 64; }
    @Override
    protected int getSpawnY() { return 544; }
    @Override
    protected String getDiamondRoleType() { return "fire_diamond"; }
    @Override
    protected String getDoorRoleType() { return "fire_door"; }
    @Override
    protected String getDeathTerrainRoleType() { return "water_terrain"; }
    @Override
    protected int getRequiredDiamondCount() { return 3; }
}