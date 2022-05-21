package org.youngmonkeys.xmobitea.eun.common.service;

import lombok.var;

import java.util.Random;

public final class CustomRandom {
    private static final Random rd = new Random();

    public static int range(int minValue, int maxValue) {
        var value = (int) range((float)minValue, maxValue);
        return value == maxValue ? value - 1 : value;
    }

    public static float range(float minValue, float maxValue) {
        return rd.nextFloat() * (maxValue - minValue) + minValue;
    }
}