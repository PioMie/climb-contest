package cc.model.climber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Climber {

	private int id;
	private String name;
	private String club;
	private String category;
	private String score;
	private List<String> routeScores;

	public Climber(int id, String name, String club, String category) {
		this(id, name, club, category, "", new ArrayList<String>());
	}

	public Climber(int id, String name, String club, String category, String score, List<String> routeScores) {
		this.id = id;
		this.name = name;
		this.club = club;
		this.category = category;
		this.score = score;
		this.routeScores = Collections.unmodifiableList(routeScores);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getScore() {
		return score != null ? score : "";
	}

	public String getClub() {
		return club;
	}

	public String getCategory() {
		return category;
	}

	public List<String> getRouteScores() {
		return routeScores;
	}

}
