package com.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.db.Database;
/**
 * Servlet implementation class SendTokens
 */
@WebServlet("/SendTokens")
public class SendTokens extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendTokens() {
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

        String beneficiaryWallet = request.getParameter("beneficiary_wallet");
        double amount = Double.parseDouble(request.getParameter("amount"));

        Connection con = null;

        try {
            con = Database.getconection();
            con.setAutoCommit(false); // 🔐 IMPORTANT

            // 1️⃣ Fetch sender details
            PreparedStatement ps1 = con.prepareStatement(
                "SELECT wallet_address, token_balance FROM users WHERE id=?"
            );
            ps1.setInt(1, userId);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
            	request.setAttribute("message", "Sender not found!");
                request.getRequestDispatcher("send_tokens.jsp").forward(request, response);
                return;
            }

            String senderWallet = rs1.getString("wallet_address");
            double senderBalance = rs1.getDouble("token_balance");

            // 2️⃣ Check balance
            if (senderBalance < amount) {
            	request.setAttribute("message", "Insufficient Balance!");
                request.getRequestDispatcher("send_tokens.jsp").forward(request, response);
                return;
            }

            // 3️⃣ Check beneficiary exists
            PreparedStatement ps2 = con.prepareStatement(
                "SELECT id FROM users WHERE wallet_address=?"
            );
            ps2.setString(1, beneficiaryWallet);
            ResultSet rs2 = ps2.executeQuery();

            if (!rs2.next()) {
            	request.setAttribute("message", "Invalid beneficiary wallet!");
                request.getRequestDispatcher("send_tokens.jsp").forward(request, response);
                return;
            }

            // 4️⃣ Deduct sender balance
            PreparedStatement ps3 = con.prepareStatement(
                "UPDATE users SET token_balance = token_balance - ? WHERE id=?"
            );
            ps3.setDouble(1, amount);
            ps3.setInt(2, userId);
            ps3.executeUpdate();

            // 5️⃣ Credit receiver balance
            PreparedStatement ps4 = con.prepareStatement(
                "UPDATE users SET token_balance = token_balance + ? WHERE wallet_address=?"
            );
            ps4.setDouble(1, amount);
            ps4.setString(2, beneficiaryWallet);
            ps4.executeUpdate();

            // 6️⃣ Log transaction
            PreparedStatement ps5 = con.prepareStatement(
                "INSERT INTO transactions(sender_id, sender_wallet, receiver_wallet, amount, status) "
              + "VALUES (?,?,?,?,?)"
            );
            ps5.setInt(1, userId);
            ps5.setString(2, senderWallet);
            ps5.setString(3, beneficiaryWallet);
            ps5.setDouble(4, amount);
            ps5.setString(5, "SUCCESS");
            ps5.executeUpdate();

            con.commit(); // ✅ TRANSACTION COMPLETE

            request.setAttribute("message", "Tokens sent successfully!");
            request.getRequestDispatcher("send_tokens.jsp").forward(request, response);

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) { }

            e.printStackTrace();
            request.setAttribute("message", "Transaction failed!");
            request.getRequestDispatcher("send_tokens.jsp").forward(request, response);

        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) { }
        }
	}

}
