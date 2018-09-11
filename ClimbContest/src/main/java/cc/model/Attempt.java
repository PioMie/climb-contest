package cc.model;

public class Attempt {
	private int competitorId;
	private int routeId;
	private IfscAttemptEffect effect;

	public int getCompetitorId() {
		return competitorId;
	}

	public void setCompetitorId(int competitorId) {
		this.competitorId = competitorId;
	}

	public IfscAttemptEffect getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = IfscAttemptEffect.valueOf(effect);
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

}
