package cc.controller.climberaddition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.controller.climberaddition.model.AddedClimber;
import cc.service.slb.ClimbersService;

@Controller
@RequestMapping("/climberAddition")
public class ClimberAdditionController {

	@Autowired
	private ClimbersService climbersService;

	@GetMapping
	public String get() {
		return "climberAddition";
	}

	@PostMapping("/add")
	public String post(@ModelAttribute AddedClimber addedClimber) {
		int id = climbersService.createClimber(addedClimber.getName().toUpperCase(), addedClimber.getClub().toUpperCase(), addedClimber.getCategory());
		return "redirect:/scoreAddition/" + id;
	}

}
