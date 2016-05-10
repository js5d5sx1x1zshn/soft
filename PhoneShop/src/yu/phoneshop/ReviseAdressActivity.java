package yu.phoneshop;

import java.util.HashMap;
import java.util.Map;

import yu.phoneshop.util.CommonUtil;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReviseAdressActivity extends Activity{
	
	private EditText editTextReciever;
	private EditText editTextPhone;
	private EditText editTextAdress;
	private Button buttonSave;
	private int adressId;
	/** volley对象*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.revise_adress_activity);
		findViewById(R.id.icon_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		requestQueue = Volley.newRequestQueue(this);
		editTextReciever = (EditText) findViewById(R.id.edit_reciever);
		editTextPhone = (EditText) findViewById(R.id.edit_phone);
		editTextAdress = (EditText) findViewById(R.id.edit_adress);
		buttonSave = (Button) findViewById(R.id.btn_save);
		if(getIntent().getStringExtra("adressid")!=null){
			//修改
			buttonSave.setText("保存");
			adressId = Integer.parseInt(getIntent().getStringExtra("adressid"));
			editTextAdress.setText(getIntent().getStringExtra("adress"));
			editTextPhone.setText(getIntent().getStringExtra("phone"));
			editTextReciever.setText(getIntent().getStringExtra("reciever"));
			buttonSave.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(editTextAdress.getText().toString().equals("")||
							editTextPhone.getText().toString().equals("")||
							editTextReciever.getText().toString().equals("")){
						return;
					}
					updateAdressToServer();
				}
			});
		}else{
			//新建
			buttonSave.setText("新建");
			buttonSave.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(editTextAdress.getText().toString().equals("")||
							editTextPhone.getText().toString().equals("")||
							editTextReciever.getText().toString().equals("")){
						return;
					}
					creatAdress();
				}
			});
		}
	}
	private Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				finish();
				break;
			case 1:
				Toast.makeText(ReviseAdressActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(ReviseAdressActivity.this, "更改失败", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
	private void updateAdressToServer(){
		stringRequest = new StringRequest(Method.POST, CommonUtil.SERVER_URL1+"UpdateAdress", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(response.equals("1")){
					myHandler.sendEmptyMessage(0);
				}else if(response.equals("0")){
					myHandler.sendEmptyMessage(2);
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				myHandler.sendEmptyMessage(1);
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("adress", editTextAdress.getText().toString());
				params.put("phone", editTextPhone.getText().toString());
				params.put("reciever", editTextReciever.getText().toString());
				params.put("id", ""+adressId);
				return params;
			}
		};
		requestQueue.add(stringRequest);
	}
	
	private void creatAdress(){
		stringRequest = new StringRequest(Method.POST, CommonUtil.SERVER_URL1+"CreateAdress", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(response.equals("1")){
					myHandler.sendEmptyMessage(0);
				}else if(response.equals("0")){
					myHandler.sendEmptyMessage(2);
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				myHandler.sendEmptyMessage(1);
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("adress", editTextAdress.getText().toString());
				params.put("phone", editTextPhone.getText().toString());
				params.put("reciever", editTextReciever.getText().toString());
				params.put("userid", CommonUtil.MYUSER.getUserName());
				return params;
			}
		};
		requestQueue.add(stringRequest);
	}
}
