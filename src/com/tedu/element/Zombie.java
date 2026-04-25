/**
 * Zombie：僵尸对象。
 *
 * 负责向左移动、显示血量、与植物近战攻击、承受子弹伤害，
 * 并在突破最左边时作为失败判定的依据。
 */
package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tedu.util.GameImage;

public class Zombie extends ElementObj implements DamageableEnemy, LaneEnemy {
    private static final String IMAGE_PATH = "images/zombies/basic_zombie.png";
    private static final BufferedImage IMAGE = GameImage.get(IMAGE_PATH);

    private final int row;
    private final int speed;
    private final int moveInterval;
    protected int health;
    private final int maxHealth;
    private Plant attackTarget;
    private int attackCounter = 0;
    private int moveCounter = 0;
    private static final int ATTACK_INTERVAL = 30;
    private boolean enteringHouse = false;
    private boolean reachedHouse = false;

    public Zombie(int row, int x, int y) {
        this(row, x, y, 42, 62, 4, 1, 2);
    }

    protected Zombie(int row, int x, int y, int w, int h, int health, int speed, int moveInterval) {
        super(x, y, w, h, null);
        this.row = row;
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.moveInterval = moveInterval;
    }

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        int x = board == null ? getX() : board.toScreenX(getX());
        int y = getY();

        BufferedImage sprite = getSprite();
        if (sprite != null) {
            GameImage.draw(g, sprite, x - 2, y - 18, 52, 84);
        } else {
            drawFallback(g, x, y);
        }

        drawStatus(g, x, y);
    }

    @Override
    protected void move() {
        if (enteringHouse) {
            walkToHouseDoor();
            return;
        }

        if (attackTarget != null && attackTarget.isLive()) {
            attackCounter++;
            if (attackCounter >= getAttackInterval()) {
                attackCounter = 0;
                attackTarget.takeDamage(1);
            }
            return;
        }

        attackCounter = 0;
        moveCounter++;
        if (moveCounter >= moveInterval) {
            setX(getX() - speed);
            moveCounter = 0;

            GameBoard board = GameBoard.getInstance();
            if (board != null && !board.isContraMode() && getX() <= board.getLawnLeftEdgeX()) {
                enteringHouse = true;
                attackTarget = null;
            }
        }
    }

    private void walkToHouseDoor() {
        GameBoard board = GameBoard.getInstance();
        if (board == null) {
            setX(getX() - speed);
            return;
        }

        int targetX = board.getHouseDoorTargetX();
        int targetY = board.getHouseDoorTargetY(row);

        if (getX() > targetX) {
            setX(Math.max(targetX, getX() - 2));
        }
        if (getY() < targetY) {
            setY(Math.min(targetY, getY() + 2));
        } else if (getY() > targetY) {
            setY(Math.max(targetY, getY() - 2));
        }

        if (getX() <= targetX && Math.abs(getY() - targetY) <= 2) {
            reachedHouse = true;
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

    public void setAttackTarget(Plant attackTarget) {
        this.attackTarget = attackTarget;
    }

    public boolean isSameRow(Plant plant) {
        return plant != null && plant.getRow() == row;
    }

    public boolean hasReachedLeftEdge() {
        return getX() <= 0;
    }

    public boolean isEnteringHouse() {
        return enteringHouse;
    }

    public boolean hasEnteredHouse() {
        return reachedHouse;
    }

    protected BufferedImage getSprite() {
        return IMAGE;
    }

    protected void drawFallback(Graphics g, int x, int y) {
        g.setColor(new Color(110, 150, 110));
        g.fillRect(x, y, getW(), getH());

        g.setColor(new Color(155, 204, 155));
        g.fillOval(x + 6, y - 18, 30, 30);

        g.setColor(Color.BLACK);
        g.fillOval(x + 13, y - 10, 4, 4);
        g.fillOval(x + 25, y - 10, 4, 4);
        g.drawLine(x + 14, y - 2, x + 27, y - 2);
    }

    protected void drawStatus(Graphics g, int x, int y) {
        g.setColor(new Color(170, 50, 50));
        g.drawString("HP:" + health + "/" + maxHealth, x, y - 24);

        if (attackTarget != null && attackTarget.isLive()) {
            g.setColor(Color.ORANGE);
            g.drawString("EAT!", x + 2, y + getH() + 16);
        }
    }

    protected int getAttackInterval() {
        return ATTACK_INTERVAL;
    }

    public int getRow() { return row; }
}
