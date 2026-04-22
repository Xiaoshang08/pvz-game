package com.tedu.element;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.image.BufferedImage;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.util.GameImage;

import com.tedu.manager.GameLoad;

/**
 * GameBoard：游戏主战场背景与状态中心。
 *
 * 本次升级：
 * 1. 首页按钮重新对齐；
 * 2. 首页左侧加入“森林冰火小人”故事背景；
 * 3. 点击开始后先进入选卡/预览流程，并做一个向右平移的镜头；
 * 4. 战斗地图加大，房前增加公路；
 * 5. 顶部功能栏改成双排布局，为后续扩展留出空间。
 */
public class GameBoard extends ElementObj {
    private static GameBoard instance;

    private static final int WINDOW_W = 1280;
    private static final int WINDOW_H = 720;

    private static final int BATTLE_BG_X = -115;
    private static final int BATTLE_BG_Y = 104;
    private static final int BATTLE_BG_W = 1450;
    private static final int BATTLE_BG_H = 608;

    private static final int BOARD_X = 0;
    private static final int BOARD_Y = BATTLE_BG_Y;
    private static final int BOARD_W = WINDOW_W;
    private static final int BOARD_H = BATTLE_BG_H;

    private static final int STATUS_BAR_X = 12;
    private static final int STATUS_BAR_Y = 8;
    private static final int STATUS_BAR_W = 1256;
    private static final int STATUS_BAR_H = 70;

    private static final int CARD_W = 150;
    private static final int CARD_H = 42;
    private static final int PEA_CARD_X = 126;
    private static final int PEA_CARD_Y = 17;
    private static final int SUNFLOWER_CARD_X = 288;
    private static final int SUNFLOWER_CARD_Y = 17;
    private static final int SHOVEL_BTN_X = 1130;
    private static final int SHOVEL_BTN_Y = 652;
    private static final int SHOVEL_BTN_W = 118;
    private static final int SHOVEL_BTN_H = 42;
    private static final int MENU_BTN_X = 1130;
    private static final int MENU_BTN_Y = 16;
    private static final int MENU_BTN_W = 118;
    private static final int MENU_BTN_H = 42;

    private static final int PREP_CAMERA_MAX = 190;

    private static final double LAWN_X_RATIO = 0.1720;
    private static final double LAWN_CENTER_Y_RATIO = 0.4950;
    private static final double LAWN_W_RATIO = 0.6065;
    private static final double LAWN_H_RATIO = 0.8200;

    private static final int HOME_BUTTON_W = 220;
    private static final int HOME_BUTTON_H = 58;
    private static final int HOME_BUTTON_X = 865;
    private static final int HOME_START_BTN_Y = 285;
    private static final int HOME_EXIT_BTN_Y = 360;

    private static final int LEVEL_CARD_W = 220;
    private static final int LEVEL_CARD_H = 280;
    private static final int LEVEL_CARD_Y = 220;
    private static final int LEVEL1_X = 220;
    private static final int LEVEL2_X = 530;
    private static final int LEVEL3_X = 840;
    private static final int LEVEL_SELECT_BACK_X = 520;
    private static final int LEVEL_SELECT_BACK_Y = 600;
    private static final int LEVEL_SELECT_BACK_W = 240;
    private static final int LEVEL_SELECT_BACK_H = 54;

    private static final int PAUSE_CONTINUE_BTN_X = 520;
    private static final int PAUSE_CONTINUE_BTN_Y = 250;
    private static final int PAUSE_CONTINUE_BTN_W = 240;
    private static final int PAUSE_CONTINUE_BTN_H = 54;
    private static final int PAUSE_RESTART_BTN_X = 520;
    private static final int PAUSE_RESTART_BTN_Y = 315;
    private static final int PAUSE_RESTART_BTN_W = 240;
    private static final int PAUSE_RESTART_BTN_H = 54;
    private static final int PAUSE_HOME_BTN_X = 520;
    private static final int PAUSE_HOME_BTN_Y = 380;
    private static final int PAUSE_HOME_BTN_W = 240;
    private static final int PAUSE_HOME_BTN_H = 54;
    private static final int PAUSE_EXIT_BTN_X = 520;
    private static final int PAUSE_EXIT_BTN_Y = 445;
    private static final int PAUSE_EXIT_BTN_W = 240;
    private static final int PAUSE_EXIT_BTN_H = 54;

    private static final int RESTART_BTN_X = 435;
    private static final int RESTART_BTN_Y = 420;
    private static final int RESTART_BTN_W = 190;
    private static final int RESTART_BTN_H = 60;
    private static final int HOME_BTN_X = 655;
    private static final int HOME_BTN_Y = 420;
    private static final int HOME_BTN_W = 190;
    private static final int HOME_BTN_H = 60;
    private static final int NEXT_BTN_X = 545; // 下一关按钮位置
    private static final int NEXT_BTN_Y = 500;
    private static final int NEXT_BTN_W = 190;
    private static final int NEXT_BTN_H = 60;

    private static final int PREP_PANEL_X = 40;
    private static final int PREP_PANEL_Y = 130;
    private static final int PREP_PANEL_W = 340;
    private static final int PREP_PANEL_H = 520;
    private static final int PREP_START_BTN_X = 95;
    private static final int PREP_START_BTN_Y = 575;
    private static final int PREP_START_BTN_W = 220;
    private static final int PREP_START_BTN_H = 52;
    private static final int PREP_BACK_BTN_X = 95;
    private static final int PREP_BACK_BTN_Y = 635;
    private static final int PREP_BACK_BTN_W = 220;
    private static final int PREP_BACK_BTN_H = 42;

    private static final int MAX_ZOMBIES = 20;
    private static final int PEA_SHOOTER_COST = 100;
    private static final int SUNFLOWER_COST = 50;
    private static final int INITIAL_SUN = 100;
    private static final int SKY_SUN_VALUE = 50;
    private static final int SUN_DROP_INTERVAL = 167;
    private static final int ZOMBIE_SPAWN_INTERVAL = 150;
    private static final int START_PROTECT_TIME = 167;

    private static final String BATTLE_SCENE_IMAGE_PATH = "images/map/lawn_scene.png";
    private static final String CONTRA_STAGE_IMAGE_PATH = "images/map/contra_stage.png";
    private static final int CONTRA_SCALE = 3;
    private static final int CONTRA_MAP_W = 3328 * CONTRA_SCALE;
    private static final int CONTRA_MAP_H = 224 * CONTRA_SCALE;
    private static final int CONTRA_MAP_Y = 48;
    private static final int CONTRA_GROUND_Y = 620;
    private static final int CONTRA_TOP_Y = 120;
    private final BufferedImage battleSceneImage = GameImage.get(BATTLE_SCENE_IMAGE_PATH);
    private final BufferedImage contraStageImage = GameImage.get(CONTRA_STAGE_IMAGE_PATH);

    private final int rows = 5;
    private final int cols = 9;
    private final int cellW = (int) Math.round(BATTLE_BG_W * LAWN_W_RATIO / cols);
    private final int cellH = (int) Math.round(BATTLE_BG_H * LAWN_H_RATIO / rows);
    private final int gapX = 0;
    private final int gapY = 0;
    private final int lawnStartX = BATTLE_BG_X + (int) Math.round(BATTLE_BG_W * LAWN_X_RATIO);
    private final int lawnStartY = BATTLE_BG_Y + (int) Math.round(BATTLE_BG_H * LAWN_CENTER_Y_RATIO)
            - (cellH * rows) / 2;

    private final Plant[][] plantGrid = new Plant[rows][cols];
    private final Random random = new Random();
    private final String[] selectablePlants = { "豌豆射手", "向日葵", "寒冰射手", "坚果墙", "双发射手", "樱桃炸弹" };

    private int zombieSpawnCounter = 0;
    private int sunDropCounter = 0;
    private int startProtectCounter = 0;
    private int totalKills = 0;
    private int spawnedZombies = 0;
    private int currentSun = INITIAL_SUN;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private boolean gameWin = false;
    private boolean shovelMode = false;
    private boolean paused = false;
    private PlantType selectedPlantType = PlantType.PEA_SHOOTER;
    private GameStage stage = GameStage.HOME;
    private int prepCameraOffset = 0;
    private int introCameraOffset = 0;
    private boolean battleIntroPlaying = false;
    private int introZombieRetreatOffset = 0;
    private int prepSelectedIndex = 0;
    private int unlockedLevel = 1;
    private int selectedLevel = 1;
    private ContraPlayer contraPlayer;
    private int contraCameraX = 0;

    public GameBoard() {
        instance = this;
        setX(0);
        setY(0);
        setW(WINDOW_W);
        setH(WINDOW_H);
    }

    public static GameBoard getInstance() {
        return instance;
    }

    public enum PlantType {
        PEA_SHOOTER,
        SUNFLOWER
    }

    public enum GameStage {
        HOME,
        LEVEL_SELECT,
        PREPARE,
        PLAYING
    }

    @Override
    public void showElement(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (stage == GameStage.HOME) {
            drawHomeScene(g2);
            return;
        }
        if (stage == GameStage.LEVEL_SELECT) {
            drawLevelSelectScene(g2);
            return;
        }
        if (stage == GameStage.PREPARE) {
            drawPrepareScene(g2);
            return;
        }

        drawBattleScene(g2);
    }

    private void drawBattleScene(Graphics2D g) {
        if (isContraMode()) {
            drawContraScene(g);
            return;
        }

        g.setColor(new Color(221, 232, 196));
        g.fillRect(0, 0, getW(), getH());
        drawBattleEnvironment(g, getSceneCameraOffset(), battleIntroPlaying);
    }

    private void drawContraScene(Graphics2D g) {
        g.setColor(new Color(6, 8, 16));
        g.fillRect(0, 0, getW(), getH());

        int camera = getContraCameraX();
        if (contraStageImage != null) {
            GameImage.draw(g, contraStageImage, -camera, CONTRA_MAP_Y, CONTRA_MAP_W, CONTRA_MAP_H);
        } else {
            g.setColor(new Color(15, 16, 28));
            g.fillRect(0, 0, getW(), getH());
            g.setColor(new Color(30, 92, 40));
            g.fillRect(0, CONTRA_GROUND_Y, getW(), 92);
        }

        g.setColor(new Color(0, 0, 0, 88));
        g.fillRect(0, 0, getW(), 84);
        g.setColor(new Color(226, 241, 196));
        g.setFont(new Font("SansSerif", Font.BOLD, 23));
        g.drawString("Level 3: Contra Peashooter", 34, 36);
        g.setFont(new Font("SansSerif", Font.PLAIN, 15));
        g.drawString("Move: WASD / Arrow keys    Shoot: Space    Pause: P or Esc", 34, 62);
    }

    private void drawHomeScene(Graphics2D g) {
        g.setColor(new Color(168, 219, 247));
        g.fillRect(0, 0, getW(), 410);
        g.setColor(new Color(196, 227, 145));
        g.fillRect(0, 410, getW(), 310);

        g.setColor(new Color(255, 244, 170));
        g.fillOval(80, 55, 120, 120);
        g.setColor(new Color(140, 208, 92));
        g.fillOval(-60, 390, 620, 250);
        g.fillOval(320, 430, 980, 290);

        drawHouse(g, 145, 255, 1.2);

        g.setColor(new Color(255, 255, 255, 228));
        g.setFont(new Font("Serif", Font.BOLD, 44));
        g.drawString("欢迎来到森林边缘的草坪", 76, 210);
        g.setFont(new Font("SansSerif", Font.PLAIN, 24));
        g.drawString("冰火小人的家受到了僵尸的进攻。", 82, 265);
        g.drawString("他们请来了老朋友植物们，一起守住这条通往家门前的道路。", 82, 307);
        g.drawString("整理好植物阵容后，就出发迎战吧！", 82, 349);

        g.setColor(new Color(118, 82, 53));
        g.fillRoundRect(790, 145, 320, 350, 34, 34);
        g.setColor(new Color(150, 121, 94));
        g.fillRoundRect(812, 167, 276, 306, 28, 28);
        g.setColor(new Color(68, 43, 27));
        g.setFont(new Font("Serif", Font.BOLD, 34));
        g.drawString("植物大战僵尸", 855, 215);
        g.setFont(new Font("SansSerif", Font.PLAIN, 18));
        g.drawString("森林冰火小人版 Mini Demo", 855, 252);

        drawButton(g, HOME_BUTTON_X, HOME_START_BTN_Y, HOME_BUTTON_W, HOME_BUTTON_H,
                new Color(83, 160, 56), "开始游戏");
        drawButton(g, HOME_BUTTON_X, HOME_EXIT_BTN_Y, HOME_BUTTON_W, HOME_BUTTON_H,
                new Color(118, 118, 118), "退出游戏");
    }

    private void drawLevelSelectScene(Graphics2D g) {
        g.setColor(new Color(168, 219, 247));
        g.fillRect(0, 0, getW(), 410);
        g.setColor(new Color(196, 227, 145));
        g.fillRect(0, 410, getW(), 310);

        g.setColor(new Color(255, 244, 170));
        g.fillOval(80, 55, 120, 120);
        g.setColor(new Color(140, 208, 92));
        g.fillOval(-60, 390, 620, 250);
        g.fillOval(320, 430, 980, 290);

        g.setColor(new Color(255, 255, 255, 228));
        g.setFont(new Font("Serif", Font.BOLD, 42));
        g.drawString("选择关卡", 520, 120);
        g.setFont(new Font("SansSerif", Font.PLAIN, 22));
        g.drawString("先从第一关出发吧，后面的关卡以后再慢慢开放。", 355, 165);

        drawLevelCard(g, LEVEL1_X, LEVEL_CARD_Y, 1, true, "门前草坪", "当前可挑战");
        drawLevelCard(g, LEVEL2_X, LEVEL_CARD_Y, 2, unlockedLevel >= 2, "林间小路", "暂未解锁");
        drawLevelCard(g, LEVEL3_X, LEVEL_CARD_Y, 3, unlockedLevel >= 3, "夜色庭院", "暂未解锁");

        drawLevelCard(g, LEVEL3_X, LEVEL_CARD_Y, 3, true, "Contra PVZ", "WASD + Space");
        drawButton(g, LEVEL_SELECT_BACK_X, LEVEL_SELECT_BACK_Y, LEVEL_SELECT_BACK_W, LEVEL_SELECT_BACK_H,
                new Color(112, 112, 112), "返回首页");
    }

    private void drawLevelCard(Graphics2D g, int x, int y, int level, boolean unlocked, String title, String desc) {
        Color outer = unlocked ? new Color(112, 76, 34) : new Color(95, 95, 95);
        Color inner = unlocked ? new Color(187, 141, 82) : new Color(146, 146, 146);
        g.setColor(outer);
        g.fillRoundRect(x, y, LEVEL_CARD_W, LEVEL_CARD_H, 28, 28);
        g.setColor(inner);
        g.fillRoundRect(x + 12, y + 12, LEVEL_CARD_W - 24, LEVEL_CARD_H - 24, 22, 22);

        g.setColor(unlocked ? new Color(224, 238, 180) : new Color(175, 175, 175));
        g.fillRoundRect(x + 28, y + 56, LEVEL_CARD_W - 56, 120, 18, 18);
        g.setColor(unlocked ? new Color(124, 181, 78) : new Color(120, 120, 120));
        g.fillRoundRect(x + 40, y + 68, LEVEL_CARD_W - 80, 96, 14, 14);

        g.setColor(new Color(255, 248, 220));
        g.setFont(new Font("SansSerif", Font.BOLD, 26));
        g.drawString("第" + level + "关", x + 68, y + 42);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString(title, x + 54, y + 215);
        g.setFont(new Font("SansSerif", Font.PLAIN, 16));
        g.drawString(desc, x + 64, y + 246);

        if (!unlocked) {
            g.setColor(new Color(0, 0, 0, 88));
            g.fillRoundRect(x + 12, y + 12, LEVEL_CARD_W - 24, LEVEL_CARD_H - 24, 22, 22);
            drawLockIcon(g, x + LEVEL_CARD_W / 2 - 26, y + 95);
        } else {
            g.setColor(new Color(255, 249, 210));
            g.fillRoundRect(x + 58, y + 95, 104, 34, 16, 16);
            g.setColor(new Color(87, 118, 44));
            g.setFont(new Font("SansSerif", Font.BOLD, 18));
            g.drawString("可进入", x + 84, y + 118);
        }
    }

    private void drawLockIcon(Graphics2D g, int x, int y) {
        g.setStroke(new BasicStroke(6f));
        g.setColor(new Color(245, 245, 245));
        g.drawArc(x + 6, y - 18, 40, 34, 0, 180);
        g.setColor(new Color(230, 230, 230));
        g.fillRoundRect(x, y, 52, 42, 12, 12);
        g.setColor(new Color(120, 120, 120));
        g.drawRoundRect(x, y, 52, 42, 12, 12);
        g.fillOval(x + 21, y + 13, 10, 10);
    }

    private void drawPrepareScene(Graphics2D g) {
        g.setColor(new Color(188, 220, 244));
        g.fillRect(0, 0, getW(), 390);
        g.setColor(new Color(205, 232, 163));
        g.fillRect(0, 390, getW(), 330);

        drawBattleEnvironment(g, prepCameraOffset, true);
        drawPrepPanel(g);
    }

    private void drawBattleEnvironment(Graphics2D g, int cameraOffset, boolean showPreviewZombies) {
        int ox = -cameraOffset;

        g.setColor(new Color(214, 228, 188));
        g.fillRect(0, 0, getW(), getH());

        if (battleSceneImage != null) {
            GameImage.draw(g, battleSceneImage, BATTLE_BG_X + ox, BATTLE_BG_Y, BATTLE_BG_W, BATTLE_BG_H);
        } else {
            g.setColor(new Color(215, 234, 179));
            g.fillRoundRect(BOARD_X + ox, BOARD_Y, BOARD_W, BOARD_H, 28, 28);
            drawHouse(g, 80 + ox, 242, 1.0);
        }

        if (showPreviewZombies) {
            int previewShift = stage == GameStage.PLAYING ? introZombieRetreatOffset : 0;
            drawZombiePreviewShowcase(g, ox + previewShift);
        }
    }

    private void drawPrepPanel(Graphics2D g) {
        g.setColor(new Color(119, 69, 28));
        g.fillRoundRect(PREP_PANEL_X, PREP_PANEL_Y, PREP_PANEL_W, PREP_PANEL_H, 26, 26);
        g.setColor(new Color(156, 89, 38));
        g.fillRoundRect(PREP_PANEL_X + 10, PREP_PANEL_Y + 10, PREP_PANEL_W - 20, PREP_PANEL_H - 20, 20, 20);
        g.setColor(new Color(244, 214, 112));
        g.setFont(new Font("Serif", Font.BOLD, 34));
        g.drawString("选择你的植物", 92, 178);
        g.setFont(new Font("SansSerif", Font.PLAIN, 18));
        g.drawString("先整理出战阵容，再正式开战。", 78, 210);

        for (int i = 0; i < selectablePlants.length; i++) {
            int x = PREP_PANEL_X + 35 + (i % 2) * 135;
            int y = PREP_PANEL_Y + 110 + (i / 2) * 88;
            boolean selected = i == prepSelectedIndex;
            g.setColor(selected ? new Color(255, 244, 168) : new Color(205, 189, 130));
            g.fillRoundRect(x, y, 118, 62, 16, 16);
            g.setColor(selected ? new Color(124, 82, 24) : new Color(108, 75, 36));
            g.drawRoundRect(x, y, 118, 62, 16, 16);
            g.setFont(new Font("SansSerif", Font.BOLD, 18));
            g.drawString(selectablePlants[i], x + 14, y + 28);
            g.setFont(new Font("SansSerif", Font.PLAIN, 13));
            g.drawString(i < 2 ? "可用" : "展示中", x + 14, y + 48);
        }

        g.setColor(new Color(255, 245, 220));
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString("当前默认携带：豌豆射手、向日葵", 68, 520);
        g.setFont(new Font("SansSerif", Font.PLAIN, 16));
        g.drawString("先看看门前公路上的僵尸，再准备布阵。", 72, 548);

        drawButton(g, PREP_START_BTN_X, PREP_START_BTN_Y, PREP_START_BTN_W, PREP_START_BTN_H,
                new Color(86, 164, 63), "开始战斗");
        drawButton(g, PREP_BACK_BTN_X, PREP_BACK_BTN_Y, PREP_BACK_BTN_W, PREP_BACK_BTN_H,
                new Color(112, 112, 112), "返回首页");
    }

    private void drawZombiePreviewShowcase(Graphics2D g, int ox) {
        int baseX = BATTLE_BG_X + (int) Math.round(BATTLE_BG_W * 0.905) + ox;
        int baseY = BATTLE_BG_Y + (int) Math.round(BATTLE_BG_H * 0.48);
        drawPreviewZombie(g, baseX, baseY, ZombiePreviewType.BASIC, "普通僵尸");
        drawPreviewZombie(g, baseX + 82, baseY + 34, ZombiePreviewType.CONE, "路障僵尸");
        drawPreviewZombie(g, baseX + 172, baseY - 8, ZombiePreviewType.BUCKET, "铁桶僵尸");
    }

    private void drawPreviewZombie(Graphics2D g, int x, int y, ZombiePreviewType type, String label) {
        java.awt.image.BufferedImage img = GameImage.get("images/zombies/basic_zombie.png");
        if (img != null) {
            GameImage.draw(g, img, x, y, 62, 98);
        } else {
            g.setColor(new Color(121, 160, 116));
            g.fillRect(x + 12, y + 24, 36, 56);
            g.setColor(new Color(178, 217, 168));
            g.fillOval(x + 8, y, 42, 42);
        }

        if (type == ZombiePreviewType.CONE) {
            g.setColor(new Color(242, 131, 36));
            int[] xs = { x + 14, x + 30, x + 48 };
            int[] ys = { y + 20, y - 10, y + 20 };
            g.fillPolygon(xs, ys, 3);
        } else if (type == ZombiePreviewType.BUCKET) {
            g.setColor(new Color(136, 144, 154));
            g.fillRoundRect(x + 10, y - 2, 40, 22, 6, 6);
            g.setColor(new Color(195, 200, 205));
            g.drawRoundRect(x + 10, y - 2, 40, 22, 6, 6);
        }

        g.setColor(new Color(62, 44, 18, 220));
        g.fillRoundRect(x - 6, y + 92, 86, 26, 12, 12);
        g.setColor(new Color(255, 244, 208));
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.drawString(label, x + 6, y + 110);
    }

    private void drawHouse(Graphics2D g, int x, int y, double scale) {
        int w = (int) (132 * scale);
        int h = (int) (112 * scale);
        g.setColor(new Color(222, 184, 122));
        g.fillRect(x + 40, y + 46, w, h);
        g.setColor(new Color(179, 89, 62));
        int[] roofX = { x + 20, x + 105, x + 190 };
        int[] roofY = { y + 48, y - 18, y + 48 };
        g.fillPolygon(scalePoints(roofX, x, scale), scalePoints(roofY, y, scale), 3);
        g.setColor(new Color(130, 81, 49));
        g.fillRect((int) (x + 94 * scale), (int) (y + 98 * scale), (int) (30 * scale), (int) (60 * scale));
        g.setColor(new Color(230, 239, 255));
        g.fillRect((int) (x + 58 * scale), (int) (y + 72 * scale), (int) (26 * scale), (int) (26 * scale));
        g.fillRect((int) (x + 142 * scale), (int) (y + 72 * scale), (int) (26 * scale), (int) (26 * scale));
    }

    private int[] scalePoints(int[] values, int origin, double scale) {
        int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = origin + (int) ((values[i] - origin) * scale);
        }
        return result;
    }

    public void drawOverlay(Graphics g) {
        if (stage != GameStage.PLAYING) {
            return;
        }
        if (paused) {
            drawPauseOverlay((Graphics2D) g);
        } else if (gameWin) {
            drawWinOverlay((Graphics2D) g);
        } else if (gameOver) {
            drawGameOverOverlay((Graphics2D) g);
        }
    }

    private void drawPauseOverlay(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRoundRect(BOARD_X, BOARD_Y, BOARD_W, BOARD_H, 28, 28);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 34));
        g.drawString("游戏菜单", 560, 210);

        drawButton(g, PAUSE_CONTINUE_BTN_X, PAUSE_CONTINUE_BTN_Y, PAUSE_CONTINUE_BTN_W, PAUSE_CONTINUE_BTN_H,
                new Color(64, 155, 88), "继续游戏");
        drawButton(g, PAUSE_RESTART_BTN_X, PAUSE_RESTART_BTN_Y, PAUSE_RESTART_BTN_W, PAUSE_RESTART_BTN_H,
                new Color(243, 156, 18), "重新开始");
        drawButton(g, PAUSE_HOME_BTN_X, PAUSE_HOME_BTN_Y, PAUSE_HOME_BTN_W, PAUSE_HOME_BTN_H,
                new Color(52, 152, 219), "返回首页");
        drawButton(g, PAUSE_EXIT_BTN_X, PAUSE_EXIT_BTN_Y, PAUSE_EXIT_BTN_W, PAUSE_EXIT_BTN_H,
                new Color(86, 86, 86), "退出游戏");
    }

    private void drawWinOverlay(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRoundRect(BOARD_X, BOARD_Y, BOARD_W, BOARD_H, 28, 28);

        g.setColor(new Color(241, 196, 15));
        g.setFont(new Font("SansSerif", Font.BOLD, 44));
        g.drawString("YOU WIN", 510, 300);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.PLAIN, 20));
        g.drawString("你已经击败了全部 20 只普通僵尸！", 470, 340);

        drawButton(g, RESTART_BTN_X, RESTART_BTN_Y, RESTART_BTN_W, RESTART_BTN_H,
                new Color(46, 204, 113), "重新开始");
        drawButton(g, HOME_BTN_X, HOME_BTN_Y, HOME_BTN_W, HOME_BTN_H,
                new Color(52, 152, 219), "返回首页");
    }

    private void drawGameOverOverlay(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 130));
        g.fillRoundRect(BOARD_X, BOARD_Y, BOARD_W, BOARD_H, 28, 28);

        g.setColor(new Color(231, 76, 60));
        g.setFont(new Font("SansSerif", Font.BOLD, 44));
        g.drawString("GAME OVER", 475, 300);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.PLAIN, 18));
        g.drawString("僵尸走到了最左边，你的草坪被攻破啦", 460, 340);

        drawButton(g, RESTART_BTN_X, RESTART_BTN_Y, RESTART_BTN_W, RESTART_BTN_H,
                new Color(243, 156, 18), "重新开始");
        drawButton(g, HOME_BTN_X, HOME_BTN_Y, HOME_BTN_W, HOME_BTN_H,
                new Color(52, 152, 219), "返回首页");
    }

    private void drawButton(Graphics2D g, int x, int y, int w, int h, Color color, String text) {
        g.setColor(color);
        g.fillRoundRect(x, y, w, h, 24, 24);
        g.setColor(new Color(255, 255, 255, 220));
        g.drawRoundRect(x, y, w, h, 24, 24);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, h >= 50 ? 24 : 18));

        FontMetrics metrics = g.getFontMetrics();
        int textX = x + (w - metrics.stringWidth(text)) / 2;
        int textY = y + (h + metrics.getAscent()) / 2 - 6;
        g.drawString(text, textX, textY);
    }

    @Override
    protected void add(long gameTime) {
        if (stage == GameStage.LEVEL_SELECT) {
            return;
        }

        if (stage == GameStage.PREPARE) {
            if (prepCameraOffset < PREP_CAMERA_MAX) {
                prepCameraOffset = Math.min(PREP_CAMERA_MAX, prepCameraOffset + 3);
            }
            return;
        }

        if (stage == GameStage.PLAYING && battleIntroPlaying) {
            if (introCameraOffset > 0) {
                introCameraOffset = Math.max(0, introCameraOffset - 5);
            }
            introZombieRetreatOffset += 7;
            if (introCameraOffset == 0) {
                battleIntroPlaying = false;
                introZombieRetreatOffset = 0;
            }
            return;
        }

        if (!isPlaying()) {
            return;
        }

        if (isContraMode()) {
            updateContraMode(gameTime);
            return;
        }

        if (spawnedZombies < getMaxZombies()) {
            startProtectCounter++;
            if (startProtectCounter >= START_PROTECT_TIME) {
                zombieSpawnCounter++;
                if (zombieSpawnCounter >= ZOMBIE_SPAWN_INTERVAL) {
                    zombieSpawnCounter = 0;
                    spawnZombie();
                }
            }
        }

        sunDropCounter++;
        if (sunDropCounter >= SUN_DROP_INTERVAL) {
            sunDropCounter = 0;
            spawnSkySun();
        }
    }

    private void updateContraMode(long gameTime) {
        if (contraPlayer != null && contraPlayer.isLive()) {
            updateContraCameraFor(contraPlayer.getX());
        }

        if (spawnedZombies < getMaxZombies()) {
            zombieSpawnCounter++;
            if (zombieSpawnCounter >= 58) {
                zombieSpawnCounter = 0;
                spawnGunZombie();
            }
        }
    }

    @Override
    public void mouseClick(int mouseX, int mouseY) {
        if (stage == GameStage.HOME) {
            if (isInHomeStartButton(mouseX, mouseY)) {
                enterLevelSelectStage();
                return;
            }
            if (isInHomeExitButton(mouseX, mouseY)) {
                System.exit(0);
            }
            return;
        }

        if (stage == GameStage.LEVEL_SELECT) {
            if (isInLevelSelectBackButton(mouseX, mouseY)) {
                returnToHome();
                return;
            }
            if (isInLevel1Button(mouseX, mouseY)) {
                enterPrepareStage(1);
                return;
            }
            if (isInLevel3Button(mouseX, mouseY)) {
                selectedLevel = 3;
                startBattle();
                return;
            }
            return;
        }

        if (stage == GameStage.PREPARE) {
            if (isInPrepStartButton(mouseX, mouseY)) {
                startBattle();
                return;
            }
            if (isInPrepBackButton(mouseX, mouseY)) {
                returnToHome();
                return;
            }
            int index = getPrepPlantIndex(mouseX, mouseY);
            if (index != -1) {
                prepSelectedIndex = index;
            }
            return;
        }

        if (paused) {
            if (isInPauseContinueButton(mouseX, mouseY)) {
                resumeGame();
                return;
            }
            if (isInPauseRestartButton(mouseX, mouseY)) {
                restartGame();
                return;
            }
            if (isInPauseHomeButton(mouseX, mouseY)) {
                returnToHome();
                return;
            }
            if (isInPauseExitButton(mouseX, mouseY)) {
                System.exit(0);
            }
            return;
        }

        if (gameWin || gameOver) {
            if (isInRestartButton(mouseX, mouseY)) {
                restartGame();
                return;
            }
            if (isInHomeButton(mouseX, mouseY)) {
                returnToHome();
            }
            return;
        }

        if (battleIntroPlaying) {
            return;
        }

        if (isInMenuButton(mouseX, mouseY)) {
            pauseGame();
            return;
        }

        if (isContraMode()) {
            return;
        }

        if (isInStatusBar(mouseX, mouseY)) {
            return;
        }

        if (shovelMode) {
            tryRemovePlantAt(mouseX, mouseY);
        } else {
            tryPlantAt(mouseX, mouseY);
        }
    }

    public void tryPlantAt(int mouseX, int mouseY) {
        if (!isPlaying() || isContraMode()) {
            return;
        }

        int row = getRowByY(mouseY);
        int col = getColByX(mouseX);
        if (row == -1 || col == -1) {
            return;
        }

        Plant oldPlant = plantGrid[row][col];
        if (oldPlant != null && oldPlant.isLive()) {
            return;
        }

        int cost = getSelectedPlantCost();
        if (currentSun < cost) {
            return;
        }

        currentSun -= cost;

        Plant plant;
        if (selectedPlantType == PlantType.SUNFLOWER) {
            plant = new Sunflower(row, col, getCellX(col), getCellY(row), cellW, cellH);
        } else {
            plant = new PeaShooter(row, col, getCellX(col), getCellY(row), cellW, cellH);
        }

        plantGrid[row][col] = plant;
        ElementManager.getManager().addElement(plant, GameElement.PLANT);
    }

    public void tryRemovePlantAt(int mouseX, int mouseY) {
        if (!isPlaying() || isContraMode()) {
            return;
        }

        int row = getRowByY(mouseY);
        int col = getColByX(mouseX);
        if (row == -1 || col == -1) {
            return;
        }

        Plant plant = plantGrid[row][col];
        if (plant != null && plant.isLive()) {
            plant.setLive(false);
            plantGrid[row][col] = null;
        }
    }

    private void spawnZombie() {
        int row = random.nextInt(rows);
        int x = isContraMode() ? getW() + 20 + random.nextInt(160) : BATTLE_BG_X + BATTLE_BG_W - 18;
        int y = getCellY(row) + 12;
        ElementManager.getManager().addElement(new Zombie(row, x, y), GameElement.ZOMBIE);
        spawnedZombies++;
    }

    private void spawnGunZombie() {
        int[] lanes = { CONTRA_GROUND_Y - 82, CONTRA_GROUND_Y - 170, CONTRA_GROUND_Y - 260 };
        int laneIndex = random.nextInt(lanes.length);
        int spawnX = Math.min(CONTRA_MAP_W - 180, getContraCameraX() + getW() + 120 + random.nextInt(220));
        ElementManager.getManager().addElement(new GunZombie(spawnX, lanes[laneIndex]), GameElement.ZOMBIE);
        spawnedZombies++;
    }

    private void spawnSkySun() {
        int col = random.nextInt(cols);
        int x = getCellX(col) + (cellW - 36) / 2;
        int targetY = getCellY(random.nextInt(rows)) + 10;
        ElementManager.getManager().addElement(
                Sun.createFallingSun(x, STATUS_BAR_Y + STATUS_BAR_H + 8, targetY, SKY_SUN_VALUE), GameElement.SUN);
    }

    public void enterLevelSelectStage() {
        clearDynamicElements();
        clearPlantGrid();
        stage = GameStage.LEVEL_SELECT;
        gameStarted = false;
        gameOver = false;
        gameWin = false;
        paused = false;
        prepCameraOffset = 0;
        introCameraOffset = 0;
        battleIntroPlaying = false;
        introZombieRetreatOffset = 0;
    }

    public void enterPrepareStage(int level) {
        selectedLevel = level;
        enterPrepareStage();
    }

    public void enterPrepareStage() {
        clearDynamicElements();
        clearPlantGrid();
        stage = GameStage.PREPARE;
        gameStarted = false;
        gameOver = false;
        gameWin = false;
        paused = false;
        prepCameraOffset = 0;
        introCameraOffset = 0;
        battleIntroPlaying = false;
        introZombieRetreatOffset = 0;
        prepSelectedIndex = 0;
        selectedPlantType = PlantType.PEA_SHOOTER;
    }

    public void startBattle() {
        clearDynamicElements();
        clearPlantGrid();
        totalKills = 0;
        spawnedZombies = 0;
        zombieSpawnCounter = 0;
        sunDropCounter = 0;
        startProtectCounter = 0;
        currentSun = INITIAL_SUN;
        shovelMode = false;
        paused = false;
        selectedPlantType = PlantType.PEA_SHOOTER;
        gameStarted = true;
        gameOver = false;
        gameWin = false;
        stage = GameStage.PLAYING;
        if (isContraMode()) {
            currentSun = 0;
            contraCameraX = 0;
            introCameraOffset = 0;
            battleIntroPlaying = false;
            contraPlayer = new ContraPlayer(110, CONTRA_GROUND_Y - 76);
            ElementManager.getManager().addElement(contraPlayer, GameElement.PLAYER);
        } else {
            introCameraOffset = PREP_CAMERA_MAX;
            battleIntroPlaying = true;
        }
        introZombieRetreatOffset = 0;
    }

    public void startGame() {
        enterLevelSelectStage();
    }

    public void restartGame() {
        startBattle();
    }

    public void pauseGame() {
        if (stage == GameStage.PLAYING && gameStarted && !gameOver && !gameWin) {
            paused = true;
        }
    }

    public void resumeGame() {
        paused = false;
    }

    public void returnToHome() {
        clearDynamicElements();
        clearPlantGrid();
        totalKills = 0;
        spawnedZombies = 0;
        zombieSpawnCounter = 0;
        sunDropCounter = 0;
        startProtectCounter = 0;
        currentSun = INITIAL_SUN;
        shovelMode = false;
        paused = false;
        selectedPlantType = PlantType.PEA_SHOOTER;
        gameStarted = false;
        gameOver = false;
        gameWin = false;
        stage = GameStage.HOME;
        prepCameraOffset = 0;
        introCameraOffset = 0;
        battleIntroPlaying = false;
        introZombieRetreatOffset = 0;
        contraCameraX = 0;
    }

    public void triggerGameOver() {
        if (stage != GameStage.PLAYING || !gameStarted || gameWin) {
            return;
        }
        gameOver = true;
        paused = false;
        shovelMode = false;
    }

    public void triggerGameWin() {
        if (stage != GameStage.PLAYING || !gameStarted || gameOver) {
            return;
        }
        gameWin = true;
        paused = false;
        shovelMode = false;
        if (unlockedLevel < 2) {
            unlockedLevel = 2;
        }
        if (selectedLevel == 3 && unlockedLevel < 3) {
            unlockedLevel = 3;
        }
    }

    private void clearDynamicElements() {
        ElementManager em = ElementManager.getManager();
        em.getElementsByKey(GameElement.PLANT).clear();
        em.getElementsByKey(GameElement.PLAYER).clear();
        em.getElementsByKey(GameElement.ZOMBIE).clear();
        em.getElementsByKey(GameElement.BULLET).clear();
        em.getElementsByKey(GameElement.ENEMY_BULLET).clear();
        em.getElementsByKey(GameElement.SUN).clear();
        contraPlayer = null;
        contraCameraX = 0;
    }

    private void clearPlantGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                plantGrid[row][col] = null;
            }
        }
    }

    public boolean isInHomeStartButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, HOME_BUTTON_X, HOME_START_BTN_Y, HOME_BUTTON_W, HOME_BUTTON_H);
    }

    public boolean isInHomeExitButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, HOME_BUTTON_X, HOME_EXIT_BTN_Y, HOME_BUTTON_W, HOME_BUTTON_H);
    }

    public boolean isInLevel1Button(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, LEVEL1_X, LEVEL_CARD_Y, LEVEL_CARD_W, LEVEL_CARD_H);
    }

    public boolean isInLevel3Button(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, LEVEL3_X, LEVEL_CARD_Y, LEVEL_CARD_W, LEVEL_CARD_H);
    }

    public boolean isInLevelSelectBackButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, LEVEL_SELECT_BACK_X, LEVEL_SELECT_BACK_Y, LEVEL_SELECT_BACK_W,
                LEVEL_SELECT_BACK_H);
    }

    public boolean isInPauseContinueButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, PAUSE_CONTINUE_BTN_X, PAUSE_CONTINUE_BTN_Y, PAUSE_CONTINUE_BTN_W,
                PAUSE_CONTINUE_BTN_H);
    }

    public boolean isInPauseRestartButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, PAUSE_RESTART_BTN_X, PAUSE_RESTART_BTN_Y, PAUSE_RESTART_BTN_W,
                PAUSE_RESTART_BTN_H);
    }

    public boolean isInPauseHomeButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, PAUSE_HOME_BTN_X, PAUSE_HOME_BTN_Y, PAUSE_HOME_BTN_W, PAUSE_HOME_BTN_H);
    }

    public boolean isInPauseExitButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, PAUSE_EXIT_BTN_X, PAUSE_EXIT_BTN_Y, PAUSE_EXIT_BTN_W, PAUSE_EXIT_BTN_H);
    }

    public boolean isInRestartButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, RESTART_BTN_X, RESTART_BTN_Y, RESTART_BTN_W, RESTART_BTN_H);
    }

    public boolean isInHomeButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, HOME_BTN_X, HOME_BTN_Y, HOME_BTN_W, HOME_BTN_H);
    }

    public boolean isInStatusBar(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, STATUS_BAR_X, STATUS_BAR_Y, STATUS_BAR_W, STATUS_BAR_H);
    }

    public boolean isInPeaCard(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, PEA_CARD_X, PEA_CARD_Y, CARD_W, CARD_H);
    }

    public boolean isInSunflowerCard(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, SUNFLOWER_CARD_X, SUNFLOWER_CARD_Y, CARD_W, CARD_H);
    }

    public boolean isInShovelButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, SHOVEL_BTN_X, SHOVEL_BTN_Y, SHOVEL_BTN_W, SHOVEL_BTN_H);
    }

    public boolean isInMenuButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, MENU_BTN_X, MENU_BTN_Y, MENU_BTN_W, MENU_BTN_H);
    }

    public boolean isInPrepStartButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, PREP_START_BTN_X, PREP_START_BTN_Y, PREP_START_BTN_W, PREP_START_BTN_H);
    }

    public boolean isInPrepBackButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, PREP_BACK_BTN_X, PREP_BACK_BTN_Y, PREP_BACK_BTN_W, PREP_BACK_BTN_H);
    }

    public int getPrepPlantIndex(int mouseX, int mouseY) {
        for (int i = 0; i < selectablePlants.length; i++) {
            int x = PREP_PANEL_X + 35 + (i % 2) * 135;
            int y = PREP_PANEL_Y + 110 + (i / 2) * 88;
            if (inRect(mouseX, mouseY, x, y, 118, 62)) {
                return i;
            }
        }
        return -1;
    }

    private boolean inRect(int mouseX, int mouseY, int x, int y, int w, int h) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    public void selectPeaShooter() {
        selectedPlantType = PlantType.PEA_SHOOTER;
        shovelMode = false;
    }

    public void selectSunflower() {
        selectedPlantType = PlantType.SUNFLOWER;
        shovelMode = false;
    }

    public void toggleShovelMode() {
        shovelMode = !shovelMode;
    }

    public boolean isPlaying() {
        return stage == GameStage.PLAYING && gameStarted && !gameOver && !gameWin && !paused && !battleIntroPlaying;
    }

    @Override
    public void keyClick(boolean pressed, int key) {
        if (!pressed) {
            return;
        }
        if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_P) {
            if (paused) {
                resumeGame();
            } else {
                pauseGame();
            }
        }
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWin() {
        return gameWin;
    }

    public boolean isShovelMode() {
        return shovelMode;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isInBattleStage() {
        return stage == GameStage.PLAYING;
    }

    public int getSpawnedZombies() {
        return spawnedZombies;
    }

    public int getMaxZombies() {
        return selectedLevel == 3 ? 14 : MAX_ZOMBIES;
    }

    public int getCurrentSun() {
        return currentSun;
    }

    public int getPeaShooterCost() {
        return PEA_SHOOTER_COST;
    }

    public int getSunflowerCost() {
        return SUNFLOWER_COST;
    }

    public PlantType getSelectedPlantType() {
        return selectedPlantType;
    }

    public int getSelectedPlantCost() {
        return selectedPlantType == PlantType.SUNFLOWER ? SUNFLOWER_COST : PEA_SHOOTER_COST;
    }

    public void addSun(int sun) {
        currentSun += sun;
    }

    public Plant getPlant(int row, int col) {
        if (!isValidCell(row, col)) {
            return null;
        }
        Plant plant = plantGrid[row][col];
        if (plant != null && plant.isLive()) {
            return plant;
        }
        return null;
    }

    public void removePlant(int row, int col) {
        if (isValidCell(row, col)) {
            plantGrid[row][col] = null;
        }
    }

    public void cleanupPlantSlots() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Plant plant = plantGrid[row][col];
                if (plant != null && !plant.isLive()) {
                    plantGrid[row][col] = null;
                }
            }
        }
    }

    public boolean hasZombieInRow(int row) {
        List<ElementObj> zombies = ElementManager.getManager().getElementsByKey(GameElement.ZOMBIE);
        for (ElementObj obj : zombies) {
            Zombie zombie = (Zombie) obj;
            if (zombie.isLive() && zombie.getRow() == row) {
                return true;
            }
        }
        return false;
    }

    public void addKill() {
        totalKills++;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public int getCellX(int col) {
        return lawnStartX + col * (cellW + gapX);
    }

    public int getCellY(int row) {
        return lawnStartY + row * (cellH + gapY);
    }

    public int getRowByY(int mouseY) {
        for (int row = 0; row < rows; row++) {
            int cellY = getCellY(row);
            if (mouseY >= cellY && mouseY <= cellY + cellH) {
                return row;
            }
        }
        return -1;
    }

    public int getColByX(int mouseX) {
        for (int col = 0; col < cols; col++) {
            int cellX = getCellX(col);
            if (mouseX >= cellX && mouseX <= cellX + cellW) {
                return col;
            }
        }
        return -1;
    }

    public boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public int getLawnLeftEdgeX() {
        return BATTLE_BG_X + (int) Math.round(BATTLE_BG_W * 0.105);
    }

    public int getHouseDoorTargetX() {
        return BATTLE_BG_X + (int) Math.round(BATTLE_BG_W * 0.080);
    }

    public int getHouseDoorTargetY(int row) {
        int[] laneTargets = {
                BATTLE_BG_Y + (int) Math.round(BATTLE_BG_H * 0.55),
                BATTLE_BG_Y + (int) Math.round(BATTLE_BG_H * 0.62),
                BATTLE_BG_Y + (int) Math.round(BATTLE_BG_H * 0.69),
                BATTLE_BG_Y + (int) Math.round(BATTLE_BG_H * 0.76),
                BATTLE_BG_Y + (int) Math.round(BATTLE_BG_H * 0.83)
        };
        if (row < 0) {
            row = 0;
        } else if (row >= laneTargets.length) {
            row = laneTargets.length - 1;
        }
        return laneTargets[row];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getCellW() {
        return cellW;
    }

    public int getCellH() {
        return cellH;
    }

    public int getStatusBarX() {
        return STATUS_BAR_X;
    }

    public int getStatusBarY() {
        return STATUS_BAR_Y;
    }

    public int getStatusBarW() {
        return STATUS_BAR_W;
    }

    public int getStatusBarH() {
        return STATUS_BAR_H;
    }

    public int getPeaCardX() {
        return PEA_CARD_X;
    }

    public int getPeaCardY() {
        return PEA_CARD_Y;
    }

    public int getSunflowerCardX() {
        return SUNFLOWER_CARD_X;
    }

    public int getSunflowerCardY() {
        return SUNFLOWER_CARD_Y;
    }

    public int getPlantCardW() {
        return CARD_W;
    }

    public int getPlantCardH() {
        return CARD_H;
    }

    public int getShovelButtonX() {
        return SHOVEL_BTN_X;
    }

    public int getShovelButtonY() {
        return SHOVEL_BTN_Y;
    }

    public int getShovelButtonW() {
        return SHOVEL_BTN_W;
    }

    public int getShovelButtonH() {
        return SHOVEL_BTN_H;
    }

    public int getMenuButtonX() {
        return MENU_BTN_X;
    }

    public int getMenuButtonY() {
        return MENU_BTN_Y;
    }

    public int getMenuButtonW() {
        return MENU_BTN_W;
    }

    public int getMenuButtonH() {
        return MENU_BTN_H;
    }

    public int getSceneCameraOffset() {
        if (isContraMode()) {
            return contraCameraX;
        }
        if (stage == GameStage.PREPARE) {
            return prepCameraOffset;
        }
        if (stage == GameStage.PLAYING && battleIntroPlaying) {
            return introCameraOffset;
        }
        return 0;
    }

    public int toScreenX(int worldX) {
        return worldX - getSceneCameraOffset();
    }

    public void updateContraCameraFor(int playerWorldX) {
        int target = playerWorldX - getW() / 3;
        contraCameraX = Math.max(0, Math.min(CONTRA_MAP_W - getW(), target));
    }

    public int getContraCameraX() {
        return contraCameraX;
    }

    public int getContraWorldWidth() {
        return CONTRA_MAP_W;
    }

    public int getContraGroundY() {
        return CONTRA_GROUND_Y;
    }

    public int getContraTopBoundY() {
        return CONTRA_TOP_Y;
    }

    public boolean isBattleIntroPlaying() {
        return battleIntroPlaying;
    }

    public boolean hasBackgroundSceneImage() {
        return battleSceneImage != null;
    }

    public boolean isContraMode() {
        return stage == GameStage.PLAYING && selectedLevel == 3;
    }

    public ContraPlayer getContraPlayer() {
        return contraPlayer;
    }

    public int getContraPlayerHealth() {
        return contraPlayer == null ? 0 : contraPlayer.getHealth();
    }

    private enum ZombiePreviewType {
        BASIC, CONE, BUCKET
    }
}
