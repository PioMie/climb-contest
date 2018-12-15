package cc.dto.climber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Climber {

	private String category;
	private String club;
	private int id;
	private String name;
	private List<String> routeScores;
	private String score;

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

	public String getCategory() {
		return category;
	}

	public String getClub() {
		return club;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<String> getRouteScores() {
		return routeScores;
	}

	public String getScore() {
		return score != null ? score : "";
	}

}
