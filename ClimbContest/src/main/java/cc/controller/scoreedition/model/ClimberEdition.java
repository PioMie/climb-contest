package cc.controller.scoreedition.model;

import java.util.List;

public class ClimberEdition {

	private String category;
	private String club;
	private int id;
	private String name;
	private List<String> routes;

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

	public List<String> getRoutes() {
		return routes;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRoutes(List<String> routes) {
		this.routes = routes;
	}

}
