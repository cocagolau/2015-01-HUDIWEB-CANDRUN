package candrun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import candrun.exception.PreparedStatementException;
import candrun.model.Goal;

public class GoalDAO extends JdbcDaoSupport {
	// connection을 만든다
	private RowMapper<Goal> rowMapper = new RowMapper<Goal>() {
		public Goal mapRow(ResultSet rs, int rowNum) {
			try {
				return new Goal(rs.getInt("id"), rs.getString("contents"),
						rs.getTimestamp("start_date"));
			} catch (SQLException e) {
				throw new BeanInstantiationException(Goal.class, e.getMessage(), e);   
			}
		}
	};
	// 입력받은 goal을 db에 넣는다.
	public int addGoal(Goal goal) {
		String sql = "INSERT INTO goal(contents, user_email, start_date) VALUES(?, ?, ?)";
	    KeyHolder keyHolder = new GeneratedKeyHolder();      
	    
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) {
					PreparedStatement ps = null;
					try {
						ps = connection.prepareStatement(sql,
								new String[] { "id" });
						ps.setString(1, goal.getContents());
						ps.setString(2, goal.getEmail());
						ps.setTimestamp(3, goal.getStartDate());
						return ps;
					} catch (SQLException e) {
						throw new PreparedStatementException();
					}
			}
		}, keyHolder);
	 
	    return keyHolder.getKey().intValue(); 
	}

	// goalId와 일치하는 goal을 불러온다
	public Goal findGoalById(int goalId) {
		String sql = "SELECT * FROM goal WHERE id = ?";
		return getJdbcTemplate().queryForObject(sql, rowMapper, goalId);
	}
		

	// 가장 최근 넣은 goal 하나만을 불러온다.
	public Goal findRecentGoal() {
		String sql = "SELECT * FROM goal ORDER BY created_date DESC LIMIT 1";
		return getJdbcTemplate().queryForObject(sql, rowMapper);
	}
}     