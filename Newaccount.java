package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Newaccount extends HttpServlet	{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fname = req.getParameter("firstname");
		String lname = req.getParameter("lastname");
		String email = req.getParameter("useremail");
		long phone = Long.parseLong(req.getParameter("contact"));
		String password = req.getParameter("pass1");
		String ConfirmPassword = req.getParameter("pass2");
		
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/hotelmanagement?user=root&password=root";
			con = DriverManager.getConnection(url);
			ps = con.prepareStatement("insert into hotelmanagement.hotel values(?,?,?,?,?)");
			ps.setString(1, email);
			ps.setString(2, fname);
			ps.setString(3, lname);
			ps.setLong(4, phone);
			if(password.equals(ConfirmPassword)) {
				ps.setString(5, password);
			}else {
				PrintWriter pw = resp.getWriter();
				pw.print("<html><body>"
					    + "<p style='color:red; position:absolute; top:50px; left:300px; z-index:10; margin-left:255px;'>"
					    + "password is not matched"
					    + "</p>"
					    + "</body></html>");
				RequestDispatcher rd = req.getRequestDispatcher("Createaccount.html");
				rd.include(req, resp);
			}
			int execution = ps.executeUpdate();
			if(execution > 0) {
				RequestDispatcher rd = req.getRequestDispatcher("accountcreated.html");
				rd.forward(req, resp);
			}else {
				RequestDispatcher rd = req.getRequestDispatcher("Createaccount.html");
				rd.include(req, resp);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
		
			e.printStackTrace();
		}
		
	}
}
