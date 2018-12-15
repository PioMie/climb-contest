package cc.controller.scorecard.slb;

import java.util.List;

public class SlbEditionResult {
	private int idx;
	private List<SlbRoute> routeScores;
	private String score;
	private String scoreWithBonus;

	public int getIdx() {
		return idx;
	}

	public List<SlbRoute> getRouteScores() {
		return routeScores;
	}

	public String getScore() {
		return score;
	}

	public String getScoreWithBonus() {
		return scoreWithBonus;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public void setRouteScores(List<SlbRoute> routeScores) {
		this.routeScores = routeScores;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public void setScoreWithBonus(String scoreWithBonus) {
		this.scoreWithBonus = scoreWithBonus;
	}

}
