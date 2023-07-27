package com.weatherapp.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.weatherapp.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.weatherapp.services.EmailService;
import com.weatherapp.services.UserService;
import com.weatherapp.services.WeatherService;

@RestController
@CrossOrigin("*")
public class HomeController {
	
	@Autowired WeatherService service;
	@Autowired UserService uservice;
	@Autowired EmailService eservice;
	@Autowired HttpSession session;
	
	@Autowired WeatherAppProperties props;

	@GetMapping("/")
	public String Homepage() {
		return "index";
	}

	@PostMapping("/login")
	public ResponseEntity<String> validate(@RequestParam String userid, @RequestParam String pwd) {
		User user = uservice.validateUser(userid, pwd);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}
		LocalDate currentDate = LocalDate.now();
		if (user.getLast_login_date() == null || !user.getLast_login_date().equals(currentDate)) {
			user.setPoints(user.getPoints() + 10);
			user.setLast_login_date(currentDate);
			uservice.saveUser(user);
			return ResponseEntity.ok("Validation successful. You received 10 daily login bonus points.");
		} else {
			return ResponseEntity.ok("Validation successful");
		}
	}

	@GetMapping("/home")
	public ResponseEntity<HomeData> getHomePageData() {
		User user = (User) session.getAttribute("user");
		String country = user.getCountry();
		String city = user.getCity();
		Weather weather = service.getWeather(country, city);
		WeatherInfo winfo = new WeatherInfo(country, city, weather);
		WeatherForecast wfinfo = service.getWeatherForecast(country, city);
		HomeData homeData = new HomeData(winfo, wfinfo);
		return ResponseEntity.ok(homeData);
	}


//	@PostMapping("/register")
//	public ResponseEntity<String> registerProcess(@RequestBody User user) {
//		uservice.saveUser(user);
//		return ResponseEntity.ok("User registered successfully");
//	}
@GetMapping("/notify/{userid}")
public ResponseEntity<String> sendNotification(@PathVariable("userid") String userid, String weather) {
	User nuser = uservice.findByUserid(userid);
	Weather nweather = service.getWeather(nuser.getCountry(), nuser.getCity());
	WeatherInfo winfo = new WeatherInfo(nuser.getCountry(), nuser.getCity(), nweather);
	final String message = "Hi " + nuser.getUname() + "!\n" +
			"The city " + winfo.getCity() + " has " + nweather.getDescription() + " with " + nweather.getCelsiusTemperature() + "°C.\n" +
			"Be careful.\n";
	eservice.sendMessage(userid, message);
	return ResponseEntity.ok("Mail sent successfully");
}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUserList(){
		List<User> userList = uservice.getAllUsersWithArticles();
		return ResponseEntity.ok(userList);
	}

	@GetMapping("/users/{userid}")
	public ResponseEntity<User> getUserById(@PathVariable("userid") String userid) {
		User user = uservice.findByUserid(userid);
		Weather weather = service.getWeather(user.getCountry(), user.getCity());
		WeatherInfo winfo = new WeatherInfo(user.getCountry(), user.getCity(), weather);
		user.setWeather(winfo.getDescription());
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/logout")
	public String Logout() {
		session.invalidate();
		return "redirect:/";
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerProcess(@RequestBody User user) {
		uservice.saveUser(user);
		return ResponseEntity.ok("User registered successfully");
	}

	@GetMapping("/current")
	public ResponseEntity<WeatherInfo> getCurrentWeather(String country, String city) {
		WeatherInfo weatherInfo = null;
		if (country != null) {
			Weather weather = service.getWeather(country, city);
			weatherInfo = new WeatherInfo(country, city, weather);
		}
		if (weatherInfo != null) {
			return ResponseEntity.ok(weatherInfo);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/forecast")
	public ResponseEntity<WeatherForecast> getForecastPage(String country, String city) {
		if (country != null) {
			WeatherForecast wfinfo = service.getWeatherForecast(country, city);
			return ResponseEntity.ok(wfinfo);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/create-article")
	public ResponseEntity<String> createArticle(@RequestParam String userid, @RequestParam String pwd, @RequestBody Article article){
		User user = uservice.validateUser(userid, pwd);

		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
		}

		uservice.createArticle(user, article);
		return ResponseEntity.ok("Article created successfully.");
	}

	@GetMapping("/articles")
	public ResponseEntity<List<Article>> getAllArticles () {
		List<Article> articles = uservice.getAllArticles();
		return ResponseEntity.ok(articles);
	}
	
}
