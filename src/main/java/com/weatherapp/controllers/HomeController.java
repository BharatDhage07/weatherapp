package com.weatherapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.weatherapp.models.User;
import com.weatherapp.models.Weather;
import com.weatherapp.models.WeatherAppProperties;
import com.weatherapp.models.WeatherForecast;
import com.weatherapp.models.WeatherInfo;
import com.weatherapp.services.EmailService;
import com.weatherapp.services.UserService;
import com.weatherapp.services.WeatherService;

@Controller
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
	
	@PostMapping("/")
	public String Validate(String userid,String pwd,RedirectAttributes ra) {
		User user=uservice.validateUser(userid, pwd);
		if(user==null) {
			ra.addFlashAttribute("error", "Invalid username or password");
			return "redirect:/";
		}
		session.setAttribute("user", user);	
		return "redirect:/home";
	}
	
	@GetMapping("/home")
	public String HomePage(Model model) {
		User user=(User)session.getAttribute("user");
		String country=user.getCountry();
		String city=user.getCity();
		Weather weather=service.getWeather(country, city);
		WeatherInfo winfo=new WeatherInfo(country,city,weather);
		model.addAttribute("info", winfo);
		WeatherForecast wfinfo=service.getWeatherForecast(country, city);
		model.addAttribute("finfo", wfinfo);
		return "homepage";
	}
	
	@GetMapping("/register")
	public String Registerpage() {
		return "register";
	}
	
	@GetMapping("/notify/{userid}")
	public String sendNotification(@PathVariable("userid") String userid,String weather,RedirectAttributes ra) {
		User nuser=uservice.findByUserid(userid);
		final String message="Hi ! "+nuser.getUname()+",\n"+
				"The city "+nuser.getCity()+" has "+weather+".\n"+
				"Be careful.\n";
		eservice.sendMessage(userid, message);
		ra.addFlashAttribute("msg", "Mail send successfully");
		return "redirect:/users";
	}
	
	@GetMapping("/users")
	public String Userslist(Model model) {
		List<User> mylist=new ArrayList<>();
		for(User user : uservice.allUsers()) {
			Weather weather=service.getWeather(user.getCountry(), user.getCity());
			WeatherInfo winfo=new WeatherInfo(user.getCountry(), user.getCity(),weather);
			user.setWeather(winfo.getDescription());
			mylist.add(user);
		}
		model.addAttribute("list", mylist);
		return "users";
	}
	
	@GetMapping("/logout")
	public String Logout() {
		session.invalidate();
		return "redirect:/";
	}
	
	@PostMapping("/register")
	public String RegisterProcess(User user,RedirectAttributes ra) {
		uservice.saveUser(user);
		ra.addFlashAttribute("msg", "User registered successfully");
		return "redirect:/";
	}

	@GetMapping("/current")
	public String Current(String country,String city,Model model) {
		if(country!=null) {
			Weather weather=service.getWeather(country, city);
			WeatherInfo winfo=new WeatherInfo(country,city,weather);
			model.addAttribute("found", true);
			model.addAttribute("info", winfo);
		}
		return "CurrentWeather";
	}
	
	@GetMapping("/forecast")
	public String ForecastPage(String country,String city,Model model) {
		if(country!=null) {
			WeatherForecast wfinfo=service.getWeatherForecast(country, city);
			model.addAttribute("found", true);
			model.addAttribute("info", wfinfo);
		}
		return "ForecastWeather";
	}
	
}
