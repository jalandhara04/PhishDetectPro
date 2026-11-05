<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    if (session == null || session.getAttribute("userid") == null) {
        response.sendRedirect("ULogin.jsp?msg=Please login first");
        return;
    }

    String username = (String) session.getAttribute("username");
    String wallet = (String) request.getAttribute("wallet");
    Double balance = (Double) request.getAttribute("balance");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Wallet | PhishDetectPro</title>

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

nav a:hover{
    color:#00e5ff;
}

/* ===== WALLET SECTION ===== */
.dashboard{
    padding:60px 8%;
}

.wallet-box{
    background:rgba(0,0,0,0.55);
    padding:40px;
    border-radius:18px;
    box-shadow:0 0 35px rgba(0,229,255,0.25);
    backdrop-filter: blur(10px);
    max-width:600px;
    margin:0 auto 60px;
    animation:fadeIn 1.2s ease-in-out;
}

.wallet-box h2{
    text-align:center;
    color:#00e5ff;
    margin-bottom:25px;
}

.wallet-box label{
    font-size:14px;
    color:#ccc;
}

.wallet-box input{
    width:100%;
    padding:12px;
    margin-top:6px;
    margin-bottom:18px;
    border:none;
    border-radius:8px;
    background:#0f172a;
    color:#fff;
}

.wallet-box hr{
    border:1px solid rgba(0,229,255,0.2);
    margin:25px 0;
}

.wallet-box button{
    width:100%;
    padding:12px;
    border:none;
    border-radius:30px;
    background:#00e5ff;
    color:#000;
    font-size:15px;
    font-weight:600;
    cursor:pointer;
    transition:0.4s;
}

.wallet-box button:hover{
    background:#00bcd4;
    box-shadow:0 0 18px #00e5ff;
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

/* ===== ANIMATION ===== */
@keyframes fadeIn{
    from{opacity:0; transform:translateY(25px);}
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

    <!-- Wallet Info -->
    <div class="wallet-box">
        <h2>💼 My Wallet</h2>

        <label>Wallet Address</label>
        <input type="text" value="<%= wallet %>" readonly>

        <label>Token Balance</label>
        <input type="text" value="<%= balance %>" readonly>

        <hr>

        <h3 style="text-align:center;color:#00e5ff;margin-bottom:15px;">➕ Recharge Wallet</h3>

        <form action="RechargeWallet" method="post">
            <input type="number" name="amount" placeholder="Enter recharge amount" min="1" required>
            <button type="submit">Recharge Wallet</button>
        </form>
    </div>

    <!-- Quick Modules -->
    <div class="modules">
    
    
        <div class="module-card">
            <h3>👤 Add Beneficiary</h3>
            <p>Add trusted beneficiary addresses.</p>
            <a href="add_beneficiary.jsp">Open</a>
        </div>

        <div class="module-card">
            <h3>📋 View Beneficiaries</h3>
            <p>View and manage saved beneficiaries.</p>
            <a href="view_beneficiaries.jsp">Open</a>
        </div>

        <div class="module-card">
            <h3>🔄 Transfer Tokens</h3>
            <p>Send tokens to beneficiaries.</p>
            <a href="send_tokens.jsp">Open</a>
        </div>

        <div class="module-card">
            <h3>✅ Approve Tokens</h3>
            <p>Grant token approvals securely.</p>
            <a href="approval.jsp">Open</a>
        </div>
        
        <div class="module-card">
            <h3>📑 View Token Approvals</h3>
            <p>Monitor active token approvals.</p>
            <a href="view_approvals.jsp">Open</a>
        </div>

        <div class="module-card">
            <h3>⚠️ Phishing Detection</h3>
            <p>Detect approval phishing attempts.</p>
            <a href="phishing_detection.jsp">Open</a>
        </div>
        
        <div class="module-card">
            <h3>💸 Approval Drain Simulation</h3>
            <p>Simulate token drain attacks.</p>
            <a href="approval_drain_simulation.jsp">Open</a>
        </div>

        <div class="module-card">
            <h3>⛓️ Create Blockchain Blocks</h3>
            <p>Generate blockchain blocks.</p>
            <a href="create_block.jsp">Open</a>
        </div>

        <div class="module-card">
            <h3>📊 Transaction History</h3>
            <p>View wallet transaction graph.</p>
            <a href="TransactionGraph">Open</a>
        </div>

    </div>
</section>

<footer>
    © 2025 PhishDetectPro | Smart Wallet & Approval Phishing Detection Framework
</footer>

</body>
</html>
