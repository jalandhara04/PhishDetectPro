package com.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.db.Database;

/**
 * Servlet implementation class ULogin
 */
@WebServlet("/ULogin")
public class ULogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ULogin() {
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
        String password = request.getParameter("password");

        Connection con = null;

        try {
            
            con = Database.getconection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT id, name, status FROM users WHERE email=? AND password=?"
            );
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String status = rs.getString("status");

                // Status must be ACTIVE
                if (!"ACTIVE".equals(status)) {
                    response.sendRedirect("ULogin.jsp?msg=Please verify your OTP first.");
                    return;
                }

                // Create session
                HttpSession session = request.getSession();
                session.setAttribute("userid", rs.getInt("id"));
                session.setAttribute("username", rs.getString("name"));
                session.setAttribute("email", email);

                response.sendRedirect("user_dashboard.jsp");
            } 
            else {
                response.sendRedirect("ULogin.jsp?msg=Invalid Credentials!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
	}

}
