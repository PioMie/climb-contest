package cc.controller.scoreedition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.controller.scoreedition.model.ClimberEdition;
import cc.dto.climber.Climber;
import cc.service.ClimbersService;

@Controller
@RequestMapping("/scoreEdition")
public class ScoreEditionController {

	@Autowired
	ClimbersService climbersService;

	@GetMapping
	public ModelAndView get() {
		return showEditPage(null);
	}

	@GetMapping("/{climberId}")
	public ModelAndView home(@PathVariable String climberId) {
		return showEditPage(climberId);
	}

	private ModelAndView showEditPage(String climberId) {
		Climber climber = climbersService.getClimber(Integer.parseInt(climberId));

		return climberDetails(climber);
	}

	Map<Integer, List<String>> organizeRoutesUnderEditions(List<String> routes) {
		Map<Integer, List<String>> res = new HashMap<>();
		for (int i = 0; i * 20 + 19 < routes.size(); ++i) {
			List<String> edition = routes.subList(i * 20, i * 20 + 20);
			res.put(i + 1, edition);
		}
		return res;
	}

	@PostMapping("/edit")
	public ModelAndView save(@ModelAttribute ClimberEdition climberEdition) {

		Climber climber = map(climberEdition);
		climbersService.saveClimber(climber);

		return climberDetails(climber);
	}
	
	private ModelAndView climberDetails(Climber climber) {
		Map<String, Object> model = new HashMap<>();
		model.put("editions", organizeRoutesUnderEditions(climber.getRouteScores()).entrySet());
		model.put("climber", climber);

		return new ModelAndView("scoreEdition", model);
	}

	private Climber map(ClimberEdition climberEdition) {
		Climber climber = climbersService.getClimber(climberEdition.getId());
		return new Climber(climber.getId(), climberEdition.getName(), climberEdition.getClub(),
				climberEdition.getCategory(), climber.getScore(), climberEdition.getRoutes());
	}
}
