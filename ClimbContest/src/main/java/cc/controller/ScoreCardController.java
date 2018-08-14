package cc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
		List<Climber> climbers = Arrays.asList(new Climber("Kozak", 5), new Climber("Dzik", 4),
				new Climber("Anakin", 4), new Climber("Zdzich", 3), new Climber("Marta", 1));

		model.put("climbers", climbers);
		model.put("header", "Score Card");

		return new ModelAndView("index", model);
	}

}
