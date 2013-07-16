package com.lbb.contact;
//Download by http://www.codefans.net
import android.os.Bundle;
import android.provider.CallLog;
import android.view.Window;

import com.lbb.R;
import com.lbb.common.Constant;
import com.lbb.contact.base.BaseContactList;

public class ContactList extends BaseContactList {
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_layout);
		String type=getIntent().getStringExtra(Constant.CALL_TYPE);
		String condition=chooseCallType(type);
		 
		setListAdapter(condition+") GROUP BY ("+CallLog.Calls.NUMBER, null);
		this.registerForContextMenu(getListView());
		setDetail(false);
	}
	
		

	
}
