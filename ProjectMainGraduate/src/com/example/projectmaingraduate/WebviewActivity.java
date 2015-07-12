package com.example.projectmaingraduate;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewActivity extends Activity{
	
	private WebView webView;
	private ActionBar actionBar;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		webView = (WebView) findViewById(R.id.activity_webview);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new HelloWebViewClient());
		Intent intent = getIntent();
		String url = intent.getExtras().getString("urlFacebook");
		webView.loadUrl(url);
		
	}
	
	private class HelloWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
	
}
