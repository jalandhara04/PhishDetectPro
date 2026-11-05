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
 * Servlet implementation class ViewWallet
 */
@WebServlet("/ViewWallet")
public class ViewWallet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewWallet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userid");

        if (userId == null) {
            response.sendRedirect("ULogin.jsp");
            return;
        }

        try (Connection con = Database.getconection()) {

            PreparedStatement ps = con.prepareStatement(
                "SELECT wallet_address, token_balance FROM users WHERE id=?"
            );
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("wallet", rs.getString("wallet_address"));
                request.setAttribute("balance", rs.getDouble("token_balance"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("wallet.jsp").forward(request, response);
    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
