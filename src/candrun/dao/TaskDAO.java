package candrun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import candrun.model.Task;

public class TaskDAO extends JdbcDaoSupport{
	
	private RowMapper<Task> rowMapper = new RowMapper<Task>() {
		public Task mapRow(ResultSet rs, int rowNum) {
			try {
				return new Task(rs.getString("contents"), rs.getInt("goal_id"), rs.getInt("id"), rs.getInt("nudge"));
			} catch (SQLException e) {
				throw new BeanInstantiationException(Task.class, e.getMessage(), e);
			}
		}
	};
	
	public void addTask(Task task) {
		String sql ="INSERT INTO tasks(contents, goal_id) VALUES(?, ?)";
		getJdbcTemplate().update(sql, task.getContents(), task.getGoalId());
	}

	//같은 goal아래 tasks을 불러온다
	public List<Task> findTasksByGoalId(int goalId) {
		String sql ="SELECT * FROM tasks WHERE goal_id = ? LIMIT 3";
		return getJdbcTemplate().query(sql, rowMapper, goalId);
	}
	
	//TaskId로 해당 태스크를 가져온다. 
	public Task findTaskByTaskId(int taskId) {
		String sql = "SELECT * FROM tasks WHERE id = ?";
		return getJdbcTemplate().queryForObject(sql, rowMapper, taskId);
	}

	//taskId를 갖고 와서 해당 task의 넛지 수를 하나 올려준다
	public void addNudge(int taskId) {
		String sql ="UPDATE tasks SET nudge=nudge+1 WHERE id=?";
		getJdbcTemplate().update(sql,taskId);
	}
}
