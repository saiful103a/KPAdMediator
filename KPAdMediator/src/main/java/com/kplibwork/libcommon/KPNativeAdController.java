package com.kplibwork.libcommon;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saiful on 12/7/17.
 */

public class KPNativeAdController {
/*    private static final String TAG = KPNativeAdController.class.getName();

    public interface OnLoadStatusNativeAd{
        void loadStatusNativeAd(boolean success, View view);
    }

    private Context mContext;
    public void setCurrentContext(Context __Context){
        this.mContext = __Context;
    }
    public Context getCurrentContext(){
        return this.mContext;
    }
    private com.facebook.ads.NativeBannerAd mNativeBannerAd;
    private com.facebook.ads.NativeAdsManager mNativeAdsManager;
    private LinearLayout fanNativeAdView;

    private boolean fbnativeTeamplateFailed = false;
    private boolean fbnativeFailed = false;

    private boolean fbnativeadmanagerFailed = false;


    private boolean fbnativeTeamplateOnly = false;

    public void onTryFbNative(final LinearLayout layout, final Activity mActivity, final OnLoadStatusNativeAd mOnLoadStatusNativeAd){
        this.mOnLoadStatusNativeAd = mOnLoadStatusNativeAd;
        onTryFbNative(layout,mActivity);
    }

    private String custom_fb_native_ad_id = "";
    public void setcustom_fb_native_ad_id(String custom_fb_native_ad_id){
        this.custom_fb_native_ad_id = custom_fb_native_ad_id;
    }

    public void onTryFbNative(final LinearLayout layout, final Activity mActivity, final OnLoadStatusNativeAd mOnLoadStatusNativeAd, final String custom_fb_native_ad_id){
        this.mOnLoadStatusNativeAd = mOnLoadStatusNativeAd;
        this.custom_fb_native_ad_id = custom_fb_native_ad_id;
        onTryFbNative(layout,mActivity);
    }

    public void onTryFbNativeTemplate(final LinearLayout layout, final Activity mActivity, final OnLoadStatusNativeAd mOnLoadStatusNativeAd, final String custom_fb_native_ad_id){
        this.mOnLoadStatusNativeAd = mOnLoadStatusNativeAd;
        this.custom_fb_native_ad_id = custom_fb_native_ad_id;
        onTryFbNativeTemplate(layout,mActivity);
    }

    public void onTryFbNativeTemplate(final LinearLayout layout, final Activity mActivity, final OnLoadStatusNativeAd mOnLoadStatusNativeAd){
        this.mOnLoadStatusNativeAd = mOnLoadStatusNativeAd;
        onTryFbNativeTemplate(layout,mActivity);
    }

    private com.facebook.ads.NativeBannerAdView.Type templateHeight = com.facebook.ads.NativeBannerAdView.Type.HEIGHT_120;

    public void set_fb_native_template_height(com.facebook.ads.NativeBannerAdView.Type _templateHeight){
        this.templateHeight = _templateHeight;
    }

    public void set_fb_native_template_only(boolean _fbnativeTeamplateOnly){
        this.fbnativeTeamplateOnly = _fbnativeTeamplateOnly;
    }



    public void onTryFbNativeTemplate(final LinearLayout layout,final Activity mActivity){
        this.setCurrentContext(mActivity);
        {
            String fb_native_ad_id = "";
            try {
                if(null!=custom_fb_native_ad_id && custom_fb_native_ad_id.length()>0){
                    fb_native_ad_id = custom_fb_native_ad_id;
                }else {
                    fb_native_ad_id = mActivity.getString(mActivity.getResources().getIdentifier("fb_native_ad_id", "string",
                            mActivity.getApplicationContext().getPackageName()));
                }
            }catch (Exception e){}

            if(fb_native_ad_id == null || fb_native_ad_id.length() == 0){
                fbnativeTeamplateFailed = true;
                if(null!=mOnLoadStatusNativeAd){
                    mOnLoadStatusNativeAd.loadStatusNativeAd(false,layout);
                }
                return;
            }

            mNativeBannerAd = new com.facebook.ads.NativeBannerAd(getCurrentContext(), fb_native_ad_id);
            mNativeBannerAd.setAdListener(new com.facebook.ads.NativeAdListener() {

                @Override
                public void onMediaDownloaded(com.facebook.ads.Ad ad) {

                }

                @Override
                public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError error) {
                    // Ad error callback
                    fbnativeTeamplateFailed = true;
                    if(null!=mOnLoadStatusNativeAd){
                        mOnLoadStatusNativeAd.loadStatusNativeAd(false,layout);
                    }

                }

                @Override
                public void onAdLoaded(com.facebook.ads.Ad ad) {
                    if (ad.isAdInvalidated() || mNativeBannerAd == null || mNativeBannerAd != ad) {
                        fbnativeTeamplateFailed = true;
                        if(null!=mOnLoadStatusNativeAd){
                            mOnLoadStatusNativeAd.loadStatusNativeAd(false,layout);
                        }
                    }else{
                        try {
                            try {
                                layout.findViewById(R.id.adProgress).setVisibility(View.GONE);
                            }catch (Exception e){}


                            FrameLayout nativeAdContainer = layout.findViewById(R.id.adFrameLayout);

                            nativeAdContainer.removeAllViews();

                            View adView = com.facebook.ads.NativeBannerAdView.render(mActivity, mNativeBannerAd, templateHeight);
                            nativeAdContainer.addView(adView);

                            if(null!=mOnLoadStatusNativeAd){
                                mOnLoadStatusNativeAd.loadStatusNativeAd(true,layout);
                            }
                        }catch (Exception e){
                            Log.v(KPNativeAdController.class.getName(),"Place a FrameLayout in your view the id @id/adFrameLayout to load the ad properly.");
                            fbnativeTeamplateFailed = true;
                            if(null!=mOnLoadStatusNativeAd){
                                mOnLoadStatusNativeAd.loadStatusNativeAd(false,layout);
                            }
                        }
                    }
                }

                @Override
                public void onAdClicked(com.facebook.ads.Ad ad) {
                    // Ad clicked callback
                }

                @Override
                public void onLoggingImpression(com.facebook.ads.Ad ad) {
                    // On logging impression callback
                }
            });

            mNativeBannerAd.loadAd();
        }
    }

    public void onTryFbNative(final LinearLayout layout,final Activity mActivity){
        this.setCurrentContext(mActivity);
        {
            String fb_native_ad_id = "";
            try {
                if(null!=custom_fb_native_ad_id && custom_fb_native_ad_id.length()>0){
                    fb_native_ad_id = custom_fb_native_ad_id;
                }else {
                    fb_native_ad_id = mActivity.getString(mActivity.getResources().getIdentifier("fb_native_ad_id", "string",
                            mActivity.getApplicationContext().getPackageName()));
                }
            }catch (Exception e){}

            if(fb_native_ad_id == null || fb_native_ad_id.length() == 0){
                fbnativeFailed = true;
                if(null!=mOnLoadStatusNativeAd){
                    mOnLoadStatusNativeAd.loadStatusNativeAd(false,layout);
                }
                return;
            }

            final com.facebook.ads.NativeAd fbnativeAd = new com.facebook.ads.NativeAd(getCurrentContext(), fb_native_ad_id);
            fbnativeAd.setAdListener(new com.facebook.ads.NativeAdListener() {

                @Override
                public void onMediaDownloaded(com.facebook.ads.Ad ad) {

                }

                @Override
                public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError error) {
                    fbnativeFailed = true;
                    if(!fbnativeTeamplateFailed){
                        onTryFbNativeTemplate(layout,mActivity);
                    }else{
                        if(null!=mOnLoadStatusNativeAd){
                            mOnLoadStatusNativeAd.loadStatusNativeAd(false,layout);
                        }
                    }

                }

                @Override
                public void onAdLoaded(com.facebook.ads.Ad ad) {
                    if (ad.isAdInvalidated() || fbnativeAd == null || fbnativeAd != ad) {
                        fbnativeFailed = true;
                        if(!fbnativeTeamplateFailed){
                            onTryFbNativeTemplate(layout,mActivity);
                        }else{
                            if(null!=mOnLoadStatusNativeAd){
                                mOnLoadStatusNativeAd.loadStatusNativeAd(false,layout);
                            }
                        }
                    }else {
                        fbnativeAd.unregisterView();
                        populateFanNativeAdInsideView(mActivity, layout, fbnativeAd, true);
                    }
                }

                @Override
                public void onAdClicked(com.facebook.ads.Ad ad) {
                    // Ad clicked callback
                }

                @Override
                public void onLoggingImpression(com.facebook.ads.Ad ad) {
                    // On logging impression callback
                }
            });

            // Request an ad
            fbnativeAd.loadAd();
        }
    }

    public void populateFanNativeAdInsideView(final Activity mActivity, final LinearLayout layout,final com.facebook.ads.NativeAd fbnativeAd, boolean callback){
        try {
            try {
                layout.findViewById(R.id.adProgress).setVisibility(View.GONE);
            }catch (Exception e){}


            FrameLayout nativeAdContainer =
                    layout.findViewById(R.id.adFrameLayout);
            nativeAdContainer.removeAllViews();

            LayoutInflater inflater = LayoutInflater.from(mActivity);

            // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
            android.support.constraint.ConstraintLayout adView = (android.support.constraint.ConstraintLayout) inflater.inflate(R.layout.native_responsive_ad, nativeAdContainer, false);
            nativeAdContainer.addView(adView);

            // Add the AdChoices icon
            LinearLayout adChoicesContainer = (LinearLayout) adView.findViewById(R.id.ad_choices_container);
            com.facebook.ads.AdChoicesView adChoicesView = new com.facebook.ads.AdChoicesView(mActivity, fbnativeAd, true);
            adChoicesContainer.addView(adChoicesView, 0);

            // Create native UI using the ad metadata.
            com.facebook.ads.AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
            TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
            com.facebook.ads.MediaView nativeAdMedia = (com.facebook.ads.MediaView) adView.findViewById(R.id.native_ad_media);
            TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id.native_ad_social_context);
            TextView nativeAdBody = (TextView) adView.findViewById(R.id.native_ad_body);
            TextView sponsoredLabel = (TextView) adView.findViewById(R.id.native_ad_sponsored_label);
            Button nativeAdCallToAction = (Button) adView.findViewById(R.id.native_ad_call_to_action);

            // Set the Text.
            nativeAdTitle.setText(fbnativeAd.getAdvertiserName());
            nativeAdBody.setText(fbnativeAd.getAdBodyText());
            nativeAdSocialContext.setText(fbnativeAd.getAdSocialContext());
            nativeAdCallToAction.setVisibility(fbnativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            nativeAdCallToAction.setText(fbnativeAd.getAdCallToAction());

            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(nativeAdTitle);
            clickableViews.add(nativeAdCallToAction);

            // Register the Title and CTA button to listen for clicks.
            fbnativeAd.registerViewForInteraction(
                    adView,
                    nativeAdMedia,
                    nativeAdIcon,clickableViews);


            if(null!=mOnLoadStatusNativeAd && callback){
                mOnLoadStatusNativeAd.loadStatusNativeAd(true,layout);
            }
        }catch (Exception e){
            Log.v(KPNativeAdController.class.getName(),"Place a FrameLayout in your view the id @id/adFrameLayout to load the ad properly.");
            if(callback) {
                fbnativeFailed = true;
                if (null != mOnLoadStatusNativeAd) {
                    mOnLoadStatusNativeAd.loadStatusNativeAd(false, layout);
                }
            }
        }
    }


    private OnLoadStatusNativeAd mOnLoadStatusNativeAd;
    public void setOnLoadStatusNativeAd(OnLoadStatusNativeAd mOnLoadStatusNativeAd){
        this.mOnLoadStatusNativeAd = mOnLoadStatusNativeAd;
    }

    public void onResetTryForNativeAd(){
        this.fbnativeadmanagerFailed = false;
        this.fbnativeTeamplateFailed = false;
        this.fbnativeFailed = false;
    }


    public void onTryFbNativeWithNativeAdsManager(final View layout,final Activity mActivity, final com.facebook.ads.NativeAdView.Type mType, final OnLoadStatusNativeAd mOnLoadStatusNativeAd){
        this.mOnLoadStatusNativeAd = mOnLoadStatusNativeAd;
        onTryFbNativeWithNativeAdsManager(layout,mActivity,mType,5);
    }

    public void onTryFbNativeWithNativeAdsManager(final Activity mActivity, final View layout, final com.facebook.ads.NativeAdView.Type mType, final int totalAdCount, final OnLoadStatusNativeAd mOnLoadStatusNativeAd){
        this.mOnLoadStatusNativeAd = mOnLoadStatusNativeAd;
        onTryFbNativeWithNativeAdsManager(layout,mActivity,mType,totalAdCount);
    }

    public com.facebook.ads.NativeAdsManager getNativeAdsManager(){
        return mNativeAdsManager;
    }

    public void onTryFbNativeWithNativeAdsManager(final View layout,final Activity mActivity, final com.facebook.ads.NativeAdView.Type mType, int totalAdCount){
        this.setCurrentContext(mActivity);
        {
            String fb_native_ad_id = "";
            try {
                if(null!=custom_fb_native_ad_id && custom_fb_native_ad_id.length()>0){
                    fb_native_ad_id = custom_fb_native_ad_id;
                }else {
                    fb_native_ad_id = mActivity.getString(mActivity.getResources().getIdentifier("fb_native_ad_id", "string",
                            mActivity.getApplicationContext().getPackageName()));
                }
            }catch (Exception e){}

            if(fb_native_ad_id == null || fb_native_ad_id.length() == 0){
                if(null!=mOnLoadStatusNativeAd){
                    mOnLoadStatusNativeAd.loadStatusNativeAd(false,layout);
                }
                return;
            }

            mNativeAdsManager = new com.facebook.ads.NativeAdsManager(getCurrentContext(),fb_native_ad_id, totalAdCount);
            mNativeAdsManager.setListener(new com.facebook.ads.NativeAdsManager.Listener(){
                @Override
                public void onAdsLoaded() {
                    if(null!=mNativeAdsManager && mNativeAdsManager.getUniqueNativeAdCount()>0 && null != mOnLoadStatusNativeAd) {
                        mOnLoadStatusNativeAd.loadStatusNativeAd(true, layout);
                    }else{
                        if(null!=mOnLoadStatusNativeAd){
                            mOnLoadStatusNativeAd.loadStatusNativeAd(false,layout);
                        }
                    }
                }
                @Override
                public void onAdError(com.facebook.ads.AdError error) {
                    Log.e(TAG,"onTryFbNativeWithNativeAdsManager onError->"+custom_fb_native_ad_id+">"+error.getErrorMessage());
                    if(null!=mOnLoadStatusNativeAd){
                        mOnLoadStatusNativeAd.loadStatusNativeAd(false,layout);
                    }
                }
            });
            mNativeAdsManager.loadAds(com.facebook.ads.NativeAd.MediaCacheFlag.ALL);
        }
    }
    */

}
