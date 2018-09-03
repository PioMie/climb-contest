package cc.model;

import java.util.Objects;

public class IfscScore {

	int tops;
	int topAttempts;
	int bonuses;
	int bonusesAttempts;

	public IfscScore(int tops, int topAttempts, int bonuses, int bonusesAttempts) {
		this.tops = tops;
		this.topAttempts = topAttempts;
		this.bonuses = bonuses;
		this.bonusesAttempts = bonusesAttempts;
	}

	public IfscScore() {
		// default constructor: 0, 0, 0, 0
	}

	public int getTops() {
		return tops;
	}

	public int getTopAttempts() {
		return topAttempts;
	}

	public int getBonuses() {
		return bonuses;
	}

	public int getBonusesAttempts() {
		return bonusesAttempts;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof IfscScore)) {
			return false;
		}

		IfscScore ifscScore = (IfscScore) o;

		return tops == ifscScore.tops && topAttempts == ifscScore.topAttempts && bonuses == ifscScore.bonuses
				&& bonusesAttempts == ifscScore.bonusesAttempts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tops, topAttempts, bonuses, bonusesAttempts);
	}

	@Override
	public String toString() {
		return tops + "t" + topAttempts + "  " + bonuses + "b" + bonusesAttempts;
	}

	public static IfscScore parseString(String ifscScoreString) {
		String[] parts = ifscScoreString.split("t|b|\\s+");
		if (parts.length != 4) {
			throw new RuntimeException(String.format("Parsing of a score: '%s' has failed: wrong number of arguments."));
		}
		int tops = Integer.parseInt(parts[0]);
		int topAttempts = Integer.parseInt(parts[1]);
		int bonuses = Integer.parseInt(parts[2]);
		int bonusesAttempts = Integer.parseInt(parts[3]);
		return new IfscScore(tops, topAttempts, bonuses, bonusesAttempts);
	}
}
