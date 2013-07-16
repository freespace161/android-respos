package com.lbb.adapter;
//Download by http://www.codefans.net
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.lbb.R;

/**
 * 读取数据后，将数据库填充到指定好的行中
 * @author Snake
 *
 */
public class CallCursorAdapter extends ResourceCursorAdapter {
	final int DAY = 1440;				//一天的分钟值
	private int[] mTo;
	private String[] mOriginalFrom;

	public CallCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c);
		mOriginalFrom = from;
		mTo = to;
	}
	
	/**
	 * 绑定数据到视图中
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		View[] views = new View[mTo.length];
		for (int i=0; i<views.length; i++){
			views[i] = view.findViewById(mTo[i]);
			String value = cursor.getString(cursor.getColumnIndex(mOriginalFrom[i]));
			if (views[i] instanceof TextView){
				if ("date".equals(mOriginalFrom[i])){
					long callTime = Long.parseLong(value);
					long duration = (new Date().getTime() - callTime) / (1000*60);
					if (duration < DAY){
						value = dateToStr(new Date(callTime), "HH:mm:ss");
					}else {
						value = dateToStr(new Date(callTime), "yyyy-MM-dd HH:mm:ss");
					}
				}else if ("type".equals(mOriginalFrom[i])){
					int type = Integer.parseInt(value);
					if (CallLog.Calls.INCOMING_TYPE == type){
						view.setBackgroundResource(R.color.incoming);	//更改行颜色
						value = "已接电话";
					}else if (CallLog.Calls.OUTGOING_TYPE == type){
						view.setBackgroundResource(R.color.outgoing);
						value = "已拨电话";
					}else if (CallLog.Calls.MISSED_TYPE == type){
						view.setBackgroundResource(R.color.missed);
						value = "未接电话";
					}
				}else if ("name".equals(mOriginalFrom[i])){
					if (null == value || "".equals(value)){
						value = cursor.getString(cursor.getColumnIndex("number"));
					}
				}else if("duration".equals(mOriginalFrom[i])){
					if (null == value || "".equals(value)){
						value="0''";
					}else{
						value = parseTime(cursor.getLong(cursor.getColumnIndex("duration")));
					}
				}
				
				setText((TextView)views[i], value);
			}
		}
		
		final Context mContext = context;
		final TextView mNumber = (TextView)view.findViewById(R.id.TextNumber);
		ImageView mailButton = (ImageView)view.findViewById(R.id.MailButton);
		if (mailButton != null){
			//为点击短信图标添加触发事件，使其进入发送短信界面
			mailButton.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					Uri smsToUri = Uri.parse("smsto:"+mNumber.getText());
					Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
					mContext.startActivity(smsIntent);
				}
			});
		}
	}
	
	public void setText(TextView v, String text){
		v.setText(text);
	}
	
	private String parseTime(long time){
		String t="";
		long sec=time%60;
		long min=time/60;
		  if(sec==0){
			  t=min+" '";
		  }
		  else{
			  t=min+" ' "+sec+" ''";
		  }
		return t;
	}
	  /**
     * 通过制定的格式，将Date对象格式化为字符串
     * 
     * @param date 需要转换的Date对象
     * @param formatStr 转换的格式
     * @return 转换之后的字符串
     */
    private  String dateToStr(Date date, String formatStr) {
        String result = null;
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            result = sdf.format(date);
        }
        return result;
    }
	
}
