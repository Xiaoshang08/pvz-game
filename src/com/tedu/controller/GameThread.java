package com.tedu.controller;

import com.tedu.element.Bullet;
import com.tedu.element.BossZombie;
import com.tedu.element.ContraPeaBullet;
import com.tedu.element.ContraPlayer;
import com.tedu.element.DamageableEnemy;
import com.tedu.element.ElementObj;
import com.tedu.element.EnemyBullet;
import com.tedu.element.FireDoor;
import com.tedu.element.GameBoard;
import com.tedu.element.GunZombie;
import com.tedu.element.LaneEnemy;
import com.tedu.element.Plant;
import com.tedu.element.WaterDoor;
import com.tedu.element.Zombie;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameThread extends Thread {

    private final ElementManager em;

    public GameThread() {
        em = ElementManager.getManager();
    }

    @Override
    public void run() {
        gameLoad();
        long gameTime = 0L;
        while (true) {
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            moveAndUpdate(all, gameTime);
            handleCollisions();
            cleanupDead(all);
            checkGameOver();
            checkGameWin();
            gameTime++;
            try {
                sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void gameLoad() {
        GameLoad.loadGame();
    }

    private void moveAndUpdate(Map<GameElement, List<ElementObj>> all, long gameTime) {
        GameBoard board = GameBoard.getInstance();
        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            if (list == null) {
                continue;
            }

            // ========== 修复点 1：创建副本遍历，防止并发修改 ==========
            List<ElementObj> tempList = new ArrayList<>(list);
            for (ElementObj obj : tempList) {
                if (!obj.isLive()) {
                    continue;
                }

                boolean alwaysUpdate = ge == GameElement.BACKGROUND || ge == GameElement.UI || ge == GameElement.GRID;
                if (!alwaysUpdate && (board == null || !board.isPlaying())) {
                    continue;
                }
                obj.model(gameTime);
            }
        }
    }

    private void handleCollisions() {
        GameBoard board = GameBoard.getInstance();
        if (board == null || !board.isPlaying()) {
            return;
        }

        if (board.isFireIceMode()) {
            checkFireIceWin();
            return;
        }

        if (board.isContraMode()) {
            handleContraCollisions(board);
            return;
        }

        List<ElementObj> bullets = em.getElementsByKey(GameElement.BULLET);
        List<ElementObj> zombies = em.getElementsByKey(GameElement.ZOMBIE);
        List<ElementObj> plants = em.getElementsByKey(GameElement.PLANT);

        for (ElementObj bulletObj : new ArrayList<>(bullets)) {
            if (!bulletObj.isLive()) {
                continue;
            }
            Bullet bullet = (Bullet) bulletObj;

            for (ElementObj zombieObj : new ArrayList<>(zombies)) {
                if (!zombieObj.isLive() || !(zombieObj instanceof DamageableEnemy) || !(zombieObj instanceof LaneEnemy)) {
                    continue;
                }
                LaneEnemy zombie = (LaneEnemy) zombieObj;
                if (bullet.getRow() != zombie.getRow()) {
                    continue;
                }
                if (bullet.pk(zombieObj)) {
                    Bullet.spawnImpact(bullet.getX() + bullet.getW() / 2, bullet.getY() + bullet.getH() / 2);
                    ((DamageableEnemy) zombieObj).takeDamage(bullet.getDamage());
                    bullet.setLive(false);
                    break;
                }
            }
        }

        for (ElementObj zombieObj : new ArrayList<>(zombies)) {
            if (!zombieObj.isLive()) {
                continue;
            }
            Zombie zombie = (Zombie) zombieObj;
            Plant targetPlant = null;
            for (ElementObj plantObj : new ArrayList<>(plants)) {
                if (!plantObj.isLive()) {
                    continue;
                }
                Plant plant = (Plant) plantObj;
                if (!zombie.isSameRow(plant)) {
                    continue;
                }
                if (zombie.pk(plant)) {
                    targetPlant = plant;
                    break;
                }
            }
            zombie.setAttackTarget(targetPlant);
        }
    }

    private void checkFireIceWin() {
        GameBoard board = GameBoard.getInstance();
        if (board.isGameWin() || board.isGameOver()) {
            return;
        }

        boolean fireDoorOpen = false, waterDoorOpen = false;
        for (ElementObj door : new ArrayList<>(em.getElementsByKey(GameElement.FIRE_DOOR))) {
            if (((FireDoor) door).isOpen()) {
                fireDoorOpen = true;
            }
        }
        for (ElementObj door : new ArrayList<>(em.getElementsByKey(GameElement.WATER_DOOR))) {
            if (((WaterDoor) door).isOpen()) {
                waterDoorOpen = true;
            }
        }
        if (fireDoorOpen && waterDoorOpen) {
            board.triggerGameWin();
        }
    }

    private void handleContraCollisions(GameBoard board) {
        List<ElementObj> bullets = em.getElementsByKey(GameElement.BULLET);
        List<ElementObj> enemyBullets = em.getElementsByKey(GameElement.ENEMY_BULLET);
        List<ElementObj> zombies = em.getElementsByKey(GameElement.ZOMBIE);
        ContraPlayer player = board.getContraPlayer();

        for (ElementObj bulletObj : new ArrayList<>(bullets)) {
            if (!bulletObj.isLive() || !(bulletObj instanceof ContraPeaBullet)) {
                continue;
            }
            ContraPeaBullet bullet = (ContraPeaBullet) bulletObj;
            for (ElementObj zombieObj : new ArrayList<>(zombies)) {
                if (!zombieObj.isLive() || !(zombieObj instanceof DamageableEnemy)) {
                    continue;
                }
                if (bullet.pk(zombieObj)) {
                    Bullet.spawnImpact(bullet.getX() + bullet.getW() / 2, bullet.getY() + bullet.getH() / 2);
                    ((DamageableEnemy) zombieObj).takeDamage(bullet.getDamage());
                    bullet.setLive(false);
                    break;
                }
            }
        }

        if (player == null || !player.isLive()) {
            return;
        }

        for (ElementObj bulletObj : new ArrayList<>(enemyBullets)) {
            if (!bulletObj.isLive() || !(bulletObj instanceof EnemyBullet)) {
                continue;
            }
            EnemyBullet bullet = (EnemyBullet) bulletObj;
            if (bullet.pk(player)) {
                player.takeDamage(bullet.getDamage());
                bullet.setLive(false);
            }
        }

        for (ElementObj zombieObj : new ArrayList<>(zombies)) {
            if (!zombieObj.isLive() || !(zombieObj instanceof GunZombie)) {
                continue;
            }
            GunZombie zombie = (GunZombie) zombieObj;
            if (zombie.pk(player)) {
                player.takeDamage(zombie.getContactDamage());
                if (!(zombie instanceof BossZombie)) {
                    zombie.setLive(false);
                }
            }
        }
    }

    private void cleanupDead(Map<GameElement, List<ElementObj>> all) {
        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            if (list == null) {
                continue;
            }

            // ========== 修复点 2：安全倒序删除 ==========
            for (int i = list.size() - 1; i >= 0; i--) {
                ElementObj obj = list.get(i);
                if (!obj.isLive()) {
                    obj.die();
                    list.remove(i);
                }
            }
        }

        GameBoard board = GameBoard.getInstance();
        if (board != null) {
            board.cleanupPlantSlots();
        }
    }

    private void checkGameOver() {
        GameBoard board = GameBoard.getInstance();
        if (board == null || !board.isPlaying() || board.isContraMode()) {
            return;
        }

        List<ElementObj> zombies = em.getElementsByKey(GameElement.ZOMBIE);
        for (ElementObj zombieObj : new ArrayList<>(zombies)) {
            Zombie zombie = (Zombie) zombieObj;
            if (zombie.isLive() && zombie.hasEnteredHouse()) {
                board.triggerGameOver();
                break;
            }
        }
    }

    private void checkGameWin() {
        GameBoard board = GameBoard.getInstance();
        if (board == null || !board.isPlaying()) {
            return;
        }

        if (board.isContraMode()) {
            if (board.isContraBossDefeated() && board.isContraPlayerInExit()) {
                board.triggerGameWin();
            }
            return;
        }

        if (board.getTotalKills() < board.getMaxZombies()) {
            return;
        }

        List<ElementObj> zombies = em.getElementsByKey(GameElement.ZOMBIE);
        for (ElementObj zombieObj : new ArrayList<>(zombies)) {
            if (zombieObj.isLive()) {
                return;
            }
        }
        board.triggerGameWin();
    }
}
