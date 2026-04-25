package com.tedu.element;

import java.awt.image.BufferedImage;

import com.tedu.util.GameImage;

public class BucketZombie extends Zombie {
    private static final BufferedImage IMAGE = GameImage.get("images/zombies/2.png");

    public BucketZombie(int row, int x, int y) {
        super(row, x, y, 42, 62, 16, 1, 2);
    }

    @Override
    protected BufferedImage getSprite() {
        return IMAGE;
    }
}
