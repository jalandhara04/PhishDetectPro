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
 * Servlet implementation class ApproveTokens
 */
@WebServlet("/ApproveTokens")
public class ApproveTokens extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApproveTokens() {
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

        String spenderWallet = request.getParameter("spender_wallet");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try (Connection con = Database.getconection()) {

            // Fetch owner wallet
            PreparedStatement ps1 = con.prepareStatement(
                "SELECT wallet_address, token_balance FROM users WHERE id=?"
            );
            ps1.setInt(1, userId);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
            	request.setAttribute("message", "User not found");
            	request.getRequestDispatcher("approval.jsp").forward(request, response);
                return;
            }

            String ownerWallet = rs1.getString("wallet_address");
            double balance = rs1.getDouble("token_balance");

            if (amount > balance) {
            	request.setAttribute("message", "Insufficient balance for approval!");
            	request.getRequestDispatcher("approval.jsp").forward(request, response);
                return;
            }

            // Insert approval
            PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO token_approvals(owner_id, owner_wallet, spender_wallet, approved_amount) "
              + "VALUES (?,?,?,?)"
            );
            ps2.setInt(1, userId);
            ps2.setString(2, ownerWallet);
            ps2.setString(3, spenderWallet);
            ps2.setDouble(4, amount);

            ps2.executeUpdate();
            
            request.setAttribute("message", "Approval granted successfully!");
        	request.getRequestDispatcher("approval.jsp").forward(request, response);
            

        } catch (Exception e) {
            e.printStackTrace();
            
            request.setAttribute("message", "Approval failed!");
        	request.getRequestDispatcher("approval.jsp").forward(request, response);
        }
	}

}
