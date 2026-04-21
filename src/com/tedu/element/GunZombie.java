package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

public class GunZombie extends ElementObj {
    private static final int SPEED = 2;
    private static final int SHOOT_INTERVAL = 42;
    private int health = 4;
    private int shootCounter;
    private int walkCounter;

    public GunZombie(int x, int y) {
        super(x, y, 56, 82, null);
        shootCounter = 15;
    }

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        int x = board == null ? getX() : board.toScreenX(getX());
        int y = getY();

        g.setColor(new Color(31, 42, 27));
        g.fillOval(x + 6, y + 72, 42, 10);
        g.setColor(new Color(68, 83, 38));
        g.fillRoundRect(x + 18, y + 26, 24, 40, 8, 8);
        g.setColor(new Color(45, 61, 34));
        g.fillRect(x + 9, y + 34, 12, 28);
        g.fillRect(x + 38, y + 36, 12, 26);
        g.setColor(new Color(96, 119, 60));
        g.fillRect(x + 14, y + 62, 12, 18);
        g.fillRect(x + 35, y + 62, 12, 18);

        g.setColor(new Color(160, 194, 132));
        g.fillOval(x + 14, y + 10, 34, 30);
        g.setColor(new Color(64, 76, 42));
        g.fillArc(x + 8, y, 46, 28, 0, 180);
        g.fillRect(x + 10, y + 13, 46, 8);

        g.setColor(new Color(238, 248, 200));
        g.fillOval(x + 23, y + 19, 7, 7);
        g.fillOval(x + 37, y + 19, 7, 7);
        g.setColor(Color.BLACK);
        g.fillOval(x + 25, y + 21, 3, 3);
        g.fillOval(x + 39, y + 21, 3, 3);
        g.drawLine(x + 28, y + 32, x + 43, y + 34);

        g.setColor(new Color(66, 56, 45));
        g.fillRect(x + 38, y + 43, 46, 7);
        g.setColor(new Color(35, 34, 32));
        g.fillRect(x + 76, y + 41, 18, 4);
        g.fillRect(x + 58, y + 50, 10, 9);

        g.setColor(new Color(202, 65, 52));
        g.fillRect(x + 8, y - 10, Math.max(0, health) * 10, 4);
    }

    @Override
    protected void move() {
        GameBoard board = GameBoard.getInstance();
        ContraPlayer player = board == null ? null : board.getContraPlayer();
        int playerX = player == null ? getX() - 500 : player.getX();
        int distance = getX() - playerX;

        if (distance > 520) {
            walkCounter++;
            if (walkCounter % 2 == 0) {
                setX(getX() - SPEED);
            }
        }

        if (distance < 930 && distance > 120) {
            shootCounter++;
            if (shootCounter >= SHOOT_INTERVAL) {
                shootCounter = 0;
                ElementManager.getManager().addElement(new EnemyBullet(getX() + 12, getY() + 42), GameElement.ENEMY_BULLET);
            }
        }

        if (board != null && getX() < board.getContraCameraX() - 160) {
            setLive(false);
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            setLive(false);
            GameBoard board = GameBoard.getInstance();
            if (board != null) {
                board.addKill();
            }
        }
    }
}
