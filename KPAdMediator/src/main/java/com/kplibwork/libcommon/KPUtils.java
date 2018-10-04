package com.kplibwork.libcommon;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public final class KPUtils{

    public static void onDestroy(Activity mActivity){
        try {
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static void onPause(Activity mActivity){
        try {
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static void onResume(Activity mActivity){
        try {
            KPConstants.CURRENT_TOP_ACTIVITY = (Activity)mActivity;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static void onStart(Activity mActivity){
        try {
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void onStop(Activity mActivity){
        try {
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean isNetworkPresent(Context context) {
        boolean isNetworkAvailable = false;

        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null)
            {
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null) {
                    isNetworkAvailable = netInfo.isConnectedOrConnecting();
                }
            }

            // check for wifi also
            if (!isNetworkAvailable) {
                WifiManager connec = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                NetworkInfo.State wifi = cm.getNetworkInfo(1).getState();
                if (connec.isWifiEnabled() && wifi.toString().equalsIgnoreCase("CONNECTED")) {
                    isNetworkAvailable = true;
                } else {

                    isNetworkAvailable = false;
                }
            }
        } catch (Exception ex) {
            Log.e("Network Avail Error", ex.getMessage());
        }

        return isNetworkAvailable;
    }

    public static int getRandomNumber(int minTime, int maxTime)
    {
        Random random = new Random();
        int randomNum = random.nextInt(maxTime - minTime + 1) + minTime;
        return randomNum;
    }



//    private static SecureRandom random = new SecureRandom();
//
//    public static String getRandomSessionString() {
//        return new BigInteger(130, random).toString(32);
//    }
//    public static NetworkInfo getNetworkInfo(Context context){
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        return cm.getActiveNetworkInfo();
//    }
//
//    public static boolean isConnected(Context context){
//        NetworkInfo info = KPUtils.getNetworkInfo(context);
//        return (info != null && info.isConnected());
//    }
//
//    public static boolean isConnectedWifi(Context context){
//        NetworkInfo info = KPUtils.getNetworkInfo(context);
//        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
//    }
//
//    public static boolean isConnectedMobile(Context context){
//        NetworkInfo info = KPUtils.getNetworkInfo(context);
//        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
//    }
//
//    public static boolean isConnectedFast(Context context){
//        NetworkInfo info = KPUtils.getNetworkInfo(context);
//        return (info != null && info.isConnected() && KPUtils.isConnectionFast(info.getType(),info.getSubtype()));
//    }
//
//    public static boolean isConnectionFast(int type, int subType){
//        if(type==ConnectivityManager.TYPE_WIFI){
//            return true;
//        }else if(type==ConnectivityManager.TYPE_MOBILE){
//            switch(subType){
//                case TelephonyManager.NETWORK_TYPE_1xRTT:
//                    return false; // ~ 50-100 kbps
//                case TelephonyManager.NETWORK_TYPE_CDMA:
//                    return false; // ~ 14-64 kbps
//                case TelephonyManager.NETWORK_TYPE_EDGE:
//                    return false; // ~ 50-100 kbps
//                case TelephonyManager.NETWORK_TYPE_EVDO_0:
//                    return true; // ~ 400-1000 kbps
//                case TelephonyManager.NETWORK_TYPE_EVDO_A:
//                    return true; // ~ 600-1400 kbps
//                case TelephonyManager.NETWORK_TYPE_GPRS:
//                    return false; // ~ 100 kbps
//                case TelephonyManager.NETWORK_TYPE_HSDPA:
//                    return true; // ~ 2-14 Mbps
//                case TelephonyManager.NETWORK_TYPE_HSPA:
//                    return true; // ~ 700-1700 kbps
//                case TelephonyManager.NETWORK_TYPE_HSUPA:
//                    return true; // ~ 1-23 Mbps
//                case TelephonyManager.NETWORK_TYPE_UMTS:
//                    return true; // ~ 400-7000 kbps
//                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
//                    return true; // ~ 1-2 Mbps
//                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
//                    return true; // ~ 5 Mbps
//                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
//                    return true; // ~ 10-20 Mbps
//                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
//                    return false; // ~25 kbps
//                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
//                    return true; // ~ 10+ Mbps
//                // Unknown
//                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
//                default:
//                    return false;
//            }
//        }else{
//            return false;
//        }
//    }


//    private static String TAG = KPUtils.class.getSimpleName();
//
//    private static KPBannerController.AdNetworkShowListener mAdNetworkShowListener;
//    private static String fbInterstitialId;
//
//    public static void setAdNetworkShowListener(KPBannerController.AdNetworkShowListener _mAdNetworkShowListener) {
//        mAdNetworkShowListener = _mAdNetworkShowListener;
//    }
//
//    private static void initAdmobInterstitialAd(final Context mActivity){
//        mAdmobInterstitialAd = new com.google.android.gms.ads.InterstitialAd(mActivity);
//        mAdmobInterstitialAd.setAdUnitId(getAdmobInterstitialId(mActivity));
//        // Set an AdListener.
//        mAdmobInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
//            @Override
//            public void onAdLoaded() {
//                Log.d("Interstitial Ad Info ", "The interstitial is loaded");
//            }
//
//            @Override
//            public void onAdClosed() {
//                Log.d("Interstitial Ad Info ", "The interstitial is Closed");
//                KPUtils.onRequestToLoadAdmobInterstitialAd(mActivity);
//            }
//        });
//    }
//
//    private static void initFBInterstitialAd(final Context mActivity){
//        mFbInterstitialAd = new com.facebook.ads.InterstitialAd(mActivity,getFbInterstitialId(mActivity));
//        // Set an AdListener.
//        mFbInterstitialAd.setAdListener(new com.facebook.ads.AbstractAdListener() {
//            @Override
//            public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {
//
//            }
//
//            @Override
//            public void onInterstitialDismissed(com.facebook.ads.Ad ad) {
//                KPUtils.onRequestToLoadFBInterstitialAd(mActivity);
//            }
//
//            @Override
//            public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError adError) {
//
//            }
//
//            @Override
//            public void onAdLoaded(com.facebook.ads.Ad ad) {
//
//            }
//
//            @Override
//            public void onAdClicked(com.facebook.ads.Ad ad) {
//
//            }
//        });
//
//    }
//
//    public static void onRequestToShowInterstitialAd(final Context mActivity){
//        if(null == mAdNetworkShowListener){
//            Log.v(KPUtils.class.getName(),"AdNetworkShowListener is not set yet check KPUtils.setAdNetworkShowListener");
//        }else {
//            try {
//                fbInterstitialId = mActivity.getString(mActivity.getResources().getIdentifier("fb_interstitial_id", "string",
//                        mActivity.getApplicationContext().getPackageName()));
//                fbInterstitialId = KPSettings.getInstance(mActivity).getStringValue(KPConstants.FB_INTERSTITIAL_ID,fbInterstitialId);
//            } catch (Exception e) {
//                Log.v(KPUtils.class.getName(), "Need fb_interstitial_id in strings.xml file");
//            }
//
//            try {
//                KPConstants.pubAdmobInterstitialId = KPSettings.getInstance(mActivity).getIntValue(KPConstants.KEY_PUB_DEV_STATUS) == 300 ? KPConstants.devAdmobInterstitialId : KPSettings.getInstance(mActivity).getStringValue(KPConstants.ADMOB_INTERSTITIAL_ID,
//                        mActivity.getString(mActivity.getResources().getIdentifier("admob_interstitial_id", "string",
//                                mActivity.getApplicationContext().getPackageName()))
//                );
//            } catch (Exception e) {
//                Log.v(KPUtils.class.getName(), "Need admob_interstitial_id in strings.xml file");
//            }
//
//            if (mFbInterstitialAd == null && mAdNetworkShowListener.shouldShowFan()) {
//                KPUtils.onRequestToLoadFBInterstitialAd(mActivity);
//            }
//            if (mAdmobInterstitialAd == null && mAdNetworkShowListener.shouldShowAdmob()) {
//                KPUtils.onRequestToLoadAdmobInterstitialAd(mActivity);
//            }
//            KPConstants.EventCount--;
//            if (KPConstants.EventCount <= 0) {
//                KPConstants.EventCount = KPConstants.FULLSCREEN_AD_FREQUENCY;
//                onShowInterstitialAd(mActivity);
//            }
//        }
//    }
//
//    public static void onShowInterstitialAd(final Context mActivity){
//        if(null == mAdNetworkShowListener){
//            Log.v(KPUtils.class.getName(),"AdNetworkShowListener is not set yet check KPUtils.setAdNetworkShowListener");
//        }else {
//            try {
//                String default_iadorder = "fan-admob";
//                try {
//                    default_iadorder = mActivity.getString(mActivity.getResources().getIdentifier("default_iad_order", "string",
//                            mActivity.getApplicationContext().getPackageName()));
//                } catch (Exception e) {
//                    Log.v(KPBannerController.class.getName(), "Need default_iad_order in strings.xml file");
//                }
//                String data = KPSettings.getInstance(mActivity).getStringValue(KPConstants.DEFAULT_IAD_ORDER, default_iadorder);
//                String[] ads = data.split("-");
//                Boolean adshown = false;
//                for (int i = 0; i < ads.length; i++) {
//                    try {
//                        if (null != ads[i] && ads[i].equalsIgnoreCase("fan") && mAdNetworkShowListener.shouldShowFan() && null != mFbInterstitialAd && mFbInterstitialAd.isAdLoaded()) {
//                            mFbInterstitialAd.show();
//                            adshown = true;
//                            if (KPSettings.getInstance(mActivity).getIntValue(KPConstants.KEY_PUB_DEV_STATUS) == 200) {
//                                if (KPConstants.pub_dev_interstitial_count < 0) {
//                                    onReloadInterstitialPubDevStatus(mActivity);
//                                }
//                                KPConstants.pub_dev_interstitial_count--;
//                            }
//                            break;
//                        }else if (null != ads[i] && ads[i].equalsIgnoreCase("admob") && mAdNetworkShowListener.shouldShowAdmob() && null != mAdmobInterstitialAd && mAdmobInterstitialAd.isLoaded()) {
//                            mAdmobInterstitialAd.show();
//                            adshown = true;
//                            if (KPSettings.getInstance(mActivity).getIntValue(KPConstants.KEY_PUB_DEV_STATUS) == 200) {
//                                if (KPConstants.pub_dev_interstitial_count < 0) {
//                                    onReloadInterstitialPubDevStatus(mActivity);
//                                }
//                                KPConstants.pub_dev_interstitial_count--;
//                            }
//                            break;
//                        }
//                    } catch (Exception e) {
//                    }
//                }
//                if (!adshown) {
//                    KPUtils.onRequestToLoadFBInterstitialAd(mActivity);
//                    KPUtils.onRequestToLoadAdmobInterstitialAd(mActivity);
//                }
//            } catch (Exception e) {
//                KPUtils.onRequestToLoadFBInterstitialAd(mActivity);
//                KPUtils.onRequestToLoadAdmobInterstitialAd(mActivity);
//            }
//        }
//    }
//
//    private static void onReloadInterstitialPubDevStatus(final Context mCurrentContext){
//        KPConstants.pub_dev_interstitial_count = KPConstants.CPUB_DEV_INTERSTITIAL_COUNT;
//        KPConstants.pub_interstitial_get = KPConstants.CPUB_INTERSTITIAL_GET;
//        KPConstants.dev_interstitial_get = KPConstants.CDEV_INTERSTITIAL_GET;
//
//        try {
//            fbInterstitialId = mCurrentContext.getString(mCurrentContext.getResources().getIdentifier("fb_interstitial_id", "string",
//                    mCurrentContext.getApplicationContext().getPackageName()));
//            fbInterstitialId = KPSettings.getInstance(mCurrentContext).getStringValue(KPConstants.FB_INTERSTITIAL_ID,fbInterstitialId);
//        }catch (Exception e){
//            Log.v(KPUtils.class.getName(),"Need fb_interstitial_id in strings.xml file");
//        }
//
//        try {
//            KPConstants.pubAdmobInterstitialId = KPSettings.getInstance(mCurrentContext).getIntValue(KPConstants.KEY_PUB_DEV_STATUS) == 300?KPConstants.devAdmobInterstitialId:KPSettings.getInstance(mCurrentContext).getStringValue(KPConstants.ADMOB_INTERSTITIAL_ID,
//                    mCurrentContext.getString(mCurrentContext.getResources().getIdentifier("admob_interstitial_id", "string",
//                            mCurrentContext.getApplicationContext().getPackageName()))
//            );
//        }catch (Exception e){
//            Log.v(KPUtils.class.getName(),"Need admob_interstitial_id in strings.xml file");
//        }
//    }
//
//    private static com.google.android.gms.ads.InterstitialAd mAdmobInterstitialAd = null;
//
//    public static void onRequestToLoadAdmobInterstitialAd(final Context mActivity) {
//        KPUtils.initAdmobInterstitialAd(mActivity);
//        // Create an ad request.
//        com.google.android.gms.ads.AdRequest.Builder adRequestBuilder = new com.google.android.gms.ads.AdRequest.Builder();
//
//        // Optionally populate the ad request builder.
//        adRequestBuilder.addTestDevice(com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR);
//
//        // Start loading the ad now so that it is ready by the time the user is
//        // ready to go to
//        // the next level.
//        mAdmobInterstitialAd.loadAd(adRequestBuilder.build());
//    }
//
//    private static com.facebook.ads.InterstitialAd mFbInterstitialAd = null;
//
//    public static void onRequestToLoadFBInterstitialAd(final Context mActivity) {
//        KPUtils.initFBInterstitialAd(mActivity);
//        mFbInterstitialAd.loadAd();
//    }

}
