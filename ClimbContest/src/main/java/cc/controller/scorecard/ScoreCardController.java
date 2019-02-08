package cc.controller.scorecard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
//import java.util.Comparator; //Added to make sorting possible

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.controller.scorecard.slb.SlbClimberMapper;
import cc.controller.scorecard.slb.SlbClimberScorecard;
import cc.dto.climber.Climber;
import cc.service.slb.ClimbersService;
import cc.service.slb.EditionsService;

@Controller
@RequestMapping("/scoreCard")
public class ScoreCardController {

	@Autowired
	SlbClimberMapper climberMapper;
	@Autowired
	ClimbersService climbersService;
	@Autowired
	EditionsService editionsService;
	@Autowired
	ScoreCardComparator comparator;

	private void assignPlaces(List<SlbClimberScorecard> scoreRows) {
		for (int i = 0; i < scoreRows.size(); ++i) {
			scoreRows.get(i).setPlace(i + 1);
		}
	}

	private List<Climber> filterClimbersByCategory(List<Climber> climbers, String category) {
		List<Climber> res = new ArrayList<>();
		for (Climber climber : climbers) {
			if (climber.getCategory().contains(category)) {
				res.add(climber);
			}
		}
		return res;
	}

	@GetMapping
	public ModelAndView home(Map<String, Object> model) {
		List<Climber> climbers = climbersService.getClimbers();
		return showScoreCard(model, climbers);
	}

	@GetMapping("/{category}")
	public ModelAndView home(Map<String, Object> model, @PathVariable String category) {
		List<Climber> climbers = climbersService.getClimbers();
		return showScoreCard(model, filterClimbersByCategory(climbers, category));
	}

	private ModelAndView showScoreCard(Map<String, Object> model, List<Climber> climbers) {
		int completedEditions = editionsService.loadCompletedEditions();
		List<SlbClimberScorecard> scoreRows = climberMapper.map(climbers, completedEditions);
		Collections.sort(scoreRows, comparator);
		assignPlaces(scoreRows);
		List<String> editions = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");

		model.put("scoreRows", scoreRows);
		model.put("editions", editions);
		model.put("header", "Score Card");

		return new ModelAndView("scoreCard", model);
	}
}
