package cc.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.dto.climber.Climber;
import cc.service.slb.ClimbersService;
import cc.service.slb.EditionsService;

@Controller
@RequestMapping("/scoreImport")
public class ScoreImportController {
	
	@Autowired
	ClimbersService climbersService;
	@Autowired
	EditionsService editionsService;

	@PostMapping("/import")
	public String importResults() {
		List<String> lines = getLines(
				new File("C:\\Programs\\repos\\ffk\\parser\\league\\output\\19-20\\transport.csv").toPath());

		climbersService.initClimbers(lines);

		return "redirect:/scoreCard";
	}
	
	@PostMapping("/export")
	public ModelAndView exportResults(Map<String, Object> model) {
		List<Climber> climbers = climbersService.getClimbers();
		String text = "";
		for (Climber climber: climbers) {
			text += climber.toString("\t", editionsService.loadCurrentEditon());
		}
		model.put("text", text);
		return new ModelAndView("scoreImport", model);
	}

	@GetMapping
	public ModelAndView get(Map<String, Object> model) {
		model.put("text", "");
		return new ModelAndView("scoreImport", model);
	}

	public List<String> getLines(Path path) {
		try (Stream<String> stream = Files.lines(path)) {
			return stream.collect(Collectors.toList());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
