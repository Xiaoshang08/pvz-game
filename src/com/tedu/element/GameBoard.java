package com.tedu.element;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.util.GameImage;

/**
 * GameBoard：游戏主战场背景与状态中心。
 *
 * 新增剧情系统：
 * - 开场剧情（黑底白字）
 * - 每关首次进入时的提示卡片
 * - 胜利后剧情台词
 * - 第三关结局动画
 */
public class GameBoard extends ElementObj {
    private static GameBoard instance;
    private BufferedImage homeBackgroundImage;
    private BufferedImage levelSelectBackgroundImage;

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
    private static final int DOUBLE_PEA_CARD_X = 450;
    private static final int DOUBLE_PEA_CARD_Y = 17;
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
    private static final int HOME_BUTTON_X = 530;
    private static final int HOME_START_BTN_Y = 420;
    private static final int HOME_EXIT_BTN_Y = 495;

    private static final int LEVEL_CARD_W = 220;
    private static final int LEVEL_CARD_H = 280;
    private static final int LEVEL_CARD_Y = 220;
    private static final int LEVEL1_X = 220;
    private static final int LEVEL2_X = 530;
    private static final int LEVEL3_X = 840;
    private static final int LEVEL_SELECT_BACK_X = 520;
    private static final int LEVEL_SELECT_BACK_Y = 560;
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

    private static final int PREP_PANEL_X = 40;
    private static final int PREP_PANEL_Y = 130;
    private static final int PREP_PANEL_W = 340;
    private static final int PREP_PANEL_H = 430;
    private static final int PREP_START_BTN_X = 95;
    private static final int PREP_START_BTN_Y = 430;
    private static final int PREP_START_BTN_W = 220;
    private static final int PREP_START_BTN_H = 52;
    private static final int PREP_BACK_BTN_X = 95;
    private static final int PREP_BACK_BTN_Y = 490;
    private static final int PREP_BACK_BTN_W = 220;
    private static final int PREP_BACK_BTN_H = 42;

    private static final int MAX_ZOMBIES = 20;
    private static final int CONTRA_MAX_ZOMBIES = 18;
    private static final int PEA_SHOOTER_COST = 100;
    private static final int SUNFLOWER_COST = 50;
    private static final int INITIAL_SUN = 100;
    private static final int SKY_SUN_VALUE = 50;
    private static final int SUN_DROP_INTERVAL = 267;
    private static final int ZOMBIE_SPAWN_INTERVAL = 150;
    private static final int START_PROTECT_TIME = 167;
    private static final int LEVEL1_ROADBLOCK_UNLOCK_AFTER = 5;
    private static final int LEVEL1_BUCKET_UNLOCK_AFTER = 10;
    private static final int LEVEL1_ROADBLOCK_MAX = 5;
    private static final int LEVEL1_BUCKET_MAX = 3;
    private static final int LEVEL3_ROADBLOCK_MAX = 2;
    private static final double LEVEL1_ROADBLOCK_CHANCE = 0.35;
    private static final double LEVEL1_BUCKET_CHANCE = 0.25;

    private static final String BATTLE_SCENE_IMAGE_PATH = "images/map/lawn_scene.png";
    private static final String CONTRA_STAGE_IMAGE_PATH = "images/map/contra_stage.png";
    private static final int CONTRA_SCALE = 3;
    private static final int CONTRA_MAP_W = 3328 * CONTRA_SCALE;
    private static final int CONTRA_MAP_H = 224 * CONTRA_SCALE;
    private static final int CONTRA_MAP_Y = 48;
    private static final int CONTRA_GROUND_Y = 620;
    private static final int CONTRA_TOP_Y = 120;
    private static final int CONTRA_SURFACE_SEARCH_BOTTOM = CONTRA_MAP_Y + CONTRA_MAP_H - 18;
    private static final int CONTRA_SPAWN_START_X = 760;
    private static final int CONTRA_SPAWN_END_MARGIN = 520;
    private static final int CONTRA_EDGE_GRACE = 18;
    private static final int CONTRA_BOSS_SOURCE_X = 1620;
    private static final int CONTRA_BOSS_W = 224;
    private static final int CONTRA_BOSS_H = 180;
    private static final int CONTRA_EXIT_LEFT_X = 3218 * CONTRA_SCALE;
    private static final int CONTRA_EXIT_TOP_Y = CONTRA_MAP_Y + 54 * CONTRA_SCALE;
    private static final int CONTRA_EXIT_BOTTOM_Y = CONTRA_MAP_Y + 184 * CONTRA_SCALE;
    private final BufferedImage battleSceneImage = GameImage.get(BATTLE_SCENE_IMAGE_PATH);
    private final BufferedImage contraStageImage = GameImage.get(CONTRA_STAGE_IMAGE_PATH);
    private final BufferedImage defeatImage = GameImage.get("images/ui/defeat.png");

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
    private final String[] selectablePlants = Arrays.stream(PlantType.values())
            .map(PlantType::getDisplayName)
            .toArray(String[]::new);

    private int zombieSpawnCounter = 0;
    private int sunDropCounter = 0;
    private int startProtectCounter = 0;
    private int totalKills = 0;
    private int spawnedZombies = 0;
    private int lawnRoadblockSpawned = 0;
    private int lawnBucketSpawned = 0;
    private int contraRoadblockSpawned = 0;
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
    private int unlockedLevel = 1; // 改为从1开始
    private int selectedLevel = 1;
    private ContraPlayer contraPlayer;
    private int contraCameraX = 0;
    private boolean contraBossSpawned = false;
    private boolean contraBossDefeated = false;

    // ==================== 剧情系统新增字段 ====================
    private boolean storyPlayed = false; // 开场剧情是否已播放
    private Map<Integer, Boolean> levelTipShown = new HashMap<>(); // 各关卡提示是否已显示
    private String winMessage = null; // 胜利后额外显示的台词
    private boolean endingPlaying = false; // 是否正在播放结局
    private long endingStartTime = 0; // 结局开始时间
    private static final long ENDING_DURATION = 4000; // 结局显示时间（毫秒）

    // 剧情文本
    private final String[] openingStory = {
            "深夜，我被一阵沙沙声惊醒。窗外的草坪上，无数双绿眼睛在晃动——是僵尸！",
            "这是红月仙给我们的第一个困难！",
            "向日葵急切地对我喊：“它们来了！快用阳光唤醒大家，守住这条路！”",
            "我握紧弹弓，深吸一口气：“好，那就让它们看看，这片草坪谁做主。”",
            "来吧，第一波——"
    };
    private int openingIndex = 0;
    private long openingLastTime = 0;
    private static final long OPENING_DELAY = 2000; // 每句显示2秒

    // 关卡剧情和玩法提示
    private final String[] levelStory = {
            // 第一关
            "深夜，我被一阵沙沙声惊醒。窗外的草坪上，无数双绿眼睛在晃动——是僵尸！\n向日葵急切地对我喊：“它们来了！快用阳光唤醒大家，守住这条路！”\n我握紧弹弓，深吸一口气：“好，那就让它们看看，这片草坪谁做主。”\n来吧，第一波——",
            // 第二关
            "击退僵尸后，我穿过迷雾，来到冰火森林。这里一半冰封，一半燃烧。\n两位精灵——冰仔和火仔——被困在结界中，互相敌对。他们的能量核心被锁在迷宫深处。\n我必须同时解开冰之迷宫和火之迷宫，找回冰火精魄。\n冰面上只能滑行，火地上需要躲避熔岩……每一步都要思考。\n只有冰与火联手，才能打开通往要塞的路。",
            // 第三关
            "冰火之力涌入弹弓，我冲进暗影要塞。黑暗法师就在最深处。\n影怪从四面八方扑来——它们速度快、护甲厚，但弱点在胸口核心。\n我用冰弹冻结高速敌人，用火弹烧毁护甲，瞄准核心一下一下地射击。\n子弹越来越密，但我不能后退。\n最后一战……为了这片森林，为了所有伙伴！"
    };
    private final String[] levelGameplay = {
            // 第一关玩法
            "玩法：\n• 点击植物卡片选择豌豆射手或向日葵\n• 点击草坪格子种植植物\n• 收集阳光，抵御僵尸\n• 不要让僵尸走到最左边！",
            // 第二关玩法
            "玩法：\n• 控制角色在冰面/火地上移动\n• 收集冰之精魄和火之精魄\n• 避开陷阱，注意冰冻、火焰和毒液伤害",
            // 第三关玩法
            "玩法：\n• 点击空格射击\n• 按 F 使用第二关获得的双发道具，持续约 10 秒\n• 击败洪粤贤\n• 躲避敌人子弹幕\n• 击败 boss 后进入最右侧基地"
    };
    private final String[] winMessages = {
            "打得漂亮！不过红月仙不会善罢甘休……",
            "冰火融合！现在弹弓有了元素之力，去给他点颜色看看。",
            null // 第三关胜利后不显示普通胜利界面，直接播放结局
    };
    private final String endingText = "恭喜你成功通关！\n\n暗影要塞已经被攻破，森林重新迎来了光亮。\n\n感谢你的参与，感谢你一路守护这片草坪与森林伙伴。";

    public GameBoard() {
        instance = this;
        setX(0);
        setY(0);
        setW(WINDOW_W);
        setH(WINDOW_H);
        // 初始化提示记录
        for (int i = 1; i <= 3; i++) {
            levelTipShown.put(i, false);
        }
    }

    public static GameBoard getInstance() {
        return instance;
    }

    @FunctionalInterface
    private interface PlantCreator {
        Plant create(int row, int col, int x, int y, int w, int h);
    }

    public enum PlantType {
        PEA_SHOOTER("豌豆射手", 100, PeaShooter::new),
        SUNFLOWER("向日葵", 50, Sunflower::new),
        DOUBLE_PEA_SHOOTER("双发豌豆", 200, DoublePeaShooter::new);

        private final String displayName;
        private final int cost;
        private final PlantCreator creator;

        PlantType(String displayName, int cost, PlantCreator creator) {
            this.displayName = displayName;
            this.cost = cost;
            this.creator = creator;
        }

        public String getDisplayName() {
            return displayName;
        }

        public int getCost() {
            return cost;
        }

        public Plant create(int row, int col, int x, int y, int w, int h) {
            return creator.create(row, col, x, y, w, h);
        }
    }

    private BufferedImage loadLocalImage(String path) {
        try {
            File file = new File(path);
            return file.exists() ? ImageIO.read(file) : null;
        } catch (Exception e) {
            return null;
        }
    }

    public enum GameStage {
        HOME,
        LEVEL_SELECT,
        PREPARE,
        PLAYING,
        OPENING_STORY, // 开场剧情
        LEVEL_TIP, // 关卡提示卡片
        ENDING // 结局
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
        if (stage == GameStage.OPENING_STORY) {
            drawLevelSelectScene(g2); // 背景为选关背景
            drawOpeningStoryOverlay(g2);
            return;
        }
        if (stage == GameStage.LEVEL_TIP) {
            drawLevelSelectScene(g2); // 背景为选关背景
            drawLevelTipOverlay(g2);
            return;
        }
        if (stage == GameStage.ENDING) {
            drawLevelSelectScene(g2); // 背景为选关背景
            drawEndingOverlay(g2);
            return;
        }
        if (stage == GameStage.PREPARE) {
            drawPrepareScene(g2);
            return;
        }
        drawBattleScene(g2);
    }

    // ==================== 剧情绘制方法 ====================
    private void drawOpeningStoryOverlay(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 220));
        g.fillRect(0, 0, getW(), getH());

        if (openingIndex < openingStory.length) {
            String line = openingStory[openingIndex];
            g.setColor(Color.WHITE);
            g.setFont(new Font("Microsoft YaHei", Font.BOLD, 28));
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(line);
            int x = (getW() - textWidth) / 2;
            int y = getH() / 2;
            g.drawString(line, x, y);

            g.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
            g.setColor(new Color(255, 255, 255, 150));
            g.drawString("点击任意键跳过", getW() - 120, getH() - 30);
        }
    }

    private void drawLevelTipOverlay(Graphics2D g) {
        // 半透明黑色遮罩
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getW(), getH());

        int cardW = 700;
        int cardH = 500;
        int cardX = (getW() - cardW) / 2;
        int cardY = (getH() - cardH) / 2;

        // 卡片背景
        g.setColor(new Color(30, 30, 40, 240));
        g.fillRoundRect(cardX, cardY, cardW, cardH, 30, 30);
        g.setColor(new Color(200, 200, 220));
        g.drawRoundRect(cardX, cardY, cardW, cardH, 30, 30);

        // 标题
        String title = selectedLevel == 1 ? "第一关 · 草坪保卫战 " : (selectedLevel == 2 ? "第二关 · 冰火森林的试炼 " : "第三关 · 暗影要塞的决战");
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 28));
        g.setColor(new Color(255, 220, 100));
        int titleW = g.getFontMetrics().stringWidth(title);
        g.drawString(title, cardX + (cardW - titleW) / 2, cardY + 50);

        // 分隔线
        g.setColor(new Color(150, 150, 170));
        g.drawLine(cardX + 30, cardY + 80, cardX + cardW - 30, cardY + 80);

        // 剧情文本（自动换行）
        g.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
        g.setColor(Color.WHITE);
        String story = levelStory[selectedLevel - 1];
        drawWrappedText(g, story, cardX + 40, cardY + 110, cardW - 80, 28);

        // 玩法文本
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
        g.setColor(new Color(255, 200, 100));
        g.drawString("【玩法说明】", cardX + 40, cardY + 280);
        g.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
        g.setColor(Color.WHITE);
        String gameplay = levelGameplay[selectedLevel - 1];
        drawWrappedText(g, gameplay, cardX + 40, cardY + 315, cardW - 80, 26);

        // 开始按钮
        int btnW = 180;
        int btnH = 50;
        int btnX = cardX + (cardW - btnW) / 2;
        int btnY = cardY + cardH - 70;
        drawStyledButton(g, btnX, btnY, btnW, btnH, new Color(70, 130, 200), "开始战斗");
    }

    private void drawEndingOverlay(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 220));
        g.fillRect(0, 0, getW(), getH());

        g.setColor(new Color(255, 236, 160));
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
        String[] lines = endingText.split("\n");
        int lineY = getH() / 2 - 70;
        for (String line : lines) {
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(line);
            int x = (getW() - textWidth) / 2;
            g.drawString(line, x, lineY);
            lineY += 56;
        }

        g.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        g.setColor(new Color(255, 255, 255, 150));
        g.drawString("点击任意位置返回首页", getW() - 170, getH() - 30);
    }

    private void drawWrappedText(Graphics2D g, String text, int x, int y, int maxWidth, int lineHeight) {
        FontMetrics fm = g.getFontMetrics();
        String[] words = text.split("(?<=[。！？\n])|(?=[。！？\n])|\\s+");
        StringBuilder line = new StringBuilder();
        int currentY = y;
        for (String word : words) {
            if (word.equals("\n")) {
                g.drawString(line.toString(), x, currentY);
                line.setLength(0);
                currentY += lineHeight;
                continue;
            }
            String testLine = line.length() == 0 ? word : line + word;
            if (fm.stringWidth(testLine) <= maxWidth) {
                line.append(word);
            } else {
                if (line.length() > 0) {
                    g.drawString(line.toString(), x, currentY);
                    currentY += lineHeight;
                    line.setLength(0);
                }
                line.append(word);
            }
        }
        if (line.length() > 0) {
            g.drawString(line.toString(), x, currentY);
        }
    }

    // ==================== 剧情逻辑方法 ====================
    private void startOpeningStory() {
        stage = GameStage.OPENING_STORY;
        openingIndex = 0;
        openingLastTime = System.currentTimeMillis();
    }

    private void updateOpeningStory() {
        if (stage != GameStage.OPENING_STORY)
            return;
        long now = System.currentTimeMillis();
        if (now - openingLastTime >= OPENING_DELAY) {
            openingIndex++;
            openingLastTime = now;
            if (openingIndex >= openingStory.length) {
                // 开场剧情结束，进入选关界面
                stage = GameStage.LEVEL_SELECT;
                storyPlayed = true;
                openingIndex = 0;
            }
        }
    }

    private void skipOpeningStory() {
        if (stage == GameStage.OPENING_STORY) {
            stage = GameStage.LEVEL_SELECT;
            storyPlayed = true;
            openingIndex = 0;
        }
    }

    private void startLevelTip(int level) {
        selectedLevel = level;
        stage = GameStage.LEVEL_TIP;
    }

    private void startEnding() {
        endingPlaying = true;
        stage = GameStage.ENDING;
        endingStartTime = System.currentTimeMillis();
    }

    private void updateEnding() {
        if (stage == GameStage.ENDING && System.currentTimeMillis() - endingStartTime >= ENDING_DURATION) {
            // 结局播放完毕，返回首页
            returnToHome();
            endingPlaying = false;
        }
    }

    // ==================== 原有场景绘制（只修改了按钮调用，其他不变） ====================
    private void drawBattleScene(Graphics2D g) {
        if (isFireIceMode()) {
            drawFireIceScene(g);
            return;
        }
        if (isContraMode()) {
            drawContraScene(g);
            return;
        }
        g.setColor(new Color(221, 232, 196));
        g.fillRect(0, 0, getW(), getH());
        drawBattleEnvironment(g, getSceneCameraOffset(), battleIntroPlaying);
    }

    private void drawFireIceScene(Graphics2D g) {
        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        drawByType(all, GameElement.MAPS, g);
        drawByType(all, GameElement.FIRE_TERRAIN, g);
        drawByType(all, GameElement.WATER_TERRAIN, g);
        drawByType(all, GameElement.FIRE_DOOR, g);
        drawByType(all, GameElement.WATER_DOOR, g);
        drawByType(all, GameElement.FIRE_DIAMOND, g);
        drawByType(all, GameElement.WATER_DIAMOND, g);
        drawByType(all, GameElement.TRAP_TERRAIN, g);
        drawByType(all, GameElement.FIRE_MAN, g);
        drawByType(all, GameElement.WATER_MAN, g);
    }

    private void drawByType(Map<GameElement, List<ElementObj>> all, GameElement type, Graphics g) {
        for (ElementObj obj : all.get(type)) {
            if (obj.isLive())
                obj.showElement(g);
        }
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
        g.drawString("Move: WASD / Arrow keys    Shoot: Space    Transform: F (10s)    Pause: P or Esc", 34, 62);
    }

    private void drawHomeScene(Graphics2D g) {
        if (homeBackgroundImage == null) {
            homeBackgroundImage = loadLocalImage("assets/images/background/Mini World Adventure.png");
        }
        if (homeBackgroundImage != null) {
            g.drawImage(homeBackgroundImage, 0, 0, getW(), getH(), null);
            g.setColor(new Color(0, 0, 0, 85));
            g.fillRoundRect(460, 315, 360, 245, 34, 34);
            g.setColor(new Color(255, 244, 184));
            g.setFont(new Font("Serif", Font.BOLD, 34));
            g.drawString("植物大战僵尸", 535, 370);
            g.setFont(new Font("SansSerif", Font.PLAIN, 18));
            g.drawString("森林冰火小人版 Mini Demo", 525, 402);
            drawStyledButton(g, HOME_BUTTON_X, HOME_START_BTN_Y, HOME_BUTTON_W, HOME_BUTTON_H,
                    new Color(83, 160, 56), "开始游戏");
            drawStyledButton(g, HOME_BUTTON_X, HOME_EXIT_BTN_Y, HOME_BUTTON_W, HOME_BUTTON_H,
                    new Color(118, 118, 118), "退出游戏");
            return;
        }

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

        drawStyledButton(g, HOME_BUTTON_X, HOME_START_BTN_Y, HOME_BUTTON_W, HOME_BUTTON_H,
                new Color(83, 160, 56), "开始游戏");
        drawStyledButton(g, HOME_BUTTON_X, HOME_EXIT_BTN_Y, HOME_BUTTON_W, HOME_BUTTON_H,
                new Color(118, 118, 118), "退出游戏");
    }

    private void drawLevelSelectScene(Graphics2D g) {
        if (levelSelectBackgroundImage == null) {
            levelSelectBackgroundImage = loadLocalImage("assets/images/background/backgroud2.png");
        }
        if (levelSelectBackgroundImage != null) {
            g.drawImage(levelSelectBackgroundImage, 0, 0, getW(), getH(), null);
        } else {
            g.setColor(new Color(168, 219, 247));
            g.fillRect(0, 0, getW(), 410);
            g.setColor(new Color(196, 227, 145));
            g.fillRect(0, 410, getW(), 310);
            g.setColor(new Color(255, 244, 170));
            g.fillOval(80, 55, 120, 120);
            g.setColor(new Color(140, 208, 92));
            g.fillOval(-60, 390, 620, 250);
            g.fillOval(320, 430, 980, 290);
        }

        g.setColor(levelSelectBackgroundImage != null ? new Color(25, 87, 87) : new Color(255, 255, 255, 228));
        g.setFont(new Font("Serif", Font.BOLD, 42));
        g.drawString("选择关卡", 550, 150);

        drawLevelCard(g, LEVEL1_X, LEVEL_CARD_Y, 1, unlockedLevel >= 1, "草坪保卫战", unlockedLevel >= 1 ? "当前可挑战" : "暂未解锁");
        drawLevelCard(g, LEVEL2_X, LEVEL_CARD_Y, 2, unlockedLevel >= 2, " 冰火森林 ",
                unlockedLevel >= 2 ? "当前可挑战" : "暂未解锁");
        drawLevelCard(g, LEVEL3_X, LEVEL_CARD_Y, 3, unlockedLevel >= 3, " 暗影要塞 ",
                unlockedLevel >= 3 ? "当前可挑战" : "暂未解锁");

        drawStyledButton(g, LEVEL_SELECT_BACK_X, LEVEL_SELECT_BACK_Y, LEVEL_SELECT_BACK_W, LEVEL_SELECT_BACK_H,
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
            g.drawString("可用", x + 14, y + 48);
        }


        drawStyledButton(g, PREP_START_BTN_X, PREP_START_BTN_Y, PREP_START_BTN_W, PREP_START_BTN_H,
                new Color(86, 164, 63), "开始战斗");
        drawStyledButton(g, PREP_BACK_BTN_X, PREP_BACK_BTN_Y, PREP_BACK_BTN_W, PREP_BACK_BTN_H,
                new Color(112, 112, 112), "返回首页");
    }

    private void drawZombiePreviewShowcase(Graphics2D g, int ox) {
        int roadCenterX = BATTLE_BG_X + (int) Math.round(BATTLE_BG_W * 0.885) + ox;
        int roadCenterY = BATTLE_BG_Y + (int) Math.round(BATTLE_BG_H * 0.60);
        drawPreviewZombie(g, roadCenterX - 12, roadCenterY - 156, ZombiePreviewType.CONE, "路障僵尸");
        drawPreviewZombie(g, roadCenterX + 24, roadCenterY - 24, ZombiePreviewType.BASIC, "普通僵尸");
        drawPreviewZombie(g, roadCenterX - 2, roadCenterY + 112, ZombiePreviewType.BUCKET, "铁桶僵尸");
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
        // 冰火人模式:显示提示消息(在所有元素绘制完成后显示，确保在最上层)
        else if (isFireIceMode()) {
            String tip = ElementManager.getManager().getTipMessage();
            if (tip != null) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("微软雅黑", Font.BOLD, 16));
                int tipX = ElementManager.getManager().getTipX();
                int tipY = ElementManager.getManager().getTipY();
                // 多行文本处理
                String[] lines = tip.split("\n");
                // 指定位置显示
                for (int i = 0; i < lines.length; i++) {
                    g.drawString(lines[i], tipX, tipY + i * 32);
                }
            }
            // 绘制菜单按钮（右上角，冰火人模式最上层）
            drawStyledButton((Graphics2D) g, MENU_BTN_X, MENU_BTN_Y, MENU_BTN_W, MENU_BTN_H,
                    new Color(104, 109, 224), "菜单 ≡");
        }
    }

    private void drawPauseOverlay(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRoundRect(BOARD_X, BOARD_Y, BOARD_W, BOARD_H, 28, 28);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 34));
        g.drawString("游戏菜单", 560, 210);

        drawStyledButton(g, PAUSE_CONTINUE_BTN_X, PAUSE_CONTINUE_BTN_Y, PAUSE_CONTINUE_BTN_W, PAUSE_CONTINUE_BTN_H,
                new Color(64, 155, 88), "继续游戏");
        drawStyledButton(g, PAUSE_RESTART_BTN_X, PAUSE_RESTART_BTN_Y, PAUSE_RESTART_BTN_W, PAUSE_RESTART_BTN_H,
                new Color(243, 156, 18), "重新开始");
        drawStyledButton(g, PAUSE_HOME_BTN_X, PAUSE_HOME_BTN_Y, PAUSE_HOME_BTN_W, PAUSE_HOME_BTN_H,
                new Color(52, 152, 219), "返回首页");
        drawStyledButton(g, PAUSE_EXIT_BTN_X, PAUSE_EXIT_BTN_Y, PAUSE_EXIT_BTN_W, PAUSE_EXIT_BTN_H,
                new Color(86, 86, 86), "退出游戏");
    }

    private void drawWinOverlay(Graphics2D g) {
        if (isFireIceMode()) {
            g.setColor(new Color(0, 0, 0, 120));
            g.fillRect(0, 0, getW(), getH());
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Serif", Font.BOLD, 44));
            g.drawString("恭喜通关！", 480, 300);
            g.setColor(Color.WHITE);
            g.drawString("森林冰火人成功获得道具", 420, 360);
            if (winMessage != null && !winMessage.isEmpty()) {
                g.setFont(new Font("SansSerif", Font.PLAIN, 22));
                g.drawString(winMessage, 480, 420);
            }
        } else {
            g.setColor(new Color(0, 0, 0, 120));
            g.fillRoundRect(BOARD_X, BOARD_Y, BOARD_W, BOARD_H, 28, 28);
            g.setColor(new Color(241, 196, 15));
            g.setFont(new Font("SansSerif", Font.BOLD, 44));
            g.drawString("YOU WIN", 510, 300);
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.PLAIN, 20));
            g.drawString("你已经击败了全部 20 只普通僵尸！", 470, 340);
            if (winMessage != null && !winMessage.isEmpty()) {
                g.setFont(new Font("SansSerif", Font.PLAIN, 18));
                g.drawString(winMessage, 520, 390);
            }
        }
        drawStyledButton(g, RESTART_BTN_X, RESTART_BTN_Y, RESTART_BTN_W, RESTART_BTN_H,
                new Color(46, 204, 113), "重新开始");
        drawStyledButton(g, HOME_BTN_X, HOME_BTN_Y, HOME_BTN_W, HOME_BTN_H,
                new Color(52, 152, 219), "返回首页");
    }

    private void drawGameOverOverlay(Graphics2D g) {
        if (defeatImage != null) {
            g.drawImage(defeatImage, 0, 0, getW(), getH(), null);
            g.setColor(new Color(0, 0, 0, 70));
            g.fillRect(0, 0, getW(), getH());
            drawButton(g, RESTART_BTN_X, RESTART_BTN_Y, RESTART_BTN_W, RESTART_BTN_H,
                    new Color(243, 156, 18), "重新开始");
            drawButton(g, HOME_BTN_X, HOME_BTN_Y, HOME_BTN_W, HOME_BTN_H,
                    new Color(52, 152, 219), "返回首页");
            return;
        }
        g.setColor(new Color(0, 0, 0, 130));
        g.fillRoundRect(BOARD_X, BOARD_Y, BOARD_W, BOARD_H, 28, 28);

        g.setColor(new Color(231, 76, 60));
        g.setFont(new Font("SansSerif", Font.BOLD, 44));
        g.drawString("GAME OVER", 475, 300);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.PLAIN, 18));
        g.drawString("僵尸走到了最左边，你的草坪被攻破啦", 460, 340);

        drawStyledButton(g, RESTART_BTN_X, RESTART_BTN_Y, RESTART_BTN_W, RESTART_BTN_H,
                new Color(243, 156, 18), "重新开始");
        drawStyledButton(g, HOME_BTN_X, HOME_BTN_Y, HOME_BTN_W, HOME_BTN_H,
                new Color(52, 152, 219), "返回首页");
    }

    private void drawStyledButton(Graphics2D g, int x, int y, int w, int h, Color baseColor, String text) {
        g.setColor(new Color(0, 0, 0, 60));
        g.fillRoundRect(x + 3, y + 3, w, h, 24, 24);
        g.setColor(new Color(0, 0, 0, 30));
        g.fillRoundRect(x + 5, y + 5, w, h, 24, 24);

        java.awt.GradientPaint grad = new java.awt.GradientPaint(x, y, baseColor.brighter(),
                x, y + h, baseColor.darker());
        g.setPaint(grad);
        g.fillRoundRect(x, y, w, h, 24, 24);

        g.setColor(Color.WHITE);
        int fontSize = h >= 50 ? 22 : 18;
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int textX = x + (w - fm.stringWidth(text)) / 2;
        int textY = y + (h + fm.getAscent()) / 2 - 2;
        g.drawString(text, textX, textY);
    }

    @Deprecated
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
        if (stage == GameStage.OPENING_STORY) {
            updateOpeningStory();
            return;
        }
        if (stage == GameStage.ENDING) {
            updateEnding();
            return;
        }
        if (stage == GameStage.LEVEL_TIP || stage == GameStage.LEVEL_SELECT) {
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
        if (stage == GameStage.PLAYING && isFireIceMode()) {
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
        if (!contraBossSpawned) {
            spawnContraBoss();
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
        if (stage == GameStage.OPENING_STORY) {
            skipOpeningStory();
            return;
        }
        if (stage == GameStage.LEVEL_TIP) {
            int cardW = 700;
            int cardH = 500;
            int cardX = (getW() - cardW) / 2;
            int cardY = (getH() - cardH) / 2;
            int btnW = 180;
            int btnH = 50;
            int btnX = cardX + (cardW - btnW) / 2;
            int btnY = cardY + cardH - 70;
            if (mouseX >= btnX && mouseX <= btnX + btnW && mouseY >= btnY && mouseY <= btnY + btnH) {
                levelTipShown.put(selectedLevel, true);
                // 根据关卡进入不同流程
                if (selectedLevel == 1) {
                    enterPrepareStage(1);
                } else {
                    startBattle(); // 第二、三关直接开始战斗
                }
            }
            return;
        }
        if (stage == GameStage.ENDING) {
            // 点击任意位置直接结束结局返回首页
            returnToHome();
            return;
        }

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
                if (!levelTipShown.getOrDefault(1, false)) {
                    startLevelTip(1);
                } else {
                    enterPrepareStage(1);
                }
                return;
            }
            if (isInLevel2Button(mouseX, mouseY) && unlockedLevel >= 2) {
                if (!levelTipShown.getOrDefault(2, false)) {
                    startLevelTip(2);
                } else {
                    selectedLevel = 2;
                    startBattle();
                }
                return;
            }
            if (isInLevel3Button(mouseX, mouseY) && unlockedLevel >= 3) {
                if (!levelTipShown.getOrDefault(3, false)) {
                    startLevelTip(3);
                } else {
                    selectedLevel = 3;
                    startBattle();
                }
                return;
            }
            return;
        }

        if (stage == GameStage.PLAYING && isFireIceMode()) {
            if (gameWin || gameOver) {
                if (isInRestartButton(mouseX, mouseY)) {
                    restartGame();
                    return;
                }
                if (isInHomeButton(mouseX, mouseY)) {
                    returnToHome();
                    return;
                }
                return;
            }
            if (isInMenuButton(mouseX, mouseY)) {
                pauseGame();
            }
            // 冰火人模式暂停后也要响应四个按钮
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
        Plant plant = selectedPlantType.create(row, col, getCellX(col), getCellY(row), cellW, cellH);
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
        Zombie zombie = createLevel1Zombie(row, x, y);
        ElementManager.getManager().addElement(zombie, GameElement.ZOMBIE);
        spawnedZombies++;
    }

    private void spawnGunZombie() {
        int[] spawnPoint = chooseContraZombieSpawnPoint();
        GunZombie zombie = createLevel3Zombie(spawnPoint[0], spawnPoint[1] - 82);
        ElementManager.getManager().addElement(zombie, GameElement.ZOMBIE);
        spawnedZombies++;
    }

    private Zombie createLevel1Zombie(int row, int x, int y) {
        int spawnOrder = spawnedZombies + 1;
        int remainingSlots = getMaxZombies() - spawnedZombies;

        if (shouldSpawnLevel1Bucket(spawnOrder, remainingSlots)) {
            lawnBucketSpawned++;
            return new BucketZombie(row, x, y);
        }
        if (shouldSpawnLevel1Roadblock(spawnOrder, remainingSlots)) {
            lawnRoadblockSpawned++;
            return new RoadblockZombie(row, x, y);
        }
        return new Zombie(row, x, y);
    }

    private GunZombie createLevel3Zombie(int x, int y) {
        int spawnOrder = spawnedZombies + 1;
        int remainingSlots = getMaxZombies() - spawnedZombies;
        if (shouldSpawnLevel3Roadblock(spawnOrder, remainingSlots)) {
            contraRoadblockSpawned++;
            return new RoadblockGunZombie(x, y);
        }
        return new BasicGunZombie(x, y);
    }

    private boolean shouldSpawnLevel1Roadblock(int spawnOrder, int remainingSlots) {
        if (spawnOrder <= LEVEL1_ROADBLOCK_UNLOCK_AFTER || lawnRoadblockSpawned >= LEVEL1_ROADBLOCK_MAX) {
            return false;
        }
        int remainingRoadblocks = LEVEL1_ROADBLOCK_MAX - lawnRoadblockSpawned;
        return shouldSpawnSpecialZombie(remainingRoadblocks, remainingSlots, LEVEL1_ROADBLOCK_CHANCE);
    }

    private boolean shouldSpawnLevel1Bucket(int spawnOrder, int remainingSlots) {
        if (spawnOrder <= LEVEL1_BUCKET_UNLOCK_AFTER || lawnBucketSpawned >= LEVEL1_BUCKET_MAX) {
            return false;
        }
        int remainingBuckets = LEVEL1_BUCKET_MAX - lawnBucketSpawned;
        return shouldSpawnSpecialZombie(remainingBuckets, remainingSlots, LEVEL1_BUCKET_CHANCE);
    }

    private boolean shouldSpawnLevel3Roadblock(int spawnOrder, int remainingSlots) {
        if (contraRoadblockSpawned >= LEVEL3_ROADBLOCK_MAX) {
            return false;
        }
        if (spawnOrder <= 1) {
            return false;
        }
        int remainingRoadblocks = LEVEL3_ROADBLOCK_MAX - contraRoadblockSpawned;
        return shouldSpawnSpecialZombie(remainingRoadblocks, remainingSlots, 0.22);
    }

    private boolean shouldSpawnSpecialZombie(int remainingSpecials, int remainingSlots, double chance) {
        if (remainingSpecials <= 0 || remainingSlots <= 0) {
            return false;
        }
        if (remainingSpecials >= remainingSlots) {
            return true;
        }
        return random.nextDouble() < chance;
    }

    private void spawnContraBoss() {
        int x = CONTRA_BOSS_SOURCE_X * CONTRA_SCALE;
        int surfaceY = getContraSurfaceBelow(x + 42, x + CONTRA_BOSS_W - 42, CONTRA_TOP_Y);
        int y = (surfaceY == -1 ? CONTRA_GROUND_Y : surfaceY) - CONTRA_BOSS_H;
        ElementManager.getManager().addElement(new BossZombie(x, y), GameElement.ZOMBIE);
        contraBossSpawned = true;
        contraBossDefeated = false;
    }

    private int[] chooseContraZombieSpawnPoint() {
        int spawnableWidth = Math.max(1, CONTRA_MAP_W - CONTRA_SPAWN_START_X - CONTRA_SPAWN_END_MARGIN);
        double progress = getMaxZombies() <= 1 ? 0.0 : (double) spawnedZombies / (getMaxZombies() - 1);
        int baseX = CONTRA_SPAWN_START_X + (int) Math.round(spawnableWidth * progress);
        int jitter = random.nextInt(520) - 260;
        int minX = Math.max(CONTRA_SPAWN_START_X, getContraCameraX() + getW() / 2);
        int maxX = CONTRA_MAP_W - CONTRA_SPAWN_END_MARGIN;
        int candidateX = clamp(baseX + jitter, minX, maxX);
        for (int i = 0; i < 28; i++) {
            List<Integer> surfaces = getContraSurfacesAt(candidateX + 8, candidateX + 48);
            int surfaceY = chooseContraSpawnSurface(surfaces);
            if (surfaceY != -1) {
                return new int[] { candidateX, surfaceY };
            }
            candidateX = clamp(candidateX + 180 + random.nextInt(260), minX, maxX);
        }
        int surfaceY = getContraSurfaceBelow(candidateX + 8, candidateX + 48, CONTRA_TOP_Y);
        return new int[] { candidateX, surfaceY == -1 ? CONTRA_GROUND_Y : surfaceY };
    }

    private int chooseContraSpawnSurface(List<Integer> surfaces) {
        if (surfaces.isEmpty())
            return -1;
        int spawnPattern = spawnedZombies % 5;
        int preferred = chooseSurfaceInSourceYRange(surfaces, spawnPattern == 0 || spawnPattern == 2 ? 154 : 118,
                spawnPattern == 0 || spawnPattern == 2 ? 218 : 154);
        if (preferred != -1)
            return preferred;
        return surfaces.get(random.nextInt(surfaces.size()));
    }

    private int chooseSurfaceInSourceYRange(List<Integer> surfaces, int minSourceY, int maxSourceY) {
        List<Integer> candidates = new ArrayList<>();
        for (Integer surfaceY : surfaces) {
            int srcY = (surfaceY - CONTRA_MAP_Y) / CONTRA_SCALE;
            if (srcY >= minSourceY && srcY <= maxSourceY)
                candidates.add(surfaceY);
        }
        if (candidates.isEmpty())
            return -1;
        return candidates.get(random.nextInt(candidates.size()));
    }

    private List<Integer> getContraSurfacesAt(int leftWorldX, int rightWorldX) {
        List<Integer> surfaces = new ArrayList<>();
        int lastSurfaceY = -100;
        for (int worldY = CONTRA_TOP_Y; worldY <= CONTRA_SURFACE_SEARCH_BOTTOM; worldY++) {
            if (hasContraSurfaceAt(leftWorldX, rightWorldX, worldY) && worldY - lastSurfaceY > 18) {
                surfaces.add(worldY);
                lastSurfaceY = worldY;
            }
        }
        return surfaces;
    }

    private void spawnSkySun() {
        int col = random.nextInt(cols);
        int x = getCellX(col) + (cellW - 36) / 2;
        int targetY = getCellY(random.nextInt(rows)) + 10;
        ElementManager.getManager().addElement(
                Sun.createFallingSun(x, STATUS_BAR_Y + STATUS_BAR_H + 8, targetY, SKY_SUN_VALUE), GameElement.SUN);
    }

    public void enterLevelSelectStage() {
        if (!storyPlayed) {
            startOpeningStory();
            return;
        }
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
        lawnRoadblockSpawned = 0;
        lawnBucketSpawned = 0;
        contraRoadblockSpawned = 0;
        zombieSpawnCounter = 0;
        contraBossSpawned = false;
        contraBossDefeated = false;
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
        if (selectedLevel == 2) {
            currentSun = 0;
            introCameraOffset = 0;
            battleIntroPlaying = false;
            GameLoad.MapLoad(1);
            ElementManager.getManager().resetTipFlags();
            GameLoad.loadFireWaterMan();
        } else if (selectedLevel == 3) {
            currentSun = 0;
            contraCameraX = 0;
            introCameraOffset = 0;
            battleIntroPlaying = false;
            int playerX = 110;
            int playerSurfaceY = getContraSurfaceBelow(playerX + 8, playerX + 60, CONTRA_TOP_Y);
            int playerY = playerSurfaceY == -1 ? CONTRA_GROUND_Y - 76 : playerSurfaceY - 76;
            contraPlayer = new ContraPlayer(playerX, playerY);
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
        if (selectedLevel == 1) {
            enterPrepareStage(1);
            return;
        }
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
        lawnRoadblockSpawned = 0;
        lawnBucketSpawned = 0;
        contraRoadblockSpawned = 0;
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
        contraBossSpawned = false;
        contraBossDefeated = false;
        // 重置胜利消息
        winMessage = null;
        // 注意：不清空 storyPlayed 和 levelTipShown，保持玩家进度
    }

    public void triggerGameOver() {
        if (stage != GameStage.PLAYING || !gameStarted || gameWin)
            return;
        gameOver = true;
        paused = false;
        shovelMode = false;
    }

    public void triggerGameWin() {
        if (stage != GameStage.PLAYING || !gameStarted || gameOver)
            return;
        gameWin = true;
        paused = false;
        shovelMode = false;
        int nextLevel = selectedLevel + 1;
        if (nextLevel <= 3 && unlockedLevel < nextLevel) {
            unlockedLevel = nextLevel;
        }
        // 设置胜利台词
        if (selectedLevel >= 1 && selectedLevel <= 3 && winMessages[selectedLevel - 1] != null) {
            winMessage = winMessages[selectedLevel - 1];
        } else {
            winMessage = null;
        }
        // 第三关胜利后播放结局
        if (selectedLevel == 3) {
            startEnding();
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
        em.getElementsByKey(GameElement.FIRE_MAN).clear();
        em.getElementsByKey(GameElement.WATER_MAN).clear();
        em.getElementsByKey(GameElement.FIRE_DOOR).clear();
        em.getElementsByKey(GameElement.WATER_DOOR).clear();
        em.getElementsByKey(GameElement.FIRE_DIAMOND).clear();
        em.getElementsByKey(GameElement.WATER_DIAMOND).clear();
        em.getElementsByKey(GameElement.FIRE_TERRAIN).clear();
        em.getElementsByKey(GameElement.WATER_TERRAIN).clear();
        em.getElementsByKey(GameElement.TRAP_TERRAIN).clear();
        em.getElementsByKey(GameElement.MAPS).clear();
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

    public boolean isInLevel2Button(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, LEVEL2_X, LEVEL_CARD_Y, LEVEL_CARD_W, LEVEL_CARD_H);
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

    public boolean isInDoublePeaCard(int mouseX, int mouseY) {
        return inRect(mouseX, mouseY, DOUBLE_PEA_CARD_X, DOUBLE_PEA_CARD_Y, CARD_W, CARD_H);
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
        selectPlant(PlantType.PEA_SHOOTER);
    }

    public void selectSunflower() {
        selectPlant(PlantType.SUNFLOWER);
    }

    public void selectDoublePeaShooter() {
        selectPlant(PlantType.DOUBLE_PEA_SHOOTER);
    }

    private void selectPlant(PlantType plantType) {
        selectedPlantType = plantType;
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
        if (!pressed)
            return;
        if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_P) {
            if (paused)
                resumeGame();
            else
                pauseGame();
            return;
        }
        if (stage == GameStage.PLAYING && isFireIceMode() && !paused && !gameOver && !gameWin) {
            List<ElementObj> fireMen = em.getElementsByKey(GameElement.FIRE_MAN);
            List<ElementObj> waterMen = em.getElementsByKey(GameElement.WATER_MAN);
            for (ElementObj obj : fireMen)
                obj.keyClick(pressed, key);
            for (ElementObj obj : waterMen)
                obj.keyClick(pressed, key);
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
        return selectedLevel == 3 ? CONTRA_MAX_ZOMBIES : MAX_ZOMBIES;
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

    public int getDoublePeaShooterCost() {
        return PlantType.DOUBLE_PEA_SHOOTER.getCost();
    }

    public PlantType getSelectedPlantType() {
        return selectedPlantType;
    }

    public int getSelectedPlantCost() {
        return selectedPlantType.getCost();
    }

    public void addSun(int sun) {
        currentSun += sun;
    }

    public Plant getPlant(int row, int col) {
        if (!isValidCell(row, col))
            return null;
        Plant plant = plantGrid[row][col];
        return (plant != null && plant.isLive()) ? plant : null;
    }

    public void removePlant(int row, int col) {
        if (isValidCell(row, col))
            plantGrid[row][col] = null;
    }

    public void cleanupPlantSlots() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Plant plant = plantGrid[row][col];
                if (plant != null && !plant.isLive())
                    plantGrid[row][col] = null;
            }
        }
    }

    public boolean hasZombieInRow(int row) {
        List<ElementObj> zombies = ElementManager.getManager().getElementsByKey(GameElement.ZOMBIE);
        for (ElementObj obj : zombies) {
            if (obj.isLive() && obj instanceof LaneEnemy && ((LaneEnemy) obj).getRow() == row)
                return true;
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
            if (mouseY >= cellY && mouseY <= cellY + cellH)
                return row;
        }
        return -1;
    }

    public int getColByX(int mouseX) {
        for (int col = 0; col < cols; col++) {
            int cellX = getCellX(col);
            if (mouseX >= cellX && mouseX <= cellX + cellW)
                return col;
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
        if (row < 0)
            row = 0;
        else if (row >= laneTargets.length)
            row = laneTargets.length - 1;
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

    public int getDoublePeaCardX() {
        return DOUBLE_PEA_CARD_X;
    }

    public int getDoublePeaCardY() {
        return DOUBLE_PEA_CARD_Y;
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
        if (isContraMode())
            return contraCameraX;
        if (stage == GameStage.PREPARE)
            return prepCameraOffset;
        if (stage == GameStage.PLAYING && battleIntroPlaying)
            return introCameraOffset;
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
        return 0;
    }

    public boolean isContraPlayerInExit() {
        if (contraPlayer == null || !contraPlayer.isLive())
            return false;
        int centerX = contraPlayer.getX() + contraPlayer.getW() / 2;
        int centerY = contraPlayer.getY() + contraPlayer.getH() / 2;
        return centerX >= CONTRA_EXIT_LEFT_X && centerY >= CONTRA_EXIT_TOP_Y && centerY <= CONTRA_EXIT_BOTTOM_Y;
    }

    public boolean isContraBossDefeated() {
        return contraBossDefeated;
    }

    public void markContraBossDefeated() {
        contraBossDefeated = true;
    }

    public int getContraSurfaceBelow(int leftWorldX, int rightWorldX, int fromWorldY) {
        return getContraSurfaceBetween(leftWorldX, rightWorldX, fromWorldY, CONTRA_SURFACE_SEARCH_BOTTOM);
    }

    public int getContraSurfaceBetween(int leftWorldX, int rightWorldX, int fromWorldY, int toWorldY) {
        if (contraStageImage == null)
            return Math.max(CONTRA_TOP_Y, Math.min(CONTRA_GROUND_Y, toWorldY));
        int startY = Math.max(CONTRA_MAP_Y, Math.min(fromWorldY, toWorldY));
        int endY = Math.min(CONTRA_SURFACE_SEARCH_BOTTOM, Math.max(fromWorldY, toWorldY));
        for (int worldY = startY; worldY <= endY; worldY++) {
            if (hasContraSurfaceAt(leftWorldX, rightWorldX, worldY))
                return worldY;
        }
        return -1;
    }

    public boolean isOnContraSurface(int leftWorldX, int rightWorldX, int footWorldY) {
        int surfaceY = getContraSurfaceBetween(leftWorldX, rightWorldX, footWorldY - 7, footWorldY + 7);
        return surfaceY != -1 && Math.abs(surfaceY - footWorldY) <= 7;
    }

    private boolean hasContraSurfaceAt(int leftWorldX, int rightWorldX, int worldY) {
        int centerX = leftWorldX + Math.max(1, (rightWorldX - leftWorldX) / 2);
        int[] samples = { leftWorldX + 10, centerX, rightWorldX - 10 };
        int matches = 0;
        for (int worldX : samples)
            if (isContraSurfacePixel(worldX, worldY))
                matches++;
        if (matches >= 2)
            return true;
        return hasContraSurfaceNear(centerX, worldY) ||
                hasContraSurfaceNear(leftWorldX + 4, worldY) ||
                hasContraSurfaceNear(rightWorldX - 4, worldY);
    }

    private boolean hasContraSurfaceNear(int worldX, int worldY) {
        for (int offset = -CONTRA_EDGE_GRACE; offset <= CONTRA_EDGE_GRACE; offset += 6)
            if (isContraSurfacePixel(worldX + offset, worldY))
                return true;
        return false;
    }

    private boolean isContraSurfacePixel(int worldX, int worldY) {
        int srcX = worldX / CONTRA_SCALE;
        int srcY = (worldY - CONTRA_MAP_Y) / CONTRA_SCALE;
        if (srcX < 0 || srcX >= contraStageImage.getWidth() || srcY <= 0 || srcY >= contraStageImage.getHeight())
            return false;
        if (!isContraStandableColor(contraStageImage.getRGB(srcX, srcY), srcY))
            return false;
        if (isContraStandableColor(contraStageImage.getRGB(srcX, srcY - 1), srcY - 1))
            return false;
        return hasFlatContraSurfaceRun(srcX, srcY);
    }

    private boolean hasFlatContraSurfaceRun(int srcX, int srcY) {
        int count = 0;
        int fromX = Math.max(0, srcX - 10);
        int toX = Math.min(contraStageImage.getWidth() - 1, srcX + 10);
        for (int x = fromX; x <= toX; x++)
            if (isContraStandableColor(contraStageImage.getRGB(x, srcY), srcY))
                count++;
        return count >= 14;
    }

    private boolean isContraStandableColor(int rgb, int srcY) {
        Color color = new Color(rgb, true);
        int r = color.getRed(), g = color.getGreen(), b = color.getBlue();
        return srcY >= 88 && g >= 115 && g > r + 35 && g > b + 35 && r <= 150 && b <= 120;
    }

    public boolean isContraWaterHazard(int leftWorldX, int rightWorldX, int topWorldY, int bottomWorldY) {
        if (contraStageImage == null)
            return false;
        int[] sampleXs = { leftWorldX, leftWorldX + Math.max(1, (rightWorldX - leftWorldX) / 2), rightWorldX };
        int startY = Math.max(CONTRA_MAP_Y, topWorldY);
        int endY = Math.min(CONTRA_MAP_Y + CONTRA_MAP_H - 1, bottomWorldY);
        for (int worldY = startY; worldY <= endY; worldY += 6)
            for (int worldX : sampleXs)
                if (isContraWaterPixel(worldX, worldY))
                    return true;
        return false;
    }

    private boolean isContraWaterPixel(int worldX, int worldY) {
        int srcX = worldX / CONTRA_SCALE;
        int srcY = (worldY - CONTRA_MAP_Y) / CONTRA_SCALE;
        if (srcX < 0 || srcX >= contraStageImage.getWidth() || srcY < 0 || srcY >= contraStageImage.getHeight())
            return false;
        Color color = new Color(contraStageImage.getRGB(srcX, srcY), true);
        int r = color.getRed(), g = color.getGreen(), b = color.getBlue();
        return srcY >= 128 && b >= 145 && g >= 70 && r <= 85;
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
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

    public boolean isFireIceMode() {
        return stage == GameStage.PLAYING && selectedLevel == 2;
    }

    public ContraPlayer getContraPlayer() {
        return contraPlayer;
    }

    public int getContraPlayerHealth() {
        return contraPlayer == null ? 0 : contraPlayer.getHealth();
    }

    public boolean isContraDoublePeaActive() {
        return contraPlayer != null && contraPlayer.isDoublePeaModeActive();
    }

    public String getContraDoublePeaStatusLabel() {
        if (contraPlayer == null) {
            return "--";
        }
        if (contraPlayer.isDoublePeaModeActive()) {
            double seconds = contraPlayer.getDoublePeaTicksRemaining() * 30.0 / 1000.0;
            return String.format("进行中 %.1fs", seconds);
        }
        if (contraPlayer.isDoublePeaAvailable()) {
            return "按 F 启动";
        }
        return "已结束";
    }

    private enum ZombiePreviewType {
        BASIC, CONE, BUCKET
    }
}
