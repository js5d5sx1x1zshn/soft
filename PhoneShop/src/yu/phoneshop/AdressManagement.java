package yu.phoneshop;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yu.phoneshop.util.CommonUtil;
import yu.phoneshop.vo.OrderForm;
import yu.phoneshop.vo.ShouyeGuanggao;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdressManagement extends Activity{
	/**底部线性的上部分，动态调正大小*/
	private RelativeLayout relativeLayoutTop;
	/**底部的线性布局*/
	private LinearLayout linearLayout_botton;
	private ListView myListView;
	private MyAdapter myAdapter;
	/** volley对象*/
	private RequestQueue requestQueue;
	private StringRequest stringRequest;
	/**地址集合*/
	private List<OrderForm> forms;
	/**按钮 新建地址*/
	private Button createAdress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adress_management_activity);
		findViewById(R.id.icon_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		forms = new ArrayList<OrderForm>();
		requestQueue = Volley.newRequestQueue(this);
		createAdress = (Button) findViewById(R.id.btn_create_adress);
		createAdress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AdressManagement.this,ReviseAdressActivity.class);
				startActivity(intent);
			}
		});
		relativeLayoutTop = (RelativeLayout) findViewById(R.id.relative_top2);
		linearLayout_botton = (LinearLayout) findViewById(R.id.linearLayout2);
		//设置上部相对布局的高度
		relativeLayoutTop.setLayoutParams(new FrameLayout.LayoutParams
				(LayoutParams.MATCH_PARENT,getTopHeight()));
		myListView = (ListView) findViewById(R.id.adress_listview);
		myAdapter = new MyAdapter(this);
		myListView.setAdapter(myAdapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}
	private class MyAdapter extends BaseAdapter{
		private Context context;
		private MyTag myTag;
		public MyAdapter(Context context) {
			
			this.context = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return forms.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return forms.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView==null){
				convertView = LayoutInflater.from(context).inflate(R.layout.adress_management_item, parent, false);
				myTag = new MyTag();
				myTag.name = (TextView) convertView.findViewById(R.id.reciever);
				myTag.phone = (TextView) convertView.findViewById(R.id.phone);
				myTag.adress = (TextView) convertView.findViewById(R.id.adress);
				myTag.check = (ImageView) convertView.findViewById(R.id.adress_icon_choose);
				myTag.relativeLayoutBack = (RelativeLayout) convertView.findViewById(R.id.relative_back_setdefault);
				myTag.relativeLayoutBack.setTag(position);
				myTag.relativeLayoutBack.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.e("relativeLayoutBack", "getView"+v.getTag());
						changeAdress(forms.get((Integer) v.getTag()).getId(),CommonUtil.MYUSER.getUserName(),(Integer) v.getTag());
					}
				});
				myTag.iamgeRevise = (ImageView) convertView.findViewById(R.id.revise_adress);
				myTag.iamgeRevise.setTag(position);
				myTag.iamgeRevise.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(AdressManagement.this,ReviseAdressActivity.class);
						int id = Integer.parseInt(v.getTag().toString());
						intent.putExtra("adressid", ""+forms.get(id).getId());
						intent.putExtra("adress",forms.get(id).getAdress());
						intent.putExtra("phone", forms.get(id).getPhone());
						intent.putExtra("reciever", forms.get(id).getReciever());
						startActivity(intent);
					}
				});
				convertView.setTag(myTag);
			}else {
				myTag = (MyTag) convertView.getTag();
			}
			myTag.name.setText(forms.get(position).getReciever());
			myTag.phone.setText(forms.get(position).getPhone());
			myTag.adress.setText(forms.get(position).getAdress());
			if(forms.get(position).getIsDefault().endsWith("1")){
				myTag.check.setVisibility(View.VISIBLE);
			}else{
				myTag.check.setVisibility(View.GONE);
			}
			return convertView;
		}
		private class MyTag{
			TextView name;
			TextView phone;
			TextView adress;
			ImageView check;
			/**点击后修改默认选择项并返回*/
			RelativeLayout relativeLayoutBack;
			ImageView iamgeRevise;
		}
	}
	
	/**
	 * 加载收货地址列表
	 */
	private void initData(){
		stringRequest = new StringRequest(CommonUtil.SERVER_URL1+"OrderForm?id="+CommonUtil.MYUSER.getUserName()+"", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				JSONArray jsonArray;
				try {
					jsonArray = new JSONArray(response);
					List<OrderForm> f = new ArrayList<OrderForm>();
					for(int i=0;i<jsonArray.length();i++){
						OrderForm form = new OrderForm(jsonArray.getJSONObject(i).getString("userid"),
								jsonArray.getJSONObject(i).getString("reciever"), jsonArray.
								getJSONObject(i).getString("phone"), jsonArray.getJSONObject(i)
								.getString("adress"), jsonArray.getJSONObject(i).getString("isDefault"),
								jsonArray.getJSONObject(i).getInt("id"));
						f.add(form);
					}
					if(!f.isEmpty()){
						forms.clear();
						forms.addAll(f);
						myHandler.sendEmptyMessage(0);
					}
				} catch (JSONException e) {
					Log.e("JSONException","JSONException"+e.toString());
				}
			}
		},new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("StringRequest", "onErrorResponse"+error.getMessage());
				myHandler.sendEmptyMessage(1);
			}
		});
		requestQueue.add(stringRequest);
	}
	/**
	 * 改变默认收货地址
	 */
	private void changeAdress(int id,String userid,final int position){
		requestQueue.add(new StringRequest(CommonUtil.SERVER_URL1+"SetDefaultAdress?id="+id+"&userid='"+userid+"'", new Listener<String>() {
			@Override
			public void onResponse(String response) {
				Message message = new Message();
				message.what = 2;
				message.arg1 = position;
				myHandler.sendMessage(message);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				myHandler.sendEmptyMessage(1);
			}
		}));
	}
	/**
	 * handler
	 * */
	private Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(AdressManagement.this, "下载数据失败", Toast.LENGTH_SHORT).show();
				break;
			case 2:
//				for(int i=0;i<forms.size();i++){
//					if(i==msg.arg1){
//						Log.e("aeg1", "arg1"+msg.arg1);
//						forms.get(msg.arg1).setIsDefault("1");
//					}else{
//						forms.get(msg.arg1).setIsDefault("0");
//					}
//				}
//				myAdapter.notifyDataSetChanged();
				finish();
				break;
			default:
				break;
			}
		}
	};
	private int getTopHeight(){
		int s = getWindowManager().getDefaultDisplay().getHeight();
		findViewById(R.id.framelayout_products).measure(0, 0);
		linearLayout_botton.measure(0, 0);
		Log.e("height","height"+getStatusheight());
			 	Log.e("height", "height"+(s-linearLayout_botton.getMeasuredHeight()-getStatusheight()));
		return s-linearLayout_botton.getMeasuredHeight()-getStatusheight();
	}
	/**
	 * 获取状态栏高度
	 * @return 高度
	 */
	 private int getStatusheight(){
		 int statusHeight = 0;
	        Rect localRect = new Rect();
	        getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
	        statusHeight = localRect.top;
	        if (0 == statusHeight){
	            Class<?> localClass;
	            try {
	                localClass = Class.forName("com.android.internal.R$dimen");
	                Object localObject = localClass.newInstance();
	                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
	                statusHeight = getResources().getDimensionPixelSize(i5);
	            } catch (ClassNotFoundException e) {
	                e.printStackTrace();
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            } catch (InstantiationException e) {
	                e.printStackTrace();
	            } catch (NumberFormatException e) {
	                e.printStackTrace();
	            } catch (IllegalArgumentException e) {
	                e.printStackTrace();
	            } catch (SecurityException e) {
	                e.printStackTrace();
	            } catch (NoSuchFieldException e) {
	                e.printStackTrace();
	            }
	        }
	        return statusHeight;
	 }
}
