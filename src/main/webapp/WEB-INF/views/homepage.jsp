<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Current Weather</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>
<body>
<jsp:include page="usernav.jsp"></jsp:include>
<div class="container-fluid mt-4">
	<div class="row">
		<div class="col-sm-4">
			<div class="card shadow bg-transparent">
				<div class="card-body text-center">
				<h4>Hi! ${sessionScope.user.uname }</h4>
				<h5>Address : ${sessionScope.user.city }, ${sessionScope.user.country }</h5>
				<h5>Current Weather</h5>
				<img src="http://openweathermap.org/img/wn/${info.icon }@2x.png">
				<span style="font-size:22px">${info.description }</span><br>
				<p style="font-size:22px">Temprature in Fahrenheit : ${info.fahrenheitTemperature } &#8457;</p>
				<p style="font-size:22px">Temprature in Celsius: ${info.celsiusTemperature } &#8451;</p>
				</div>
			</div>
		</div>
		<div class="col-sm-8">
		<div class="card mt-2 bg-transparent">
		<div class="card-header text-center">
			<h5>Your weekly forecast</h5>
		</div>
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
		<c:forEach items="${finfo.entries }" var="e">
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
		</div>
	</div>
	
	
	</div>
</body>
</html>