package com.tedu.element;

import com.tedu.manager.GameLoad;
import javax.swing.ImageIcon;
import java.awt.Graphics;

public class FireDiamond extends ElementObj {
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        setX(Integer.parseInt(split[0]));
        setY(Integer.parseInt(split[1]));
        ImageIcon icon = GameLoad.imgMap.get("fire_diamond");
        setIcon(icon);
        setW(icon.getIconWidth());
        setH(icon.getIconHeight());
        setRoleType("fire_diamond");
        return this;
    }

    @Override
    public boolean isBlocking() { return false; }
}