package cc.service.slb;

import java.util.Comparator;
import java.util.Objects;

import org.springframework.util.StringUtils;

public class SlbScore implements Comparable<SlbScore> {
	public static SlbScore parseString(String slbScoreString) {
		if (StringUtils.isEmpty(slbScoreString)) {
			return new SlbScore(0, 0);
		}
		String[] parts = slbScoreString.split("t|b");
		if (parts.length != 2) {
			throw new RuntimeException(
					String.format("Parsing of a score: '%s' has failed: wrong number of arguments."));
		}
		int tops = Integer.parseInt(parts[0]);
		int bonuses = Integer.parseInt(parts[1]);
		return new SlbScore(tops, bonuses);
	}

	int bonuses;
	int tops;

	public SlbScore() {
		// default constructor: 0, 0, 0, 0
	}

	public SlbScore(int tops, int bonuses) {
		this.tops = tops;
		this.bonuses = bonuses;
	}

	@Override
	public int compareTo(SlbScore o) {
		return Comparator.comparing(SlbScore::getTops).thenComparing(SlbScore::getBonuses).compare(this, o);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof SlbScore)) {
			return false;
		}

		SlbScore slbScore = (SlbScore) o;

		return tops == slbScore.tops && bonuses == slbScore.bonuses;
	}

	public int getBonuses() {
		return bonuses;
	}

	public String getScore() {
		return toString();
	}

	public int getTops() {
		return tops;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tops, bonuses);
	}

	@Override
	public String toString() {
		return tops + "t" + bonuses + "b";
	}
}
