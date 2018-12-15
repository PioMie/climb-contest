package cc.service.slb;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cc.service.BoulderAttemptEffect;
import cc.service.Calculator;

@Service
public class SlbCalculator implements Calculator {

	@Override
	public String addAttempt(String score, String effect) {
		if (BoulderAttemptEffect.TOP.toString().equals(score) || BoulderAttemptEffect.TOP.toString().equals(effect)) {
			return BoulderAttemptEffect.TOP.toString();
		} else if (BoulderAttemptEffect.BONUS.toString().equals(score)
				|| BoulderAttemptEffect.BONUS.toString().equals(effect)) {
			return BoulderAttemptEffect.BONUS.toString();
		} else {
			return BoulderAttemptEffect.NONE.toString();
		}
	}

	private SlbScore addScores(SlbScore score1, SlbScore score2) {
		return new SlbScore(score1.getTops() + score2.getTops(), score1.getBonuses() + score2.getBonuses());
	}

	public int compare(String score1, String score2) {
		SlbScore slbScore1 = SlbScore.parseString(score1);
		SlbScore slbScore2 = SlbScore.parseString(score2);
		return slbScore1.compareTo(slbScore2);
	}

	private SlbScore sumAllScores(List<String> scores) {
		int tops = 0, bonuses = 0;
		for (String score : scores) {
			if (BoulderAttemptEffect.TOP.toString().equals(score)) {
				tops++;
				bonuses++;
			} else if (BoulderAttemptEffect.BONUS.toString().equals(score)) {
				bonuses++;
			}
		}
		return new SlbScore(tops, bonuses);
	}

	public String sumEditions(List<String> editionScores, int excluded) {
		SlbScore res = new SlbScore();
		List<SlbScore> slbScores = editionScores.stream().map(SlbScore::parseString).sorted()
				.collect(Collectors.toList());
		for (int i = Math.min(slbScores.size(), excluded); i < slbScores.size(); ++i) {
			res = addScores(res, slbScores.get(i));
		}
		return res.toString();
	}

	@Override
	public String sumScores(List<String> scores, String category) {
		if (category.contains("JUNIOR") || category.contains("VETERAN")) {
			return sumAllScores(scores).toString();
		}

		SlbScore yellowScore = sumAllScores(scores.subList(0, 5));
		SlbScore blueScore = sumAllScores(scores.subList(5, 10));
		SlbScore redScore = sumAllScores(scores.subList(10, 15));
		SlbScore blackScore = sumAllScores(scores.subList(15, 20));

		if (category.contains("PRO")) {
			return addScores(blackScore, redScore).toString();
		}
		if (category.contains("HARD")) {
			SlbScore res = addScores(redScore, blueScore);
			if (res.getTops() == 10 && blackScore.getTops() > 0) {
				return new SlbScore(11, 0).toString();
			}
			if (res.getTops() == 10 && blackScore.getBonuses() > 0) {
				return new SlbScore(10, 1).toString();
			}
			return res.toString();
		}
		if (category.contains("EASY")) {
			SlbScore res = addScores(blueScore, yellowScore);
			if (res.getTops() == 10 && (redScore.getTops() > 0 || blackScore.getTops() > 0)) {
				return new SlbScore(11, 0).toString();
			}
			if (res.getTops() == 10 && (redScore.getBonuses() > 0 || blackScore.getBonuses() > 0)) {
				return new SlbScore(10, 1).toString();
			}
			return res.toString();
		}

		throw new RuntimeException("Category: '" + category + "' not recognized");
	}

}
