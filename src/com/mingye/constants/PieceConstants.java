package com.mingye.constants;

import java.util.HashMap;
import java.util.Map;

public class PieceConstants {

    /**
     * 鼠
     */
    public static final String MOUSE = "mouse";

    /**
     * 猫
     */
    public static final String CAT = "cat";

    /**
     *  狗
     */
    public static final String DOG = "dog";

    /**
     * 狼
     */
    public static final String WOLF = "wolf";

    /**
     * 豹
     */
    public static final String LEOPARD = "leopard";

    /**
     * 虎
     */
    public static final String TIGER = "tiger";

    /**
     * 狮
     */
    public static final String LION = "lion";

    /**
     * 象
     */
    public static final String ELEPHANT = "elephant";


    /**
     * 无战斗力（进入敌方陷阱时）
     */
    public static final int NO_POWER = 0;

    /**
     * 最低战斗力 - 鼠
     */
    public static final int MIN_ANIMAL_POWER = 10;

    /**
     * 最高战斗力 - 象
     */
    public static final int MAX_ANIMAL_POWER = 100;

    private static Map<String, Integer> animalPowerMap;

    static {
        animalPowerMap = new HashMap<>();
        animalPowerMap.put(MOUSE, MIN_ANIMAL_POWER);
        animalPowerMap.put(CAT, MIN_ANIMAL_POWER + 10);
        animalPowerMap.put(DOG, MIN_ANIMAL_POWER + 20);
        animalPowerMap.put(WOLF, MIN_ANIMAL_POWER + 30);
        animalPowerMap.put(LEOPARD, MIN_ANIMAL_POWER + 40);
        animalPowerMap.put(TIGER, MIN_ANIMAL_POWER + 50);
        animalPowerMap.put(LION, MIN_ANIMAL_POWER + 60);
        animalPowerMap.put(ELEPHANT, MAX_ANIMAL_POWER);
    }

    public static Map<String, Integer> getAnimalPowerMap() {
        return animalPowerMap;
    }
}
