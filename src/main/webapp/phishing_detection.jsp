<%@page import="java.sql.*"%>
<%@page import="com.db.Database"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
    if (session == null || session.getAttribute("userid") == null) {
        response.sendRedirect("ULogin.jsp?msg=Please login first");
        return;
    }

    Integer userId = (Integer) session.getAttribute("userid");
    String username = (String) session.getAttribute("username");

    Connection con = Database.getconection();
    PreparedStatement ps = con.prepareStatement(
        "SELECT ta.*, u.token_balance FROM token_approvals ta " +
        "JOIN users u ON ta.owner_id = u.id WHERE ta.owner_id=?"
    );
    ps.setInt(1, userId);
    ResultSet rs = ps.executeQuery();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Phishing Detection | PhishDetectPro</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

<style>
*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:'Poppins', sans-serif;
}

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
}

nav a:hover{
    color:#00e5ff;
}

/* ===== CONTENT ===== */
.dashboard{
    padding:60px 8%;
}

.card{
    background:rgba(0,0,0,0.55);
    padding:35px;
    border-radius:20px;
    box-shadow:0 0 35px rgba(0,229,255,0.25);
    backdrop-filter: blur(12px);
    margin-bottom:60px;
    animation:fadeIn 1.2s ease-in-out;
}

.card h2{
    text-align:center;
    color:#00e5ff;
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
    background:#0f172a;
    color:#00e5ff;
}

tr:nth-child(even){
    background:rgba(255,255,255,0.05);
}

.high{ color:#ff4d4d; font-weight:600; }
.medium{ color:#ffae42; font-weight:600; }
.safe{ color:#4caf50; font-weight:600; }

button{
    padding:8px 18px;
    border:none;
    border-radius:20px;
    background:#ff4d4d;
    color:#fff;
    cursor:pointer;
    font-weight:600;
}

button:hover{
    background:#ff1a1a;
    box-shadow:0 0 12px #ff4d4d;
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

@keyframes fadeIn{
    from{opacity:0; transform:translateY(30px);}
    to{opacity:1; transform:translateY(0);}
}
</style>
</head>

<body>

<header>
    <div class="logo">🔐 PhishDetectPro</div>
    <nav>
        <a href="user_dashboard.jsp">Dashboard</a>
        <a href="ULogin.jsp">Logout</a>
    </nav>
</header>

<section class="dashboard">

<div class="card">
<h2>⚠️ Phishing Risk Analysis</h2>

<table>
<tr>
    <th>Spender Wallet</th>
    <th>Approved</th>
    <th>Used</th>
    <th>Balance</th>
    <th>Risk Level</th>
    <th>Action</th>
</tr>

<%
while(rs.next()){
    double approved = rs.getDouble("approved_amount");
    double used = rs.getDouble("used_amount");
    double balance = rs.getDouble("token_balance");

    String risk="SAFE", css="safe";
    if(approved > balance*0.7){ risk="HIGH RISK"; css="high"; }
    else if(approved > balance*0.4){ risk="MEDIUM RISK"; css="medium"; }
%>

<tr>
<td><%=rs.getString("spender_wallet")%></td>
<td><%=approved%></td>
<td><%=used%></td>
<td><%=balance%></td>
<td class="<%=css%>"><%=risk%></td>
<td>
<% if(!risk.equals("SAFE")){ %>
<form action="RevokeApproval" method="post">
<input type="hidden" name="approval_id" value="<%=rs.getInt("id")%>">
<button type="submit">Revoke</button>
</form>
<% } %>
</td>
</tr>

<% } %>
</table>
</div>

<!-- MODULES -->
<div class="modules">

<div class="module-card"><h3>💰 Wallet</h3><a href="ViewWallet">Open</a></div>
<div class="module-card"><h3>👤 Add Beneficiary</h3><a href="add_beneficiary.jsp">Open</a></div>
<div class="module-card"><h3>📋 Beneficiaries</h3><a href="view_beneficiaries.jsp">Open</a></div>
<div class="module-card"><h3>🔄 Transfer Tokens</h3><a href="send_tokens.jsp">Open</a></div>
<div class="module-card"><h3>✅ Approvals</h3><a href="approval.jsp">Open</a></div>
<div class="module-card"><h3>📑 View Approvals</h3><a href="view_approvals.jsp">Open</a></div>
<div class="module-card"><h3>💸 Drain Simulation</h3><a href="approval_drain_simulation.jsp">Open</a></div>
<div class="module-card"><h3>⛓️ Blockchain</h3><a href="create_block.jsp">Open</a></div>
<div class="module-card"><h3>📊 Transactions</h3><a href="TransactionGraph">Open</a></div>

</div>

</section>

<footer>
© 2025 PhishDetectPro | Smart Wallet & Approval Phishing Detection Framework
</footer>

</body>
</html>
