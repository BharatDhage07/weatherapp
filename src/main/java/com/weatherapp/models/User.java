package com.weatherapp.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(nullable = false, unique = true)
	private String userid;

	@Column(nullable = false)
	private String uname;

	@Column(nullable = false)
	private String pwd;

	@Column(nullable = false)
	private String country;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String role;

	@Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'sunny'")
	private String weather;

	@Column(nullable = false)
	private int bonus_points;

	@Column(name = "last_login_date")
	private LocalDate last_login_date;

	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public int getPoints() {
		return bonus_points;
	}

	public void setPoints(int bonus_points) {
		this.bonus_points = bonus_points;
	}

	public LocalDate getLast_login_date() {
		return last_login_date;
	}

	public void setLast_login_date(LocalDate last_login_date) {
		this.last_login_date = last_login_date;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", uname=" + uname + ", pwd=" + pwd + ", country=" + country + ", city="
				+ city + ", role=" + role + "]";
	}
	
	
}
