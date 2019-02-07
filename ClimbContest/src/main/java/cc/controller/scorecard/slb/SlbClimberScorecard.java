package cc.controller.scorecard.slb;

import java.util.List;

public class SlbClimberScorecard {
	private String category;
	private String club;
	private List<SlbEditionResult> editions;
	private int id;
	private String name;
	private int place;
	private String score;
	private String scoreOfAll;
	private String scoreOfAllWithBonus;
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

	public int getId() {
		return id;
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

	public String getScoreOfAll() {
		return scoreOfAll;
	}

	public String getScoreOfAllWithBonus() {
		return scoreOfAllWithBonus;
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

	public void setId(int id) {
		this.id = id;
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

	public void setScoreOfAll(String scoreOfAll) {
		this.scoreOfAll = scoreOfAll;
	}

	public void setScoreOfAllWithBonus(String scoreOfAllWithBonus) {
		this.scoreOfAllWithBonus = scoreOfAllWithBonus;
	}

	public void setScoreWithBonus(String scoreWithBonus) {
		this.scoreWithBonus = scoreWithBonus;
	}

}
