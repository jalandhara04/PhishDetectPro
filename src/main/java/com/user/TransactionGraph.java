package com.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.db.Database;

/**
 * Servlet implementation class TransactionGraph
 */
@WebServlet("/TransactionGraph")
public class TransactionGraph extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionGraph() {
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

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Double> amounts = new ArrayList<>();

        try (Connection con = Database.getconection()) {

            PreparedStatement ps = con.prepareStatement(
                "SELECT id, amount FROM transactions WHERE sender_id=? ORDER BY tx_time"
            );
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                labels.add("TX-" + rs.getInt("id"));
                amounts.add(rs.getDouble("amount"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // VERY IMPORTANT
        request.setAttribute("labels", labels);
        request.setAttribute("amounts", amounts);

        request.getRequestDispatcher("transaction_graph.jsp").forward(request, response);
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
