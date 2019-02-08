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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.service.slb.ClimbersService;

@Controller
@RequestMapping("/scoreImport")
public class ScoreImportController {
	@Autowired
	ClimbersService climbersService;

	@PostMapping("/import")
	public String addAttempt() {
		List<String> lines = getLines(
				new File("C:\\Programs\\repos\\ffk\\parser\\league\\output\\18-19\\transport.csv").toPath());

		climbersService.initClimbers(lines);

		return "redirect:/scoreCard";
	}

	@GetMapping
	public String get() {
		return "scoreImport";
	}

	public List<String> getLines(Path path) {
		try (Stream<String> stream = Files.lines(path)) {
			return stream.collect(Collectors.toList());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
