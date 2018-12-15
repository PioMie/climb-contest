package cc.dto;

public class Attempt {
	private int competitorId;
	private String effect;
	private int routeId;

	public int getCompetitorId() {
		return competitorId;
	}

	public String getEffect() {
		return effect;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setCompetitorId(int competitorId) {
		this.competitorId = competitorId;
	}

	public void setEffect(String effect) {
		this.effect = String.valueOf(effect);
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

}
