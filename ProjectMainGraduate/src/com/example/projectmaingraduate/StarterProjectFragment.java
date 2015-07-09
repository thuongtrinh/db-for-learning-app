package com.example.projectmaingraduate;

import com.example.adapter.TabsPagerAdapter;
import com.parse.ParseAnalytics;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StarterProjectFragment extends Fragment implements
		ActionBar.TabListener {
	/** Called when the activity is first created. */

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	private String[] tabs = { "Điện thoại", "Máy tính bảng", "Phụ kiện", "Quà tặng" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.activity_main, container, false);

		ParseAnalytics.trackAppOpenedInBackground(getActivity().getIntent());

		// Initilization
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		actionBar = getActivity().getActionBar();
		mAdapter = new TabsPagerAdapter(getFragmentManager());

		viewPager.setAdapter(mAdapter);
		// actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		return view;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}


































// @Override
// public void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.activity_main);
// ParseAnalytics.trackAppOpenedInBackground(getIntent());
//
// // Initilization
// viewPager = (ViewPager) findViewById(R.id.pager);
// actionBar = getActionBar();
// mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
//
// viewPager.setAdapter(mAdapter);
// // actionBar.setHomeButtonEnabled(false);
// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
// // Adding Tabs
// for (String tab_name : tabs) {
// actionBar.addTab(actionBar.newTab().setText(tab_name)
// .setTabListener(this));
// }
//
// /**
// * on swiping the viewpager make respective tab selected
// * */
// viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
// @Override
// public void onPageSelected(int position) {
// // on changing the page
// // make respected tab selected
// actionBar.setSelectedNavigationItem(position);
// }
//
// @Override
// public void onPageScrolled(int arg0, float arg1, int arg2) {
// }
//
// @Override
// public void onPageScrollStateChanged(int arg0) {
// }
// });
// FragmentManager fragmentManager = getSupportFragmentManager();
// }
//
// @SuppressWarnings("deprecation")
// @Override
// public void onTabSelected(Tab tab, FragmentTransaction ft) {
// viewPager.setCurrentItem(tab.getPosition());
// }
//
// @Override
// public void onTabUnselected(Tab tab, FragmentTransaction ft) {
// // TODO Auto-generated method stub
//
// }
//
// @Override
// public void onTabReselected(Tab tab, FragmentTransaction ft) {
// // TODO Auto-generated method stub
//
// }
//
// }

// protected void loadPhone(ParseObject phoneType) {
// ParseQuery<ParseObject> query = ParseQuery.getQuery("Phone");
// query.whereEqualTo("PhoneType", phoneType);
// query.findInBackground(new FindCallback<ParseObject>() {
//
// @Override
// public void done(List<ParseObject> objects, ParseException e) {
// for (ParseObject obj : objects) {
// System.out.println(obj.get("Name").toString());
// }
// }
// });
// }

// private SaveCallback callback = new SaveCallback() {
//
// @Override
// public void done(ParseException e) {
// if (e == null) {
// System.out.println("Saved");
// } else {
// System.out.println("Can't save");
// e.printStackTrace();
// }
// }
// };

