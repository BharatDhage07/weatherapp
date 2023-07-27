package com.weatherapp.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.weatherapp.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	NamedParameterJdbcTemplate temp;

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private WeatherService weatherService;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void saveUser(User user) {
		user.setRole("User");
		final String sql = "INSERT INTO users(userid, uname, country, city, pwd, role, bonus_points, last_login_date) " +
				"VALUES(:userid, :uname, :country, :city, :pwd, :role, :bonus_points, :last_login_date) " +
				"ON DUPLICATE KEY UPDATE bonus_points=:bonus_points, last_login_date=:last_login_date";
		MapSqlParameterSource params = (MapSqlParameterSource) getSqlParameterByModel(user);
		params.addValue("bonus_points", user.getPoints());
		params.addValue("last_login_date", user.getLast_login_date());
		temp.update(sql, params);
	}

//	public List<User> allUsers() {
//		return temp.query("select * from users", new UserRowMapper());
//	}

	public User validateUser(String userid, String pwd) {
		try {
			MapSqlParameterSource map = new MapSqlParameterSource();
			map.addValue("userid", userid);
			map.addValue("pwd", pwd);
			return temp.queryForObject("select * from users where userid=:userid and pwd=:pwd", map, new UserRowMapper());
		} catch (Exception ex) {
			return null;
		}
	}

	public User findByUserid(String userid) {
		User user = userRepository.findById(userid).orElse(null);
		if (user != null){
			List<Article> articles = articleRepository.findAllByAuthor(user);
			user.setArticles(articles);
		}
//		MapSqlParameterSource map = new MapSqlParameterSource();
//		map.addValue("userid", userid);
//		return temp.queryForObject("select * from users where userid=:userid", map, new UserRowMapper());
		return user;
	}

	public void createArticle(User user, Article article) {

		if (user.getArticles() == null) {
			user.setArticles(new ArrayList<>());
		}
		article.setAuthor(user);
		article.setPublishedDate(LocalDateTime.now());
		user.getArticles().add(article);

		final String sql = "INSERT INTO articles (title, content, published_date, author_id) " +
				"VALUES (:title, :content, :published_date, :authorId)";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("title", article.getTitle());
		params.addValue("content", article.getContent());
		params.addValue("published_date", Timestamp.valueOf(article.getPublishedDate()));
		params.addValue("authorId", user.getUserid());
		jdbcTemplate.update(sql, params);

	}

	public List<Article> getAllArticles() {

		final String sql = "SELECT * FROM articles";

		List<Article> articles = jdbcTemplate.query(sql, new ArticleRowMapper());

		return articles;
	}

	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

	public List<User> getAllUsersWithArticles(){
		List<User> users = userRepository.findAll();

		for(User user: users) {
			List<Article> articles = articleRepository.findAllByAuthor(user);
			user.setArticles(articles);
			Weather weather = weatherService.getWeather(user.getCountry(), user.getCity());
			WeatherInfo weatherInfo = new WeatherInfo(user.getCountry(), user.getCity(), weather);
			user.setWeather(weatherInfo.getDescription());
		}
//		List<User> users1 = userRepository.findAll();
//		for (User user: users1){
//			List<Article> articles = user.getArticles();
//			user.setArticles(articles);
//		}
		return users;
	}

	public List<Article> getArticleByUserId (String userId) {
		User user = userRepository.findById(userId).orElse(null);

		if(user != null) {
			return user.getArticles();
		}
		return new ArrayList<>();
	}

	private SqlParameterSource getSqlParameterByModel(User u) {
		MapSqlParameterSource ps = new MapSqlParameterSource();
		if (u != null) {
			ps.addValue("userid", u.getUserid());
			ps.addValue("uname", u.getUname());
			ps.addValue("country", u.getCountry());
			ps.addValue("city", u.getCity());
			ps.addValue("pwd", u.getPwd());
			ps.addValue("role", u.getRole());
			ps.addValue("bonus_points", u.getPoints());
			ps.addValue("last_login_date", u.getLast_login_date());
		}
		return ps;
	}

	private class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			User user = new User();
			user.setUserid(rs.getString("userid"));
			user.setUname(rs.getString("uname"));
			user.setCountry(rs.getString("country"));
			user.setCity(rs.getString("city"));
			user.setPwd(rs.getString("pwd"));
			user.setRole(rs.getString("role"));
			user.setPoints(rs.getInt("bonus_points"));
			user.setLast_login_date(rs.getDate("last_login_date").toLocalDate());
			return user;
		}

	}

	private class ArticleRowMapper implements RowMapper<Article> {
		@Override
		public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
			Article article = new Article();
			article.setId(rs.getLong("id"));
			article.setTitle(rs.getString("title"));
			article.setContent(rs.getString("content"));
			article.setPublishedDate(rs.getTimestamp("published_date").toLocalDateTime());
			// Note: You may also load the author details from the users table if needed.
			// In this example, we only set the author ID in the article object.
			User author = new User();
			author.setUserid(rs.getString("author_id"));
			article.setAuthor(author);
			return article;
		}
	}
}
