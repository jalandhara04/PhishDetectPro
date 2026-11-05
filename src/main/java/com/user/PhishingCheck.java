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
 * Servlet implementation class PhishingCheck
 */
@WebServlet("/PhishingCheck")
public class PhishingCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhishingCheck() {
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

        int approvalAmount = Integer.parseInt(request.getParameter("amount"));
        String spenderWallet = request.getParameter("spender_wallet");

        boolean suspicious = false;

        try (Connection con = Database.getconection()) {

            PreparedStatement ps = con.prepareStatement(
                "SELECT token_balance FROM users WHERE id=?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            rs.next();

            double balance = rs.getDouble("token_balance");

            if (approvalAmount > balance) {
                suspicious = true;
            }

            PreparedStatement ps2 = con.prepareStatement(
                "SELECT id FROM beneficiaries WHERE user_id=? AND beneficiary_wallet=?"
            );
            ps2.setInt(1, userId);
            ps2.setString(2, spenderWallet);

            ResultSet rs2 = ps2.executeQuery();
            if (!rs2.next()) {
                suspicious = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (suspicious) {
            // 🔐 STORE IN SESSION (NOT REQUEST)
            session.setAttribute("PENDING_SPENDER", spenderWallet);
            session.setAttribute("PENDING_AMOUNT", approvalAmount);

            response.sendRedirect("phishing_alert.jsp");
        } else {
            response.sendRedirect("approvalsuccess.jsp");
        }
	}

}
