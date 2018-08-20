package cc.controller;

//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
//import java.util.Comparator; //Added to make sorting possible


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.model.Climber;

@Controller
@RequestMapping("/")
public class ScoreCardController {

	@GetMapping("/")
	public ModelAndView home(Map<String, Object> model) {
		
		 
		
		List<Climber> climbers = Arrays.asList(new Climber("Kozak", 5, 90, 85), new Climber("Dzik", 4, 21, 90),
				new Climber("Anakin", 4, 24, 78), new Climber("Zdzich", 3, 30, 98), new Climber("Marta", 1, 20, 62));

		
		
		model.put("climbers", climbers);
//		model.put("climbersSortedByName", climbersSortedByName);
//		model.put("climbersSortedByScore", climbersSortedByScore);
//		model.put("climbersSortedByAge", climbersSortedByAge);		
//		model.put("climbersSortedByWeight", climbersSortedByWeight);
		model.put("header", "Score Card");
		
		return new ModelAndView("index", model);
	}

}
