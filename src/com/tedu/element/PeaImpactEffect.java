package com.tedu.element;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.tedu.util.GameImage;

public class PeaImpactEffect extends ElementObj {
    private static final BufferedImage IMAGE = GameImage.get("images/effects/pea_impact.png");
    private static final int LIFE_TICKS = 12;

    private int tick;

    public PeaImpactEffect(int centerX, int centerY) {
        super(centerX - 34, centerY - 34, 68, 68, null);
    }

    @Override
    public void showElement(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            float progress = Math.min(1f, tick / (float) LIFE_TICKS);
            float alpha = 1f - progress;
            int size = Math.round(getW() * (0.8f + 0.35f * progress));
            int drawX = getX() - (size - getW()) / 2;
            int drawY = getY() - (size - getH()) / 2;

            GameBoard board = GameBoard.getInstance();
            int screenX = board == null ? drawX : board.toScreenX(drawX);
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, Math.max(0f, alpha)));

            if (IMAGE != null) {
                GameImage.draw(g2, IMAGE, screenX, drawY, size, size);
            }
        } finally {
            g2.dispose();
        }
    }

    @Override
    protected void add(long gameTime) {
        tick++;
        if (tick >= LIFE_TICKS) {
            setLive(false);
        }
    }
}
