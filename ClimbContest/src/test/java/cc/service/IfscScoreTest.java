package cc.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import cc.model.IfscScore;

@RunWith(MockitoJUnitRunner.class)
public class IfscScoreTest {

	@Test // Metoda testująca
	public void WillIfscScoreToStringFormatStringAsNeeded() {

		// given - tworzymy objekt do testowania
		IfscScore testScore = new IfscScore(0, 3, 1, 2);
		String expectedString = "0t3  1b2";

		// when
		String testScoreString = testScore.toString();

		// then
		Assert.assertEquals(testScoreString, expectedString);
	}

}
