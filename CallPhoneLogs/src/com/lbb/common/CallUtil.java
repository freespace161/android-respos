package com.lbb.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.ClipboardManager;

public class CallUtil {

	public static void sendMsg(Context  ctx,String number){
		Uri smsToUri = Uri.parse("smsto:"+number);
		Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
		ctx.startActivity(smsIntent);
	}
	
	public static void call(Context ctx,String number){
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
		ctx.startActivity(callIntent);
	}
	
	public static void copy(Context context,String content)  
	{  
		ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
		cm.setText(content.trim());  
	} 
}
