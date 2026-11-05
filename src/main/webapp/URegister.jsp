<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Registration | PhishDetectPro</title>

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

/* ===== REGISTRATION SECTION ===== */
.register-section{
    min-height:80vh;
    display:flex;
    justify-content:center;
    align-items:center;
}

.register-box{
    width:420px;
    background:rgba(0,0,0,0.55);
    padding:40px;
    border-radius:20px;
    backdrop-filter: blur(12px);
    box-shadow:0 0 35px rgba(0,229,255,0.3);
    animation:fadeIn 1.2s ease-in-out;
}

.register-box h2{
    text-align:center;
    color:#00e5ff;
    margin-bottom:25px;
}

.register-box input,
.register-box select{
    width:100%;
    padding:12px 14px;
    margin-bottom:18px;
    border:none;
    outline:none;
    border-radius:8px;
    background:rgba(255,255,255,0.12);
    color:#fff;
    font-size:14px;
}

.register-box input::placeholder{
    color:#ccc;
}

.register-box select{
    color:#ccc;
}

.register-box select option{
    color:#000;
}

/* ===== BUTTON ===== */
.register-box button{
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

.register-box button:hover{
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

<section class="register-section">
    <div class="register-box">
        <h2>User Registration</h2>

        <form action="URegister" method="post">

            <input type="text" name="name" placeholder="Full Name" required>

            <input type="email" name="email" placeholder="Email Address" required>

            <input type="password" name="password" placeholder="Password" required>

            <select name="gender" required>
                <option disabled selected>Select Gender</option>
                <option>Male</option>
                <option>Female</option>
                <option>Other</option>
            </select>

            <input type="text" name="mobile" placeholder="Mobile Number" required>

            <input type="date" name="dob" required>

            <button type="submit">Create Account</button>
        </form>
    </div>
</section>

<footer>
    © 2025 PhishDetectPro | Servlet-Based Approval Phishing Detection Framework
</footer>

</body>
</html>
