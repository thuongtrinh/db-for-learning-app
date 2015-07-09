package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.MessageDrawerListAdapter;
import com.example.fragment.AdminShowMessageReceived;
import com.example.object.AccountUser;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class FeedbackManagementActivity extends FragmentActivity{
	
	private DrawerLayout drawerLayout;
	private ListView lvMessageUser;
	private ActionBarDrawerToggle barDrawerToggle;
	public static MessageDrawerListAdapter adapter;  
	public static ArrayList<AccountUser> list = new ArrayList<AccountUser>();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_manager);
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		lvMessageUser = (ListView) findViewById(R.id.activity_feedback_manager_lv_message);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		barDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				invalidateOptionsMenu();
			}
			
			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				adapter.notifyDataSetChanged();
				invalidateOptionsMenu();
			}
		};
		
		drawerLayout.setDrawerListener(barDrawerToggle);
		barDrawerToggle.isDrawerIndicatorEnabled();
		
		lvMessageUser.setOnItemClickListener(new SlideMenuClickListener());
		
		list = ParseApplication.getAllInforUser(); 
		if(list.size() > 0) {
			adapter = new MessageDrawerListAdapter(FeedbackManagementActivity.this, list);
			lvMessageUser.setAdapter(adapter);
		}
		
		if(savedInstanceState == null) {
			 displayView(0);
		}
		
	}
	
	
	private class SlideMenuClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	public void displayView(int position) {

		Fragment fragment = new AdminShowMessageReceived(list.get(position), position);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.activity_feedback_manager_frame_container, fragment).commit();
		// update selected item and title, then close the drawer
		lvMessageUser.setItemChecked(position, true);
		lvMessageUser.setSelection(position);
		drawerLayout.closeDrawer(lvMessageUser);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(barDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		barDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		barDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	
	
}






