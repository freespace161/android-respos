package com.lbb.contact.base;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.lbb.R;
import com.lbb.adapter.CallCursorAdapter;
import com.lbb.common.CallUtil;
import com.lbb.common.Constant;
import com.lbb.contact.DetailList;

/**
 * ListActivity���࣬ʵ����ÿ�е����¼��Ͳ�ѯ���ݷ���
 * @author Snake
 *
 */
public class BaseContactList extends  ListActivity {
	
	protected boolean isDetail;
	/**
	 * ���ݲ�ѯ����������ʽ���������ݲ�ѯ������ѯ���Ľ����䵽list����
	 * @param condition
	 * @param order
	 */
	protected void setListAdapter(String condition, String order){
		if (order == null){
			order = CallLog.Calls.DEFAULT_SORT_ORDER;
		}
		
		Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,
				null, condition, null, order);
		//��cursor�������ڽ���activity������
		startManagingCursor(cursor);
		CallCursorAdapter adapter = new CallCursorAdapter(this,
				R.layout.callinfo, cursor,
				new String[] { "number", "name", "date", "type","duration"},
				new int[] { R.id.TextNumber,R.id.TextName, R.id.TextDuration, R.id.TextType,R.id.TextTime});
		setListAdapter(adapter);
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo); 
		AdapterContextMenuInfo m=(AdapterContextMenuInfo) menuInfo;
		TextView numberText = (TextView)m.targetView.findViewById(R.id.TextNumber);
		menu.setHeaderTitle( numberText.getText());
		if(!isDetail){ //��ʾͳ����Ϣ
		  menu.add(0,0,Menu.NONE,"ͳ����Ϣ"); 
		}
		menu.add(0,1,Menu.NONE,"���Ͷ���Ϣ");
		menu.add(0,2,Menu.NONE,"���"+ numberText.getText());
		menu.add(0,3,Menu.NONE,"���ƺ���");
	}  
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo m=(AdapterContextMenuInfo) item.getMenuInfo();
		TextView numberText = (TextView)m.targetView.findViewById(R.id.TextNumber);
		switch(item.getItemId()){
		case 0: //ͳ����Ϣ
			Intent i=new Intent(this,DetailList.class);
			i.putExtra(Constant.NUMBER, numberText.getText());
			i.putExtra(Constant.CALL_TYPE,getIntent().getStringExtra(Constant.CALL_TYPE));
			startActivity(i);
			break;
		case 1:
			CallUtil.sendMsg(m.targetView.getContext(), numberText.getText().toString());
			break;
		case 2:
			CallUtil.call(m.targetView.getContext(), numberText.getText().toString());
			break;
		case 3:
			CallUtil.copy(m.targetView.getContext(),  numberText.getText().toString());
			break;
		}
		return true;
	}
	protected String chooseCallType(String type){
		String condition="";
		if(type.equals(Constant.ALL_CALLS)){
			condition=" 0==0";
		}else if(type.equals(Constant.RECEIVE_CALLS)){
			condition="type == 1 ";
		}else if(type.equals(Constant.CALLED_CALLS)){
			condition="type == 2 ";
		}else if(type.equals(Constant.MISSED_CALLS)){
			condition=" type == 3 ";
	    }
		return condition;
	}

	public void setDetail(boolean isDetail) {
		this.isDetail = isDetail;
	}
}
