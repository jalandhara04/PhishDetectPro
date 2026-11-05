<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Login | PhishDetectPro</title>

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

/* ===== LOGIN SECTION ===== */
.login-section{
    height:85vh;
    display:flex;
    align-items:center;
    justify-content:center;
}

.login-box{
    width:420px;
    background:rgba(0,0,0,0.55);
    padding:40px;
    border-radius:18px;
    box-shadow:0 0 35px rgba(0,229,255,0.25);
    backdrop-filter: blur(10px);
    animation:fadeIn 1.2s ease-in-out;
}

.login-box h2{
    text-align:center;
    color:#00e5ff;
    margin-bottom:25px;
}

/* ===== FORM ===== */
.login-box label{
    font-size:14px;
    color:#ccc;
}

.login-box input{
    width:100%;
    padding:12px;
    margin-top:6px;
    margin-bottom:18px;
    border:none;
    outline:none;
    border-radius:8px;
    background:#0f172a;
    color:#fff;
}

.login-box input::placeholder{
    color:#aaa;
}

/* ===== BUTTON ===== */
.login-box button{
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

.login-box button:hover{
    background:#00bcd4;
    transform:translateY(-3px);
    box-shadow:0 0 18px #00e5ff;
}

/* ===== ERROR MESSAGE ===== */
.error-msg{
    margin-top:15px;
    text-align:center;
    color:#ff6b6b;
    font-size:14px;
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
        <a href="URegister.jsp">Register</a>
    </nav>
</header>

<section class="login-section">
    <div class="login-box">
        <h2>User Login</h2>

        <form action="ULogin" method="post">
            <label>Email Address</label>
            <input type="email" name="email" placeholder="Enter your email" required>

            <label>Password</label>
            <input type="password" name="password" placeholder="Enter your password" required>

            <button type="submit">Login</button>
        </form>

        <% 
            String msg = request.getParameter("msg");
            if(msg != null){
        %>
            <div class="error-msg"><%= msg %></div>
        <% } %>
    </div>
</section>

<footer>
    © 2025 PhishDetectPro | Approval Phishing Detection Framework
</footer>

</body>
</html>
