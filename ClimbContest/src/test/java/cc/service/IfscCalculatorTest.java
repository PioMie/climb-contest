package cc.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import cc.service.ifsc.IfscCalculator;
import cc.service.ifsc.IfscScore;

@RunWith(MockitoJUnitRunner.class)
public class IfscCalculatorTest {

	IfscCalculator calculator = new IfscCalculator();

	@Test
	public void testAddAttempt() {
		// given
		String testScore = "";

		// when
		testScore = calculator.addAttempt(testScore, BoulderAttemptEffect.NONE);
		testScore = calculator.addAttempt(testScore, BoulderAttemptEffect.NONE);
		testScore = calculator.addAttempt(testScore, BoulderAttemptEffect.BONUS);
		testScore = calculator.addAttempt(testScore, BoulderAttemptEffect.NONE);
		testScore = calculator.addAttempt(testScore, BoulderAttemptEffect.BONUS);
		testScore = calculator.addAttempt(testScore, BoulderAttemptEffect.TOP);
		testScore = calculator.addAttempt(testScore, BoulderAttemptEffect.NONE);
		testScore = calculator.addAttempt(testScore, BoulderAttemptEffect.BONUS);
		testScore = calculator.addAttempt(testScore, BoulderAttemptEffect.TOP);

		// then
		Assert.assertEquals(testScore, new IfscScore(1, 6, 1, 3).toString());
	}

	@Test
	public void testSumScores() {
		// given
		List<String> ifscScores = Arrays.asList(new IfscScore(9, 9, 9, 9).toString(),
				new IfscScore(1, 2, 3, 4).toString(), new IfscScore(0, 1, 0, 1).toString());
		String expectedScore = new IfscScore(10, 11, 12, 13).toString();

		// when
		String resultScore = calculator.sumScores(ifscScores, null);

		// then
		Assert.assertEquals(resultScore, expectedScore);
	}

}
