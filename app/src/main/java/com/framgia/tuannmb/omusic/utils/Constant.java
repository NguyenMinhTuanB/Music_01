package com.framgia.tuannmb.omusic.utils;

import android.support.annotation.StringDef;

public class Constant {
    //Network
    public static final String BASE_URL = "https://api-v2.soundcloud.com/";
    public static final String PARA_MUSIC_GENRE = "charts?kind=top&genre=soundcloud%3Agenres%3A";
    public static final String CLIENT_ID = "client_id";
    public static final String LIMIT = "limit";
    public static final String PARA_OFFSET = "offset";
    public static final String REQUEST_METHOD_GET = "GET";
    public static final int READ_TIME_OUT = 5000; /*milliseconds*/
    public static final int CONNECT_TIME_OUT = 5000; /*milliseconds*/
    public static final String NULL_RESULT = "null";

    public static final String PARA_STREAM = "stream";

    public static final String BREAK_LINE = "\n";

    public static final int LIMIT_20 = 20;
    public static final int OFFSET_20 = 20;


}
