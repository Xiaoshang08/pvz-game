package com.tedu.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tedu.util.GameImage;

public class BossZombie extends GunZombie {
    private static final String IMAGE_PATH = "images/zombies/boss.png";
    private static final BufferedImage IMAGE = GameImage.get(IMAGE_PATH);
    private static final String BOSS_NAME = "\u6d2a\u7ca4\u8d24";
    private static final int BOSS_HEALTH = 12;
    private static final int BOSS_W = 224;
    private static final int BOSS_H = 180;

    public BossZombie(int x, int y) {
        super(x, y, BOSS_W, BOSS_H, BOSS_HEALTH);
    }

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        int x = board == null ? getX() : board.toScreenX(getX());
        int y = getY();

        if (IMAGE != null) {
            GameImage.draw(g, IMAGE, x - 18, y - 20, getW() + 36, getH() + 28);
        } else {
            g.setColor(new Color(78, 116, 72));
            g.fillRoundRect(x, y + 20, getW(), getH() - 20, 18, 18);
            g.setColor(new Color(158, 204, 142));
            g.fillOval(x + 58, y, 84, 70);
        }

        drawName(g, x, y);
        drawHealthBar(g, x, y);
    }

    private void drawName(Graphics g, int x, int y) {
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 26));
        FontMetrics metrics = g.getFontMetrics();
        int textX = x + (getW() - metrics.stringWidth(BOSS_NAME)) / 2;
        int textY = y - 22;
        g.setColor(new Color(0, 0, 0, 170));
        g.drawString(BOSS_NAME, textX + 2, textY + 2);
        g.setColor(new Color(255, 230, 90));
        g.drawString(BOSS_NAME, textX, textY);
    }

    private void drawHealthBar(Graphics g, int x, int y) {
        int barX = x + 28;
        int barY = y - 14;
        int barW = getW() - 56;
        g.setColor(new Color(22, 18, 18, 190));
        g.fillRoundRect(barX, barY, barW, 9, 8, 8);
        g.setColor(new Color(210, 48, 42));
        int hpW = Math.max(0, health) * barW / getMaxHealth();
        g.fillRoundRect(barX, barY, hpW, 9, 8, 8);
        g.setColor(new Color(255, 230, 170));
        g.drawRoundRect(barX, barY, barW, 9, 8, 8);
    }

    @Override
    public void takeDamage(int damage) {
        int oldHealth = health;
        super.takeDamage(damage);
        if (oldHealth > 0 && health <= 0) {
            GameBoard board = GameBoard.getInstance();
            if (board != null) {
                board.markContraBossDefeated();
            }
        }
    }

    @Override
    public int getBulletDamage() {
        return 2;
    }

    @Override
    public int getContactDamage() {
        return 4;
    }
}
