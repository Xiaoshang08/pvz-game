package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

public class GunZombie extends ElementObj implements DamageableEnemy {
    private static final double GRAVITY = 0.65;
    private static final double MAX_FALL_SPEED = 14.0;
    private static final int DETECT_RANGE = 640;
    private static final double BULLET_SPEED = 8.0;
    private static final int SHOOT_INTERVAL = 42;
    protected int health = 4;
    private final int maxHealth;
    private int shootCounter;
    private double verticalSpeed;

    public GunZombie(int x, int y) {
        this(x, y, 56, 82, 4);
    }

    protected GunZombie(int x, int y, int w, int h, int health) {
        super(x, y, w, h, null);
        this.health = health;
        this.maxHealth = health;
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
        int hpBarW = 42;
        int hp = Math.max(0, health) * hpBarW / Math.max(1, maxHealth);
        g.fillRect(x + 8, y - 10, hp, 4);
    }

    @Override
    protected void move() {
        GameBoard board = GameBoard.getInstance();
        ContraPlayer player = board == null ? null : board.getContraPlayer();

        applyContraGravity(board);

        if (player != null && player.isLive() && canSeePlayer(player)) {
            shootCounter++;
            if (shootCounter >= SHOOT_INTERVAL) {
                shootCounter = 0;
                shootAt(player);
            }
        } else if (shootCounter < SHOOT_INTERVAL) {
            shootCounter++;
        }
    }

    private boolean canSeePlayer(ContraPlayer player) {
        int dx = (player.getX() + player.getW() / 2) - (getX() + getW() / 2);
        int dy = (player.getY() + player.getH() / 2) - (getY() + getH() / 2);
        return dx * dx + dy * dy <= DETECT_RANGE * DETECT_RANGE;
    }

    private void shootAt(ContraPlayer player) {
        double startX = getX() + getW() / 2.0;
        double startY = getY() + 42.0;
        double targetX = player.getX() + player.getW() / 2.0;
        double targetY = player.getY() + player.getH() / 2.0;
        double dx = targetX - startX;
        double dy = targetY - startY;
        double length = Math.max(1.0, Math.sqrt(dx * dx + dy * dy));
        double vx = dx / length * BULLET_SPEED;
        double vy = dy / length * BULLET_SPEED;
        ElementManager.getManager().addElement(new EnemyBullet((int) startX, (int) startY, vx, vy, getBulletDamage()), GameElement.ENEMY_BULLET);
    }

    private void applyContraGravity(GameBoard board) {
        if (board == null) {
            return;
        }

        int leftFoot = getX() + 7;
        int rightFoot = getX() + getW() - 7;
        int oldFootY = getY() + getH();
        boolean onGround = board.isOnContraSurface(leftFoot, rightFoot, oldFootY);
        if (!onGround) {
            verticalSpeed = Math.min(MAX_FALL_SPEED, verticalSpeed + GRAVITY);
        } else if (verticalSpeed > 0) {
            verticalSpeed = 0;
        }

        int nextY = getY() + (int) Math.round(verticalSpeed);
        int nextFootY = nextY + getH();
        if (verticalSpeed >= 0) {
            int surfaceY = board.getContraSurfaceBetween(leftFoot, rightFoot, oldFootY + 1, nextFootY);
            if (surfaceY != -1) {
                setY(surfaceY - getH());
                verticalSpeed = 0;
                return;
            }
        }
        setY(nextY);
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

    public int getBulletDamage() {
        return 1;
    }

    public int getContactDamage() {
        return 2;
    }

    protected int getMaxHealth() {
        return maxHealth;
    }
}
