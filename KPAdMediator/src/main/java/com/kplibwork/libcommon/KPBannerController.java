package com.kplibwork.libcommon;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by saiful on 9/15/18.
 */

public class KPBannerController {
    private static final String TAG = KPBannerController.class.getName();

    public interface AdFailedToShowListener{
        void onFailedToLoadAd();
    }

    public static class Builder {

        private Context mCurrentContext; //This is important, so we'll pass it to the constructor.

        private com.google.android.gms.ads.AdSize admobAdSize = com.google.android.gms.ads.AdSize.SMART_BANNER;
        private com.facebook.ads.AdSize fanAdSize = com.facebook.ads.AdSize.BANNER_HEIGHT_50;

        private View bannerHolder;

        private long mAdRotationIntervalTime = 30000;

        private String admob_banner_id;
        private String fan_banner_id;
        private String admob_app_id;

        private int ad_rotation_policy = KPConstants.AD_ROTATION_POLICY_ON_FAIL;

        private int ad_priority_policy = KPConstants.AD_PRIORITY_POLICY_ADMOB_FIRST;

        private static final int DEFAULT_BANNER_RETRY_COUNT = 2;
        private int mAdmobRetryCount = DEFAULT_BANNER_RETRY_COUNT;
        private int mFanRetryCount = DEFAULT_BANNER_RETRY_COUNT;

        private AdFailedToShowListener mAdFailedToShowListener;

        private boolean mBothAd = false;
        private boolean mAdmobOnly = false;
        private boolean mFanOnly = false;

        public Builder setAdFailedToShowListener(AdFailedToShowListener mAdFailedToShowListener) {
            this.mAdFailedToShowListener = mAdFailedToShowListener;
            return this;
        }

        public Builder(Context mCurrentContext) {
            this.mCurrentContext = mCurrentContext;
        }

        public Builder setAdmobAppId(String admob_app_id){
            this.admob_app_id = admob_app_id;
            return this;
        }

        public Builder setAdmobBannerId(String admob_banner_id){
            this.mAdmobOnly = true;
            if(null!=fan_banner_id && fan_banner_id.length()>0){
                this.mBothAd = true;
                this.mAdmobOnly = false;
                this.mFanOnly = false;
            }
            this.admob_banner_id = admob_banner_id;
            return this;
        }

        public Builder setFanBannerId(String fan_banner_id){
            this.mFanOnly = true;
            if(null!=admob_banner_id && admob_banner_id.length()>0){
                this.mBothAd = true;
                this.mAdmobOnly = false;
                this.mFanOnly = false;
            }
            this.fan_banner_id = fan_banner_id;
            return this;
        }

        private boolean admobAd() throws Exception{
            if(null==admob_banner_id || !(admob_banner_id.length()>0)){
                throw new Exception("You need to setAdmobBannerId");
            }else{
                return true;
            }
        }

        private boolean fanAd() throws Exception{
            if(null==fan_banner_id || !(fan_banner_id.length()>0)){
                throw new Exception("You need to setFanBannerId");
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

        /*
            default ad_priority_policy KPConstants.AD_PRIORITY_POLICY_ADMOB_FIRST
         */
        public Builder setAdPriorityPolicy(int mAdPriorityPolicy) throws Exception{
            if(!mBothAd){
                throw new Exception("You need to setAdmobBannerId and setFanBannerId both");
            }
            this.ad_priority_policy = mAdPriorityPolicy;
            return this;
        }


        public Builder setAdRotationPolicy(int mAdRotationPolicy) throws Exception{
            if(!mBothAd){
                throw new Exception("setAdRotationPolicy is valid only for both ad. You need to setAdmobBannerId and setFanBannerId both if you want to apply AdRotationPolicy. Also use setAdmobRetryCount setFanRetryCount to specify retry attempt if you use AD_ROTATION_POLICY_ON_FAIL, default is"+DEFAULT_BANNER_RETRY_COUNT+" ");
            }
            this.ad_rotation_policy = mAdRotationPolicy;
            return this;
        }

        public Builder setAdRotationIntervalTime(long mAdRotationIntervalTime) throws Exception{
            if(this.ad_rotation_policy!=KPConstants.AD_ROTATION_POLICY_TIME_INTERVAL){
                throw new Exception("setAdRotationIntervalTime is valid when you set setAdRotationPolicy to AD_ROTATION_POLICY_TIME_INTERVAL.");
            }
            this.mAdRotationIntervalTime = mAdRotationIntervalTime;
            return this;
        }

        public Builder setAdmobRetryCount(int mAdmobRetryCount) throws Exception{
            if(!mBothAd){
                throw new Exception("setAdmobRetryCount is valid only for both ad. You need to setAdmobBannerId and setFanBannerId both if you want to apply AdmobRetryCount");
            }
            this.mAdmobRetryCount = mAdmobRetryCount;
            return this;
        }

        public Builder setFanRetryCount(int mFanRetryCount) throws Exception{
            if(!mBothAd){
                throw new Exception("setFanRetryCount is valid only for both ad. You need to setAdmobBannerId and setFanBannerId both if you want to apply FanRetryCount");
            }
            this.mFanRetryCount = mFanRetryCount;
            return this;
        }

        public Builder setAdmobAdSize(com.google.android.gms.ads.AdSize admobAdSize) throws Exception{
            if(admobAd()) {
                this.admobAdSize = admobAdSize;
            }
            return this;
        }

        public Builder setFanAdSize(com.facebook.ads.AdSize fanAdSize) throws Exception{
            if(fanAd()) {
                this.fanAdSize = fanAdSize;
            }
            return this;
        }

        public Builder setBannerHolder(final View view) throws Exception{
            if(!(view instanceof LinearLayout)){
                throw new Exception("View is not a linear layout");
            }
            this.bannerHolder =  view;
            return this;
        }

        public KPBannerController build(){
            //Here we create the actual bank account object, which is always in a fully initialised state when it's returned.
            KPBannerController bannerController2 = new KPBannerController();  //Since the builder is in the BankAccount class, we can invoke its private constructor.
            bannerController2.mCurrentContext = this.mCurrentContext;
            bannerController2.admobAdSize = this.admobAdSize;
            bannerController2.fanAdSize = this.fanAdSize;
            bannerController2.bannerHolder = this.bannerHolder;
            bannerController2.mAdRotationIntervalTime = this.mAdRotationIntervalTime;
            bannerController2.admob_app_id = this.admob_app_id;
            bannerController2.admob_banner_id = this.admob_banner_id;
            bannerController2.fan_banner_id = this.fan_banner_id;
            bannerController2.ad_rotation_policy = this.ad_rotation_policy;
            bannerController2.mAdmobRetryCount = this.mAdmobRetryCount;
            bannerController2.mFanRetryCount = this.mFanRetryCount;
            bannerController2.mAdFailedToShowListener = this.mAdFailedToShowListener;
            bannerController2.mBothAd = this.mBothAd;
            bannerController2.mAdmobOnly = this.mAdmobOnly;
            bannerController2.mFanOnly = this.mFanOnly;
            bannerController2.ad_priority_policy = this.ad_priority_policy;
            return bannerController2;
        }
    }

    private Context mCurrentContext;
    private com.google.android.gms.ads.AdSize admobAdSize = com.google.android.gms.ads.AdSize.SMART_BANNER;
    private com.facebook.ads.AdSize fanAdSize = com.facebook.ads.AdSize.BANNER_HEIGHT_50;
    private View bannerHolder;
    private long mAdRotationIntervalTime = 30000;

    private String admob_banner_id;
    private String fan_banner_id;
    private String admob_app_id;

    private int ad_rotation_policy = KPConstants.AD_ROTATION_POLICY_ON_FAIL;
    private int ad_priority_policy = KPConstants.AD_PRIORITY_POLICY_ADMOB_FIRST;

    private static final int DEFAULT_BANNER_RETRY_COUNT = 2;
    private int mAdmobRetryCount = DEFAULT_BANNER_RETRY_COUNT;
    private int mFanRetryCount = DEFAULT_BANNER_RETRY_COUNT;

    private int mAdmobRetryCounter = DEFAULT_BANNER_RETRY_COUNT;
    private int mFanRetryCounter = DEFAULT_BANNER_RETRY_COUNT;

    private AdFailedToShowListener mAdFailedToShowListener;

    private boolean mBothAd = false;
    private boolean mAdmobOnly = false;
    private boolean mFanOnly = false;

    private com.google.android.gms.ads.AdView admobAdView;
    private com.facebook.ads.AdView fanAdView;
    private Handler handler;
    private Runnable mAdRunnable;

    //Fields omitted for brevity.
    private KPBannerController() {
        //Constructor is now private.
        this.handler = new Handler();
    }

    public void onBannerDestroy(){
        try {
            if(admobAdView!=null) {
                admobAdView.setAdListener(null);
                admobAdView.destroy();
                admobAdView = null;
            }
            if(fanAdView!=null) {
                fanAdView.setAdListener(null);
                fanAdView.destroy();
                fanAdView = null;
            }
            if(null!=mAdRunnable) {
                handler.removeCallbacks(mAdRunnable);
            }
            mAdRunnable = null;
            handler = null;
        } catch (Exception e) {

        }
    }

    public void onBannerPause(){
        try {
            if(admobAdView!=null) {
                admobAdView.pause();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void onBannerResume(){
        try {
            if(admobAdView!=null) {
                admobAdView.resume();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }



    private void loadFanBannerAds(final View view){
        if(null!=fanAdSize){
            loadFanBannerAds(view, fanAdSize);
        }else {
            loadFanBannerAds(view, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        }
    }

    private void loadFanBannerAds(final View view, final com.facebook.ads.AdSize mFanAdSize){
        try {
            LinearLayout adHolder = (LinearLayout)view;
            adHolder.setGravity(Gravity.CENTER);
            try {
                if(fanAdView!=null) {
                    fanAdView.setAdListener(null);
                    fanAdView.destroy();
                    fanAdView = null;
                }
            }catch (Exception e){}
            try {
                if(admobAdView!=null) {
                    admobAdView.setAdListener(null);
                    admobAdView.destroy();
                    admobAdView = null;
                }
            }catch (Exception e){}

            try {
                adHolder.removeAllViews();
            }catch (Exception e){}


            if(KPUtils.isNetworkPresent(mCurrentContext)){
                fanAdView = new com.facebook.ads.AdView(mCurrentContext,fan_banner_id, mFanAdSize);
                fanAdView.setAdListener(new com.facebook.ads.AbstractAdListener() {
                    @Override
                    public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError adError) {
                        if(mBothAd && ad_rotation_policy == KPConstants.AD_ROTATION_POLICY_ON_FAIL) {
                            mFanRetryCount--;
                            if (mFanRetryCount <= 0) {
                                mFanRetryCount = DEFAULT_BANNER_RETRY_COUNT;
                                KPBannerController.this.loadAdmobBannerAds(view);
                            }
                        }else if(null!=mAdFailedToShowListener) {
                                mAdFailedToShowListener.onFailedToLoadAd();
                        }
                    }

                    @Override
                    public void onAdLoaded(com.facebook.ads.Ad ad) {
                        loadRunnables();
                        if(null!=handler && null!=mAdRunnable && ad_rotation_policy == KPConstants.AD_ROTATION_POLICY_TIME_INTERVAL) {
                            handler.removeCallbacks(mAdRunnable);
                            handler.postDelayed(mAdRunnable, mAdRotationIntervalTime);
                        }
                    }

                    @Override
                    public void onAdClicked(com.facebook.ads.Ad ad) {

                    }
                });
                adHolder.setGravity(Gravity.CENTER);
                adHolder.addView(fanAdView);

                fanAdView.loadAd();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadAdmobBannerAds(final View view,com.google.android.gms.ads.AdSize mAdmobAdsize){
        try {
            final LinearLayout adholder = (LinearLayout)view;
            adholder.setGravity(Gravity.CENTER);

            try {
                if(admobAdView!=null) {
                    admobAdView.setAdListener(null);
                    admobAdView.destroy();
                    admobAdView = null;
                }
            }catch (Exception e){}
            try {
                if(fanAdView!=null) {
                    fanAdView.setAdListener(null);
                    fanAdView.destroy();
                    fanAdView = null;
                }
            }catch (Exception e){}
            try {
                adholder.removeAllViews();
            }catch (Exception e){}

            if(KPUtils.isNetworkPresent(mCurrentContext)) {

                admobAdView = new com.google.android.gms.ads.AdView(mCurrentContext);

                admobAdView.setAdSize(mAdmobAdsize);

                admobAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        if(mBothAd && ad_rotation_policy == KPConstants.AD_ROTATION_POLICY_ON_FAIL) {
                            mAdmobRetryCounter--;
                            if (mAdmobRetryCounter <= 0) {
                                mAdmobRetryCounter = mAdmobRetryCount;
                                KPBannerController.this.loadFanBannerAds(view);
                            }
                        }else if(null!=mAdFailedToShowListener) {
                            mAdFailedToShowListener.onFailedToLoadAd();
                        }

                        super.onAdFailedToLoad(errorCode);
                    }

                    @Override
                    public void onAdLeftApplication() {
                        super.onAdLeftApplication();
                    }

                    @Override
                    public void onAdLoaded() {
                        loadRunnables();
                        if(null!=handler && null!=mAdRunnable && ad_rotation_policy == KPConstants.AD_ROTATION_POLICY_TIME_INTERVAL) {
                            handler.removeCallbacks(mAdRunnable);
                            handler.postDelayed(mAdRunnable, mAdRotationIntervalTime);
                        }
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }
                });
                String admb = admob_banner_id;
                // Start loading the ad in the background.
                admobAdView.setAdUnitId(admb);
                adholder.setGravity(Gravity.CENTER);
                adholder.addView(admobAdView);

                com.google.android.gms.ads.AdRequest adRequest = null;
                adRequest = new com.google.android.gms.ads.AdRequest.Builder()
                        .addTestDevice(com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR)
                        .build();
                admobAdView.loadAd(adRequest);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void loadAdmobBannerAds(final View view){
        if(null!=admobAdSize){
            loadAdmobBannerAds(view, admobAdSize);
        }else {
            loadAdmobBannerAds(view, com.google.android.gms.ads.AdSize.SMART_BANNER);
        }
    }

    private void loadRunnables(){
        if(ad_rotation_policy == KPConstants.AD_ROTATION_POLICY_TIME_INTERVAL) {
            if(handler==null){
                handler = new Handler();
            }
            if(mAdRunnable==null) {
                mAdRunnable = new Runnable() {
                    public void run() {
                        if (mFanRetryCounter <= 0) {
                            mFanRetryCounter = mFanRetryCount;
                        }
                        if (mAdmobRetryCounter <= 0) {
                            mAdmobRetryCounter = mAdmobRetryCount;
                        }
                        onRequestBannerAds();
                    }
                };
            }
        }
    }

    private void onRequestBannerAds(){
        if(null!=handler && null!=mAdRunnable && ad_rotation_policy == KPConstants.AD_ROTATION_POLICY_TIME_INTERVAL){
            handler.removeCallbacks(mAdRunnable);
            handler.postDelayed(mAdRunnable, mAdRotationIntervalTime);
        }
        if(mAdmobOnly || (mBothAd && ad_priority_policy==KPConstants.AD_PRIORITY_POLICY_ADMOB_FIRST)){
            KPBannerController.this.loadAdmobBannerAds(bannerHolder);
        }else if(mFanOnly || (mBothAd && ad_priority_policy==KPConstants.AD_PRIORITY_POLICY_FAN_FIRST)){
            KPBannerController.this.loadFanBannerAds(bannerHolder);
        }else{
            Log.d(TAG,"No Ad");
        }

    }

    private void loadBannerAds() throws Exception{
        try {
            com.google.android.gms.ads.MobileAds.initialize(mCurrentContext, admob_app_id);
        }catch (Exception e){
            Log.v(KPBannerController.class.getName(),"You need to setAdmobAppId (Optional).");
        }
        if(null==bannerHolder || !(bannerHolder instanceof LinearLayout)){
            throw new Exception("BannerHolder view is not a linear layout, use setBannerHolder inside Builder");
        }else {
            loadRunnables();
            onRequestBannerAds();
        }
    }

    public void requestBannerAds() throws Exception{
        loadBannerAds();
    }
}
