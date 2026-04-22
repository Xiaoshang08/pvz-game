package com.tedu.element;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tedu.util.GameImage;

final class PeaBulletSprite {
    private static final String IMAGE_PATH = "images/projectiles/pea.png";
    private static final BufferedImage IMAGE = createSprite(GameImage.get(IMAGE_PATH));

    private PeaBulletSprite() {}

    static boolean draw(Graphics g, int x, int y, int w, int h) {
        if (IMAGE == null) {
            return false;
        }
        GameImage.draw(g, IMAGE, x, y, w, h);
        return true;
    }

    private static BufferedImage createSprite(BufferedImage source) {
        if (source == null) {
            return null;
        }

        int minX = source.getWidth();
        int minY = source.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                if (isPeaPixel(source.getRGB(x, y))) {
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }

        if (maxX < minX || maxY < minY) {
            return source;
        }

        int padding = 10;
        minX = Math.max(0, minX - padding);
        minY = Math.max(0, minY - padding);
        maxX = Math.min(source.getWidth() - 1, maxX + padding);
        maxY = Math.min(source.getHeight() - 1, maxY + padding);

        BufferedImage sprite = new BufferedImage(maxX - minX + 1, maxY - minY + 1, BufferedImage.TYPE_INT_ARGB);
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                int rgb = source.getRGB(x, y);
                if (isPeaPixel(rgb)) {
                    sprite.setRGB(x - minX, y - minY, rgb);
                }
            }
        }
        return sprite;
    }

    private static boolean isPeaPixel(int argb) {
        int alpha = (argb >>> 24) & 0xff;
        int red = (argb >>> 16) & 0xff;
        int green = (argb >>> 8) & 0xff;
        int blue = argb & 0xff;
        if (alpha < 16) {
            return false;
        }
        if (red > 220 && green > 220 && blue > 220) {
            return false;
        }
        return green > red + 12 && green > blue + 12;
    }
}
