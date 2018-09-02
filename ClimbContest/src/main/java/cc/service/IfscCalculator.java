package cc.service;

import cc.model.IfscScore;

public class IfscCalculator {
	public IfscScore addAttempt(IfscScore ifscScore, String attemptEffect) {
		int tops = ifscScore.getTops();
		int topAttempts = ifscScore.getTopAttempts();
		int bonuses = ifscScore.getBonuses();
		int bonusesAttempts = ifscScore.getBonusesAttempts();

		topAttempts = tops == 1 ? topAttempts : ++topAttempts;
		bonusesAttempts = bonuses == 1 ? bonusesAttempts : ++bonusesAttempts;

		//TODO rework using enums
		switch (attemptEffect) {
		case "top":
			tops = 1;
			bonuses = 1;
			break;
		case "bonus":
			bonuses = 1;
			break;
		}

		IfscScore resultIfscScore = new IfscScore(tops, topAttempts, bonuses, bonusesAttempts);
		return resultIfscScore;

	}

	// ??
	public IfscScore sumScores(IfscScore[] ifscScores) {

		int tops = 0;
		int topAttempts = 0;
		int bonuses = 0;
		int bonusesAttempts = 0;

		for (IfscScore ifscScore : ifscScores) {
			tops += ifscScore.getTops();
			topAttempts += ifscScore.getTopAttempts();
			bonuses += ifscScore.getBonuses();
			bonusesAttempts += ifscScore.getBonusesAttempts();
		}

		IfscScore resultIfscScore = new IfscScore(tops, topAttempts, bonuses, bonusesAttempts);
		return resultIfscScore;
	}

}
