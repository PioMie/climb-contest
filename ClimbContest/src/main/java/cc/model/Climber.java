package cc.model;

public class Climber {

	private String name;
	private String club;
	private String category;
	private IfscScore ifscScore;

	public Climber(String name, String club, String category) {
		this.name = name;
		this.club = club;
		this.category = category;
		ifscScore = new IfscScore();
	}

	public Climber(String name, String club, String category, IfscScore ifscScore) {
		this.name = name;
		this.club = club;
		this.category = category;
		this.ifscScore = ifscScore;
	}

	public String getName() {
		return name;
	}

	public String getScore() {
		return ifscScore.toString();
	}

	public String getClub() {
		return club;
	}

	public String getCategory() {
		return category;
	}

	public IfscScore getIfscScore() {
		return ifscScore;
	}

}
