package cc.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import cc.model.IfscScore;

@RunWith(MockitoJUnitRunner.class) // Tak zaczynam każdy test! (odpalacz JUnitów)
public class IfscControllerTest {

	IfscCalculator calculator = new IfscCalculator();

	@Test // Metoda testująca 1
	public void willAddAttemptTopIncrementBothTopsAndTopAttempts() {

		// given - tworzymy objekt do testowania
		IfscScore testScore = new IfscScore(0, 3, 0, 2);

		// when
		IfscScore resultScore = calculator.addAttempt(testScore, "top");

		// then
		Assert.assertEquals(resultScore, new IfscScore(1, 4, 1, 3));
	}

	@Test // Metoda testująca 2
	public void willAddAttemptTopWontIncrementAnythingWhenItShouldNot() {

		// given - tworzymy objekt do testowania
		IfscScore testScore = new IfscScore(1, 1, 1, 1);

		// when
		IfscScore resultScore = calculator.addAttempt(testScore, "top");

		// then
		Assert.assertEquals(resultScore, new IfscScore(1, 1, 1, 1));
	}

	@Test // Metoda testująca 3
	public void willAddAttemptBonusIncrementTopAttemptsAndBonusesAndBonusAttempts() {

		// given - tworzymy objekt do testowania
		IfscScore testScore = new IfscScore(0, 1, 0, 1);

		// when
		IfscScore resultScore = calculator.addAttempt(testScore, "bonus");

		// then
		Assert.assertEquals(resultScore, new IfscScore(0, 2, 1, 2));
	}

	@Test // Metoda testująca 4
	public void willAddAttemptBonusWontIncrementAnythingWhenItShouldNot() {

		// given - tworzymy objekt do testowania
		IfscScore testScore = new IfscScore(1, 1, 1, 1);

		// when
		IfscScore resultScore = calculator.addAttempt(testScore, "bonus");

		// then
		Assert.assertEquals(resultScore, new IfscScore(1, 1, 1, 1));
	}

	@Test // Metoda testująca 5
	public void willAddAttemptNoneIncrementTopAttemptsAndBonusAttempts() {

		// given - tworzymy objekt do testowania
		IfscScore testScore = new IfscScore(0, 1, 0, 1);

		// when
		IfscScore resultScore = calculator.addAttempt(testScore, "none");

		// then
		Assert.assertEquals(resultScore, new IfscScore(0, 2, 0, 2));
	}

	@Test // Metoda testująca 6
	public void willAddAttemptNoneWontIncrementAnythingWhenItShouldNot() {

		// given - tworzymy objekt do testowania
		IfscScore testScore = new IfscScore(1, 1, 1, 1);

		// when
		IfscScore resultScore = calculator.addAttempt(testScore, "none");

		// then
		Assert.assertEquals(resultScore, new IfscScore(1, 1, 1, 1));
	}

	@Test // SumScores
	public void willSumScoresReturnExpectedResult() {

		// given - tworzymy objekt do testowania
		IfscScore[] ifscScores = { new IfscScore(9, 9, 9, 9), new IfscScore(1, 2, 3, 4), new IfscScore(0, 1, 0, 1) };
		IfscScore expectedScore = new IfscScore(10, 12, 12, 14);

		// when
		IfscScore resultScore = calculator.sumScores(ifscScores);

		// then
		Assert.assertEquals(resultScore, expectedScore);
	}

}
