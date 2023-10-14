<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
  <title>EasyBank</title>
</head>
<body>




<div class="header">
  <div>
      <a class="logo" href="${pageContext.servletContext.contextPath}">EasyBank</a>
  </div>
  <div class="nav-links">
    <ul>
      <li><a href="<%= request.getContextPath() %>/ClientServlet?action=list">Client</a></li>
      <li><a href="<%= request.getContextPath() %>/EmployeeServlet?action=list">Employe</a></li>
    </ul>
  </div>

</div>

<div class="hero__content">
  <div class="text_content">
    <h1>
      Next generation <br> digital banking
    </h1>
    <p>
      Take your financial life online. Your Easybank account will be a one-stop-shop
      for spending, saving, budgeting, investing, and much more.
    </p>
    <div class="b1">
      <button class="cta-2">
        Request Invite
      </button>
    </div>

  </div>
  <div class="image__content">
    <img src="image/bank.jpg" alt="digital banking">
  </div>
</div>

<div class="footer">
  <div class="logo-social">
    <div class="logo">
      EasyBank
    </div>
    <div class="socials">
      <img src="/icon-facebook.svg" alt="social-media">
      <img src="/icon-twitter.svg" alt="social-media">
      <img src="/icon-instagram.svg" alt="social-media">
      <img src="/icon-youtube.svg" alt="social-media">
      <img src="/icon-pinterest.svg" alt="social-media">
    </div>
  </div>
  <div class="footer_links">
    <Ul>
      <li><a href="#">About us</a></li>
      <li><a href="#">Contact</a></li>
      <li><a href="#">Blog</a></li>
    </Ul>
    <Ul>
      <li><a href="#">Careers</a></li>
      <li><a href="#">Support</a></li>
      <li><a href="#">Privacy Policy</a></li>
    </Ul>
  </div>
  <div class="end_links">
    <button class="cta-2">Request Invite</button>
    <p> © Easybank. All Rights Reserved </p>
  </div>
</div>
</body>
</html>