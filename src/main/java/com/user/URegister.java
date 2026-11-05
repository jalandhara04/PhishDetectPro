package com.user;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.Database;

/**
 * Servlet implementation class URegister
 */
@WebServlet("/URegister")
public class URegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public URegister() {
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
		
		
		String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String mobile = request.getParameter("mobile");
        String dob = request.getParameter("dob");

        Connection con = null;

        try {
            con = Database.getconection();

            // ================================
            // Step 1: Check duplicate email
            // ================================
            PreparedStatement psCheck = con.prepareStatement(
                    "SELECT otp_expiry, status FROM users WHERE email=?");
            psCheck.setString(1, email);
            ResultSet rs = psCheck.executeQuery();

            Timestamp now = new Timestamp(System.currentTimeMillis());

            if (rs.next()) {
                String status = rs.getString("status");
                Timestamp expiry = rs.getTimestamp("otp_expiry");

                if (status.equals("ACTIVE")) {
                    response.getWriter().println("<h3>Email already exists!</h3>");
                    return;
                }

                if (status.equals("PENDING") && now.after(expiry)) {
                    PreparedStatement psDelete = con.prepareStatement(
                            "DELETE FROM users WHERE email=?");
                    psDelete.setString(1, email);
                    psDelete.executeUpdate();
                } else if (status.equals("PENDING")) {
                    response.getWriter().println("<h3>OTP already sent. Verify first!</h3>");
                    return;
                }
            }

            // ================================
            // Step 2: Generate Wallet Address
            // ================================
            String walletAddress = generateWalletAddress();

            // ================================
            // Step 3: Generate OTP
            // ================================
            String otp = String.valueOf(100000 + new Random().nextInt(900000));

            // ================================
            // Step 4: Insert New User
            // ================================
            PreparedStatement ps2 = con.prepareStatement(
                    "INSERT INTO users(name,email,password,gender,mobile,dob,wallet_address,token_balance,otp,otp_expiry,status) "
                    + "VALUES (?,?,?,?,?,?,?,100,?,NOW() + INTERVAL 10 MINUTE,'PENDING')");

            ps2.setString(1, name);
            ps2.setString(2, email);
            ps2.setString(3, password);
            ps2.setString(4, gender);
            ps2.setString(5, mobile);
            ps2.setString(6, dob);
            ps2.setString(7, walletAddress);
            ps2.setString(8, otp);

            int row = ps2.executeUpdate();

            if (row > 0) {
                sendOTPEmail(email, otp);
                response.sendRedirect("verify_otp.jsp?email=" + email);
            } else {
                response.getWriter().println("Registration failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error : " + e.getMessage());
        }
    }

    // ========================================
    // Generate a 42-character crypto-style address
    // ========================================
    private String generateWalletAddress() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder("0x");

        String hex = "ABCDEF0123456789";

        for (int i = 0; i < 40; i++) {
            sb.append(hex.charAt(random.nextInt(hex.length())));
        }
        return sb.toString();
    }

    // ========================================
    // Send OTP email using javax.mail
    // ========================================
    private void sendOTPEmail(String toEmail, String otp) {

        final String fromEmail = "testcheck639@gmail.com";
        final String password = "gogbyinyorfdahiw";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject("Your OTP Verification Code");
            msg.setText("Your OTP is : " + otp + "\nThis OTP is valid for 10 minutes.");

            Transport.send(msg);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}

}
