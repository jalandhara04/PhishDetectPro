package com.user;

import java.io.IOException;
import java.security.MessageDigest;
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
 * Servlet implementation class CreateBlock
 */
@WebServlet("/CreateBlock")
public class CreateBlock extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateBlock() {
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

        try (Connection con = Database.getconection()) {

            // 1️⃣ Get last block hash for this user
            String prevHash = "0";
            int blockIndex = 1;

            PreparedStatement psLast = con.prepareStatement(
                "SELECT * FROM blockchain_blocks WHERE user_id=? ORDER BY block_index DESC LIMIT 1"
            );
            psLast.setInt(1, userId);
            ResultSet rsLast = psLast.executeQuery();

            if (rsLast.next()) {
                prevHash = rsLast.getString("current_hash");
                blockIndex = rsLast.getInt("block_index") + 1;
            }

            // 2️⃣ Get user's transactions (limit 5 per block)
            PreparedStatement psTx = con.prepareStatement(
                "SELECT id FROM transactions WHERE sender_id=? ORDER BY tx_time LIMIT 5"
            );
            psTx.setInt(1, userId);
            ResultSet rsTx = psTx.executeQuery();

            StringBuilder txData = new StringBuilder();
            while (rsTx.next()) {
                txData.append(rsTx.getInt("id")).append(",");
            }

            if (txData.length() == 0) {
                response.getWriter().println("No transactions to form a block!");
                return;
            }

            // 3️⃣ Generate hash
            String rawData = userId + blockIndex + txData.toString() + prevHash;
            String currentHash = sha256(rawData);

            // 4️⃣ Insert block
            PreparedStatement psInsert = con.prepareStatement(
                "INSERT INTO blockchain_blocks(user_id, block_index, previous_hash, current_hash, transactions) VALUES(?,?,?,?,?)"
            );
            psInsert.setInt(1, userId);
            psInsert.setInt(2, blockIndex);
            psInsert.setString(3, prevHash);
            psInsert.setString(4, currentHash);
            psInsert.setString(5, txData.toString());

            psInsert.executeUpdate();

            response.sendRedirect("view_blockchain.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Block creation failed");
        }
    }

    private String sha256(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes("UTF-8"));
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
	

}
