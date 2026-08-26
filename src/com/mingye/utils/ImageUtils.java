package com.mingye.utils;

import com.mingye.constants.PieceConstants;
import com.mingye.model.Piece;
import com.mingye.service.GameService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImageUtils {

    public static Map<String, String> imagePathMap;

    /**
     * 图片文件的基本路径
     */
    private static final String BASE_FILE_PATH;

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
        try {
            String imagePath = "river.png";
            BufferedImage image = ImageIO.read(new File(BASE_FILE_PATH + imagePath));
            return image;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static BufferedImage loadTrapImage() {
        try {
            String imagePath = "trap.png";
            BufferedImage image = ImageIO.read(new File(BASE_FILE_PATH + imagePath));
            return image;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static BufferedImage loadCaveImage() {
        try {
            String imagePath = "cave.png";
            BufferedImage image = ImageIO.read(new File(BASE_FILE_PATH + imagePath));
            return image;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 加载棋子的图片
     *
     * @param piece
     * @return
     */
    public static BufferedImage loadPieceImageByValue(Piece piece) {
        String mapKey = piece.getColor() + piece.getPieceName();
        try {
            if (imagePathMap.containsKey(mapKey)) {
                String imagePath = imagePathMap.get(mapKey);
                BufferedImage image = ImageIO.read(new File(BASE_FILE_PATH + imagePath));
                return image;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String getBaseFilePath() {
        // 获取当前工作目录
        String projectRoot = System.getProperty("user.dir");
        String filePath = projectRoot + "/images";
        return filePath + File.separator;
    }

}
