package cc.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import cc.model.IfscScore;

@RunWith(MockitoJUnitRunner.class)
public class IfscCalculatorTest {

	IfscCalculator calculator = new IfscCalculator();

	@Test
	public void testAddAttempt() {
		// given
		IfscScore testScore = new IfscScore();

		// when
		testScore = calculator.addAttempt(testScore, IfscAttemptEffect.NOTHING);
		testScore = calculator.addAttempt(testScore, IfscAttemptEffect.NOTHING);
		testScore = calculator.addAttempt(testScore, IfscAttemptEffect.BONUS);
		testScore = calculator.addAttempt(testScore, IfscAttemptEffect.NOTHING);
		testScore = calculator.addAttempt(testScore, IfscAttemptEffect.BONUS);
		testScore = calculator.addAttempt(testScore, IfscAttemptEffect.TOP);
		testScore = calculator.addAttempt(testScore, IfscAttemptEffect.NOTHING);
		testScore = calculator.addAttempt(testScore, IfscAttemptEffect.BONUS);
		testScore = calculator.addAttempt(testScore, IfscAttemptEffect.TOP);

		// then
		Assert.assertEquals(testScore, new IfscScore(1, 6, 1, 3));
	}

	@Test
	public void testSumScores() {
		// given
		List<IfscScore> ifscScores = Arrays.asList(new IfscScore(9, 9, 9, 9), new IfscScore(1, 2, 3, 4), new IfscScore(0, 1, 0, 1));
		IfscScore expectedScore = new IfscScore(10, 11, 12, 13);

		// when
		IfscScore resultScore = calculator.sumScores(ifscScores);

		// then
		Assert.assertEquals(resultScore, expectedScore);
	}

}