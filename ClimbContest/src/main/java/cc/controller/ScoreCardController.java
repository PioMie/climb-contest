package cc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
//import java.util.Comparator; //Added to make sorting possible

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.model.Climber;
import cc.service.ClimbersService;

@Controller
@RequestMapping("/scoreCard")
public class ScoreCardController {

	@Autowired
	ClimbersService climbersService;

	@GetMapping
	public ModelAndView home(Map<String, Object> model) {

		List<Climber> climbers = climbersService.getClimbers();

		model.put("climbers", climbers);
		model.put("header", "Score Card");
		model.put("routes", Arrays.asList("route 1", "route 2", "route 3", "route 4", "route 5"));

		return new ModelAndView("scoreCard", model);
	}

}
