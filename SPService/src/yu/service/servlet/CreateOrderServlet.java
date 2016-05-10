package yu.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import yu.service.mysql.ShopOrder;

public class CreateOrderServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String adressid = req.getParameter("adressid");
		String shoplist = req.getParameter("shoplist");
		String money = req.getParameter("price");
		String userid = req.getParameter("userid");
		ShopOrder shopOrder = new ShopOrder();
		int flag = shopOrder.creatOrder(adressid, shoplist, money,userid);
    	PrintWriter writer = resp.getWriter();
		writer.write(String.valueOf(flag));
		resp.getWriter().flush();
		writer.close();
	}
}
