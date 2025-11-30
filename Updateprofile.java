package com.servlet;

import java.io.IOException;
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

public class Updateprofile extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String newmail = req.getParameter("email");
		String nfname = req.getParameter("newfirstname");
		String nlname = req.getParameter("newlastname");
		long ncontact = Long.parseLong(req.getParameter("newmobilenumber"));
		String npass = req.getParameter("newpassword");
		Connection con =null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				String url="jdbc:mysql://localhost:3306/hotelmanagement?user=root&password=root";
				con = DriverManager.getConnection(url);
				ps = con.prepareStatement("select * from hotelmanagement.hotel where email = ?");
				ps.setString(1,newmail);
				rs =ps.executeQuery();
				rs.next();
				String Fname = rs.getString("firstname");
				String Lname = rs.getString("lastname");
				long contact = rs.getLong("contact");
				String pwd = rs.getString("password");
				if(!(Fname.equals(nfname))) {
					ps = con.prepareStatement("update hotelmanagement.hotel set firstname=? where email = ? ");
					ps.setString(1, nfname);
					ps.setString(2, newmail);
					ps.executeUpdate();
				}
				if(!(Lname.equals(nlname))) {
					ps = con.prepareStatement("update hotelmanagement.hotel set lastname=? where email = ? ");
					ps.setString(1, nlname);
					ps.setString(2, newmail);
					ps.executeUpdate();
				}
				
				if(ncontact != contact) {
					ps = con.prepareStatement("update hotelmanagement.hotel set contact=? where email = ? ");
					ps.setLong(1, ncontact);
					ps.setString(2, newmail);
					ps.executeUpdate();
				}
				if(!(npass.equals(pwd))) {
					ps = con.prepareStatement("update hotelmanagement.hotel set password=? where email = ? ");
					ps.setString(1, npass);
					ps.setString(2, newmail);
					ps.executeUpdate();
				}
				RequestDispatcher rd = req.getRequestDispatcher("home.html");
				rd.forward(req, resp);
			} catch(ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}finally {	
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
	}
}
