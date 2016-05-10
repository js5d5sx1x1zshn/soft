package yu.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yu.service.mysql.User;

public class UpdateAdressServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String adress = req.getParameter("adress");
		String phone = req.getParameter("phone");
		String reciever = req.getParameter("reciever");
		String id = req.getParameter("id");
		User user = new User();
		int flag = user.updateAdress(id, reciever, phone, adress);
		PrintWriter writer = resp.getWriter();
		writer.write(String.valueOf(flag));
		resp.getWriter().flush();
		writer.close();		
	}
}
