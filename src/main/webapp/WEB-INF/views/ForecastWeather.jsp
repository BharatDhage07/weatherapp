<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt_rt" %>
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
<div class="jumbotron p-3 mb-0 bg-transparent shadow">
	<h4 style="text-align:center">Forecast Weather Search</h4>
</div>
<div class="container mt-4">
	<div class="row">
		<div class="col-sm-12">
			<div class="card shadow bg-transparent">
				<form class="card-body">
					<div class="form-group form-row">
					<label class="col-sm-2 col-form-label">Country Name </label>
					<div class="col-sm-3">
					<input type="text" value="${param.country }" class="form-control"
					name="country" required placeholder="Country Name">
					</div>
					<label class="col-sm-2 col-form-label">City Name </label>
					<div class="col-sm-3">
					<input type="text" value="${param.city }" class="form-control"
					name="city" required placeholder="City Name">
					</div>
					<div class="col-sm-2">
					<button class="btn btn-primary float-right">Show Me</button>
					</div>
					</div>					
				</form>
				</div>
	
	<c:if test="${found }">
	<div class="card mt-2">
		<table class="table table-bordered table-striped">
		<thead>
			<tr>
				<th>Date</th>
				<th>Temperature in Celcius</th>
				<th>Temperature in Fahrenheit</th>
				<th>Weather</th>
			</tr>			
		</thead>		
		<tbody>
		<c:forEach items="${info.entries }" var="e">
			<tr>
				<td class="font-weight-bold align-middle"><f:parseDate value="${ e.date }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
				<f:formatDate pattern="dd-MMM-yyyy hh:mm a" value="${ parsedDateTime }" /></td>				
				<td class="align-middle">${e.celsiusTemperature }&#8451;</td>
				<td class="align-middle">${e.fahrenheitTemperature }&#8457;</td>
				<td>${e.description } <img src="http://openweathermap.org/img/wn/${e.weatherIcon }@2x.png"></td>
			</tr>
		</c:forEach>
		</tbody>
		</table>
	</div>
		
	</c:if>
	</div>
	</div>
	</div>
</body>
</html>