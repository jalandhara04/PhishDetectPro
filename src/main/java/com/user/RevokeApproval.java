package com.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.db.Database;

/**
 * Servlet implementation class RevokeApproval
 */
@WebServlet("/RevokeApproval")
public class RevokeApproval extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RevokeApproval() {
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
		
		
		
		HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userid");

        if (userId == null) {
            response.sendRedirect("ULogin.jsp");
            return;
        }

        int approvalId = Integer.parseInt(request.getParameter("approval_id"));

        try (Connection con = Database.getconection()) {

            PreparedStatement ps = con.prepareStatement(
                "UPDATE token_approvals SET status='REVOKED' WHERE id=? AND owner_id=?"
            );
            ps.setInt(1, approvalId);
            ps.setInt(2, userId);

            ps.executeUpdate();
            
            request.setAttribute("message","Revoked Successfully...");
            request.getRequestDispatcher("phishing_detection.jsp").forward(request, response);
           // response.sendRedirect("phishing_detection.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error revoking approval!");
        }
	}

}
