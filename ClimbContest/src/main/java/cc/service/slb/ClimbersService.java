package cc.service.slb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cc.dto.Attempt;
import cc.dto.climber.Climber;
import cc.repository.ClimbersRepository;
import cc.service.BoulderAttemptEffect;
import cc.service.ifsc.IfscCalculator;

@Service
public class ClimbersService {

	@Autowired
	IfscCalculator calculator;
	@Autowired
	ClimbersRepository climbersRepository;
	@Autowired
	EditionsService editonsService;
	@Autowired
	TaskExecutor taskExecutor;

	private Optional<Integer> lastId = Optional.empty();

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
//		routes.addAll(parseEdition(climberFields[7]));
//		routes.addAll(parseEdition(climberFields[9]));
//		routes.addAll(parseEdition(climberFields[11]));
//		routes.addAll(parseEdition(climberFields[13]));
//		routes.addAll(parseEdition(climberFields[15]));
//		routes.addAll(parseEdition(climberFields[17]));
//		routes.addAll(parseEdition(climberFields.length > 19 ? climberFields[19] : null));
		// routes.addAll(Collections.nCopies(20, "-"));

		Category category = Category.mapCategory(climberFields[3] + " " + climberFields[2]);
		return new Climber(getNextId(), climberFields[0], climberFields[1], category.toString(), "", routes);
	}

	private List<String> parseEdition(String editionRoutes) {
		if (StringUtils.isEmpty(editionRoutes)) {
			return Collections.nCopies(20, "-");
		}
		List<String> res = Arrays.asList(editionRoutes.split("\\s"));
		return res.stream().map(r -> r.replace("BONUS", BoulderAttemptEffect.BONUS))
				.map(r -> r.replace("TOP", BoulderAttemptEffect.TOP))
				.map(r -> r.replace("ZERO", BoulderAttemptEffect.NONE)).collect(Collectors.toList());
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

	public void saveClimber(Climber climber) {
		Climber oldVerstion = getClimber(climber.getId());
		if (!oldVerstion.equals(climber)) {
			climbersRepository.saveClimber(climber);
		}
	}

	public int createClimber(String name, String club, String category) {
		Climber climber = new Climber(getNextId(), name, club, category, "0t0b",
				Collections.nCopies(20 * editonsService.loadCompletedEditions(), BoulderAttemptEffect.NONE));
		climbersRepository.saveClimber(climber);
		return climber.getId();
	}

	private int getNextId() {
		if (lastId.isPresent()) {
			lastId = Optional.of(lastId.get() + 1);
		} else {
			List<Climber> climbers = getClimbers();
			Optional<Integer> maxId = climbers.stream().map(Climber::getId).max(Comparator.comparing(Integer::valueOf));
			lastId = Optional.of(maxId.isPresent() ? maxId.get() + 1 : 0);
		}
		return lastId.get();
	}
}
