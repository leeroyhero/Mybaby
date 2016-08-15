package ru.bogdanov.mybaby.DBHintsHelper;

/**
 * Created by Владимир on 15.08.2016.
 */
public class SubtitleText {
    private String subtitle;

    public SubtitleText(String subtitle, String text) {
        this.subtitle = subtitle;
        this.text = text;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;
}
