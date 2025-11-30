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

public class Checkin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int room = Integer.parseInt(req.getParameter("roomnumber"));
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hotelmanagement?user=root&password=root";
            con = DriverManager.getConnection(url);
            ps = con.prepareStatement("SELECT * FROM hotelmanagement.booking WHERE Roomno = ?");
            ps.setInt(1, room);
            rs = ps.executeQuery();
            PrintWriter pw = resp.getWriter();

            if (rs.next()) {
                // --- SUCCESS: Room Found ---
                String checkinDate = rs.getString("checkin");
                String checkoutDate = rs.getString("checkout"); // Assuming this column exists
                System.out.println(checkinDate);

                // Generating a more detailed and beautiful success page using PrintWriter
                pw.println("<!DOCTYPE html><html lang='en'><head><title>Booking Details</title>");
                pw.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap' rel='stylesheet'>");
                pw.println("<style>");
                pw.println("body { font-family: 'Poppins', sans-serif; background: linear-gradient(135deg, #f0f4f8, #e9ecef); display: flex; justify-content: center; align-items: center; min-height: 100vh; margin:0; text-align: center; }");
                pw.println(".card { background: #fff; padding: 40px 50px; border-radius: 16px; box-shadow: 0 10px 40px rgba(0, 95, 115, 0.1); border-top: 5px solid #0a9396; max-width: 500px; width: 100%; animation: fadeIn 0.5s ease; }");
                pw.println("@keyframes fadeIn { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: translateY(0); } }");
                pw.println("h1 { color: #005f73; margin-top:0; }");
                pw.println(".details-grid { text-align: left; margin: 30px 0; border-left: 3px solid #0a9396; padding-left: 20px; }");
                pw.println(".detail-item { margin-bottom: 15px; }");
                pw.println(".detail-item p { margin: 0; font-size: 0.9rem; color: #6c757d; }");
                pw.println(".detail-item span { font-size: 1.1rem; color: #005f73; font-weight: 600; }");
                pw.println(".home-btn { display: inline-block; margin-top: 20px; padding: 12px 30px; background: #ee9b00; color: #fff; text-decoration: none; border-radius: 50px; font-weight: 600; transition: all 0.3s ease; }");
                pw.println(".home-btn:hover { transform: translateY(-3px); box-shadow: 0 4px 15px rgba(0,0,0,0.2); }");
                pw.println("</style></head><body>");
                pw.println("<div class='card'>");
                pw.println("<h1>Booking Found!</h1>");
                pw.println("<div class='details-grid'>");
                pw.println("<div class='detail-item'><p>Room Number</p><span>" + room + "</span></div>");
                pw.println("<div class='detail-item'><p>Check-In Date</p><span>" + checkinDate + "</span></div>");
                pw.println("<div class='detail-item'><p>Check-Out Date</p><span>" + checkoutDate + "</span></div>");
                pw.println("</div>");
                pw.println("<a href='home.html' class='home-btn'>Back to Home</a>");
                pw.println("</div></body></html>");
            } else {
                RequestDispatcher dispatcher = req.getRequestDispatcher("checkin.html");
                pw.println("<b style ='position : absolute; z-index : 10; margin:auto; top : 90px; color : red;'>Invalid room number please check once again and retry</b>");
                dispatcher.include(req, resp);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } 
    }
}

