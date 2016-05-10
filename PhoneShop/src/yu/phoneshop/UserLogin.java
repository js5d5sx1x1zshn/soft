package yu.phoneshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserLogin extends Activity{
	private TextView register;
	private TextView login;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login_activity);
		findViewById(R.id.icon_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		register = (TextView) findViewById(R.id.btn_register);
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserLogin.this,Register.class);
				startActivityForResult(intent, 2);
			}
		});
		login = (TextView) findViewById(R.id.btn_login);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserLogin.this,Userlogin2.class);
				startActivityForResult(intent, 1);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
	         Log.e("UserLogin", "UserLogin"+data.getStringExtra("success"));
			 setResult(RESULT_OK,data);
			 finish();
	    }
	}
	
}
