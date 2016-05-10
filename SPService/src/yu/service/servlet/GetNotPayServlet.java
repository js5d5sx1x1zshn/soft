package yu.service.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import yu.service.mysql.ShopOrder;
import yu.service.vo.Order;

import com.itheima.redbaby.service.CommonUtil;

public class GetNotPayServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userid = req.getParameter("userid");
		ShopOrder order = new ShopOrder();
    	ArrayList<Order> orders = order.getWaitPayByOreder(userid);
    	ArrayList<Order> orders2 = new ArrayList<Order>();
    	for(int i=orders.size()-1;i>=0;i--){
    		orders2.add(orders.get(i));
    	}
    	JSONArray jsonArrayOrders = JSONArray.fromObject(orders2);
    	JSONArray jsonArrayShoplist = new JSONArray();
    	for(int i=0;i<jsonArrayOrders.size();i++){
    		jsonArrayShoplist.add(JSONArray.fromObject(jsonArrayOrders.getJSONObject(i).get("shoplist")));
    		System.out.println(JSONArray.fromObject(jsonArrayOrders.getJSONObject(i).get("shoplist")));
    		jsonArrayOrders.getJSONObject(i).remove("shoplist");
    	}
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.element("shoplist", jsonArrayShoplist);
    	jsonObject.element("orders", jsonArrayOrders);
    	String jsonString = jsonObject.toString();
    	CommonUtil.render(resp, "application/json", jsonString);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}
}
