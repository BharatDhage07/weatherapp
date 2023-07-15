<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Current Weather</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<style>
body{
background-image:url('images/wbg2.jpg');
height:100vh;
background-size:100% 100%;
}
</style>
</head>
<body>
<jsp:include page="usernav.jsp"></jsp:include>
<div class="container mt-4">
<c:if test="${msg ne null }">
<div class="alert alert-success text-center">${msg }</div>
</c:if>
<h4 class="p-2 text-center">User List</h4>	
		<table class="table table-bordered table-striped">
		<thead class="table-primary">
			<tr>
				<th>User ID</th>
				<th>User Name</th>
				<th>Address</th>
				<th>Password</th>
				<th>Weather</th>
				<th>Action</th>
			</tr>			
		</thead>		
		<tbody>
		<c:forEach items="${list }" var="u">
			<tr>
				<th>${u.userid }</th>
				<th>${u.uname }</th>
				<th>${u.city}, ${u.country }</th>
				<th>${u.pwd }</th>
				<th>${u.weather }</th>
				<td><a href="/notify/${u.userid }?weather=${u.weather}" class="btn btn-primary btn-sm">Notify User</a></td>
			</tr>
		</c:forEach>
		</tbody>
		</table>
	
</div>
</body>
</html>