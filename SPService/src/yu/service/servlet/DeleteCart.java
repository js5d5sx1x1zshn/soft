package yu.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yu.service.mysql.ShopCart;

import net.sf.json.JSONArray;

public class DeleteCart extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String zhishi = req.getParameter("deletes");
		ShopCart shopCart = new ShopCart();
		System.out.println(zhishi);
		JSONArray array = JSONArray.fromObject(zhishi);
		boolean flag = false;
		for(int i=0;i<array.size();i++){
			flag = shopCart.deleteCart(array.getJSONObject(i).getString("zhishi"), array.
					getJSONObject(i).getString("color"), array.getJSONObject(i).
					getString("shopid"),array.getJSONObject(i).
					getString("userid"));
			if(!flag){
				break;
			}
		}
		System.out.println(flag);
		PrintWriter writer = resp.getWriter();
		writer.write(String.valueOf(flag));
		resp.getWriter().flush();
		writer.close();
	}
	
}
