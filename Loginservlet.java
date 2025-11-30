package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Loginservlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String Uemail = req.getParameter("mail");
		String password = req.getParameter("pass");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PrintWriter pw = resp.getWriter();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/hotelmanagement?user=root&password=root";
			con = DriverManager.getConnection(url);
			ps = con.prepareStatement("select * from hotelmanagement.hotel where email = ?");
			ps.setString(1, Uemail);
			rs =ps.executeQuery();
			if(rs.next()){
					String dbpass = rs.getString("password");
					if(password.equals(dbpass)) {
						HttpSession session=req.getSession();
						session.setAttribute("sendpassword", password);
						RequestDispatcher rd = req.getRequestDispatcher("home.html");
						rd.forward(req, resp);
					}else {
						pw.print("<html><body>"
							    + "<p style='color:red; position:absolute; font-weight:bold; top:140px; left:350px; z-index:10; margin-left:255px;''>"
							    + "incorrect  password"
							    + "</p>"
							    + "</body></html>");
						RequestDispatcher rd = req.getRequestDispatcher("login.html");
						rd.include(req, resp);
					}
			}else {
				pw.print("<html><body>"
					    + "<p style='color:red; position:absolute; font-weight:bold; top:140px; left:280px; z-index:10; margin-left:255px;'>"
					    + "incorrect username and password"
					    + "</p>"
					    + "</body></html>");
				
				RequestDispatcher rd = req.getRequestDispatcher("login.html");
				rd.include(req, resp);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
		}
		
	}
}
