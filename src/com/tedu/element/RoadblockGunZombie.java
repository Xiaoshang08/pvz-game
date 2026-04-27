package com.tedu.element;

import java.awt.image.BufferedImage;

import com.tedu.util.GameImage;

public class RoadblockGunZombie extends GunZombie {
    private static final BufferedImage IMAGE = GameImage.get("images/zombies/1_gun.png");

    public RoadblockGunZombie(int x, int y) {
        super(x, y, 56, 82, 8);
    }

    @Override
    protected BufferedImage getSprite() {
        return IMAGE;
    }
}
