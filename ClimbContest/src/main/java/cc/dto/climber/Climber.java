package cc.dto.climber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((club == null) ? 0 : club.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((routeScores == null) ? 0 : routeScores.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Climber other = (Climber) obj;

		if (id != other.id) {
			return false;
		}
		if (!StringUtils.equals(name, other.name)) {
			return false;
		}
		if (!StringUtils.equals(club, other.club)) {
			return false;
		}
		if (!StringUtils.equals(category, other.category)) {
			return false;
		}
		if (!StringUtils.equals(score, other.score)) {
			return false;
		}

		if (routeScores == null) {
			if (other.routeScores != null) {
				return false;
			}
		} else if (!routeScores.equals(other.routeScores)) {
			return false;
		}

		return true;
	}

	public String toString(String separator, Integer edition) {
		String routes = "";
		boolean present = false;
		for (int i = 20 * (edition - 1); i < 20 * edition; ++i) {
			if ("-".equals(routeScores.get(i))) {
				routes += separator;
			} else {
				routes += routeScores.get(i) + separator;
				present = true;
			}
		}
		if (!present) {
			return "";
		}
		String res = name + separator;
		res += club + separator;
		res += routes;
		res += mapCategory(category) + separator;
		res += category.substring(1) + "\n";
		return res;
	}

	String mapCategory(String category) {
		switch (category) {
		case "PM":
			return "Pro";
		case "TM":
			return "Trudna";
		case "LM":
			return "Lajt";
		case "JM":
			return "Junior";
		case "WM":
			return "Weteran";
		case "TK":
			return "Trudna";
		case "LK":
			return "Lajt";
		case "JK":
			return "Junior";
		case "WK":
			return "Weteran";
		}
		return "";
	}
}
