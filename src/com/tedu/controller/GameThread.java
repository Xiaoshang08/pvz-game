/**
 * GameThread：游戏主循环线程。
 * 主要职责：
 * 1. 初始化游戏元素；
 * 2. 驱动所有元素进行 model 更新；
 * 3. 处理子弹、僵尸、植物之间的碰撞；
 * 4. 清理死亡对象；
 * 5. 检查僵尸是否突破最左边并触发 Game Over；
 * 6. 检查 20 只僵尸是否已经全部被击败并触发胜利。
 */
package com.tedu.controller;

import java.util.List;
import java.util.Map;

import com.tedu.element.Bullet;
import com.tedu.element.ContraPeaBullet;
import com.tedu.element.ContraPlayer;
import com.tedu.element.ElementObj;
import com.tedu.element.EnemyBullet;
import com.tedu.element.GunZombie;
import com.tedu.element.GameBoard;
import com.tedu.element.Plant;
import com.tedu.element.Zombie;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class GameThread extends Thread {
    private final ElementManager em;

    public GameThread() {
        em = ElementManager.getManager();
    }

    //游戏的帧数间隔是30
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
            for (ElementObj obj : list) {
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

        if (board.isContraMode()) {
            handleContraCollisions(board);
            return;
        }

        List<ElementObj> bullets = em.getElementsByKey(GameElement.BULLET);
        List<ElementObj> zombies = em.getElementsByKey(GameElement.ZOMBIE);
        List<ElementObj> plants = em.getElementsByKey(GameElement.PLANT);

        for (ElementObj bulletObj : bullets) {
            if (!bulletObj.isLive()) {
                continue;
            }
            Bullet bullet = (Bullet) bulletObj;
            for (ElementObj zombieObj : zombies) {
                if (!zombieObj.isLive()) {
                    continue;
                }
                Zombie zombie = (Zombie) zombieObj;
                if (bullet.getRow() != zombie.getRow()) {
                    continue;
                }
                if (bullet.pk(zombie)) {
                    zombie.takeDamage(bullet.getDamage());
                    bullet.setLive(false);
                    break;
                }
            }
        }

        for (ElementObj zombieObj : zombies) {
            if (!zombieObj.isLive()) {
                continue;
            }
            Zombie zombie = (Zombie) zombieObj;
            Plant targetPlant = null;
            for (ElementObj plantObj : plants) {
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

    private void handleContraCollisions(GameBoard board) {
        List<ElementObj> bullets = em.getElementsByKey(GameElement.BULLET);
        List<ElementObj> enemyBullets = em.getElementsByKey(GameElement.ENEMY_BULLET);
        List<ElementObj> zombies = em.getElementsByKey(GameElement.ZOMBIE);
        ContraPlayer player = board.getContraPlayer();

        for (ElementObj bulletObj : bullets) {
            if (!bulletObj.isLive() || !(bulletObj instanceof ContraPeaBullet)) {
                continue;
            }
            ContraPeaBullet bullet = (ContraPeaBullet) bulletObj;
            for (ElementObj zombieObj : zombies) {
                if (!zombieObj.isLive() || !(zombieObj instanceof GunZombie)) {
                    continue;
                }
                GunZombie zombie = (GunZombie) zombieObj;
                if (bullet.pk(zombie)) {
                    zombie.takeDamage(bullet.getDamage());
                    bullet.setLive(false);
                    break;
                }
            }
        }

        if (player == null || !player.isLive()) {
            return;
        }

        for (ElementObj bulletObj : enemyBullets) {
            if (!bulletObj.isLive() || !(bulletObj instanceof EnemyBullet)) {
                continue;
            }
            EnemyBullet bullet = (EnemyBullet) bulletObj;
            if (bullet.pk(player)) {
                player.takeDamage(bullet.getDamage());
                bullet.setLive(false);
            }
        }

        for (ElementObj zombieObj : zombies) {
            if (!zombieObj.isLive() || !(zombieObj instanceof GunZombie)) {
                continue;
            }
            GunZombie zombie = (GunZombie) zombieObj;
            if (zombie.pk(player)) {
                player.takeDamage(2);
                zombie.setLive(false);
            }
        }
    }

    private void cleanupDead(Map<GameElement, List<ElementObj>> all) {
        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for (int i = 0; i < list.size();) {
                ElementObj obj = list.get(i);
                if (!obj.isLive()) {
                    obj.die();
                    list.remove(i);
                } else {
                    i++;
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
        for (ElementObj zombieObj : zombies) {
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

        if (board.getTotalKills() < board.getMaxZombies()) {
            return;
        }

        List<ElementObj> zombies = em.getElementsByKey(GameElement.ZOMBIE);
        for (ElementObj zombieObj : zombies) {
            if (zombieObj.isLive()) {
                return;
            }
        }
        if (board.isContraMode() && !board.isContraPlayerInExit()) {
            return;
        }
        board.triggerGameWin();
    }
}
