<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<style>
body{
background-image:url('images/wbg2.jpg');
background-attachment:fixed;
height:100vh;
background-size:100% 100%;
}
.navbar-dark .navbar-nav .nav-link {
    color: white;
}
</style>
<nav class="navbar navbar-expand-lg navbar-dark bg-transparent shadow fixed-top">
  <a class="navbar-brand" href="#">
    <img src="images/logo.png" width="30" height="30" alt="">
  </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    <ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="/home">Home</a>
      </li>
      <c:if test="${sessionScope.user.role eq 'admin' }">
      <li class="nav-item">
        <a class="nav-link" href="/users">Users</a>
      </li>
      </c:if>
      <li class="nav-item">
        <a class="nav-link" href="/current">Search Weather</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/forecast">Search Forecast</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/logout">Logout</a>
      </li>
    </ul>
  </div>
</nav>
<div class="mt-4">&nbsp;</div>