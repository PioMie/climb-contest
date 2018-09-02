package cc.model;

public class Climber {

	private String name;
	private Integer age;
	private Integer weight;
	private IfscScore ifscScore;

	public Climber(String name, Integer age, Integer weight) {
		this.name = name;
		this.age = age;
		this.weight = weight;
		ifscScore = new IfscScore();
	}

	public Climber(String name, Integer age, Integer weight, IfscScore ifscScore) {
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.ifscScore = ifscScore;
	}

	public String getName() {
		return name;
	}

	public String getScore() {
		return ifscScore.toString();
	}

	public Integer getAge() {
		return age;
	}

	public Integer getWeight() {
		return weight;
	}

	public IfscScore getIfscScore() {
		return ifscScore;
	}

}
