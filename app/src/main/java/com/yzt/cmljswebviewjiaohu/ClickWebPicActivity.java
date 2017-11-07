package com.yzt.cmljswebviewjiaohu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.SeekBar;
import android.widget.Toast;

public class ClickWebPicActivity extends AppCompatActivity {

    private WebView mWebView;
    String url = "https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_15015129107283868386%22%7D&n_type=0&p_from=1";
    private SeekBar mSeekBar;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_web_pic);

        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mWebView = (WebView) findViewById(R.id.webview);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mSeekBar.setProgress(newProgress);
            }
        });

        mWebView.loadUrl(url);

        mWebView.addJavascriptInterface(new JavascriptInterface(this),"imagelistner");

        mWebView.setWebViewClient(new MyWebViewClient());
    }

    public class JavascriptInterface{

        private Context context;

        public JavascriptInterface(Context context){
            this.context = context;
        }

        @android.webkit.JavascriptInterface //这个注解必须加上，否则无效
        public void openImage(String img){
            Toast.makeText(context, "pic的图片地址："+img, Toast.LENGTH_LONG).show();
        }
    }

    private class MyWebViewClient extends WebViewClient{


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            addImageClickListener();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }
    }

    private void addImageClickListener() {
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }
}
