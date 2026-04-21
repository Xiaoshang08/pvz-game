package com.tedu.util;

import java.awt.Graphics;
import java.awt.Image;
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
        if (image == null) {
            return;
        }
        g.drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
    }
}
