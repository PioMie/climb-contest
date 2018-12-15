package cc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cc.dto.Attempt;
import cc.dto.climber.Climber;
import cc.repository.ClimbersRepository;
import cc.service.ifsc.IfscCalculator;

@Service
public class ClimbersService {

	@Autowired
	IfscCalculator calculator;
	@Autowired
	ClimbersRepository climbersRepository;
	private int nextId = 0;
	@Autowired
	TaskExecutor taskExecutor;

	public Climber getClimber(int climberId) {
		List<Climber> climbers = getClimbers();
		for (Climber c : climbers) {
			if (c.getId() == climberId) {
				return c;
			}
		}
		throw new RuntimeException(String.format("Climber with ID: %d does not exist.", climberId));
	}

	public List<Climber> getClimbers() {
		return climbersRepository.loadClimbers();
	}

	public void initClimbers(List<String> lines) {
		for (String line : lines) {
			climbersRepository.saveClimber(parseClimberLine(line));
		}
	}

	private Climber parseClimberLine(String climberLine) {
		String[] climberFields = climberLine.split(";");

		List<String> routes = new ArrayList<>();
		routes.addAll(parseEdition(climberFields[5]));
		routes.addAll(parseEdition(climberFields[7]));
		routes.addAll(parseEdition(climberFields[9]));
		routes.addAll(parseEdition(climberFields.length > 11 ? climberFields[11] : null));

		return new Climber(nextId++, climberFields[0], climberFields[1], climberFields[3] + " " + climberFields[2], "",
				routes);
	}

	private List<String> parseEdition(String editionRoutes) {
		if (StringUtils.isEmpty(editionRoutes)) {
			return Arrays.asList("-", "-", "-", "-", "-", //
					"-", "-", "-", "-", "-", //
					"-", "-", "-", "-", "-", //
					"-", "-", "-", "-", "-");
		}
		return Arrays.asList(editionRoutes.split("\\s"));
	}

	public Climber updateClimber(Attempt attempt) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {

				Climber climber = getClimber(attempt.getCompetitorId());
				Climber updatedClimber = updateClimber(climber, attempt);
				climbersRepository.saveClimber(updatedClimber);
			}
		});
		Climber climber = getClimber(attempt.getCompetitorId());
		return updateClimber(climber, attempt);
	}

	private Climber updateClimber(Climber climber, Attempt attempt) {
		List<String> routeScores = new ArrayList<>(climber.getRouteScores());
		for (int i = routeScores.size(); i <= attempt.getRouteId(); ++i) {
			routeScores.add("");
		}
		String oldScore = routeScores.get(attempt.getRouteId());
		String newScore = calculator.addAttempt(oldScore, attempt.getEffect());
		routeScores.set(attempt.getRouteId(), newScore);

		String scoreSum = calculator.sumScores(routeScores, null);
		return new Climber(climber.getId(), climber.getName(), climber.getClub(), climber.getCategory(), scoreSum,
				routeScores);
	}
}
