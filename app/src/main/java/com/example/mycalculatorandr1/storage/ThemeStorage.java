package com.example.mycalculatorandr1.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mycalculatorandr1.domain.Theme;

public class ThemeStorage {

    private static final String ARG_SAVED_THEME = "ARG_SAVED_THEME";

    private final Context context;

    public ThemeStorage(Context context) {
        this.context = context;
    }

    public void saveTheme(Theme theme) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Themes", Context.MODE_PRIVATE);

        sharedPreferences.edit()
                .putString(ARG_SAVED_THEME, theme.getKey())
                .apply();
    }

    public Theme getSavedTheme() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Themes", Context.MODE_PRIVATE);

        String key = sharedPreferences.getString(ARG_SAVED_THEME, Theme.DEFAULT.getKey());

        for (Theme theme : Theme.values()) {
            if (key.equals(theme.getKey())) {
                return theme;
            }
        }

        return Theme.DEFAULT;
    }
}
