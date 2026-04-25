package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.util.GameImage;

public class ContraPlayer extends ElementObj {
    private static final String IMAGE_PATH = "images/plants/pea_shooter.png";
    private static final BufferedImage IMAGE = GameImage.get(IMAGE_PATH);
    private static final String DOUBLE_IMAGE_PATH = "images/plants/Double Peashooter.png";
    private static final BufferedImage DOUBLE_IMAGE = GameImage.get(DOUBLE_IMAGE_PATH);
    private static final int SPEED = 8;
    private static final double GRAVITY = 0.68;
    private static final double JUMP_SPEED = -16.5;
    private static final double MAX_FALL_SPEED = 15.0;
    private static final int DROP_IGNORE_HEIGHT = 18;
    private static final int MAX_JUMPS = 2;
    private static final int SHOOT_COOLDOWN = 8;
    private static final int HIT_COOLDOWN = 35;
    private static final int DOUBLE_PEA_DURATION_TICKS = 333;
    private static final double DOUBLE_PEA_REFERENCE_ASPECT_RATIO = 713.0 / 790.0;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean jumpRequested;
    private boolean dropRequested;
    private double verticalSpeed;
    private boolean onGround;
    private int jumpsUsed;
    private int health = 10;
    private int shootCooldown;
    private int hitCooldown;
    private int doublePeaTicksRemaining;
    private boolean doublePeaAvailable = true;

    public ContraPlayer(int x, int y) {
        super(x, y, 68, 76, null);
    }

    @Override
    public void showElement(Graphics g) {
        GameBoard board = GameBoard.getInstance();
        int drawX = board == null ? getX() : board.toScreenX(getX());
        int drawY = getY();

        if (isDoublePeaModeActive() && DOUBLE_IMAGE != null) {
            int drawH = getH() + 12;
            int maxDrawW = getW() + 24;
            int drawW = Math.min(maxDrawW, (int) Math.round(drawH * DOUBLE_PEA_REFERENCE_ASPECT_RATIO));
            int spriteX = drawX + (getW() - drawW) / 2;
            GameImage.draw(g, DOUBLE_IMAGE, spriteX, drawY - 8, drawW, drawH);
        } else if (IMAGE != null) {
            GameImage.draw(g, IMAGE, drawX - 8, drawY - 8, getW() + 16, getH() + 12);
        } else {
            g.setColor(new Color(32, 160, 61));
            g.fillOval(drawX, drawY + 8, getW() - 14, getH() - 18);
            g.setColor(new Color(20, 122, 41));
            g.fillOval(drawX + 42, drawY + 24, 30, 24);
        }

        if (hitCooldown > 0) {
            g.setColor(new Color(255, 255, 255, 110));
            g.fillOval(drawX - 8, drawY - 8, getW() + 16, getH() + 16);
        }
    }

    @Override
    public void keyClick(boolean pressed, int key) {
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            left = pressed;
        } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            right = pressed;
        } else if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            if (pressed && !up) {
                jumpRequested = true;
            }
            up = pressed;
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            if (pressed && !down) {
                dropRequested = true;
            }
            down = pressed;
        } else if (pressed && key == KeyEvent.VK_SPACE) {
            shoot();
        } else if (pressed && key == KeyEvent.VK_F) {
            activateDoublePeaMode();
        }
    }

    @Override
    protected void move() {
        GameBoard board = GameBoard.getInstance();
        int worldW = board == null ? 1280 : board.getContraWorldWidth();
        int minY = board == null ? 120 : board.getContraTopBoundY();

        int nextX = getX();
        if (left) {
            nextX -= SPEED;
        }
        if (right) {
            nextX += SPEED;
        }

        setX(clamp(nextX, 30, worldW - getW() - 40));
        applyContraGravity(board, minY);

        if (board != null) {
            board.updateContraCameraFor(getX());
        }
        checkContraAbyssDeath(board);
        if (shootCooldown > 0) {
            shootCooldown--;
        }
        if (hitCooldown > 0) {
            hitCooldown--;
        }
        if (doublePeaTicksRemaining > 0) {
            doublePeaTicksRemaining--;
        }
    }

    private void applyContraGravity(GameBoard board, int minY) {
        if (board == null) {
            return;
        }

        int leftFoot = getX() + 8;
        int rightFoot = getX() + getW() - 8;
        int footY = getY() + getH();
        onGround = board.isOnContraSurface(leftFoot, rightFoot, footY);
        if (onGround) {
            jumpsUsed = 0;
        } else if (jumpsUsed == 0) {
            jumpsUsed = 1;
        }

        if (dropRequested) {
            int nextSurface = board.getContraSurfaceBelow(leftFoot, rightFoot, footY + DROP_IGNORE_HEIGHT);
            if (onGround && nextSurface != -1) {
                setY(getY() + DROP_IGNORE_HEIGHT);
                verticalSpeed = Math.max(verticalSpeed, 4.0);
                onGround = false;
                jumpsUsed = 1;
            }
            dropRequested = false;
        }

        if (jumpRequested) {
            if (onGround || jumpsUsed < MAX_JUMPS) {
                verticalSpeed = JUMP_SPEED;
                onGround = false;
                jumpsUsed++;
            }
            jumpRequested = false;
        }

        if (!onGround) {
            verticalSpeed = Math.min(MAX_FALL_SPEED, verticalSpeed + GRAVITY);
        } else if (verticalSpeed > 0) {
            verticalSpeed = 0;
        }

        int oldFootY = getY() + getH();
        int nextY = getY() + (int) Math.round(verticalSpeed);
        if (nextY < minY) {
            nextY = minY;
            verticalSpeed = 0;
        }

        int nextFootY = nextY + getH();
        if (verticalSpeed >= 0) {
            int surfaceY = board.getContraSurfaceBetween(leftFoot, rightFoot, oldFootY + 1, nextFootY);
            if (surfaceY != -1) {
                setY(surfaceY - getH());
                verticalSpeed = 0;
                onGround = true;
                jumpsUsed = 0;
                checkContraWaterDeath(board);
                return;
            }
        }

        setY(nextY);
        if (onGround && !board.isOnContraSurface(leftFoot, rightFoot, getY() + getH())) {
            onGround = false;
            if (jumpsUsed == 0) {
                jumpsUsed = 1;
            }
        }
        checkContraWaterDeath(board);
        checkContraAbyssDeath(board);
    }

    private void checkContraWaterDeath(GameBoard board) {
        if (board.isOnContraSurface(getX() + 8, getX() + getW() - 8, getY() + getH())) {
            return;
        }
        if (board.isContraWaterHazard(getX() + 8, getX() + getW() - 8, getY() + getH() - 8, getY() + getH())) {
            health = 0;
            setLive(false);
            board.triggerGameOver();
        }
    }

    private void checkContraAbyssDeath(GameBoard board) {
        if (board == null || !isLive()) {
            return;
        }
        if (getY() > board.getH()) {
            health = 0;
            setLive(false);
            board.triggerGameOver();
        }
    }

    private void shoot() {
        if (shootCooldown > 0) {
            return;
        }
        int bulletX = getX() + getW() - 2;
        int bulletY = getY() + getH() / 2 - 7;
        ElementManager.getManager().addElement(new ContraPeaBullet(bulletX, bulletY), GameElement.BULLET);
        if (isDoublePeaModeActive()) {
            ElementManager.getManager().addElement(new ContraPeaBullet(bulletX, bulletY - 14), GameElement.BULLET);
        }
        shootCooldown = SHOOT_COOLDOWN;
    }

    private void activateDoublePeaMode() {
        if (!doublePeaAvailable || isDoublePeaModeActive() || !isLive()) {
            return;
        }
        doublePeaAvailable = false;
        doublePeaTicksRemaining = DOUBLE_PEA_DURATION_TICKS;
    }

    public void takeDamage(int damage) {
        if (hitCooldown > 0 || !isLive()) {
            return;
        }
        health -= damage;
        hitCooldown = HIT_COOLDOWN;
        if (health <= 0) {
            health = 0;
            setLive(false);
            GameBoard board = GameBoard.getInstance();
            if (board != null) {
                board.triggerGameOver();
            }
        }
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public int getHealth() {
        return health;
    }

    public boolean isDoublePeaModeActive() {
        return doublePeaTicksRemaining > 0;
    }

    public boolean isDoublePeaAvailable() {
        return doublePeaAvailable;
    }

    public int getDoublePeaTicksRemaining() {
        return doublePeaTicksRemaining;
    }
}
