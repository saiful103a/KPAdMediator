package com.kplibwork.libcommon;

import android.app.Activity;

import java.util.Random;

public class KPConstants {

    public static final int AD_ROTATION_POLICY_ON_FAIL = 11;
    public static final int AD_ROTATION_POLICY_TIME_INTERVAL = 22;

    public static final int AD_PRIORITY_POLICY_ADMOB_FIRST = 10;
    public static final int AD_PRIORITY_POLICY_FAN_FIRST = 20;

    public static final int AD_SHOW_POLICY_EVENT_COUNT = 9;
    public static final int AD_SHOW_POLICY_ON_REQUEST = 19;

    public static Activity CURRENT_TOP_ACTIVITY = null;

    public static boolean appMinimized = false;
    public static boolean timePassed = false;

    public static final int ONE_MIN = 1*60*1000;
    public static final int THIRTY_SECONDS = 30 * 1000;
    public static final int THIRTY_MINUTE = 30 * ONE_MIN;

    public static boolean SHOWING_FAN_LOAD_TOO_FREQUENTLY_ERROR = false;

    public static long getRandomNumber(int minTime, int maxTime)
    {
        Random random = new Random();
        long randomNum = random.nextInt(maxTime - minTime + 1) + minTime;
        return randomNum;
    }
}
