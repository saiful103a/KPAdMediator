package com.kpadlib.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.kplibwork.libcommon.KPBannerController;
import com.kplibwork.libcommon.KPConstants;
import com.kplibwork.libcommon.KPInterstitialController;


public class SampleCarouselViewActivity extends Activity {
    KPBannerController mKpBannerController;
    KPInterstitialController mKPInterstitialController;

    String admob_banner = "ca-app-pub-3940256099942544/6300978111";
    String adbmob_interstitial = "ca-app-pub-3940256099942544/1033173712";

    String fan_banner = "IMG_16_9_LINK#248843235772281_248846032438668";
    String fan_interstitial = "VID_HD_9_16_39S_LINK#248843235772281_248853845771220";
    String fan_banner_native = "test";
    String fan_big_custom_native = "test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_carousel_view);
        try {
            mKpBannerController = new KPBannerController.Builder(this)
                    .setAdmobBannerId(admob_banner)
                    .setFanBannerId(fan_banner)
                    .setAdPriorityPolicy(KPConstants.AD_PRIORITY_POLICY_ADMOB_FIRST)
                    .setBannerHolder(findViewById(R.id.carouselView)).build();
            mKpBannerController.requestBannerAds();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            mKPInterstitialController = new KPInterstitialController.Builder(this)
                    .setAdmobInterstitialId(adbmob_interstitial)
                    .setFanInterstitialId(fan_interstitial)
//                    .setAdEventCount(3)
                    .setAdShowPolicy(KPConstants.AD_SHOW_POLICY_EVENT_COUNT)
                    .setAdPriorityPolicy(KPConstants.AD_PRIORITY_POLICY_FAN_FIRST)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }

        /*
         *  reScheduleAdvertiseTime();
         *  This method call should be removed if Advertise with Time Interval not required.
         *  Need to remove reScheduleAdvertiseTime() method call while advertise is Dismissed or Closed.
        * */
        mKPInterstitialController.reScheduleAdvertiseTime(this);

        findViewById(R.id.pauseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //you can use one builder and use getInstance and onRequestToShowInterstitialAd to show Interstitial Ad
                try {
                    //mKPInterstitialController.onRequestToShowInterstitialAd(SampleCarouselViewActivity.this);
                    KPInterstitialController.getInstance().onRequestToShowInterstitialAd(SampleCarouselViewActivity.this);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        KPConstants.appMinimized = false;
        if (KPConstants.timePassed && mKPInterstitialController != null) {
            KPConstants.timePassed = false;
            mKPInterstitialController.onShowInterstitialAd(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        KPConstants.appMinimized = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        KPConstants.appMinimized = false;
    }

}
