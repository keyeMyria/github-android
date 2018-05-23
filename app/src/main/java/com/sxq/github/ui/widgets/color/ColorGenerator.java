package com.sxq.github.ui.widgets.color;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;

import java.util.Arrays;
import java.util.List;

public class ColorGenerator {

    private static ColorGenerator MATERIAL;
    private static ColorGenerator MATERIAL_DARK;

    static {
        MATERIAL = create(Arrays.asList(
                0xff1976d2,
                0xff00838f,
                0xff512da8,
                0xff2e7d32,
                0xff283593,
                0xff01579b,
                0xffc51162,
                0xff6a1b9a,
                0xffd50000,
                0xff00695c
        ));
        MATERIAL_DARK = create(Arrays.asList(
                0xffffc107,
                0xffffc400,
                0xff2196f3,
                0xff2979ff,
                0xffa1887f,
                0xff4dd0e1,
                0xff00acc1,
                0xffe64a19,
                0xff9575cd,
                0xff66bb6a
        ));
    }

    private final List<Integer> colors;

    private static ColorGenerator create(List<Integer> colorList) {
        return new ColorGenerator(colorList);
    }

    private ColorGenerator(List<Integer> colorList) {
        colors = colorList;
    }

    private int getColor(@Nullable Object key) {
        key = Objects.toString(key, "default");
        return colors.get(Math.abs(key.hashCode()) % colors.size());
    }

    @ColorInt
    public static int getColor(@NonNull Context context, @Nullable Object object) {
        return MATERIAL.getColor(object);
    }
}