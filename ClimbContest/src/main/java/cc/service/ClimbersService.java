package cc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

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

	public Climber getClimber(int climberId) throws InterruptedException, ExecutionException {
		List<Climber> climbers = getClimbers();
		for (Climber c : climbers) {
			if (c.getId() == climberId) {
				return c;
			}
		}
		throw new NoSuchElementException(String.format("Climber with ID: %d does not exist.", climberId));
	}

	public List<Climber> getClimbers() throws InterruptedException, ExecutionException {
		return scoreRepository.loadClimbers();
	}

	public Climber updateClimber(Attempt attempt) throws InterruptedException, ExecutionException {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Climber climber = getClimber(attempt.getCompetitorId());
					Climber updatedClimber = updateClimber(climber, attempt);
					scoreRepository.saveClimber(updatedClimber);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
