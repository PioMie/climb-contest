package cc.controller.scorecard.slb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.dto.climber.Climber;
import cc.service.slb.SlbCalculator;

@Service
public class SlbClimberMapper {

	@Autowired
	SlbCalculator calculator;

	private String addBonusToScore(String score, String category) {
		if (category.contains("PRO")) {
			return calculator.sumEditions(Arrays.asList(score, "10t10b"), 0);
		}
		if (category.contains("HARD")) {
			return calculator.sumEditions(Arrays.asList(score, "5t5b"), 0);
		}
		return score;
	}

	public SlbClimberScorecard map(Climber climber) {
		SlbClimberScorecard res = new SlbClimberScorecard();
		res.setId(climber.getId());
		res.setCategory(climber.getCategory());
		res.setClub(climber.getClub());
		res.setName(climber.getName());
		List<String> editionScores = new ArrayList<>();
		List<String> editionScoresWithBonus = new ArrayList<>();
		res.setEditions(new ArrayList<SlbEditionResult>());

		List<List<String>> editions = new ArrayList<>();
		for (int i = 0; i < climber.getRouteScores().size(); i += 20) {
			editions.add(climber.getRouteScores().subList(i, i + 20));
		}

		for (int i = 0; i < editions.size(); ++i) {
			SlbEditionResult editionScore = new SlbEditionResult();
			editionScore.setRouteScores(new ArrayList<SlbRoute>());
			for (int j = 0; j < editions.get(i).size(); ++j) {
				editionScore.getRouteScores().add(mapSingleResult(editions.get(i).get(j), j));
			}
			String score = calculator.sumScores(editions.get(i), res.getCategory());
			editionScore.setScore("0t0b".equals(score) ? "" : score);
			editionScore.setScoreWithBonus(addBonusToScore(editionScore.getScore(), res.getCategory()));
			editionScore.setIdx(i + 1);
			editionScores.add(editionScore.getScore());
			editionScoresWithBonus.add(editionScore.getScoreWithBonus());
			res.getEditions().add(editionScore);
		}

		res.setScore(calculator.sumEditions(editionScores, 2));
		res.setScoreOfAll(calculator.sumEditions(editionScores, 0));
		res.setScoreWithBonus(calculator.sumEditions(editionScoresWithBonus, 2));
		res.setScoreOfAllWithBonus(calculator.sumEditions(editionScoresWithBonus, 0));
		return res;
	}

	public List<SlbClimberScorecard> map(List<Climber> climbers) {
		List<SlbClimberScorecard> res = new ArrayList<>();
		for (Climber c : climbers) {
			res.add(map(c));
		}
		return res;
	}

	private String mapRouteColor(int idx) {
		if (idx < 5) {
			return "yellow";
		}
		if (idx < 10) {
			return "blue";
		}
		if (idx < 15) {
			return "red";
		}
		return "black";
	}

	private SlbRoute mapSingleResult(String singleResult, int idx) {
		SlbRoute res = new SlbRoute();
		res.setScore(singleResult);
		res.setColor(mapRouteColor(idx));
		return res;
	}
}
