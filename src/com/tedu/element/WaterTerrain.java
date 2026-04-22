package com.tedu.element;

import com.tedu.manager.GameLoad;
import javax.swing.ImageIcon;
import java.awt.Graphics;

public class WaterTerrain extends ElementObj {
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        setX(Integer.parseInt(split[0]));
        setY(Integer.parseInt(split[1]));
        ImageIcon icon = GameLoad.imgMap.get("water_terrain");
        setIcon(icon);
        setW(icon.getIconWidth());
        setH(icon.getIconHeight());
        setRoleType("water");
        return this;
    }
}