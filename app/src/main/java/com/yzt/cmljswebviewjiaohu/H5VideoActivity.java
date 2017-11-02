package com.yzt.cmljswebviewjiaohu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class H5VideoActivity extends AppCompatActivity {

    private WebView mWebView;
    private String url = "http://baishi.baidu.com/watch/5242047883246606673.html?frm=FuzzySearch&page=videoMultiNeed";
    private SeekBar mSeekBar;
    private RelativeLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5_video);


        mWebView = (WebView) findViewById(R.id.webview);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mFrameLayout = (RelativeLayout) findViewById(R.id.frameLayout);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(chromeClient);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }

        });
        mWebView.loadUrl(url);
    }

    View videoView;
    WebChromeClient.CustomViewCallback mCustomViewCallback;
    WebChromeClient chromeClient = new WebChromeClient(){
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            Log.e("CML","开启全屏播放后调用");
            if(videoView !=null){
                callback.onCustomViewHidden();
                return;
            }

            videoView = view;
            mFrameLayout.addView(videoView);
            mCustomViewCallback = callback;
            mWebView.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }


        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            Log.e("CML","关闭全屏播放后调用");
            mWebView.setVisibility(View.VISIBLE);
            if(videoView == null){
                return;
            }
            videoView.setVisibility(View.GONE);
            mFrameLayout.removeView(videoView);
            mCustomViewCallback.onCustomViewHidden();
            videoView = null;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }



        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mSeekBar.setProgress(newProgress);
        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Window window = getWindow();
        switch (newConfig.orientation){
            case Configuration.ORIENTATION_LANDSCAPE:
                Log.e("CML","开启全屏播放后调用且在onShowCustomView()之后调用");
                window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                Log.e("CML","关闭全屏播放后调用且在onHideCustomView()之后调用");
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onBackPressed() {
        if(videoView != null){
            chromeClient.onHideCustomView();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }
}
