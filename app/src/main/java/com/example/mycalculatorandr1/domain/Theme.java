package com.example.mycalculatorandr1.domain;

import androidx.annotation.StyleRes;

import com.example.mycalculatorandr1.R;

public enum Theme {

    DEFAULT(R.style.Theme_CalcTheme, R.string.Default_Theme, "default"),
    ORANGE(R.style.Orange, R.string.Orange_Theme, "orange"),
    PURPLE(R.style.Purple, R.string.Purple_Theme, "purple");

    @StyleRes
    private final int theme;

    @StyleRes
    private final int name;

    private String key;

    Theme(int theme, int name, String key) {
        this.theme = theme;
        this.name = name;
        this.key = key;
    }

    public int getTheme() {
        return theme;
    }

    public int getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
