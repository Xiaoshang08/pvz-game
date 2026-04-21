package com.tedu.element;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.util.GameImage;

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

    private static final int BOARD_X = 300;
    private static final int BOARD_Y = 168;
    private static final int BOARD_W = 920;
    private static final int BOARD_H = 470;

    private static final int STATUS_BAR_X = 40;
    private static final int STATUS_BAR_Y = 50;
    private static final int STATUS_BAR_W = 1180;
    private static final int STATUS_BAR_H = 92;

    private static final int CARD_W = 160;
    private static final int CARD_H = 38;
    private static final int PEA_CARD_X = 170;
    private static final int PEA_CARD_Y = 60;
    private static final int SUNFLOWER_CARD_X = 345;
    private static final int SUNFLOWER_CARD_Y = 60;
    private static final int SHOVEL_BTN_X = 170;
    private static final int SHOVEL_BTN_Y = 102;
    private static final int SHOVEL_BTN_W = 160;
    private static final int SHOVEL_BTN_H = 32;
    private static final int MENU_BTN_X = 345;
    private static final int MENU_BTN_Y = 102;
    private static final int MENU_BTN_W = 160;
    private static final int MENU_BTN_H = 32;

    private static final int HOME_BUTTON_W = 220;
    private static final int HOME_BUTTON_H = 58;
    private static final int HOME_BUTTON_X = 865;
    private static final int HOME_START_BTN_Y = 285;
    private static final int HOME_EXIT_BTN_Y = 360;

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

    private final int rows = 5;
    private final int cols = 9;
    private final int cellW = 92;
    private final int cellH = 82;
    private final int gapX = 8;
    private final int gapY = 8;
    private final int lawnStartX = 320;
    private final int lawnStartY = 186;

    private final Plant[][] plantGrid = new Plant[rows][cols];
    private final Random random = new Random();
    private final String[] selectablePlants = {"豌豆射手", "向日葵", "寒冰射手", "坚果墙", "双发射手", "樱桃炸弹"};

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
    private int prepSelectedIndex = 0;

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
        if (stage == GameStage.PREPARE) {
            drawPrepareScene(g2);
            return;
        }

        drawBattleScene(g2);
    }

    private void drawBattleScene(Graphics2D g) {
        g.setColor(new Color(221, 232, 196));
        g.fillRect(0, 0, getW(), getH());

        drawBattleEnvironment(g, getSceneCameraOffset(), true, battleIntroPlaying);

        g.setColor(new Color(101, 138, 57));
        g.setFont(new Font("SansSerif", Font.BOLD, 28));
        g.drawString("Plants vs Zombies - Forest & Firewater Demo", 44, 36);

        for (int row = 0; row < rows; row++) {
            int y = getCellY(row) - 8;
            g.setColor(new Color(72, 118, 40));
            g.setFont(new Font("SansSerif", Font.PLAIN, 18));
            g.drawString("L" + (row + 1), 20, y + cellH / 2 + 8);
        }
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

    private void drawPrepareScene(Graphics2D g) {
        g.setColor(new Color(188, 220, 244));
        g.fillRect(0, 0, getW(), 390);
        g.setColor(new Color(205, 232, 163));
        g.fillRect(0, 390, getW(), 330);

        drawBattleEnvironment(g, prepCameraOffset, false, true);
        drawPrepPanel(g);
    }

    private void drawBattleEnvironment(Graphics2D g, int cameraOffset, boolean showLawnGridBg, boolean showPreviewZombies) {
        int ox = -cameraOffset;

        g.setColor(new Color(255, 248, 184));
        g.fillOval(85 + ox, 42, 90, 90);

        g.setColor(new Color(148, 214, 95));
        g.fillOval(-120 + ox, 410, 650, 260);
        g.fillOval(380 + ox, 420, 980, 300);

        drawHouse(g, 80 + ox, 242, 1.0);

        g.setColor(new Color(124, 130, 124));
        g.fillRoundRect(235 + ox, 420, 930, 70, 34, 34);
        g.setColor(new Color(173, 180, 173));
        g.fillRoundRect(265 + ox, 447, 880, 10, 10, 10);
        g.fillRoundRect(265 + ox, 470, 880, 10, 10, 10);
        for (int i = 0; i < 14; i++) {
            int rx = 280 + ox + i * 62;
            g.fillRoundRect(rx, 438, 24, 50, 8, 8);
        }

        g.setColor(new Color(215, 234, 179));
        g.fillRoundRect(BOARD_X + ox, BOARD_Y, BOARD_W, BOARD_H, 28, 28);
        g.setColor(new Color(124, 181, 78));
        g.fillRoundRect(BOARD_X + 22 + ox, BOARD_Y + 22, BOARD_W - 44, BOARD_H - 44, 24, 24);

        if (showLawnGridBg) {
            g.setColor(new Color(151, 196, 88, 55));
            g.fillRoundRect(BOARD_X + 22 + ox, BOARD_Y + 22, BOARD_W - 44, BOARD_H - 44, 24, 24);
        }

        if (showPreviewZombies) {
            drawZombiePreviewShowcase(g, ox);
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
        g.drawString("镜头会先扫过门前道路，再进入布阵。", 72, 548);

        drawButton(g, PREP_START_BTN_X, PREP_START_BTN_Y, PREP_START_BTN_W, PREP_START_BTN_H,
                new Color(86, 164, 63), "开始战斗");
        drawButton(g, PREP_BACK_BTN_X, PREP_BACK_BTN_Y, PREP_BACK_BTN_W, PREP_BACK_BTN_H,
                new Color(112, 112, 112), "返回首页");
    }

    private void drawZombiePreviewShowcase(Graphics2D g, int ox) {
        int baseX = 980 + ox;
        int baseY = 275;
        drawPreviewZombie(g, baseX, baseY, ZombiePreviewType.BASIC, "普通僵尸");
        drawPreviewZombie(g, baseX + 88, baseY + 35, ZombiePreviewType.CONE, "路障僵尸");
        drawPreviewZombie(g, baseX + 176, baseY - 10, ZombiePreviewType.BUCKET, "铁桶僵尸");
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
            int[] xs = {x + 14, x + 30, x + 48};
            int[] ys = {y + 20, y - 10, y + 20};
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
        int[] roofX = {x + 20, x + 105, x + 190};
        int[] roofY = {y + 48, y - 18, y + 48};
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
        if (stage == GameStage.PREPARE) {
            if (prepCameraOffset < 170) {
                prepCameraOffset += 2;
            }
            return;
        }

        if (stage == GameStage.PLAYING && battleIntroPlaying) {
            if (introCameraOffset > 0) {
                introCameraOffset = Math.max(0, introCameraOffset - 4);
            }
            if (introCameraOffset == 0) {
                battleIntroPlaying = false;
            }
            return;
        }

        if (!isPlaying()) {
            return;
        }

        if (spawnedZombies < MAX_ZOMBIES) {
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

    @Override
    public void mouseClick(int mouseX, int mouseY) {
        if (stage == GameStage.HOME) {
            if (isInHomeStartButton(mouseX, mouseY)) {
                enterPrepareStage();
                return;
            }
            if (isInHomeExitButton(mouseX, mouseY)) {
                System.exit(0);
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
        if (!isPlaying()) {
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
        if (!isPlaying()) {
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
            shovelMode = false;
        }
    }

    private void spawnZombie() {
        int row = random.nextInt(rows);
        int x = getCellX(cols - 1) + cellW + 60;
        int y = getCellY(row) + 10;
        ElementManager.getManager().addElement(new Zombie(row, x, y), GameElement.ZOMBIE);
        spawnedZombies++;
    }

    private void spawnSkySun() {
        int col = random.nextInt(cols);
        int x = getCellX(col) + (cellW - 36) / 2;
        int targetY = getCellY(random.nextInt(rows)) + 10;
        ElementManager.getManager().addElement(Sun.createFallingSun(x, STATUS_BAR_Y + STATUS_BAR_H + 8, targetY, SKY_SUN_VALUE), GameElement.SUN);
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
        introCameraOffset = 170;
        battleIntroPlaying = true;
    }

    public void startGame() {
        enterPrepareStage();
    }

    public void restartGame() {
        startBattle();
    }

    public void pauseGame() {
        if (stage == GameStage.PLAYING && gameStarted && !gameOver && !gameWin) {
            paused = true;
            shovelMode = false;
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
    }

    private void clearDynamicElements() {
        ElementManager em = ElementManager.getManager();
        em.getElementsByKey(GameElement.PLANT).clear();
        em.getElementsByKey(GameElement.ZOMBIE).clear();
        em.getElementsByKey(GameElement.BULLET).clear();
        em.getElementsByKey(GameElement.SUN).clear();
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

    public boolean isInPauseContinueButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, PAUSE_CONTINUE_BTN_X, PAUSE_CONTINUE_BTN_Y, PAUSE_CONTINUE_BTN_W, PAUSE_CONTINUE_BTN_H);
    }

    public boolean isInPauseRestartButton(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, PAUSE_RESTART_BTN_X, PAUSE_RESTART_BTN_Y, PAUSE_RESTART_BTN_W, PAUSE_RESTART_BTN_H);
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

    public boolean isGameStarted() { return gameStarted; }
    public boolean isGameOver() { return gameOver; }
    public boolean isGameWin() { return gameWin; }
    public boolean isShovelMode() { return shovelMode; }
    public boolean isPaused() { return paused; }
    public boolean isInBattleStage() { return stage == GameStage.PLAYING; }
    public int getSpawnedZombies() { return spawnedZombies; }
    public int getMaxZombies() { return MAX_ZOMBIES; }
    public int getCurrentSun() { return currentSun; }
    public int getPeaShooterCost() { return PEA_SHOOTER_COST; }
    public int getSunflowerCost() { return SUNFLOWER_COST; }
    public PlantType getSelectedPlantType() { return selectedPlantType; }
    public int getSelectedPlantCost() { return selectedPlantType == PlantType.SUNFLOWER ? SUNFLOWER_COST : PEA_SHOOTER_COST; }
    public void addSun(int sun) { currentSun += sun; }

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
        return BOARD_X + 22;
    }

    public int getHouseDoorTargetX() {
        return 255;
    }

    public int getHouseDoorTargetY(int row) {
        int[] laneTargets = {350, 382, 414, 446, 478};
        if (row < 0) {
            row = 0;
        } else if (row >= laneTargets.length) {
            row = laneTargets.length - 1;
        }
        return laneTargets[row];
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getCellW() { return cellW; }
    public int getCellH() { return cellH; }
    public int getStatusBarX() { return STATUS_BAR_X; }
    public int getStatusBarY() { return STATUS_BAR_Y; }
    public int getStatusBarW() { return STATUS_BAR_W; }
    public int getStatusBarH() { return STATUS_BAR_H; }
    public int getPeaCardX() { return PEA_CARD_X; }
    public int getPeaCardY() { return PEA_CARD_Y; }
    public int getSunflowerCardX() { return SUNFLOWER_CARD_X; }
    public int getSunflowerCardY() { return SUNFLOWER_CARD_Y; }
    public int getPlantCardW() { return CARD_W; }
    public int getPlantCardH() { return CARD_H; }
    public int getShovelButtonX() { return SHOVEL_BTN_X; }
    public int getShovelButtonY() { return SHOVEL_BTN_Y; }
    public int getShovelButtonW() { return SHOVEL_BTN_W; }
    public int getShovelButtonH() { return SHOVEL_BTN_H; }
    public int getMenuButtonX() { return MENU_BTN_X; }
    public int getMenuButtonY() { return MENU_BTN_Y; }
    public int getMenuButtonW() { return MENU_BTN_W; }
    public int getMenuButtonH() { return MENU_BTN_H; }


    public int getSceneCameraOffset() {
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

    public boolean isBattleIntroPlaying() {
        return battleIntroPlaying;
    }

    private enum ZombiePreviewType {
        BASIC, CONE, BUCKET
    }
}
