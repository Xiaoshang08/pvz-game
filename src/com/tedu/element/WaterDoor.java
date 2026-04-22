package com.tedu.element;

import com.tedu.manager.GameLoad;
import java.awt.Graphics;

public class WaterDoor extends ElementObj {
    private boolean isOpen = false;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        setX(Integer.parseInt(split[0]));
        setY(Integer.parseInt(split[1]));
        setIcon(GameLoad.imgMap.get("door_close"));
        setW(getIcon().getIconWidth());
        setH(getIcon().getIconHeight());
        setRoleType("water_door");
        return this;
    }

    @Override
    public boolean isBlocking() { return false; }

    public boolean isOpen() { return isOpen; }
    public void setOpen(boolean open) {
        isOpen = open;
        if (isOpen) {
            setIcon(GameLoad.imgMap.get("door_open"));
        } else {
            setIcon(GameLoad.imgMap.get("door_close"));
        }
    }
}