package cc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Climber {

	private int id;
	private String name;
	private String club;
	private String category;
	private IfscScore ifscScore;
	private List<IfscScore> routeScores;

	public Climber(int id, String name, String club, String category) {
		this(id, name, club, category, new IfscScore(), new ArrayList<IfscScore>());
	}

	public Climber(int id, String name, String club, String category, IfscScore ifscScore, List<IfscScore> routeScores) {
		this.id = id;
		this.name = name;
		this.club = club;
		this.category = category;
		this.ifscScore = ifscScore;
		this.routeScores = Collections.unmodifiableList(routeScores);
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

	public List<IfscScore> getRouteScores() {
		return routeScores;
	}

}
