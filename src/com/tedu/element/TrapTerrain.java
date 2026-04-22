package com.tedu.element;

import com.tedu.manager.GameLoad;
import java.awt.Graphics;

public class TrapTerrain extends ElementObj {
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        setX(Integer.parseInt(split[0]));
        setY(Integer.parseInt(split[1]));
        setIcon(GameLoad.imgMap.get("trap_terrain"));
        setW(getIcon().getIconWidth());
        setH(getIcon().getIconHeight());
        setRoleType("trap");
        return this;
    }

    @Override
    public boolean isBlocking() { return false; }
}