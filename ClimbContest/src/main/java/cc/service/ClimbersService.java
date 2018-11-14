package cc.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import cc.model.Attempt;
import cc.model.climber.Climber;
import cc.repository.ClimbersRepository;
import cc.service.ifsc.IfscCalculator;
import cc.service.ifsc.IfscScore;

@Service
public class ClimbersService {

	@Autowired
	IfscCalculator calculator;
	@Autowired
	ClimbersRepository climbersRepository;
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

		String scoreSum = calculator.sumScores(routeScores);
		return new Climber(climber.getId(), climber.getName(), climber.getClub(), climber.getCategory(), scoreSum,
				routeScores);
	}
}
