package yu.phoneshop.util;

import yu.phoneshop.vo.User;
import android.content.Context;

public class CommonUtil {
	/**用户已登陆信息*/
	public static final User MYUSER = new User();  
	/**访问服务器地址1*/
	public static final String SERVER_URL1 = "http://192.168.191.1:8080//ECServer_D/";
	/**
	* 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	*/
	public static int dip2px(Context context, float dpValue) {
	  final float scale = context.getResources().getDisplayMetrics().density;
	  return (int) (dpValue * scale + 0.5f);
	}

	/**
	* 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	*/
	public static int px2dip(Context context, float pxValue) {
	  final float scale = context.getResources().getDisplayMetrics().density;
	  return (int) (pxValue / scale + 0.5f);
	}
	
}
