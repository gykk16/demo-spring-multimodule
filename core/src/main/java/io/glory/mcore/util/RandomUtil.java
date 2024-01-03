package io.glory.mcore.util;

import java.util.Random;

public class RandomUtil {

    private static final Random RANDOM = new Random();

    private RandomUtil() {
    }

    /**
     * Returns 0 or 1 randomly.
     */
    public static int getZeroOrOne() {
        return RANDOM.nextInt(2);
    }

    /**
     * Returns 0 or 1 or 2 randomly.
     */

    public static int getZeroOrOneOrTwo() {
        return RANDOM.nextInt(3);
    }

    /**
     * Returns random integer between min and max.
     *
     * @param min min number, inclusive
     * @param max max number, inclusive
     */
    public static int getRandomIntInRange(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    /**
     * Returns random integer of length.
     *
     * @param length length of random integer
     */
    public static int getRandomIntOfLength(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be a positive integer.");
        }

        int minValue = (int)Math.pow(10, length - 1);
        int maxValue = (int)Math.pow(10, length) - 1;
        int range = maxValue - minValue;

        return RANDOM.nextInt(range + 1) + minValue;
    }

}
