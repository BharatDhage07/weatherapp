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
<div class="jumbotron p-3 mb-0 bg-transparent shadow">
	<h4 style="text-align:center">Current Weather Search</h4>
</div>
<div class="container mt-4">
	<div class="row">
		<div class="col-sm-6 mx-auto">
			<div class="card shadow bg-transparent">
				<form class="card-body">
					<div class="form-group form-row">
					<label class="col-sm-4 col-form-label">Country Name </label>
					<div class="col-sm-8">
					<input type="text" value="${param.country }" class="form-control"
					name="country" required placeholder="Country Name">
					</div>
					</div>
					
					<div class="form-group form-row">
					<label class="col-sm-4 col-form-label">City Name </label>
					<div class="col-sm-8">
					<input type="text" value="${param.city }" class="form-control"
					name="city" required placeholder="City Name">
					</div>
					</div>
					
					<button class="btn btn-primary float-right">Show Me</button>
				</form>
			</div>
			
			<c:if test="${found }">
			<div class="card shadow mt-4">
				<div class="card-body text-center">
				<img src="http://openweathermap.org/img/wn/${info.icon }@2x.png">
				${info.description }<br>
				Temprature in Fahrenheit : ${info.fahrenheitTemperature } &#8457;<br>
				Temprature in Celsius: ${info.celsiusTemperature } &#8451;<br>
				</div>
			</div>
			</c:if>
		</div>
	</div>
	
	
	</div>
</body>
</html>