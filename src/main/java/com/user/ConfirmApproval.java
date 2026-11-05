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
 * Servlet implementation class ConfirmApproval
 */
@WebServlet("/ConfirmApproval")
public class ConfirmApproval extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmApproval() {
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

        String spenderWallet = (String) session.getAttribute("PENDING_SPENDER");
        Integer amount = (Integer) session.getAttribute("PENDING_AMOUNT");

        if (spenderWallet == null || amount == null) {
            response.getWriter().println("Approval session expired or invalid!");
            return;
        }

        try (Connection con = Database.getconection()) {
        	
        	
        	PreparedStatement ps1 = con.prepareStatement(
                    "SELECT wallet_address FROM users WHERE id=?"
                );
                ps1.setInt(1, userId);
                ResultSet rs = ps1.executeQuery();
                rs.next();

                String ownerWallet = rs.getString("wallet_address");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO token_approvals(owner_id, owner_wallet, spender_wallet, approved_amount, used_amount, status, approved_on) " +
                "VALUES (?, ?, ?, ?, 0, 'ACTIVE', NOW())"
            );

            ps.setInt(1, userId);
            ps.setString(2, ownerWallet);
            ps.setString(3, spenderWallet);
            ps.setDouble(4, amount);

            ps.executeUpdate();

            // 🔥 CLEAR SESSION
            session.removeAttribute("PENDING_SPENDER");
            session.removeAttribute("PENDING_AMOUNT");

            response.sendRedirect("approvalsuccess.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Approval Failed");
        }
	}

}
