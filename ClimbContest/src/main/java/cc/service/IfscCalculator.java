package cc.service;

import java.util.List;

import cc.model.IfscScore;

public class IfscCalculator {
	public IfscScore addAttempt(IfscScore ifscScore, IfscAttemptEffect attemptEffect) {
		int tops = ifscScore.getTops();
		int topAttempts = ifscScore.getTopAttempts();
		int bonuses = ifscScore.getBonuses();
		int bonusesAttempts = ifscScore.getBonusesAttempts();

		topAttempts = tops == 1 ? topAttempts : ++topAttempts;
		bonusesAttempts = bonuses == 1 ? bonusesAttempts : ++bonusesAttempts;
		tops = IfscAttemptEffect.TOP.equals(attemptEffect) ? 1 : tops;
		bonuses = IfscAttemptEffect.TOP.equals(attemptEffect) ? 1 : bonuses;
		bonuses = IfscAttemptEffect.BONUS.equals(attemptEffect) ? 1 : bonuses;

		IfscScore resultIfscScore = new IfscScore(tops, topAttempts, bonuses, bonusesAttempts);
		return resultIfscScore;

	}

	public IfscScore sumScores(List<IfscScore> ifscScores) {

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

}
