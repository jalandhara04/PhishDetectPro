package com.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.Database;

/**
 * Servlet implementation class VerifyOTP
 */
@WebServlet("/VerifyOTP")
public class VerifyOTP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyOTP() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		
		String email = request.getParameter("email");
        String enteredOtp = request.getParameter("otp");

        Connection con = null;

        try {
            
            con = Database.getconection();

            // Fetch OTP & expiry
            PreparedStatement ps = con.prepareStatement(
                "SELECT otp, otp_expiry FROM users WHERE email=? AND status='PENDING'"
            );
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                response.getWriter().println("<h3>Invalid Email or Already Verified!</h3>");
                return;
            }

            String dbOtp = rs.getString("otp");
            Timestamp expiry = rs.getTimestamp("otp_expiry");
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // OTP expired?
            if (now.after(expiry)) {
                response.getWriter().println("<h3>OTP expired! Please register again.</h3>");
                return;
            }

            // OTP mismatch?
            if (!enteredOtp.equals(dbOtp)) {
                response.getWriter().println("<h3>Incorrect OTP!</h3>");
                return;
            }

            // OTP correct → update status
            PreparedStatement ps2 = con.prepareStatement(
                "UPDATE users SET status='ACTIVE' WHERE email=?"
            );
            ps2.setString(1, email);
            ps2.executeUpdate();

            response.getWriter().println("<h3>OTP Verified Successfully! You can now log in.</h3>");
            response.sendRedirect("ULogin.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
	}

}
