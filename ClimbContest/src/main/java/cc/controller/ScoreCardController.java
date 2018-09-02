package cc.controller;

import java.util.List;
import java.util.Map;
//import java.util.Comparator; //Added to make sorting possible
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.model.Climber;
import cc.repository.ScoreRepository;

@Controller
@RequestMapping("/public")
public class ScoreCardController {

	@Autowired
	ScoreRepository scoreRepository;
	
	@GetMapping
	public ModelAndView home(Map<String, Object> model) throws InterruptedException, ExecutionException {

		List<Climber> climbers = scoreRepository.loadClimbers();

		model.put("climbers", climbers);
		model.put("header", "Score Card");

		return new ModelAndView("index", model);
	}

}
