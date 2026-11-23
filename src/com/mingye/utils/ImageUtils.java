package com.mingye.utils;

import com.mingye.constants.PieceConstants;
import com.mingye.model.Piece;
import com.mingye.service.GameService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImageUtils {

    public static Map<String, String> imagePathMap;

    /**
     * 图片文件的基本路径
     */
    private static final String BASE_FILE_PATH;

    /**
     * 图片缓存：key 为图片文件名。
     * 重绘时直接从内存取，避免每次绘制都读磁盘。
     */
    private static final Map<String, BufferedImage> IMAGE_CACHE = new HashMap<>();

    static {
        imagePathMap = new LinkedHashMap<>();

        String redPiece = GameService.RED_PIECE;
        imagePathMap.put(redPiece + PieceConstants.MOUSE, "red_mouse.png");
        imagePathMap.put(redPiece + PieceConstants.CAT, "red_cat.png");
        imagePathMap.put(redPiece + PieceConstants.DOG, "red_dog.png");
        imagePathMap.put(redPiece + PieceConstants.WOLF, "red_wolf.png");
        imagePathMap.put(redPiece + PieceConstants.LEOPARD, "red_leopard.png");
        imagePathMap.put(redPiece + PieceConstants.TIGER, "red_tiger.png");
        imagePathMap.put(redPiece + PieceConstants.LION, "red_lion.png");
        imagePathMap.put(redPiece + PieceConstants.ELEPHANT, "red_elephant.png");

        String blackPiece = GameService.BLACK_PIECE;
        imagePathMap.put(blackPiece + PieceConstants.MOUSE, "black_cannon.png");
        imagePathMap.put(blackPiece + PieceConstants.MOUSE, "black_mouse.png");
        imagePathMap.put(blackPiece + PieceConstants.CAT, "black_cat.png");
        imagePathMap.put(blackPiece + PieceConstants.DOG, "black_dog.png");
        imagePathMap.put(blackPiece + PieceConstants.WOLF, "black_wolf.png");
        imagePathMap.put(blackPiece + PieceConstants.LEOPARD, "black_leopard.png");
        imagePathMap.put(blackPiece + PieceConstants.TIGER, "black_tiger.png");
        imagePathMap.put(blackPiece + PieceConstants.LION, "black_lion.png");
        imagePathMap.put(blackPiece + PieceConstants.ELEPHANT, "black_elephant.png");

        BASE_FILE_PATH = getBaseFilePath();
    }

    public static BufferedImage loadRiverImage() {
        return loadCachedImage("river.png");
    }

    public static BufferedImage loadTrapImage() {
        return loadCachedImage("trap.png");
    }

    public static BufferedImage loadCaveImage() {
        return loadCachedImage("cave.png");
    }

    /**
     * 加载棋子的图片
     *
     * @param piece 棋子
     * @return 对应图片，未配置时返回 null
     */
    public static BufferedImage loadPieceImageByValue(Piece piece) {
        String mapKey = piece.getColor() + piece.getPieceName();
        String imagePath = imagePathMap.get(mapKey);
        if (imagePath == null) {
            return null;
        }
        return loadCachedImage(imagePath);
    }

    /**
     * 加载图片并缓存。
     * 同一张图片只会从磁盘读取一次，后续重绘直接复用内存中的 BufferedImage。
     *
     * @param imagePath 图片文件名（不含目录）
     * @return 图片对象
     */
    private static BufferedImage loadCachedImage(String imagePath) {
        BufferedImage cached = IMAGE_CACHE.get(imagePath);
        if (cached != null) {
            return cached;
        }
        try {
            BufferedImage image = ImageIO.read(new File(BASE_FILE_PATH + imagePath));
            if (image == null) {
                throw new RuntimeException("图片无法解析: " + BASE_FILE_PATH + imagePath);
            }
            IMAGE_CACHE.put(imagePath, image);
            return image;
        } catch (IOException e) {
            throw new RuntimeException("加载图片失败: " + BASE_FILE_PATH + imagePath, e);
        }
    }

    private static String getBaseFilePath() {
        // 获取当前工作目录
        String projectRoot = System.getProperty("user.dir");
        String filePath = projectRoot + "/images";
        return filePath + File.separator;
    }

}
