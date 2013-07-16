package com.lbb.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.lbb.R;
import com.lbb.common.Constant;
import com.lbb.contact.base.BaseContactList;

public class DetailList extends BaseContactList {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_layout);
		TextView type=(TextView) findViewById(R.id.call_type);
		TextView number=(TextView) findViewById(R.id.cal_number);
//		TextView duration=(TextView) findViewById(R.id.call_duration);
//		TextView user=(TextView) findViewById(R.id.user);
		this.registerForContextMenu(getListView());
		setDetail(true);
		
		Intent intent=getIntent();
		String typeStr=intent.getStringExtra(Constant.CALL_TYPE);
		String num=intent.getStringExtra(Constant.NUMBER);
		type.setText("当前类型: "+typeStr);
		number.setText("当前号码: "+num);
		
		String con=chooseCallType(typeStr);
		setListAdapter(con+" and number =="+num, null);
	}
}
