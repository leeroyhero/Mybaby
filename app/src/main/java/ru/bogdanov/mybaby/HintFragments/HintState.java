package ru.bogdanov.mybaby.HintFragments;

/**
 * Created by Владимир on 17.08.2016.
 */
public class HintState {
    private static String SUBTITLE;
    private static String TEXT;

    public static String getTEXT() {
        return TEXT;
    }

    public static void setTEXT(String TEXT) {
        HintState.TEXT = TEXT;
    }

    public static String getSUBTITLE() {
        return SUBTITLE;
    }

    public static void setSUBTITLE(String SUBTITLE) {
        HintState.SUBTITLE = SUBTITLE;
    }
}
