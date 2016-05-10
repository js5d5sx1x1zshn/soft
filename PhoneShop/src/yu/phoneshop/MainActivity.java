package yu.phoneshop;

import java.util.ArrayList;
import java.util.List;
import yu.phoneshop.fragment.ServiceFragment;
import yu.phoneshop.fragment.ShopFragment;
import yu.phoneshop.fragment.SortingFragment;
import yu.phoneshop.util.CommonUtil;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	/** volley对象*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest; 
	/** 主适配器*/
	private MainFragmentPagerAdapter mainFragmentPagerAdapter;
	/** view pager*/
	private ViewPager viewPagerMainContent;
	/** 存放导航按钮*/
	private TextView[] arr_tabs;
	/**购物车*/
	private ImageView imageViewCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest("http://192.168.191.1:8080/ECServer_D/search", new Listener<String>() {

			@Override
			public void onResponse(String response) {
				Log.e("StringRequest", "onResponse"+response);
			}
        	
		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("StringRequest", "onErrorResponse"+error.getMessage());
			}
		});
        initTab();
        imageViewCart = (ImageView) findViewById(R.id.textview4);
        imageViewCart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(CommonUtil.MYUSER.getUserName()==null||CommonUtil.MYUSER.getUserName().equals("")){
					Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(MainActivity.this,ShopCartActivity.class);
				startActivity(intent);
			}
		});
        initView();
//        findViewById(R.id.search).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				requestQueue.add(stringRequest);
//			}
//		});
    }
    /**
	 * 初始化导航点
	 */
    private void initTab(){
    	arr_tabs = new TextView[3];
    	TextView textView1 = (TextView) findViewById(R.id.textview1);
    	arr_tabs[0] = textView1;
    	TextView textView2 = (TextView) findViewById(R.id.textview2);
    	arr_tabs[1] = textView2;
    	TextView textView3 = (TextView) findViewById(R.id.textview3);
    	arr_tabs[2] = textView3;
    	for(int i=0;i<arr_tabs.length;i++){
    		arr_tabs[i].setTextColor(Color.GRAY);
    		if (i == 0) {
    			arr_tabs[i].setEnabled(false);
    			arr_tabs[i].setTextColor(Color.parseColor("#EB5D08"));
			} else {
				arr_tabs[i].setEnabled(true);
//				arr_tabs[i].setBackgroundColor(Color.parseColor("#ebebeb"));
			}
    		arr_tabs[i].setTag(i);
    		arr_tabs[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					viewPagerMainContent.setCurrentItem((Integer) v.getTag());
				}
			});
    	}
    }
    /**
     * 初始化View page
     */
    private void initView(){
    	List<Fragment> fragments = new ArrayList<Fragment>();
    	ShopFragment shopFragment = new ShopFragment();
    	SortingFragment sortingFragment = new SortingFragment();
    	ServiceFragment serviceFragment = new ServiceFragment();
    	fragments.add(shopFragment);
    	fragments.add(sortingFragment);
    	fragments.add(serviceFragment);
    	mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragments);
    	viewPagerMainContent = (ViewPager) findViewById(R.id.viewPager_main_content);
    	viewPagerMainContent.setAdapter(mainFragmentPagerAdapter);
    	viewPagerMainContent.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				for (int i = 0; i < arr_tabs.length; i++) {
					if (i == arg0) {
						arr_tabs[i].setEnabled(false);
						arr_tabs[i].setTextColor(Color.parseColor("#EB5D08"));
					} else {
						arr_tabs[i].setEnabled(true);
						arr_tabs[i].setTextColor(Color.GRAY);
//						arr_tabs[i].setBackgroundColor(Color
//								.parseColor("#ebebeb"));
					}
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    
    /**
     * 
     * @author y
     *  适配器
     */
    public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    	private final String TAG = "MainFragmentPagerAdapter";
    	private List<Fragment> list = null;

    	public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
    		super(fm);
    		this.list = list;
    	}

    	@Override
    	public Fragment getItem(int arg0) {
    		// TODO Auto-generated method stub
    		return list.get(arg0);
    	}

    	@Override
    	public Object instantiateItem(View container, int position) {
    		Log.i(TAG, "==instantiateItem()");
    		return super.instantiateItem(container, position);
    	}

    	@Override
    	public int getCount() {
    		return list.size();
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
