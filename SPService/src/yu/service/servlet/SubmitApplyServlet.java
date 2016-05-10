package yu.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yu.service.mysql.ShopOrder;

public class SubmitApplyServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String status = req.getParameter("status");
		String orderid = req.getParameter("orderid");
		ShopOrder shopOrder = new ShopOrder();
		int result = shopOrder.submitApply(orderid, status);
		PrintWriter writer = resp.getWriter();
		writer.write(String.valueOf(result));
		resp.getWriter().flush();
		writer.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}
	
}
