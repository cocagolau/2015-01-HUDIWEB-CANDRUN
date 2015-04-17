package candrun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;

@RequestMapping("/nudges")
@RestController
public class NudgesController {
	private static final Logger LOGGER = LoggerFactory.getLogger(NudgesController.class);
	
	@Autowired
	TaskDAO taskDao;
	
	@Autowired
	GoalDAO goalDao;

	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable String id, HttpServletRequest req, HttpServletResponse resp) {
		int taskId = Integer.parseInt(id);
		LOGGER.debug("taskId: {}", taskId);	
		taskDao.addNudge(taskId);

		return "showGoalAndTasks";
	}
}
