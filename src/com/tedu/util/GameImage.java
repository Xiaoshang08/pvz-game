package com.tedu.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

/**
 * GameImage：简单图片资源加载工具。
 *
 * 设计目标：
 * 1. 优先加载外部图片文件，方便学生自己替换素材；
 * 2. 支持多种常见放置路径，减少“为什么读不到图片”的问题；
 * 3. 自动缓存，避免每一帧都重复读硬盘；
 * 4. 如果图片不存在，则返回 null，让调用方继续使用原先的手绘方案。
 */
public final class GameImage {
    private static final Map<String, BufferedImage> CACHE = new ConcurrentHashMap<>();
    private static final Map<String, BufferedImage> SCALED_CACHE = new ConcurrentHashMap<>();
    private static final BufferedImage EMPTY = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    private GameImage() {}

    public static BufferedImage get(String relativePath) {
        BufferedImage image = CACHE.get(relativePath);
        if (image != null) {
            return image == EMPTY ? null : image;
        }

        BufferedImage loaded = load(relativePath);
        CACHE.put(relativePath, loaded == null ? EMPTY : loaded);
        return loaded;
    }

    private static BufferedImage load(String relativePath) {
        String normalized = relativePath.replace("\\", "/");

        String[] candidates = new String[] {
                normalized,
                "src/" + normalized,
                "assets/" + normalized,
                "src/assets/" + normalized,
                "src/com/tedu/" + normalized,
                "com/tedu/" + normalized
        };

        for (String candidate : candidates) {
            File file = new File(candidate);
            if (file.exists() && file.isFile()) {
                try {
                    return ImageIO.read(file);
                } catch (IOException ignored) {
                }
            }
        }

        ClassLoader classLoader = GameImage.class.getClassLoader();
        for (String candidate : candidates) {
            URL url = classLoader.getResource(candidate);
            if (url != null) {
                try {
                    return ImageIO.read(url);
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }

    public static void draw(Graphics g, BufferedImage image, int x, int y, int w, int h) {
        if (image == null || w <= 0 || h <= 0) {
            return;
        }

        BufferedImage drawImage = getScaledImage(image, w, h);
        g.drawImage(drawImage, x, y, null);
    }

    private static BufferedImage getScaledImage(BufferedImage source, int w, int h) {
        if (source.getWidth() == w && source.getHeight() == h) {
            return source;
        }

        String key = System.identityHashCode(source) + "_" + w + "x" + h;
        BufferedImage cached = SCALED_CACHE.get(key);
        if (cached != null) {
            return cached;
        }

        BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaled.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(source, 0, 0, w, h, null);
        g2.dispose();

        SCALED_CACHE.put(key, scaled);
        return scaled;
    }
}
