package cc.model;

public class Climber {

	private int id;
	private String name;
	private String club;
	private String category;
	private IfscScore ifscScore;

	public Climber(int id, String name, String club, String category) {
		this(id, name, club, category, new IfscScore());
		this.name = name;
		this.club = club;
		this.category = category;
		ifscScore = new IfscScore();
	}

	public Climber(int id, String name, String club, String category, IfscScore ifscScore) {
		this.id = id;
		this.name = name;
		this.club = club;
		this.category = category;
		this.ifscScore = ifscScore;
	}

	public int getId() {
		return id;
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
