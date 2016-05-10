package yu.phoneshop;

import java.util.HashMap;
import java.util.Map;

import yu.phoneshop.util.CommonUtil;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Userlogin2  extends Activity{
	private Button login;
	/** volley对象*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest; 
    private EditText editTextName;
    private EditText editTextpwd;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login2_activity);
		findViewById(R.id.icon_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		requestQueue = Volley.newRequestQueue(this);
		editTextName = (EditText) findViewById(R.id.editText1);
		editTextpwd = (EditText) findViewById(R.id.editText2);
		login = (Button) findViewById(R.id.btn_login);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(editTextName.getText().toString().equals("")||editTextpwd.getText().toString().equals("")){
					myHandler.sendEmptyMessage(1);
					return;
				}
				stringRequest = new StringRequest(Method.POST, CommonUtil.SERVER_URL1+"UserLongin", new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.e("response", "response"+response);
						if(Boolean.valueOf(response)){
							myHandler.sendEmptyMessage(0);
						}else{
							myHandler.sendEmptyMessage(2);
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("StringRequest", "onErrorResponse"+error.getMessage());
						
					}
				}){
					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						Map<String, String> params = new HashMap<String, String>();
						params.put("name", editTextName.getText().toString());
						params.put("pwd", editTextpwd.getText().toString());
						return params;
					}
				};
				requestQueue.add(stringRequest);
			}
		});
	}
    
	private Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				Toast.makeText(Userlogin2.this, "登录成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("username", editTextName.getText().toString());
				setResult(RESULT_OK,intent);
				finish();
				break;
			case 1:
				Toast.makeText(Userlogin2.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(Userlogin2.this, "请检查用户名或者密码", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
	
}
