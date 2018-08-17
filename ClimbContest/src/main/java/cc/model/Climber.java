package cc.model;

public class Climber {

	private String name;
	private Integer score;
	private Integer age;
	private Integer weight;

	public Climber(String name, Integer score, Integer age, Integer weight) {
		this.name = name;
		this.score = score;
		this.age = age;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public Integer getScore() {
		return score;
	}

	public Integer getAge() {
		return age;
	}

	public Integer getWeight() {
		return weight;
	}
	
	

}
