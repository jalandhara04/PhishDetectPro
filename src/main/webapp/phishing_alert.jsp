<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%
    String spender = (String) session.getAttribute("PENDING_SPENDER");
    Integer amount = (Integer) session.getAttribute("PENDING_AMOUNT");

    boolean invalid = (spender == null || amount == null);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Security Alert | PhishDetectPro</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

<script>
    <% if(!invalid){ %>
    alert("⚠️ PHISHING WARNING!\n\nThis approval can drain your tokens.\nProceed only if you trust it.");
    <% } %>
</script>

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

/* ===== ALERT SECTION ===== */
.login-section{
    height:85vh;
    display:flex;
    align-items:center;
    justify-content:center;
}

.login-box{
    width:440px;
    background:rgba(0,0,0,0.55);
    padding:40px;
    border-radius:18px;
    box-shadow:0 0 35px rgba(255,0,0,0.45);
    backdrop-filter: blur(10px);
    animation:fadeIn 1.2s ease-in-out;
    text-align:center;
}

.login-box h2{
    color:#ff5252;
    margin-bottom:15px;
}

.login-box p{
    font-size:14px;
    color:#ddd;
    margin-bottom:25px;
}

/* ===== BUTTON ===== */
.login-box button{
    width:100%;
    padding:12px;
    border:none;
    border-radius:30px;
    background:#ff5252;
    color:#fff;
    font-size:15px;
    font-weight:600;
    cursor:pointer;
    transition:0.4s;
}

.login-box button:hover{
    background:#ff1744;
    transform:translateY(-3px);
    box-shadow:0 0 18px #ff5252;
}

/* ===== BACK LINK ===== */
.back-link{
    display:block;
    margin-top:20px;
    text-decoration:none;
    color:#00e5ff;
    font-size:14px;
}

.back-link:hover{
    text-decoration:underline;
}

/* ===== FOOTER ===== */
footer{
    text-align:center;
    padding:15px;
    background:rgba(0,0,0,0.6);
    font-size:13px;
    color:#bbb;
}

/* ===== ANIMATION ===== */
@keyframes fadeIn{
    from{
        opacity:0;
        transform:translateY(25px);
    }
    to{
        opacity:1;
        transform:translateY(0);
    }
}
</style>
</head>

<body>

<header>
    <div class="logo">🔐 PhishDetectPro</div>
    <nav>
        <a href="user_dashboard.jsp">Dashboard</a>
    </nav>
</header>

<section class="login-section">
    <div class="login-box">

        <% if(invalid){ %>
            <h2>⚠ Session Expired</h2>
            <p>Your approval session is invalid or expired.</p>
            <a href="user_dashboard.jsp" class="back-link">⬅ Go Back</a>

        <% } else { %>

            <h2>🚨 Phishing Alert</h2>
            <p>
                You are about to approve a <b>high-risk token permission</b>.<br>
                Malicious spenders can drain your wallet.
            </p>

            <form action="ConfirmApproval" method="post">
                <button type="submit">Proceed Anyway</button>
            </form>

            <a href="user_dashboard.jsp" class="back-link">Cancel & Go Back</a>

        <% } %>

    </div>
</section>

<footer>
    © 2025 PhishDetectPro | Approval Phishing Detection Framework
</footer>

</body>
</html>
