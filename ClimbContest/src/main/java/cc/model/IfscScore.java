package cc.model;

import java.util.Objects;

public class IfscScore {

	int tops;
	int topAttempts;
	int bonuses;
	int bonusesAttempts;

	public IfscScore(int tops, int topAttempts, int bonuses, int bonusesAttempts) {
		super();
		this.tops = tops;
		this.topAttempts = topAttempts;
		this.bonuses = bonuses;
		this.bonusesAttempts = bonusesAttempts;
	}

	public int getTops() {
		return tops;
	}

	public int getTopAttempts() {
		return topAttempts;
	}

	public int getBonuses() {
		return bonuses;
	}

	public int getBonusesAttempts() {
		return bonusesAttempts;
	}
	
	@Override
	public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof IfscScore)) {
            return false;
        }

        IfscScore ifscScore = (IfscScore) o;

        return tops == ifscScore.tops &&
               topAttempts == ifscScore.topAttempts &&
               bonuses == ifscScore.bonuses &&
               bonusesAttempts == ifscScore.bonusesAttempts;
	}

	
    @Override
    public int hashCode() {
        return Objects.hash(tops, topAttempts, bonuses, bonusesAttempts);
    }
    
    @Override
    public String toString() {
    	String wyswietlanyWynik = Integer.toString(tops) + "t" + Integer.toString(topAttempts) + "  "  + Integer.toString(bonuses) + "b" + Integer.toString(bonusesAttempts);
    	
    	return wyswietlanyWynik; //TODO zwracać zapis / wynik
    }
}
