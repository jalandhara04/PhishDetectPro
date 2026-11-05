<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.db.Database"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%
    if (session == null || session.getAttribute("userid") == null) {
        response.sendRedirect("ULogin.jsp?msg=Please login first");
        return;
    }

    Integer userId = (Integer) session.getAttribute("userid");
    String username = (String) session.getAttribute("username");

    Connection con = Database.getconection();

    PreparedStatement psWallet = con.prepareStatement(
        "SELECT wallet_address FROM users WHERE id=?"
    );
    psWallet.setInt(1, userId);
    ResultSet rsWallet = psWallet.executeQuery();

    if (!rsWallet.next()) {
        out.println("Wallet not found");
        return;
    }

    String spenderWallet = rsWallet.getString("wallet_address");

    PreparedStatement ps = con.prepareStatement(
        "SELECT ta.*, u.token_balance FROM token_approvals ta " +
        "JOIN users u ON ta.owner_id=u.id " +
        "WHERE ta.spender_wallet=? AND ta.status='ACTIVE'"
    );
    ps.setString(1, spenderWallet);
    ResultSet rs = ps.executeQuery();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Approval Drain Simulation | PhishDetectPro</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

<style>
*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:'Poppins', sans-serif;
}

/* ===== BACKGROUND ===== */
body{
    min-height:100vh;
    background:
        linear-gradient(rgba(0,0,0,0.45), rgba(0,0,0,0.55)),
        url("images/phish.jpeg") center/cover no-repeat fixed;
    color:#fff;
}

/* ===== HEADER ===== */
header{
    padding:20px 60px;
    display:flex;
    justify-content:space-between;
    align-items:center;
    backdrop-filter: blur(8px);
}

.logo{
    font-size:26px;
    font-weight:600;
    color:#00e5ff;
}

nav a{
    color:#fff;
    margin-left:25px;
    text-decoration:none;
    font-size:15px;
    transition:0.3s;
}
nav a:hover{ color:#00e5ff; }

/* ===== MAIN SECTION ===== */
.dashboard{
    padding:60px 8%;
}

/* ===== GLASS BOX ===== */
.glass-box{
    background:rgba(0,0,0,0.55);
    padding:35px;
    border-radius:18px;
    box-shadow:0 0 35px rgba(0,229,255,0.25);
    backdrop-filter: blur(10px);
    margin-bottom:60px;
    animation:fadeIn 1.2s ease-in-out;
}

.glass-box h2{
    color:#00e5ff;
    text-align:center;
    margin-bottom:25px;
}

/* ===== TABLE ===== */
table{
    width:100%;
    border-collapse:collapse;
}

th, td{
    padding:14px;
    text-align:center;
}

th{
    background:rgba(0,229,255,0.15);
    color:#00e5ff;
}

tr{
    border-bottom:1px solid rgba(255,255,255,0.1);
}

.high{ color:#ff5252; font-weight:600; }
.low{ color:#4caf50; font-weight:600; }

button{
    padding:8px 18px;
    border:none;
    border-radius:20px;
    background:#ff5252;
    color:#fff;
    cursor:pointer;
    transition:0.3s;
}
button:hover{
    background:#ff1744;
    box-shadow:0 0 12px #ff5252;
}

/* ===== MODULE GRID ===== */
.modules{
    display:grid;
    grid-template-columns:repeat(auto-fit,minmax(250px,1fr));
    gap:30px;
}

.module-card{
    background:rgba(0,0,0,0.55);
    padding:30px;
    border-radius:18px;
    border:1px solid rgba(0,229,255,0.2);
    backdrop-filter: blur(10px);
    text-align:center;
    transition:0.4s;
}

.module-card:hover{
    transform:translateY(-10px);
    box-shadow:0 0 25px rgba(0,229,255,0.4);
}

.module-card h3{
    color:#00e5ff;
    margin-bottom:10px;
    font-size:18px;
}

.module-card p{
    font-size:14px;
    color:#ccc;
    margin-bottom:18px;
}

.module-card a{
    display:inline-block;
    padding:10px 22px;
    border-radius:25px;
    background:#00e5ff;
    color:#000;
    text-decoration:none;
    font-size:14px;
    font-weight:600;
}
.module-card a:hover{
    background:#00bcd4;
    box-shadow:0 0 15px #00e5ff;
}

/* ===== FOOTER ===== */
footer{
    text-align:center;
    padding:18px;
    background:rgba(0,0,0,0.6);
    font-size:13px;
    color:#bbb;
    margin-top:60px;
}

/* ===== ANIMATION ===== */
@keyframes fadeIn{
    from{ opacity:0; transform:translateY(25px); }
    to{ opacity:1; transform:translateY(0); }
}
</style>
</head>

<body>

<header>
    <div class="logo">🔐 PhishDetectPro</div>
    <nav>
        <a href="index.jsp">Home</a>
        <a href="ULogin.jsp">Logout</a>
    </nav>
</header>

<section class="dashboard">

    <!-- Approval Drain Simulation -->
    <div class="glass-box">
        <h2>💸 Approval-Based Token Drain Simulation</h2>

        <table>
            <tr>
                <th>Owner Wallet</th>
                <th>Approved</th>
                <th>Used</th>
                <th>Balance</th>
                <th>Risk</th>
                <th>Action</th>
            </tr>

            <%
            while(rs.next()){
                double approved = rs.getDouble("approved_amount");
                double used = rs.getDouble("used_amount");
                double balance = rs.getDouble("token_balance");
                boolean highRisk = approved > balance * 0.6;
            %>
            <tr>
                <td><%= rs.getString("owner_wallet") %></td>
                <td><%= approved %></td>
                <td><%= used %></td>
                <td><%= balance %></td>
                <td class="<%= highRisk ? "high":"low" %>">
                    <%= highRisk ? "HIGH RISK":"LOW RISK" %>
                </td>
                <td>
                <% if(highRisk){ %>
                    <form action="DrainAttack" method="post">
                        <input type="hidden" name="approval_id" value="<%= rs.getInt("id") %>">
                        <button type="submit">Simulate Drain</button>
                    </form>
                <% } %>
                </td>
            </tr>
            <% } con.close(); %>
        </table>
    </div>

    <!-- MODULES (SAME AS DASHBOARD) -->
    <div class="modules">

        <div class="module-card"><h3>💰 Wallet</h3><p>View wallet balance</p><a href="ViewWallet">Open</a></div>
        <div class="module-card"><h3>👤 Add Beneficiary</h3><p>Add trusted users</p><a href="add_beneficiary.jsp">Open</a></div>
        <div class="module-card"><h3>📋 Beneficiaries</h3><p>Manage beneficiaries</p><a href="view_beneficiaries.jsp">Open</a></div>
        <div class="module-card"><h3>🔄 Transfer</h3><p>Send tokens</p><a href="send_tokens.jsp">Open</a></div>
        <div class="module-card"><h3>✅ Approvals</h3><p>Approve tokens</p><a href="approval.jsp">Open</a></div>
        <div class="module-card"><h3>📑 View Approvals</h3><p>Monitor approvals</p><a href="view_approvals.jsp">Open</a></div>
        <div class="module-card"><h3>⚠️ Phishing Detection</h3><p>Detect phishing</p><a href="phishing_detection.jsp">Open</a></div>
        <div class="module-card"><h3>⛓️ Blockchain</h3><p>Create blocks</p><a href="create_block.jsp">Open</a></div>
        <div class="module-card"><h3>📊 Transactions</h3><p>Transaction history</p><a href="TransactionGraph">Open</a></div>

    </div>
</section>

<footer>
    © 2025 PhishDetectPro | Smart Wallet & Approval Phishing Detection Framework
</footer>

</body>
</html>
