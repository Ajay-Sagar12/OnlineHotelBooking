package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class trackingdetails extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int Room = Integer.parseInt(req.getParameter("trackingId"));
        String name = req.getParameter("trackingname");
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hotelmanagement?user=root&password=root";
            con = DriverManager.getConnection(url);
            ps = con.prepareStatement("select * from hotelmanagement.booking where Roomno = ? AND name = ?");
            ps.setInt(1, Room);
            ps.setString(2, name);
            rs = ps.executeQuery();
            
            resp.setContentType("text/html");
            PrintWriter pw = resp.getWriter();

            if (rs.next()) {
                String dbname = rs.getString("name");
                int room = rs.getInt("Roomno");
                String mail = rs.getString("email");
                String checkin = rs.getString("checkin");
                String checkout = rs.getString("checkout");

                pw.println("<!DOCTYPE html>");
                pw.println("<html lang='en'>");
                pw.println("<head>");
                pw.println("<meta charset='UTF-8'>");
                pw.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                pw.println("<title>Booking Details</title>");
                pw.println("<style>");
                pw.println("@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap');");
                pw.println("body { font-family: 'Poppins', sans-serif; background-color: #f0f2f5; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }");
                pw.println(".card { background-color: #ffffff; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); width: 100%; max-width: 550px; padding: 30px 40px; box-sizing: border-box; }");
                pw.println(".header { text-align: center; margin-bottom: 30px; }");
                pw.println(".header h1 { font-size: 2em; color: #333; margin: 0; }");
                pw.println(".header h1 span { color: #5c67f2; }");
                pw.println(".details-table { width: 100%; border-collapse: collapse; }");
                pw.println(".details-table td { padding: 15px 5px; }");
                pw.println(".details-table tr { border-bottom: 1px solid #e9ecef; }");
                pw.println(".details-table tr:last-child { border-bottom: none; }");
                pw.println(".label { font-weight: 600; color: #555; text-align: left; }");
                pw.println(".value { color: #333; text-align: right; background-color: #f8f9fa; padding-right: 15px !important; border-radius: 5px; }");
                pw.println(".button-container { text-align: center; margin-top: 30px; }");
                pw.println(".back-button { background-color: #5c67f2; color: white; border: none; padding: 12px 25px; border-radius: 8px; font-size: 16px; cursor: pointer; text-decoration: none; transition: background-color 0.3s ease; }");
                pw.println(".back-button:hover { background-color: #4a54d1; }");
                pw.println("</style>");
                pw.println("</head>");
                pw.println("<body>");
                pw.println("<div class='card'>");
                pw.println("<div class='header'>");
                pw.println("<h1>Booking<span>Details</span></h1>");
                pw.println("</div>");
                pw.println("<table class='details-table'>");
                pw.println("<tr><td class='label'>Name:</td><td class='value'>" + dbname + "</td></tr>");
                pw.println("<tr><td class='label'>Room Number:</td><td class='value'>" + room + "</td></tr>");
                pw.println("<tr><td class='label'>Email:</td><td class='value'>" + mail + "</td></tr>");
                pw.println("<tr><td class='label'>Check-in Date:</td><td class='value'>" + checkin + "</td></tr>");
                pw.println("<tr><td class='label'>Check-out Date:</td><td class='value'>" + checkout + "</td></tr>");
                pw.println("</table>");
                pw.println("<div class='button-container'>");
                // --- CORRECTED CODE BLOCK ---
                pw.println("<form action='track' method='post'>");
                pw.println("<button type='submit' id='back' class='back-button'>Back</button>");
                pw.println("</form>");
                // --- END OF CORRECTION ---
                pw.println("</div>");
                pw.println("</div>");
                pw.println("</body>");
                pw.println("</html>");

            } else {
//               RequestDispatcher dispatcher = req.getRequestDispatcher("trackingdetails.java");
               pw.println("<html><head><title>Error</title>");
               pw.println("<style> body { font-family: sans-serif; text-align: center; padding-top: 50px; } .error { color: red; } a { color: #5c67f2; text-decoration:none; } </style>");
               pw.println("</head><body>");
               pw.println("<p class='error' style= 'font-weight : 900'  >Room number is not valid or name is incorrect.</p>");
               pw.println("<a href='booktrack.html' style='border : 2px solid black; padding : 5px 10px;'>Try Again</a>");
               pw.println("</body></html>");
//               dispatcher.include(req, resp);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}