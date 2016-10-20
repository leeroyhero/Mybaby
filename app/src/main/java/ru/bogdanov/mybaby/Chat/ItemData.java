package ru.bogdanov.mybaby.Chat;

/**
 * Created by Владимир on 08.07.2016.
 */
public class ItemData {
    String text;
    Integer imageId;
    public ItemData(String text, Integer imageId){
        this.text=text;
        this.imageId=imageId;
    }

    public String getText(){
        return text;
    }

    public Integer getImageId(){
        return imageId;
    }
}
