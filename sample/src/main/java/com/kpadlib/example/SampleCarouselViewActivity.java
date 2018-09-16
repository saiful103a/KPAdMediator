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

    String fan_banner = "1366788343427705_1370735309699675";
    String fan_interstitial = "1366788343427705_1367668590006347";
    String fan_banner_native = "1366788343427705_1464880376951834";
    String fan_big_custom_native = "1366788343427705_1371136552992884";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_carousel_view);
        try {
            mKpBannerController = new KPBannerController.Builder(this)
                    .setAdmobBannerId(admob_banner)
//                    .setFanBannerId(fan_banner)
                    .setAdPriorityPolicy(KPConstants.AD_PRIORITY_POLICY_FAN_FIRST)
                    .setBannerHolder(findViewById(R.id.carouselView)).build();
            mKpBannerController.requestBannerAds();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            mKPInterstitialController = new KPInterstitialController.Builder(this)
                    .setAdmobInterstitialId(adbmob_interstitial)
//                    .setFanInterstitialId(fan_interstitial)
//                    .setAdEventCount(3)
                    .setAdShowPolicy(KPConstants.AD_SHOW_POLICY_EVENT_COUNT)
//                    .setAdPriorityPolicy(KPConstants.AD_PRIORITY_POLICY_FAN_FIRST)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }

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


}
