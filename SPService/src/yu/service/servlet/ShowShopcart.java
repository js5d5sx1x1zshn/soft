package yu.service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.redbaby.service.CommonUtil;

import net.sf.json.JSONArray;

import yu.service.mysql.ShopCart;

public class ShowShopcart extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userid = req.getParameter("userid");
		System.out.println(userid);
		ShopCart cart = new ShopCart();
    	JSONArray jsonArray = JSONArray.fromObject(cart.showShowCart(userid));
    	CommonUtil.render(resp, "application/json", jsonArray.toString());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
}
