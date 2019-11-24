package cc.service.slb;

import java.util.Arrays;
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
	public String sumScores(List<String> scores, String categoryCode) {
		Category category = Category.getByCode(categoryCode);

		SlbScore yellowScore = sumAllScores(scores.subList(0, 5));
		SlbScore blueScore = sumAllScores(scores.subList(5, 11));
		SlbScore redScore = sumAllScores(scores.subList(11, 17));
		SlbScore blackScore = sumAllScores(scores.subList(17, 20));

		if (Category.PRO_MALE.equals(category)) {
			return addScores(blackScore, redScore).toString();
		}
		if (Arrays.asList(Category.HARD_FEMALE, Category.HARD_MALE).contains(category)) {
			SlbScore res = addScores(redScore, blueScore);
			if (res.getTops() == 12 && blackScore.getTops() > 0) {
				return new SlbScore(13, 13).toString();
			}
			if (res.getTops() == 12 && blackScore.getBonuses() > 0) {
				return new SlbScore(11, 12).toString();
			}
			return res.toString();
		}
		if (Arrays.asList(Category.EASY_FEMALE, Category.EASY_MALE).contains(category)) {
			SlbScore res = addScores(blueScore, yellowScore);
			if (res.getTops() == 11 && (redScore.getTops() > 0 || blackScore.getTops() > 0)) {
				return new SlbScore(12, 12).toString();
			}
			if (res.getTops() == 10 && (redScore.getBonuses() > 0 || blackScore.getBonuses() > 0)) {
				return new SlbScore(11, 12).toString();
			}
			return res.toString();
		}
		
		return sumAllScores(scores).toString();
	}

}
