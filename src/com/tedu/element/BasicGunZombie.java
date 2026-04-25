package com.tedu.element;

import java.awt.image.BufferedImage;

import com.tedu.util.GameImage;

public class BasicGunZombie extends GunZombie {
    private static final BufferedImage IMAGE = GameImage.get("images/zombies/basic_zombie_gun.png");

    public BasicGunZombie(int x, int y) {
        super(x, y);
    }

    @Override
    protected BufferedImage getSprite() {
        return IMAGE;
    }
}
