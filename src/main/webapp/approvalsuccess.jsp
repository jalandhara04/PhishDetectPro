<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Approval Successful | PhishDetectPro</title>

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

/* ===== SUCCESS SECTION ===== */
.success-section{
    height:85vh;
    display:flex;
    align-items:center;
    justify-content:center;
}

.success-box{
    width:460px;
    background:rgba(0,0,0,0.55);
    padding:40px;
    border-radius:18px;
    box-shadow:0 0 35px rgba(0,229,255,0.25);
    backdrop-filter: blur(10px);
    text-align:center;
    animation:fadeIn 1.2s ease-in-out;
}

.success-box h2{
    color:#00ff9c;
    margin-bottom:18px;
    font-size:22px;
}

.success-box p{
    font-size:14px;
    color:#ffd54f;
    line-height:1.6;
    margin-bottom:25px;
}

/* ===== BACK LINK ===== */
.back-link{
    display:inline-block;
    margin-top:10px;
    text-decoration:none;
    color:#00e5ff;
    font-weight:500;
    transition:0.3s;
}

.back-link:hover{
    color:#00bcd4;
    text-shadow:0 0 10px #00e5ff;
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
        <a href="index.jsp">Home</a>
        <a href="user_dashboard.jsp">Dashboard</a>
    </nav>
</header>

<section class="success-section">
    <div class="success-box">
        <h2>✅ Approval Granted Successfully</h2>

        <p>
            ⚠ <b>Security Warning:</b><br>
            You approved a high-risk token permission.<br>
            If the spender is malicious, your tokens may be drained.
        </p>

        <!-- Existing back link only -->
        <a href="user_dashboard.jsp" class="back-link">⬅ Go to Dashboard</a>
    </div>
</section>

<footer>
    © 2025 PhishDetectPro | Approval Phishing Detection Framework
</footer>

</body>
</html>
