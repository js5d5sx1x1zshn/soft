package yu.phoneshop;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class IndexActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_activity);
		Timer timer = new Timer();
        TimerTask task = new TimerTask() {
	         @Override
	         public void run() {
	        	 Intent intent = new Intent(IndexActivity.this, MainActivity.class); 
	        	 startActivity(intent);
	        	 IndexActivity.this.finish();
	         }
        };
        timer.schedule(task, 1000);
	}
	
}
