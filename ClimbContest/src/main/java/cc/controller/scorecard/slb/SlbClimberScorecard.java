package cc.controller.scorecard.slb;

import java.util.List;

public class SlbClimberScorecard {
	private String category;
	private String club;
	private List<SlbEditionResult> editions;
	private String name;
	private int place;
	private String score;
	private String scoreWithBonus;

	public String getCategory() {
		return category;
	}

	public String getClub() {
		return club;
	}

	public List<SlbEditionResult> getEditions() {
		return editions;
	}

	public String getName() {
		return name;
	}

	public int getPlace() {
		return place;
	}

	public String getScore() {
		return score;
	}

	public String getScoreWithBonus() {
		return scoreWithBonus;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public void setEditions(List<SlbEditionResult> editions) {
		this.editions = editions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public void setScoreWithBonus(String scoreWithBonus) {
		this.scoreWithBonus = scoreWithBonus;
	}

}
