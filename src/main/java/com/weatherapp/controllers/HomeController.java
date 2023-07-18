package com.weatherapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.weatherapp.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		// Perform necessary operations
		// ...
		return ResponseEntity.ok("Validation successful");
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

	@GetMapping("/register")
	public ResponseEntity<String> getRegisterPage() {
		return ResponseEntity.ok("Register page");
	}

	@GetMapping("/notify/{userid}")
	public ResponseEntity<String> sendNotification(@PathVariable("userid") String userid, String weather) {
		User nuser = uservice.findByUserid(userid);
		final String message = "Hi ! " + nuser.getUname() + ",\n" +
				"The city " + nuser.getCity() + " has " + weather + ".\n" +
				"Be careful.\n";
		eservice.sendMessage(userid, message);
		return ResponseEntity.ok("Mail sent successfully");
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsersList() {
		List<User> userList = new ArrayList<>();
		for (User user : uservice.allUsers()) {
			Weather weather = service.getWeather(user.getCountry(), user.getCity());
			WeatherInfo winfo = new WeatherInfo(user.getCountry(), user.getCity(), weather);
			user.setWeather(winfo.getDescription());
			userList.add(user);
		}
		return ResponseEntity.ok(userList);
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
	
}
