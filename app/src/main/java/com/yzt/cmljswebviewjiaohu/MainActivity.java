package com.yzt.cmljswebviewjiaohu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.yzt.cmljswebviewjiaohu.R.id.webview;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webView;
    private TextView textView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button show = (Button) findViewById(R.id.show);
        Button sendToJs = (Button) findViewById(R.id.sendToJs);
        textView = (TextView) findViewById(R.id.textView);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        show.setOnClickListener(this);
        sendToJs.setOnClickListener(this);

        webView = (WebView) findViewById(webview);
        webView.addJavascriptInterface(new JSBridge(), "android");




        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                seekBar.setProgress(newProgress);
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show:
                showHtml();
                break;
            case R.id.sendToJs:
                sendToJs();
                break;
        }
    }

    private void sendToJs() {
        webView.loadUrl("javascript:show('fdlkajf')");
    }

    private void showHtml() {
        webView.loadUrl("file:///android_asset/demo.html");
    }



    public class JSBridge{

        @JavascriptInterface
        public void showMessage(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            showToTextView(message);
        }
    }

    private void showToTextView(String str) {
        Message msg = new Message();
        msg.what = 1;
        msg.obj = str;
        handler.sendMessage(msg);
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                String message = (String) msg.obj;
                textView.setText(message);
            }
        }
    };

}
