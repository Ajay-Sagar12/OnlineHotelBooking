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
import javax.servlet.http.HttpSession;

public class Profile extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        // 🚨 Security Note: Storing a password in the session is not recommended.
        // It's better to store a unique user ID.
        String pass = (String) session.getAttribute("sendpassword");
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // A more modern way to handle resources is try-with-resources
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hotelmanagement?user=root&password=root";
            con = DriverManager.getConnection(url);
            
            // 🚨 Security Note: Querying by password implies it might be stored in plain text.
            // Always hash passwords in your database.
            ps = con.prepareStatement("select * from hotelmanagement.hotel where password = ?");
            ps.setString(1, pass);
            rs = ps.executeQuery();

            if (rs.next()) { // Check if a user was found
                String mail = rs.getString("email");
                String Fname = rs.getString("firstname");
                String Lname = rs.getString("lastname");
                long contact = rs.getLong("contact");
                String pwd = rs.getString("password");

                resp.setContentType("text/html;charset=UTF-8");
                PrintWriter pw = resp.getWriter();

                pw.println("<!DOCTYPE html>");
                pw.println("<html lang='en'>");
                pw.println("<head>");
                pw.println("    <meta charset='UTF-8'>");
                pw.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                pw.println("    <title>My Profile</title>");
                pw.println("    <link rel='preconnect' href='https://fonts.googleapis.com'>");
                pw.println("    <link rel='preconnect' href='https://fonts.gstatic.com' crossorigin>");
                pw.println("    <link href='https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap' rel='stylesheet'>");
                pw.println("    <style>");
                pw.println("        :root {");
                pw.println("            --primary-color: #005f73;");
                pw.println("            --secondary-color: #0a9396;");
                pw.println("            --accent-color: #ee9b00;");
                pw.println("            --background-gradient: linear-gradient(135deg, #e9f5f7, #d8eef2);");
                pw.println("            --card-bg: #ffffff;");
                pw.println("            --text-color: #333;");
                pw.println("            --label-color: #555;");
                pw.println("            --border-color: #ced4da;");
                pw.println("            --shadow-color: rgba(0, 95, 115, 0.1);");
                pw.println("        }");
                pw.println("        body {");
                pw.println("            font-family: 'Poppins', sans-serif;");
                pw.println("            background: var(--background-gradient);");
                pw.println("            display: flex;");
                pw.println("            justify-content: center;");
                pw.println("            align-items: center;");
                pw.println("            min-height: 100vh;");
                pw.println("            margin: 0;");
                pw.println("        }");
                pw.println("        .profile-container {");
                pw.println("            background: var(--card-bg);");
                pw.println("            padding: 40px;");
                pw.println("            border-radius: 20px;");
                pw.println("            box-shadow: 0 10px 40px var(--shadow-color);");
                pw.println("            width: 100%;");
                pw.println("            max-width: 550px;");
                pw.println("            border-top: 5px solid var(--secondary-color);");
                pw.println("            animation: fadeIn 0.5s ease-in-out;");
                pw.println("        }");
                pw.println("        @keyframes fadeIn { from { opacity: 0; transform: translateY(-20px); } to { opacity: 1; transform: translateY(0); } }");
                pw.println("        .profile-header {");
                pw.println("            text-align: center;");
                pw.println("            margin-bottom: 35px;");
                pw.println("        }");
                pw.println("        .profile-header h1 {");
                pw.println("            color: var(--primary-color);");
                pw.println("            font-weight: 700;");
                pw.println("            margin-bottom: 5px;");
                pw.println("        }");
                pw.println("        .profile-header p {");
                pw.println("            color: var(--label-color);");
                pw.println("            font-size: 1rem;");
                pw.println("        }");
                pw.println("        .form-row {");
                pw.println("            margin-bottom: 22px;");
                pw.println("        }");
                pw.println("        .form-row label {");
                pw.println("            display: block;");
                pw.println("            font-weight: 500;");
                pw.println("            color: var(--label-color);");
                pw.println("            margin-bottom: 8px;");
                pw.println("        }");
                pw.println("        .form-row input {");
                pw.println("            width: 100%;");
                pw.println("            padding: 14px;");
                pw.println("            border: 1px solid var(--border-color);");
                pw.println("            border-radius: 10px;");
                pw.println("            font-size: 16px;");
                pw.println("            font-family: 'Poppins', sans-serif;");
                pw.println("            box-sizing: border-box; /* Important for padding and width */");
                pw.println("            transition: border-color 0.3s, box-shadow 0.3s;");
                pw.println("        }");
                pw.println("        .form-row input:focus {");
                pw.println("            border-color: var(--secondary-color);");
                pw.println("            box-shadow: 0 0 0 3px rgba(10, 147, 150, 0.2);");
                pw.println("            outline: none;");
                pw.println("        }");
                pw.println("        .form-row input[readonly] {");
                pw.println("            background-color: #f1f3f5;");
                pw.println("            cursor: not-allowed;");
                pw.println("            color: #6c757d;");
                pw.println("        }");
                pw.println("        .button-container {");
                pw.println("            display: flex;");
                pw.println("            justify-content: center;");
                pw.println("            gap: 20px;");
                pw.println("            margin-top: 35px;");
                pw.println("            flex-wrap: wrap; /* For smaller screens */");
                pw.println("        }");
                pw.println("        .btn {");
                pw.println("            padding: 14px 35px;");
                pw.println("            border: none;");
                pw.println("            border-radius: 50px;");
                pw.println("            cursor: pointer;");
                pw.println("            font-weight: 600;");
                pw.println("            font-size: 16px;");
                pw.println("            transition: all 0.3s ease;");
                pw.println("            letter-spacing: 0.5px;");
                pw.println("        }");
                pw.println("        #updatee {");
                pw.println("            background-color: var(--accent-color);");
                pw.println("            color: #fff;");
                pw.println("            box-shadow: 0 4px 15px rgba(238, 155, 0, 0.3);");
                pw.println("        }");
                pw.println("        #back {");
                pw.println("            background-color: transparent;");
                pw.println("            color: var(--primary-color);");
                pw.println("            border: 2px solid var(--border-color);");
                pw.println("        }");
                pw.println("        .btn:hover {");
                pw.println("            transform: translateY(-4px);");
                pw.println("            box-shadow: 0 6px 20px rgba(0,0,0,0.15);");
                pw.println("        }");
                pw.println("        #back:hover {");
                pw.println("            background-color: #f1f3f5;");
                pw.println("            border-color: var(--primary-color);");
                pw.println("        }");
                pw.println("    </style>");
                pw.println("</head>");
                pw.println("<body>");
                pw.println("    <div class='profile-container'>");
                pw.println("        <div class='profile-header'>");
                pw.println("            <h1>Your Profile</h1>");
                pw.println("            <p>View and update your personal information.</p>");
                pw.println("        </div>");
                pw.println("        <form action='update' method='post'>");
                pw.println("            <div class='form-row'><label for='email'>Email Address</label><input type='email' id='email' value='" + mail + "' name='email' readonly></div>");
                pw.println("            <div class='form-row'><label for='newfirstname'>First Name</label><input type='text' id='newfirstname' value='" + Fname + "' name='newfirstname' required></div>");
                pw.println("            <div class='form-row'><label for='newlastname'>Last Name</label><input type='text' id='newlastname' value='" + Lname + "' name='newlastname' required></div>");
                pw.println("            <div class='form-row'><label for='newmobilenumber'>Mobile Number</label><input type='tel' id='newmobilenumber' value='" + contact + "' name='newmobilenumber' required></div>");
                pw.println("            <div class='form-row'><label for='newpassword'>Password</label><input type='password' id='newpassword' value='" + pwd + "' name='newpassword' required></div>");
                pw.println("            <div class='button-container'>");
                pw.println("                <button type='submit' id='updatee' class='btn'>Update Profile</button>");
                // Note: The 'back' button is outside this form now for better structure
                pw.println("        </form>");
                pw.println("        <form action='login' method='post' style='margin:0;'>"); // A separate form just for the back button
                pw.println("            <input type='hidden' value='" + mail + "' name='mail'>");
                pw.println("            <input type='hidden' value='" + pwd + "' name='pass'>");
                pw.println("            <button type='submit' id='back' class='btn'>Back to Home</button>");
                pw.println("        </form>");
                pw.println("            </div>"); // Closing button-container
                pw.println("    </div>");
                pw.println("</body>");
                pw.println("</html>");

            } else {
                // Handle case where no user is found with that password
                resp.getWriter().println("<h1>Error: Profile not found.</h1>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // In a real application, you would show a user-friendly error page.
            resp.getWriter().println("<h1>Error: Could not connect to the database.</h1>");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}