package cc.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import cc.model.Attempt;
import cc.model.Climber;
import cc.model.IfscScore;
import cc.repository.ClimbersRepository;

@Service
public class ClimbersService {

	@Autowired
	IfscCalculator calculator;
	@Autowired
	ClimbersRepository scoreRepository;
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
		return scoreRepository.loadClimbers();
	}

	public Climber updateClimber(Attempt attempt) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {

					Climber climber = getClimber(attempt.getCompetitorId());
					Climber updatedClimber = updateClimber(climber, attempt);
					scoreRepository.saveClimber(updatedClimber);
			}
		});
		Climber climber = getClimber(attempt.getCompetitorId());
		return updateClimber(climber, attempt);
	}

	private Climber updateClimber(Climber climber, Attempt attempt) {
		List<IfscScore> routeScores = new ArrayList<>(climber.getRouteScores());
		for (int i = routeScores.size(); i <= attempt.getRouteId(); ++i) {
			routeScores.add(new IfscScore());
		}
		IfscScore oldScore = routeScores.get(attempt.getRouteId());
		IfscScore newScore = calculator.addAttempt(oldScore, attempt.getEffect());
		routeScores.set(attempt.getRouteId(), newScore);

		IfscScore scoreSum = calculator.sumScores(routeScores);
		return new Climber(climber.getId(), climber.getName(), climber.getClub(), climber.getCategory(), scoreSum,
				routeScores);
	}
}
