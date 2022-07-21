package com.criticalsoftware.cards;

import org.jetbrains.annotations.NotNull;

public class Color {
    public static String intToWeb(int color) {
        return String.format("#%06X", color);
    }

    public static int webToInt(@NotNull String color) throws NumberFormatException {
        if (color.startsWith("#"))
            color = color.substring(1);
        return Integer.parseInt(color, 16);
    }
}
