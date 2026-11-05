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
 * Servlet implementation class RechargeWallet
 */
@WebServlet("/RechargeWallet")
public class RechargeWallet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RechargeWallet() {
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

        double amount = Double.parseDouble(request.getParameter("amount"));

        try (Connection con = Database.getconection()) {

            con.setAutoCommit(false);

            // 1️⃣ Update wallet balance
            PreparedStatement ps1 = con.prepareStatement(
                "UPDATE users SET token_balance = token_balance + ? WHERE id=?"
            );
            ps1.setDouble(1, amount);
            ps1.setInt(2, userId);
            ps1.executeUpdate();

            // 2️⃣ Insert recharge transaction
            PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO transactions(sender_id, sender_wallet, receiver_wallet, amount, status, tx_time) " +
                "SELECT id, 'SYSTEM', wallet_address, ?, 'SUCCESS', NOW() FROM users WHERE id=?"
            );
            ps2.setDouble(1, amount);
            ps2.setInt(2, userId);
            ps2.executeUpdate();

            con.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("ViewWallet");
    }
	

}
