package com.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.Database;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class AddBeneficiary
 */
@WebServlet("/AddBeneficiary")
public class AddBeneficiary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBeneficiary() {
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
        Integer user_id = (Integer) session.getAttribute("userid");

        if (user_id == null) {
            response.sendRedirect("ULogin.jsp");
            return;
        }

        String wallet = request.getParameter("wallet");

        try {
            Connection con = Database.getconection();

            // Get user linked with this wallet
            String sql1 = "SELECT id FROM users WHERE wallet_address=? AND status='active'";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setString(1, wallet);
            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                session.setAttribute("msg", "Invalid or inactive wallet selected!");
                response.sendRedirect("add_beneficiary.jsp");
                return;
            }

            int foundUserId = rs.getInt("id");

            // Prevent adding yourself
            if (foundUserId == user_id) {
                session.setAttribute("msg", "You cannot add your own wallet!");
                response.sendRedirect("add_beneficiary.jsp");
                return;
            }

            // Check duplicate
            String sql2 = "SELECT * FROM beneficiaries WHERE user_id=? AND beneficiary_wallet=?";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setInt(1, user_id);
            ps2.setString(2, wallet);
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {
                session.setAttribute("msg", "Beneficiary already added!");
                response.sendRedirect("add_beneficiary.jsp");
                return;
            }

            // Insert
            String sql3 = "INSERT INTO beneficiaries(user_id, beneficiary_wallet) VALUES(?,?)";
            PreparedStatement ps3 = con.prepareStatement(sql3);
            ps3.setInt(1, user_id);
            ps3.setString(2, wallet);

            int i = ps3.executeUpdate();

            if (i > 0) {
                session.setAttribute("msg", "Beneficiary added successfully!");
            } else {
                session.setAttribute("msg", "Failed to add beneficiary!");
            }

            response.sendRedirect("add_beneficiary.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "Error: " + e.getMessage());
            response.sendRedirect("add_beneficiary.jsp");
        }
	}

}
