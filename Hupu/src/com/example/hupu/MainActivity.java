package com.example.hupu;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends Activity {

	 private WebView webview = new WebView(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("m.hupu.com");
        this.setContentView(R.layout.activity_main);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
    	if((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()){
    		webview.goBack();
    		return true;
    	}
    	return false;
    }
}
