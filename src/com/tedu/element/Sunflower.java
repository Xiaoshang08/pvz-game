/**
 * Sunflower：向日葵植物。
 *
 * 花费 50 阳光种植，每隔 20 秒产出 50 阳光。
 * 为了配合当前“自动收集”的资源系统，向日葵生成的阳光会先在自己头顶出现，
 * 停留片刻后自动加入总阳光，玩家无需额外点击。
 */
package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.util.GameImage;

public class Sunflower extends Plant {
    private static final String IMAGE_PATH = "images/plants/sunflower.png";
    private static final BufferedImage IMAGE = GameImage.get(IMAGE_PATH);

    private static final int SUNFLOWER_COST = 50;
    private static final int SUN_VALUE = 50;
    private static final int PRODUCE_INTERVAL = 667; // 大约 20 秒（667 * 30ms ≈ 20s）

    private int produceCounter = 0;

    public Sunflower(int row, int col, int x, int y, int w, int h) {
        super(row, col, x, y, w, h, 5);
    }

    @Override
    protected void drawPlant(Graphics g, int x, int y) {

        if (IMAGE != null) {
            GameImage.draw(g, IMAGE, x + 2, y + 2, getW() - 4, getH() - 4);
            return;
        }

        g.setColor(new Color(62, 149, 58));
        g.fillRect(x + 34, y + 26, 8, 34);

        g.setColor(new Color(77, 176, 87));
        g.fillOval(x + 18, y + 42, 20, 12);
        g.fillOval(x + 40, y + 42, 20, 12);

        g.setColor(new Color(128, 84, 33));
        g.fillOval(x + 16, y + 10, 44, 44);

        g.setColor(new Color(255, 203, 52));
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45);
            int px = x + 38 + (int) (Math.cos(angle) * 22) - 9;
            int py = y + 32 + (int) (Math.sin(angle) * 22) - 9;
            g.fillOval(px, py, 18, 18);
        }

        g.setColor(new Color(255, 226, 92));
        g.fillOval(x + 24, y + 18, 28, 28);

        g.setColor(Color.BLACK);
        g.fillOval(x + 31, y + 27, 4, 4);
        g.fillOval(x + 41, y + 27, 4, 4);
        g.drawArc(x + 31, y + 29, 12, 8, 180, 180);
    }

    @Override
    protected void add(long gameTime) {
        produceCounter++;
        if (produceCounter < PRODUCE_INTERVAL) {
            return;
        }
        produceCounter = 0;

        int sunX = getX() + getW() / 2 - 18;
        int sunY = getY() - 10;
        ElementManager.getManager().addElement(Sun.createProducedSun(sunX, sunY, SUN_VALUE, 45), GameElement.SUN);
    }

    public static int getSunflowerCost() {
        return SUNFLOWER_COST;
    }
}
