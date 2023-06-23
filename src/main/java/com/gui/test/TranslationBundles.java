package com.gui.test;

import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationBundles {
    private static Locale currentLanguage;
    private static ResourceBundle currentBundle;

    static {
        setLanguage(Locale.ENGLISH); // Default language
    }

    public static void setLanguage(Locale language) {
        currentLanguage = language;
        currentBundle = ResourceBundle.getBundle("bundels.LabelBundle", currentLanguage);
    }

    public static ResourceBundle getBundle() {
        return currentBundle;
    }
}
