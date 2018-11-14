package cc.model;

public class Attempt {
	private int competitorId;
	private int routeId;
	private String effect;

	public int getCompetitorId() {
		return competitorId;
	}

	public void setCompetitorId(int competitorId) {
		this.competitorId = competitorId;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = String.valueOf(effect);
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

}
