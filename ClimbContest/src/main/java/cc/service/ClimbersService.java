package cc.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.model.Attempt;
import cc.model.Climber;
import cc.repository.ClimbersRepository;

@Service
public class ClimbersService {

	@Autowired
	ClimbersRepository scoreRepository;
	@Autowired
	IfscCalculator calculator;

	public List<Climber> getClimbers() throws InterruptedException, ExecutionException {
		return scoreRepository.loadClimbers();
	}

	public Climber getClimber(int climberId) throws InterruptedException, ExecutionException {
		List<Climber> climbers = getClimbers();
		for (Climber c : climbers) {
			if (c.getId() == climberId) {
				return c;
			}
		}
		throw new NoSuchElementException(String.format("Climber with ID: %d does not exist.", climberId));
	}

	public Climber updateClimber(Attempt attempt) throws InterruptedException, ExecutionException {
		Climber climber = getClimber(attempt.getCompetitorId());
		scoreRepository.updateClimber(attempt, climber);
		return new Climber(climber.getId(), climber.getName(), climber.getClub(), climber.getCategory(),
				calculator.addAttempt(climber.getIfscScore(), attempt.getEffect()));
	}
}
