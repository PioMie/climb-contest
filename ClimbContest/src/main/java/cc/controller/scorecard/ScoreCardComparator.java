package cc.controller.scorecard;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.controller.scorecard.slb.SlbClimberScorecard;
import cc.service.slb.SlbCalculator;

@Service
public class ScoreCardComparator implements Comparator<SlbClimberScorecard> {

	@Autowired
	private SlbCalculator calculator;

	@Override
	public int compare(SlbClimberScorecard o1, SlbClimberScorecard o2) {
		return -calculator.compare(o1.getScoreWithBonus(), o2.getScoreWithBonus());
	}

}
