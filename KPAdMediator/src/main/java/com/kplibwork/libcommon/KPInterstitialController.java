package com.kplibwork.libcommon;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.ads.AdError;

import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.ads.AdRequest.ERROR_CODE_INTERNAL_ERROR;
import static com.google.android.gms.ads.AdRequest.ERROR_CODE_NETWORK_ERROR;
import static com.google.android.gms.ads.AdRequest.ERROR_CODE_NO_FILL;
/*
 * Created by saiful on 9/15/18.
 */


public class KPInterstitialController
{
    private static final String TAG = KPInterstitialController.class.getName();
    private Timer timer, retryTimer;

    public interface AdFailedToShowListener
    {
        void onFailedToLoadAd();
    }

    public static class Builder
    {
        private Context mCurrentContext; //This is important, so we'll pass it to the constructor.

        private String admob_interstitial_id;
        private String fan_interstitial_id;

        private int ad_priority_policy = KPConstants.AD_PRIORITY_POLICY_ADMOB_FIRST;

        private int ad_show_policy = KPConstants.AD_SHOW_POLICY_ON_REQUEST;

        public long EventCount = 4;
        private long FULLSCREEN_AD_FREQUENCY = 3;

        private AdFailedToShowListener mAdFailedToShowListener;

        private boolean mBothAd = false;
        private boolean mAdmobOnly = false;
        private boolean mFanOnly = false;

        public Builder setAdFailedToShowListener(AdFailedToShowListener mAdFailedToShowListener)
        {
            this.mAdFailedToShowListener = mAdFailedToShowListener;
            return this;
        }

        public Builder(Context mCurrentContext)
        {
            this.mCurrentContext = mCurrentContext;
        }

        public Builder setAdmobInterstitialId(String admob_interstitial_id)
        {
            this.mAdmobOnly = true;
            if(null!=fan_interstitial_id && fan_interstitial_id.length()>0)
            {
                this.mBothAd = true;
                this.mAdmobOnly = false;
                this.mFanOnly = false;
            }
            this.admob_interstitial_id = admob_interstitial_id;
            return this;
        }

        public Builder setFanInterstitialId(String fan_interstitial_id){
            this.mFanOnly = true;
            if(null!=admob_interstitial_id && admob_interstitial_id.length()>0){
                this.mBothAd = true;
                this.mAdmobOnly = false;
                this.mFanOnly = false;
            }
            this.fan_interstitial_id = fan_interstitial_id;
            return this;
        }

        private boolean admobAd() throws Exception{
            if(null==admob_interstitial_id || !(admob_interstitial_id.length()>0)){
                throw new Exception("You need to setAdmobInterstitialId");
            }else{
                return true;
            }
        }

        private boolean fanAd() throws Exception{
            if(null==fan_interstitial_id || !(fan_interstitial_id.length()>0)){
                throw new Exception("You need to setFanInterstitialId");
            }else{
                return true;
            }
        }

        private boolean bothAd() throws Exception{
            return (admobAd() && fanAd());
        }

        public Builder setShowAdmobOnly(boolean mAdmobOnly) throws Exception{
            if(admobAd()){
                this.mAdmobOnly = mAdmobOnly;
            }
            return this;
        }

        public Builder setShowFanOnly(boolean mFanOnly) throws Exception{
            if(fanAd()){
                this.mFanOnly = mFanOnly;
            }
            return this;
        }

        public Builder setAdPriorityPolicy(int mAdPriorityPolicy) throws Exception{
            if(!mBothAd){
                throw new Exception("You need to setAdmobInterstitialId and setFanInterstitialId both");
            }
            this.ad_priority_policy = mAdPriorityPolicy;
            return this;
        }

        public Builder setAdShowPolicy(int mAdShowPolicy){
            this.ad_show_policy = mAdShowPolicy;
            return this;
        }

        public Builder setAdEventCount(int mAdEventCount) throws Exception{
            this.FULLSCREEN_AD_FREQUENCY = mAdEventCount;
            this.EventCount = mAdEventCount;
            return this;
        }

        public KPInterstitialController build(){
            //Here we create the actual bank account object, which is always in a fully initialised state when it's returned.
            KPInterstitialController bannerController2 = new KPInterstitialController();  //Since the builder is in the BankAccount class, we can invoke its private constructor.
            bannerController2.mCurrentContext = this.mCurrentContext;
            bannerController2.admob_interstitial_id = this.admob_interstitial_id;
            bannerController2.fan_interstitial_id = this.fan_interstitial_id;
            bannerController2.mBothAd = this.mBothAd;
            bannerController2.mAdFailedToShowListener = this.mAdFailedToShowListener;
            bannerController2.mBothAd = this.mBothAd;
            bannerController2.mAdmobOnly = this.mAdmobOnly;
            bannerController2.mFanOnly = this.mFanOnly;
            bannerController2.ad_priority_policy = this.ad_priority_policy;
            bannerController2.ad_show_policy = this.ad_show_policy;
            bannerController2.FULLSCREEN_AD_FREQUENCY = this.FULLSCREEN_AD_FREQUENCY;
            bannerController2.EventCount = this.EventCount;

            bannerController2.setInstance(bannerController2);
            return bannerController2;
        }
    }

    private Context mCurrentContext;

    private String admob_interstitial_id;
    private String fan_interstitial_id;

    private AdFailedToShowListener mAdFailedToShowListener;

    private boolean mBothAd = false;
    private boolean mAdmobOnly = false;
    private boolean mFanOnly = false;
    private int ad_priority_policy = KPConstants.AD_PRIORITY_POLICY_ADMOB_FIRST;
    private int ad_show_policy = KPConstants.AD_SHOW_POLICY_EVENT_COUNT;

    public long EventCount = 4;
    private long FULLSCREEN_AD_FREQUENCY = 3;

    private static KPInterstitialController kpInterstitialController;

    private static void setInstance(KPInterstitialController _kpInterstitialController){
        kpInterstitialController = _kpInterstitialController;
    }

    public static KPInterstitialController getInstance() throws Exception{
        if(null==kpInterstitialController){
            throw new Exception("Please build the instance using builder.");
        }
        return kpInterstitialController;
    }
    //Fields omitted for brevity.
    private KPInterstitialController() {
        //Constructor is now private.
    }

    private void initAdmobInterstitialAd(final Context mActivity){
        mAdmobInterstitialAd = new com.google.android.gms.ads.InterstitialAd(mActivity);
        mAdmobInterstitialAd.setAdUnitId(admob_interstitial_id);
        // Set an AdListener.
        mAdmobInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                //Log.d("Interstitial Ad Info ", "The interstitial is loaded");
            }

            @Override
            public void onAdClosed() {
                //Log.d("Interstitial Ad Info ", "The interstitial is Closed");
                //reScheduleAdvertiseTime(mActivity); /*This method call should be removed if Advertise with Time Interval is not required*/
                onRequestToLoadAdmobInterstitialAd(mActivity);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if(i == ERROR_CODE_NO_FILL
                        || i == ERROR_CODE_NETWORK_ERROR
                        || i == ERROR_CODE_INTERNAL_ERROR){
                    retryAfterGivenTime(mActivity, true, KPConstants.THIRTY_SECONDS);
                }
            }
        });
    }

    private void initFBInterstitialAd(final Context mActivity){
        mFbInterstitialAd = new com.facebook.ads.InterstitialAd(mActivity,fan_interstitial_id);
        // Set an AdListener.
        mFbInterstitialAd.setAdListener(new com.facebook.ads.AbstractAdListener() {
            @Override
            public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(com.facebook.ads.Ad ad) {
                //reScheduleAdvertiseTime(mActivity); /*This method call should be removed if Advertise with Time Interval is not required*/
                onRequestToLoadFBInterstitialAd(mActivity);
            }

            @Override
            public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError adError) {
                if(adError.getErrorCode() == AdError.NO_FILL_ERROR_CODE
                        || adError.getErrorCode() == AdError.SERVER_ERROR_CODE
                        || adError.getErrorCode() == AdError.INTERNAL_ERROR_CODE
                        || adError.getErrorCode() == AdError.NETWORK_ERROR_CODE){
                    retryAfterGivenTime(mActivity, false, KPConstants.THIRTY_SECONDS);
                }else if(adError.getErrorCode() == AdError.LOAD_TOO_FREQUENTLY_ERROR_CODE){
                    retryAfterGivenTime(mActivity, false, KPConstants.THIRTY_MINUTE);
                }
            }

            @Override
            public void onAdLoaded(com.facebook.ads.Ad ad) {
            }

            @Override
            public void onAdClicked(com.facebook.ads.Ad ad) {

            }
        });

    }

    public void onRequestToShowInterstitialAd(final Context mActivity){
        if (mFbInterstitialAd == null) {
            onRequestToLoadFBInterstitialAd(mActivity);
        }
        if (mAdmobInterstitialAd == null) {
            onRequestToLoadAdmobInterstitialAd(mActivity);
        }
        if(ad_show_policy==KPConstants.AD_SHOW_POLICY_EVENT_COUNT) {
            EventCount--;
            if (EventCount <= 0) {
                EventCount = FULLSCREEN_AD_FREQUENCY;
                onShowInterstitialAd(mActivity);
            }
        }else{
            onShowInterstitialAd(mActivity);
        }
    }

    public void onShowInterstitialAd(final Context mActivity){
        Boolean adshown = false;
        boolean admobloaded = false;
        boolean fanloaded = false;

        if(null != mAdmobInterstitialAd && mAdmobInterstitialAd.isLoaded()){
            admobloaded = true;
        }

        if(null != mFbInterstitialAd && mFbInterstitialAd.isAdLoaded()){
            fanloaded = true;
        }

        if(KPConstants.appMinimized)
            return;

        if((mAdmobOnly || (mBothAd && ad_priority_policy==KPConstants.AD_PRIORITY_POLICY_ADMOB_FIRST)))
        {
            if(admobloaded)
            {
                mAdmobInterstitialAd.show();
                adshown = true;
            }else if(fanloaded){
                mFbInterstitialAd.show();
                adshown = true;
            }
        }else if((mFanOnly || (mBothAd && ad_priority_policy==KPConstants.AD_PRIORITY_POLICY_FAN_FIRST))){
            if(fanloaded){
                mFbInterstitialAd.show();
                adshown = true;
            }else if(admobloaded){
                mAdmobInterstitialAd.show();
                adshown = true;
            }
        }else{
            //Log.d(TAG,"No Ad");
        }

        if(adshown && timer != null)
            timer.cancel();

        if (!adshown) {
            onRequestToLoadFBInterstitialAd(mActivity);
            onRequestToLoadAdmobInterstitialAd(mActivity);
        }
    }


    private com.google.android.gms.ads.InterstitialAd mAdmobInterstitialAd = null;

    private void onRequestToLoadAdmobInterstitialAd(final Context mActivity) {
        if(mBothAd || mAdmobOnly) {
            initAdmobInterstitialAd(mActivity);
            // Create an ad request.
            com.google.android.gms.ads.AdRequest.Builder adRequestBuilder = new com.google.android.gms.ads.AdRequest.Builder();

            // Optionally populate the ad request builder.
            adRequestBuilder.addTestDevice(com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR);

            // Start loading the ad now so that it is ready by the time the user is
            // ready to go to
            // the next level.
            mAdmobInterstitialAd.loadAd(adRequestBuilder.build());
        }
    }

    private com.facebook.ads.InterstitialAd mFbInterstitialAd = null;

    private void onRequestToLoadFBInterstitialAd(final Context mActivity) {
        if(mBothAd || mFanOnly) {
            initFBInterstitialAd(mActivity);
            mFbInterstitialAd.loadAd();
        }
    }

    public void onDestroy(Activity mActivity){
        try {
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void onPause(Activity mActivity){
        try {
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void onResume(Activity mActivity){
        try {
            KPConstants.CURRENT_TOP_ACTIVITY = (Activity)mActivity;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void onStart(Activity mActivity){
        try {
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void onStop(Activity mActivity){
        try {
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void reScheduleAdvertiseTime(final Context context)
    {
        /*
            Purpose of this method: To show Advertise with a Time Interval.
         */

        int oneMin = KPConstants.ONE_MIN;
        long randomNum = KPUtils.getRandomNumber(oneMin*3, oneMin*5);
        //Log.d("Timer", "Time = " + randomNum);
        timer = new Timer("fullScreenAdTimer", true);

        TimerTask timerTask = new MyTimerTask(new Runnable() {
            public void run() {
                doCallInterstitialController(context);
                KPConstants.timePassed = true;
            }
        });
        timer.schedule(timerTask, randomNum, randomNum);
        KPConstants.timePassed = false;
    }

    public class MyTimerTask extends TimerTask {
        Runnable runnable;
        MyTimerTask(Runnable runnable){
            this.runnable = runnable;
        }
        @Override
        public void run() {
            if(runnable != null){
                ((Activity) mCurrentContext).runOnUiThread(runnable);
            }
        }
    }

    public void doCallInterstitialController(Context context)
    {
        try {
            //mKPInterstitialController.onRequestToShowInterstitialAd(SampleCarouselViewActivity.this);
            KPInterstitialController.getInstance().onRequestToShowInterstitialAd(context);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showAdmobInterstitialAd(Context context) {
        if (mAdmobInterstitialAd == null) {
            onRequestToLoadAdmobInterstitialAd(context);
        }
        if (null != mAdmobInterstitialAd && mAdmobInterstitialAd.isLoaded()) {
            mAdmobInterstitialAd.show();
        }
    }

    private void retryAfterGivenTime(final Context context, final boolean isGoogleAd, int givenTime){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    if(isGoogleAd){
                        onRequestToLoadAdmobInterstitialAd(context);
                    }else{
                        onRequestToLoadFBInterstitialAd(context);
                    }
                }catch(Exception e){}
            }
        };

        startTimer(runnable, givenTime);
    }

    private void startTimer(Runnable runnable, int time){
        try{
            if(retryTimer == null){
                retryTimer = new Timer("retryTimer", true);
            }

            TimerTask timerTask = new MyTimerTask(runnable);
            retryTimer.schedule(timerTask, time);
        }catch (Exception e){}
    }
}