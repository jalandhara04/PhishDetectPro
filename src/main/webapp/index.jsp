<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PhishDetectPro</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

<style>
*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:'Poppins', sans-serif;
}

/* ===== FULL PAGE BACKGROUND ===== */
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

/* ===== HERO ===== */
.hero{
    height:90vh;
    display:flex;
    align-items:center;
    justify-content:center;
    text-align:center;
}

.hero-box{
    max-width:900px;
    background:rgba(0,0,0,0.45);
    padding:45px;
    border-radius:18px;
    box-shadow:0 0 40px rgba(0,229,255,0.2);
    animation:fadeIn 1.3s ease-in-out;
}

.hero-box h1{
    font-size:44px;
    color:#00e5ff;
    margin-bottom:20px;
}

.hero-box p{
    font-size:16px;
    line-height:1.9;
    color:#eee;
}

/* ===== BUTTONS ===== */
.btn-group{
    margin-top:25px;
}

.btn{
    padding:12px 30px;
    border-radius:30px;
    text-decoration:none;
    margin:10px;
    font-size:15px;
    font-weight:500;
    transition:0.4s;
    display:inline-block;
}

.btn-primary{
    background:#00e5ff;
    color:#000;
}

.btn-primary:hover{
    background:#00bcd4;
    transform:translateY(-4px);
    box-shadow:0 0 18px #00e5ff;
}

.btn-outline{
    border:2px solid #00e5ff;
    color:#00e5ff;
}

.btn-outline:hover{
    background:#00e5ff;
    color:#000;
}

/* ===== FEATURES ===== */
.features{
    padding:80px 10%;
    display:grid;
    grid-template-columns:repeat(auto-fit,minmax(260px,1fr));
    gap:30px;
}

.feature-box{
    background:rgba(0,0,0,0.55);
    padding:30px;
    border-radius:18px;
    border:1px solid rgba(0,229,255,0.2);
    transition:0.4s;
    backdrop-filter: blur(10px);
}

.feature-box:hover{
    transform:translateY(-10px);
    box-shadow:0 0 25px rgba(0,229,255,0.4);
}

.feature-box h3{
    color:#00e5ff;
    margin-bottom:12px;
}

.feature-box p{
    font-size:14px;
    color:#ddd;
    line-height:1.7;
}

/* ===== FOOTER ===== */
footer{
    text-align:center;
    padding:18px;
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
        <a href="#features">Features</a>
        <a href="ULogin.jsp">Login</a>
        <a href="URegister.jsp">Register</a>
    </nav>
</header>

<section class="hero">
    <div class="hero-box">
        <h1>PhishDetectPro</h1>
        <p>
            A Servlet-Based Smart Wallet Simulation and Approval Phishing Detection Framework
            Using Intent Validation.
            <br><br>
            Learn how approval phishing, token drain attacks, and fraudulent approvals
            exploit blockchain wallets — and how intent validation can stop them.
        </p>

        <div class="btn-group">
            <a href="ULogin.jsp" class="btn btn-primary">User Login</a>
            <a href="URegister.jsp" class="btn btn-outline">New User Registration</a>
        </div>
    </div>
</section>

<section class="features" id="features">
    <div class="feature-box">
        <h3>🔐 Smart Wallet Simulation</h3>
        <p>Simulates wallet balance, approvals, transfers, and spending permissions.</p>
    </div>

    <div class="feature-box">
        <h3>⚠️ Approval Phishing Detection</h3>
        <p>Detects suspicious approval requests using intent validation logic.</p>
    </div>

    <div class="feature-box">
        <h3>💸 Token Drain Attack</h3>
        <p>Demonstrates real-world approval-based token drain fraud scenarios.</p>
    </div>

    <div class="feature-box">
        <h3>📊 Transaction Analytics</h3>
        <p>Visualizes user transactions and blockchain block creation.</p>
    </div>
</section>

<footer>
    © 2025 PhishDetectPro | 
</footer>

</body>
</html>
