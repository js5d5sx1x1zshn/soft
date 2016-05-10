package yu.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yu.service.mysql.ShopCart;
//加入到购物车
public class AddToCartServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2537188727563522430L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			System.out.println(req.getParameter("zhishi"));
			
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String zhishi = req.getParameter("zhishi");
		String color = req.getParameter("color");
		String number = req.getParameter("number");
		String proid = req.getParameter("proid");
		String userid = req.getParameter("userid");
		System.out.println(zhishi);
		ShopCart shopCart = new ShopCart();
		int flag = shopCart.addToCart(zhishi, color, Integer.parseInt(number), proid,userid);
		System.out.println(flag);
		PrintWriter writer = resp.getWriter();
		writer.write(String.valueOf(flag));
		resp.getWriter().flush();
		writer.close();
	}
	
}

