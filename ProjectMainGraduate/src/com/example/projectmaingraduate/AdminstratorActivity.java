package com.example.projectmaingraduate;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class AdminstratorActivity extends Activity {

	private ImageButton imbtnCustomer, imbtnProducts, imbtnIncome,
			imbtnFeedback, imbtnPromotion, imbtnDelivery;
	private static NotificationManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adminstrator);
		
		initiation();
		
		// check admin have message 
		boolean message = ParseApplication.CheckMessageAdmin();
		if(message) {
			sendNotification("Xem chi tiết tin nhắn");
		}
		
		imbtnCustomer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		imbtnProducts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AdminstratorActivity.this, ProductManagementActivity.class);
				startActivity(intent);
			}
		});
		
		imbtnFeedback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AdminstratorActivity.this, FeedbackManagementActivity.class);
				startActivity(intent);
			}
		});
		
		imbtnCustomer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AdminstratorActivity.this, CustomerManagementActivity.class);
				startActivity(intent);
			}
		});
		
		imbtnDelivery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AdminstratorActivity.this, DeliveryManagementActivity.class);
				startActivity(intent);
			}
		});
		
		imbtnIncome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AdminstratorActivity.this, IncomeManagementActivity.class);
				startActivity(intent);
			}
		});
		
	}

	private void initiation() {
		// TODO Auto-generated method stub
		imbtnCustomer = (ImageButton) findViewById(R.id.activity_adminstrator_imgbtn_customer);
		imbtnProducts = (ImageButton) findViewById(R.id.activity_adminstrator_imgbtn_products);
		imbtnIncome = (ImageButton) findViewById(R.id.activity_adminstrator_imgbtn_payment);
		imbtnFeedback = (ImageButton) findViewById(R.id.activity_adminstrator_imgbtn_feedback);
		imbtnDelivery = (ImageButton) findViewById(R.id.activity_adminstrator_imgbtn_delivery);
	}
	
	private void sendNotification(String text) {
        // create the intent for the notification
        Intent notificationIntent = new Intent(this, FeedbackManagementActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
        // create the pending intent
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent pendingIntent = 
                PendingIntent.getActivity(this, 0, notificationIntent, flags);
        
        // create the variables for the notification
        int icon = R.drawable.ic_envelope;
        CharSequence tickerText = "Admin có tin nhắn từ khách hàng!";
        CharSequence contentTitle = getText(R.string.app_name);
        CharSequence contentText = text;

        // create the notification and set its data
        Notification notification = 
                new NotificationCompat.Builder(this)
            .setSmallIcon(icon)
            .setTicker(tickerText)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build();
        
        // display the notification
        manager = (NotificationManager) 
                getSystemService(NOTIFICATION_SERVICE);
        final int NOTIFICATION_ID = 1;
        manager.notify(NOTIFICATION_ID, notification);
    }
	
	public static void CancelNotificationMessage() {
		manager.cancelAll();
	}

}










