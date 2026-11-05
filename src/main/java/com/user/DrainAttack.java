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
 * Servlet implementation class DrainAttack
 */
@WebServlet("/DrainAttack")
public class DrainAttack extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DrainAttack() {
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
        Integer spenderId = (Integer) session.getAttribute("userid");

        if (spenderId == null) {
            response.sendRedirect("ULogin.jsp");
            return;
        }

        int approvalId = Integer.parseInt(request.getParameter("approval_id"));

        try (Connection con = Database.getconection()) {

            con.setAutoCommit(false);

            /* -------------------------------------------------
             * 1. Get spender wallet
             * ------------------------------------------------- */
            PreparedStatement psSpender = con.prepareStatement(
                "SELECT wallet_address FROM users WHERE id=?"
            );
            psSpender.setInt(1, spenderId);
            ResultSet rsSpender = psSpender.executeQuery();

            if (!rsSpender.next()) {
                response.getWriter().println("Spender wallet not found");
                return;
            }

            String spenderWallet = rsSpender.getString("wallet_address");

            /* -------------------------------------------------
             * 2. Get approval (BASED ON SPENDER)
             * ------------------------------------------------- */
            PreparedStatement ps1 = con.prepareStatement(
                "SELECT * FROM token_approvals WHERE id=? AND spender_wallet=? AND status='ACTIVE'"
            );
            ps1.setInt(1, approvalId);
            ps1.setString(2, spenderWallet);

            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                response.getWriter().println("Invalid or unauthorized approval");
                return;
            }

            int ownerId = rs.getInt("owner_id");
            double approved = rs.getDouble("approved_amount");
            double used = rs.getDouble("used_amount");

            double drainAmount = approved - used;

            /* -------------------------------------------------
             * 3. Get owner wallet
             * ------------------------------------------------- */
            PreparedStatement psOwner = con.prepareStatement(
                "SELECT wallet_address FROM users WHERE id=?"
            );
            psOwner.setInt(1, ownerId);
            ResultSet rsOwner = psOwner.executeQuery();

            if (!rsOwner.next()) {
                response.getWriter().println("Owner wallet not found");
                return;
            }

            String ownerWallet = rsOwner.getString("wallet_address");

            /* -------------------------------------------------
             * 4. Reduce OWNER balance
             * ------------------------------------------------- */
            PreparedStatement ps2 = con.prepareStatement(
                "UPDATE users SET token_balance = token_balance - ? WHERE id=?"
            );
            ps2.setDouble(1, drainAmount);
            ps2.setInt(2, ownerId);
            ps2.executeUpdate();

            /* -------------------------------------------------
             * 5. Mark approval fully used
             * ------------------------------------------------- */
            PreparedStatement ps3 = con.prepareStatement(
                "UPDATE token_approvals SET used_amount = approved_amount WHERE id=?"
            );
            ps3.setInt(1, approvalId);
            ps3.executeUpdate();

            /* -------------------------------------------------
             * 6. Insert transaction (OWNER ➜ SPENDER)
             * ------------------------------------------------- */
            PreparedStatement ps4 = con.prepareStatement(
                "INSERT INTO transactions " +
                "(sender_id, sender_wallet, receiver_wallet, amount, status, tx_time) " +
                "VALUES (?, ?, ?, ?, 'SUCCESS', NOW())"
            );

            ps4.setInt(1, ownerId);
            ps4.setString(2, ownerWallet);
            ps4.setString(3, spenderWallet);
            ps4.setDouble(4, drainAmount);
            ps4.executeUpdate();

            con.commit();

            response.sendRedirect("attack_result.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Drain attack failed");
        }
	}

}
