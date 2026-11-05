<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Verify OTP | PhishDetectPro</title>

<!-- Google Font -->
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
        linear-gradient(rgba(0,0,0,0.45), rgba(0,0,0,0.6)),
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

/* ===== OTP SECTION ===== */
.otp-section{
    min-height:80vh;
    display:flex;
    justify-content:center;
    align-items:center;
}

.otp-box{
    width:420px;
    background:rgba(0,0,0,0.55);
    padding:40px;
    border-radius:20px;
    backdrop-filter: blur(12px);
    box-shadow:0 0 35px rgba(0,229,255,0.3);
    animation:fadeIn 1.2s ease-in-out;
}

.otp-box h2{
    text-align:center;
    color:#00e5ff;
    margin-bottom:10px;
}

.otp-box p{
    text-align:center;
    font-size:14px;
    color:#ccc;
    margin-bottom:25px;
}

.otp-box input{
    width:100%;
    padding:14px;
    margin-bottom:20px;
    border:none;
    outline:none;
    border-radius:10px;
    background:rgba(255,255,255,0.12);
    color:#fff;
    font-size:15px;
    text-align:center;
    letter-spacing:3px;
}

.otp-box input::placeholder{
    color:#ccc;
}

/* ===== BUTTON ===== */
.otp-box button{
    width:100%;
    padding:13px;
    border:none;
    border-radius:30px;
    background:#00e5ff;
    color:#000;
    font-size:15px;
    font-weight:600;
    cursor:pointer;
    transition:0.4s;
}

.otp-box button:hover{
    background:#00bcd4;
    transform:translateY(-3px);
    box-shadow:0 0 18px #00e5ff;
}

/* ===== FOOTER ===== */
footer{
    text-align:center;
    padding:18px;
    background:rgba(0,0,0,0.65);
    font-size:13px;
    color:#bbb;
}

/* ===== ANIMATION ===== */
@keyframes fadeIn{
    from{
        opacity:0;
        transform:translateY(30px);
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
        <a href="ULogin.jsp">Login</a>
        <a href="URegister.jsp">Register</a>
    </nav>
</header>

<section class="otp-section">
    <div class="otp-box">
        <h2>OTP Verification</h2>
        <p>Please enter the 6-digit OTP sent to your email</p>

        <form action="VerifyOTP" method="post">
            <input type="hidden" name="email" value="<%= request.getParameter("email") %>">

            <input type="text" name="otp" maxlength="6" placeholder="Enter OTP" required>

            <button type="submit">Verify OTP</button>
        </form>
    </div>
</section>

<footer>
    © 2025 PhishDetectPro | Servlet-Based Approval Phishing Detection Framework
</footer>

</body>
</html>
