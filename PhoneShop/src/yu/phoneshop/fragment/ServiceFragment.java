package yu.phoneshop.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yu.phoneshop.AdressManagement;
import yu.phoneshop.AllOrdersActivity;
import yu.phoneshop.R;
import yu.phoneshop.Settlement;
import yu.phoneshop.UserLogin;
import yu.phoneshop.util.CommonUtil;
import yu.phoneshop.view.MyListview;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceFragment extends Fragment{
	
	private MyListview myListview;
	private RelativeLayout relativeLogin;
	private String username = "";
	private TextView textView;
	private SharedPreferences myPreferences;
	private Editor editor;
	private TextView quit_textview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myPreferences = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
		editor = myPreferences.edit();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		username = myPreferences.getString("username", "");
		CommonUtil.MYUSER.setUserName(username);
		View view = inflater.inflate(yu.phoneshop.R.layout.service_fragment, null);
		relativeLogin = (RelativeLayout) view.findViewById(R.id.relative_user_back);
		relativeLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),UserLogin.class);
				startActivityForResult(intent, 3);
			}
		});
		textView = (TextView) view.findViewById(R.id.text);
		quit_textview = (TextView) view.findViewById(R.id.text_quit);
		quit_textview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editor.putString("username", "");
		        editor.commit();
		        username = "";
		        CommonUtil.MYUSER.setUserName(username);
		        textView.setText("");
		        quit_textview.setText("点击登录");
		        quit_textview.setClickable(false);
		        relativeLogin.setClickable(true);
			}
		});
		if(!username.equals("")){
			textView.setText(""+username+",");
			quit_textview.setText("退出登录");
			relativeLogin.setClickable(false);
		}else{
			textView.setText("");
			quit_textview.setText("点击登录");
			quit_textview.setClickable(false);
			relativeLogin.setClickable(true);
		}
		myListview = (MyListview) view.findViewById(R.id.list_items);
		List<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();
		
		HashMap<String, String> item = new HashMap<String, String>();
		
		item = new HashMap<String, String>();
		item.put("name", "全部订单");
		data.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", "待付款");
		data.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", "待收货");
		data.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", "返修/退换");
		data.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", "已完成订单");
		data.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", "管理我的收货地址");
		data.add(item);
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), data, R.layout.user_list_item, 
				new String[]{"name"}, new int[]{R.id.item_name});
		myListview.setAdapter(simpleAdapter);
		myListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.e("onItemClick", "onItemClick"+position);
				if(CommonUtil.MYUSER.getUserName().equals("")){
					Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
					return;
				}
				switch (position) {
				case 0:
					//查看年全部订单
					Intent intent = new Intent(getActivity(),AllOrdersActivity.class);
					intent.putExtra("orderurl", 0);
					startActivity(intent);
					break;
				case 1:
					Intent intent2 = new Intent(getActivity(),AllOrdersActivity.class);
					intent2.putExtra("orderurl", 1);
					startActivity(intent2);
					break;
				case 2:
					Intent intent3 = new Intent(getActivity(),AllOrdersActivity.class);
					intent3.putExtra("orderurl", 2);
					startActivity(intent3);
					break;
				case 3:
					Intent intent5 = new Intent(getActivity(),AllOrdersActivity.class);
					intent5.putExtra("orderurl", 4);
					startActivity(intent5);
					break;
				case 4:
					Intent intent6 = new Intent(getActivity(),AllOrdersActivity.class);
					intent6.putExtra("orderurl", 5);
					startActivity(intent6);
					break;
				case 5:
					Intent intent7 = new Intent(getActivity(),AdressManagement.class);
					startActivity(intent7);
					break;
				default:
					break;
				}
			}
		});
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == getActivity().RESULT_OK) {
	         Log.e("ServiceFragment", "ServiceFragment"+data.getStringExtra("username"));
	         username = data.getStringExtra("username");
	         editor.putString("username", username);
	         editor.commit();
	         CommonUtil.MYUSER.setUserName(username);
	         textView.setText(""+username+",");
	         quit_textview.setText("退出登录");
	         quit_textview.setClickable(true);
	         relativeLogin.setClickable(false);
	    }
	}
	
	private void init(){
		
	}
}
