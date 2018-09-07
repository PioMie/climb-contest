package cc.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.model.Attempt;
import cc.model.Climber;
import cc.service.ClimbersService;

@Controller
@RequestMapping("/secret")
public class SecretController {

	@Autowired
	ClimbersService climbersService;

	@GetMapping
	public String get() {
		return "secret";
	}

	@PostMapping("/addAttempt")
	public ModelAndView addAttempt(@ModelAttribute Attempt attempt) throws InterruptedException, ExecutionException {
		Climber climber = climbersService.getClimber(attempt.getCompetitorId());
		return new ModelAndView("competitor", "climber", climber);
	}

}
