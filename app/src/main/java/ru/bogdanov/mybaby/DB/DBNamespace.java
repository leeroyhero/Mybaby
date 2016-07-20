package ru.bogdanov.mybaby.DB;

import android.provider.BaseColumns;

/**
 * Created by Владимир on 14.07.2016.
 */
public class DBNamespace implements BaseColumns {
    public static final String TABLE_USER="user";
    public static final String TABLE_USER_NAME="name";
    public static final String TABLE_USER_PIC_ID="pic_id";

    public static final String TABLE_BABY="baby";
    public static final String TABLE_BABY_NAME="name";
    public static final String TABLE_BABY_DATE="date";
    public static final String TABLE_BABY_WEIGHT="weight";
    public static final String TABLE_BABY_HEIGHT="height";

    public static final String TABLE_BABY_INFO="baby_info";
    public static final String TABLE_BABY_INFO_NAME="name";
    public static final String TABLE_BABY_INFO_BIRTH="birth";
}
