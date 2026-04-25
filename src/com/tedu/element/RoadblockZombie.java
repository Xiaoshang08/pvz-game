package com.tedu.element;

import java.awt.image.BufferedImage;

import com.tedu.util.GameImage;

public class RoadblockZombie extends Zombie {
    private static final BufferedImage IMAGE = GameImage.get("images/zombies/1.png");

    public RoadblockZombie(int row, int x, int y) {
        super(row, x, y, 42, 62, 8, 1, 2);
    }

    @Override
    protected BufferedImage getSprite() {
        return IMAGE;
    }
}
