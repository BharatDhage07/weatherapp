package com.weatherapp.services;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.weatherapp.models.User;

@Service
public class UserService {

	@Autowired NamedParameterJdbcTemplate temp;

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
	
	public List<User> allUsers() {
		return temp.query("select * from users", new UserRowMapper());
	}
	
	public User validateUser(String userid,String pwd) {
		try {
			MapSqlParameterSource map = new MapSqlParameterSource();
			map.addValue("userid",userid);
			map.addValue("pwd",pwd);
			return temp.queryForObject("select * from users where userid=:userid and pwd=:pwd", map,new UserRowMapper());
		}catch(Exception ex) {
			return null;
		}
	}
	
	public User findByUserid(String userid) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("userid",userid);
		return temp.queryForObject("select * from users where userid=:userid", map, new UserRowMapper());
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
	
	private class UserRowMapper implements RowMapper<User>{

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			User user=new User();
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
}
