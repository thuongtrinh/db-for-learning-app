package com.example.projectmaingraduate;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AboutDialog extends Activity {

	private TextView tvFacebook;
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_about);
		
		tvFacebook = (TextView) findViewById(R.id.dialog_about_tv_selected_facebook);
		
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		tvFacebook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url_facebook = "https://www.facebook.com/cellphoneSstore";
				Intent  intent = new Intent(AboutDialog.this, WebviewActivity.class);
				intent.putExtra("urlFacebook", url_facebook);
				startActivityForResult(intent, 1);
			}
		});
		
	}
	
}
