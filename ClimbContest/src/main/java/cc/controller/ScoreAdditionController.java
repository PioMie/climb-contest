package cc.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.dto.Attempt;
import cc.dto.climber.Climber;
import cc.service.ClimbersService;

@Controller
@RequestMapping("/scoreAddition")
public class ScoreAdditionController {

	@Autowired
	ClimbersService climbersService;

	@PostMapping("/addAttempt")
	public ModelAndView addAttempt(@ModelAttribute Attempt attempt) {
		if ("IMPORT".equals(attempt.getEffect())) {
			List<String> lines = getLines(
					new File("C:\\Programs\\repos\\ffk\\parser\\league\\output\\18-19\\transport.csv").toPath());

			climbersService.initClimbers(lines);
			return new ModelAndView("scoreCard");
		}

		Climber climber = climbersService.updateClimber(attempt);
		return new ModelAndView("competitor", "climber", climber);
	}

	@GetMapping
	public String get() {
		return "scoreAddition";
	}

	public List<String> getLines(Path path) {
		try (Stream<String> stream = Files.lines(path)) {
			return stream.collect(Collectors.toList());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
