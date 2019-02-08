package cc.controller.scoreaddition.model;

public class ClimberToPick {

	private int id;
	private String name;
	private String selected;

	public ClimberToPick(int id, String name, String selected) {
		super();
		this.id = id;
		this.name = name;
		this.selected = selected;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSelected() {
		return selected;
	}

}
