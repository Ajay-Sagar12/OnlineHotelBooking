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

@SuppressWarnings("serial")
public class Bookingdetails extends HttpServlet {
	
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet resultSet = null;
	
	private int getRoomNo() {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/hotelmanagement?user=root&password=root";
	        con = DriverManager.getConnection(url);

	        ps = con.prepareStatement("SELECT MAX(Roomno) AS maxRoomNo FROM hotelmanagement.booking");
	        resultSet = ps.executeQuery();

	        if (resultSet.next()) {
	            return resultSet.getInt("maxRoomNo");
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("bname");
		String email = req.getParameter("bemail");
		String checkin = req.getParameter("bcheckindate");
		String checkout = req.getParameter("bcheckoutdate");
		String gender = req.getParameter("gender");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/hotelmanagement?user=root&password=root";
			con = DriverManager.getConnection(url);
			ps = con.prepareStatement("insert into hotelmanagement.booking values(?,?,?,?,?,?)");
			ps.setString(1, name);
			ps.setInt(2, getRoomNo()+1);
			ps.setString(3, email);
			ps.setString(4, checkin);
			ps.setString(5, checkout);
			ps.setString(6, gender);
			int b = ps.executeUpdate();
			if (b > 0) {

				RequestDispatcher RD = req.getRequestDispatcher("bookedsuccessfully.html");
				RD.forward(req, resp);
			} else {
				RequestDispatcher RD = req.getRequestDispatcher("profile.java");
				RD.forward(req, resp);

			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
