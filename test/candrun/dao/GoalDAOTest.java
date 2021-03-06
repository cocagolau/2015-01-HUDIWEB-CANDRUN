package candrun.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import candrun.model.Goal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
public class GoalDAOTest {
	
	@Autowired
	private GoalDAO goalDao;
	
	@Test
	public void findGoal() throws SQLException {
		Goal goal = new Goal("goalContents", "email");
		int goalId = goalDao.addGoal(goal);
		Goal dbGoal = goalDao.findGoalById(goalId);
		
		assertEquals(dbGoal.getId(), goalId);
	}
	
	@Test
	public void findRecentGoal() throws SQLException {
		int goalId = goalDao.addGoal(new Goal("recentGoal", "email"));
		Goal dbGoal = goalDao.findRecentGoal();
		
		assertEquals(goalId, dbGoal.getId());
	}	

	@Test(expected=EmptyResultDataAccessException.class)
	public void findUserByNotExistKey() throws SQLException {
		goalDao.findGoalById(-1);
	}
}
