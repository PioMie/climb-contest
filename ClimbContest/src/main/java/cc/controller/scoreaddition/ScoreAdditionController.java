package cc.controller.scoreaddition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.controller.scoreaddition.model.ClimberToPick;
import cc.controller.scoreaddition.model.Editions;
import cc.controller.scoreaddition.model.PickedClimber;
import cc.controller.scoreaddition.model.ScoreAddition;
import cc.dto.climber.Climber;
import cc.service.BoulderAttemptEffect;
import cc.service.slb.ClimbersService;
import cc.service.slb.EditionsService;

@Controller
@RequestMapping("/scoreAddition")
public class ScoreAdditionController {

	@Autowired
	private ClimbersService climbersService;
	@Autowired
	private EditionsService editionsService;

	@GetMapping
	public String get() {
		List<Climber> climbers = climbersService.getClimbers();
		return climbers.isEmpty() ? "redirect:/scoreAddition/-1" : "redirect:/scoreAddition/" + climbers.get(0).getId();
	}

	@GetMapping("/{climberId}")
	public ModelAndView get(@PathVariable String climberId) {
		Map<String, Object> model = new HashMap<>();

		int completedEditions = editionsService.loadCompletedEditions();
		int currentEdition = editionsService.loadCurrentEditon();
		model.put("completed", completedEditions);
		model.put("current", currentEdition);

		if (climberId != null) {
			Climber climber = climbersService.getClimber(Integer.parseInt(climberId));
			model.put("routes", getCurrentEdition(climber, currentEdition));
			model.put("id", climber.getId());
		}

		List<Climber> climbers = climbersService.getClimbers();
		model.put("climbers", mapClimbersToPick(climbers, Integer.parseInt(climberId)));

		return new ModelAndView("scoreAddition", model);
	}

	private List<ClimberToPick> mapClimbersToPick(List<Climber> climbers, int selectedClimberId) {
		return climbers.stream()
				.map(c -> new ClimberToPick(c.getId(), c.getName(), c.getId() == selectedClimberId ? "selected" : ""))
				.collect(Collectors.toList());
	}

	@PostMapping("/pick")
	public String pick(Map<String, Object> model, @ModelAttribute PickedClimber pickedClimber) {
		return "redirect:/scoreAddition/" + pickedClimber.getId();
	}

	private List<String> getCurrentEdition(Climber climber, int currentEdition) {
		int from = (currentEdition - 1) * 20;
		int to = currentEdition * 20;
		boolean fromOk = from >= 0 && from < climber.getRouteScores().size();
		boolean toOk = to >= 0 && to <= climber.getRouteScores().size();
		return fromOk && toOk ? climber.getRouteScores().subList(from, to)
				: Collections.nCopies(20, BoulderAttemptEffect.NONE);
	}

	@PostMapping("/add")
	public String add(@ModelAttribute ScoreAddition scoreAddition) {
		Climber climber = climbersService.getClimber(scoreAddition.getId());
		List<String> scores = replaceRoutes(climber, scoreAddition.getRoutes());
		Climber updatedClimber = new Climber(climber.getId(), climber.getName(), climber.getClub(),
				climber.getCategory(), "0t0b", scores);
		climbersService.saveClimber(updatedClimber);
		return "redirect:/scoreAddition/" + scoreAddition.getId();
	}

	private List<String> replaceRoutes(Climber climber, List<String> currentResults) {
		int currentEdition = editionsService.loadCurrentEditon();
		int from = (currentEdition - 1) * 20;
		int to = currentEdition * 20;
		List<String> res = new ArrayList<String>();
		res.addAll(climber.getRouteScores().subList(0, from));
		res.addAll(currentResults);
		if (to < climber.getRouteScores().size())
			res.addAll(climber.getRouteScores().subList(to, climber.getRouteScores().size()));
		return res;
	}

	@PostMapping("/manage")
	public String manage(@ModelAttribute Editions editions) {
		climbersService.extendAllResults(editions.getCompleted());
		editionsService.saveCompletedEditions(editions.getCompleted());
		editionsService.saveCurrentEdition(editions.getCurrent());
		return "redirect:/scoreCard";
	}

}
