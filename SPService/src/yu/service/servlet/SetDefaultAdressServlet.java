package yu.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yu.service.mysql.OrderForm;

public class SetDefaultAdressServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String userid = req.getParameter("userid");
		boolean flag = false;
		OrderForm orderForm = new OrderForm();
		flag = orderForm.updateAdress(id, userid);
		System.out.println(flag);
		PrintWriter writer = resp.getWriter();
		writer.write(String.valueOf(flag));
		resp.getWriter().flush();
		writer.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
		
	}
	
}
