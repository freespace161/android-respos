package com.lbb.portal;

import android.app.ActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

import com.lbb.R;
import com.lbb.common.Constant;
import com.lbb.contact.ContactList;

public class CalllogTabs extends TabActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final TabHost host = getTabHost();
		
		Intent intentAll=new Intent(this, ContactList.class);
		intentAll.putExtra(Constant.CALL_TYPE, Constant.ALL_CALLS);
		host.addTab(host.newTabSpec("all")
					.setIndicator("ALL", getResources().getDrawable(R.drawable.mycontact))
					.setContent(intentAll));
		
		Intent intentReceived=new Intent(this, ContactList.class);
		intentReceived.putExtra(Constant.CALL_TYPE, Constant.RECEIVE_CALLS);
		host.addTab(host.newTabSpec("received")
				.setIndicator("已接", getResources().getDrawable(R.drawable.strangercontact))
				.setContent(intentReceived));
		
		Intent intentCalled=new Intent(this, ContactList.class);
		intentCalled.putExtra(Constant.CALL_TYPE, Constant.CALLED_CALLS);
		host.addTab(host.newTabSpec("called")
				.setIndicator("已拨", getResources().getDrawable(R.drawable.alwayscontact))
				.setContent(intentCalled));
		
		Intent intentMissed=new Intent(this, ContactList.class);
		intentMissed.putExtra(Constant.CALL_TYPE, Constant.MISSED_CALLS);
		host.addTab(host.newTabSpec("missed")
				.setIndicator("未接", getResources().getDrawable(R.drawable.alwayscontact))
				.setContent(intentMissed));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		   finish();
		   ActivityManager activityMgr= (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		   activityMgr.restartPackage(getPackageName());
         
	}
}
