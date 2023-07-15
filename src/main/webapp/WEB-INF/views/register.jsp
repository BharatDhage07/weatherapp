<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Weather App</title>
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
<div class="jumbotron text-center bg-transparent shadow">
	<h3>Weather Application</h3>
</div>
<div class="container">
	<div class="row">
		<div class="col-sm-6 mx-auto">
			<div class="card shadow bg-transparent">
				<div class="card-header text-center">
					<h5>User Registration Screen</h5>
				</div>
				<div class="card-body">
					<form method="post">
						<div class="form-group form-row">
							<label class="col-form-label col-sm-4">User Name</label>
							<div class="col-sm-8">
								<input type="text" name="uname" required placeholder="Your Name" class="form-control">
							</div>
						</div>
						<div class="form-group form-row">
							<label class="col-form-label col-sm-4">Country</label>
							<div class="col-sm-8">
								<input type="text" name="country" required placeholder="Country Name" class="form-control">
							</div>
						</div>
						<div class="form-group form-row">
							<label class="col-form-label col-sm-4">City</label>
							<div class="col-sm-8">
								<input type="text" name="city" required placeholder="City Name" class="form-control">
							</div>
						</div>
						<div class="form-group form-row">
							<label class="col-form-label col-sm-4">User ID</label>
							<div class="col-sm-8">
								<input type="text" name="userid" required placeholder="Your Email Id" class="form-control">
							</div>
						</div>
						<div class="form-group form-row">
							<label class="col-form-label col-sm-4">Password</label>
							<div class="col-sm-8">
								<input type="password" required placeholder="Password" name="pwd" class="form-control">
							</div>
						</div>
						<button class="btn btn-primary px-4 float-right">Login</button>
					</form>
				</div>
				<div class="card-footer text-right">
					Already registered <a href="/">Click here</a>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>