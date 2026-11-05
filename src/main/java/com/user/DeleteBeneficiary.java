package com.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.db.Database;
/**
 * Servlet implementation class DeleteBeneficiary
 */
@WebServlet("/DeleteBeneficiary")
public class DeleteBeneficiary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBeneficiary() {
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
		
		
		HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("ULogin.jsp");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userid");
        if (userId == null) userId = (Integer) session.getAttribute("userid");
        if (userId == null) {
            response.sendRedirect("ULogin.jsp");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("view_beneficiaries.jsp");
            return;
        }

        int id = Integer.parseInt(idStr);

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Database.getconection();
            String sql = "DELETE FROM beneficiaries WHERE id = ? AND user_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, userId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                session.setAttribute("msg", "Beneficiary deleted.");
            } else {
                session.setAttribute("msg", "Unable to delete (not owner or already removed).");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "Error: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }

        response.sendRedirect("view_beneficiaries.jsp");
    }
	

}
