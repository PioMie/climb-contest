package cc.service.ifsc;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cc.service.BoulderAttemptEffect;
import cc.service.Calculator;

@Service
public class IfscCalculator implements Calculator {

	private IfscScore addAttempt(IfscScore ifscScore, String attemptEffect) {
		int tops = ifscScore.getTops();
		int topAttempts = ifscScore.getTopAttempts();
		int bonuses = ifscScore.getBonuses();
		int bonusesAttempts = ifscScore.getBonusesAttempts();

		topAttempts = tops == 1 ? topAttempts : ++topAttempts;
		bonusesAttempts = bonuses == 1 ? bonusesAttempts : ++bonusesAttempts;
		tops = BoulderAttemptEffect.TOP.toString().equals(attemptEffect) ? 1 : tops;
		bonuses = BoulderAttemptEffect.TOP.toString().equals(attemptEffect) ? 1 : bonuses;
		bonuses = BoulderAttemptEffect.BONUS.toString().equals(attemptEffect) ? 1 : bonuses;

		IfscScore resultIfscScore = new IfscScore(tops, topAttempts, bonuses, bonusesAttempts);
		return resultIfscScore;
	}

	@Override
	public String addAttempt(String score, String attemptEffect) {
		IfscScore ifscScore = addAttempt(IfscScore.parseString(score), attemptEffect);
		return ifscScore.toString();
	}

	private IfscScore sumIfscScores(List<IfscScore> ifscScores) {

		int tops = 0;
		int topAttempts = 0;
		int bonuses = 0;
		int bonusesAttempts = 0;

		for (IfscScore ifscScore : ifscScores) {
			tops += ifscScore.getTops();
			if (ifscScore.getTops() > 0) {
				topAttempts += ifscScore.getTopAttempts();
			}
			bonuses += ifscScore.getBonuses();
			if (ifscScore.getBonuses() > 0) {
				bonusesAttempts += ifscScore.getBonusesAttempts();
			}
		}

		IfscScore resultIfscScore = new IfscScore(tops, topAttempts, bonuses, bonusesAttempts);
		return resultIfscScore;
	}

	@Override
	public String sumScores(List<String> scores, String category) {
		List<IfscScore> ifscScores = scores.stream().map(IfscScore::parseString).collect(Collectors.toList());
		IfscScore ifscScore = sumIfscScores(ifscScores);
		return ifscScore.toString();
	}

}
