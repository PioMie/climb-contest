package cc.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import cc.service.ifsc.IfscScore;

@RunWith(MockitoJUnitRunner.class)
public class IfscScoreTest {

	@Test
	public void testToString() {
		// given
		IfscScore testScore = new IfscScore(0, 3, 1, 2);
		String expectedString = "0t3  1b2";

		// when
		String testScoreString = testScore.toString();

		// then
		Assert.assertEquals(testScoreString, expectedString);
	}

	@Test
	public void testParseString() {
		// given
		String testString = "0t3  1b2";

		// when
		IfscScore resultScore = IfscScore.parseString(testString);

		// then
		Assert.assertEquals(resultScore, new IfscScore(0, 3, 1, 2));
	}

	@Test(expected = RuntimeException.class)
	public void testParseStringFailsOnWrongNumberOfArguments() {
		// given
		String testString = "0t  1b";

		// when
		IfscScore.parseString(testString);
	}

	@Test(expected = NumberFormatException.class)
	public void testParseStringFailsOnParsingInt() {
		// given
		String testString = "0.5t3  1b2";

		// when
		IfscScore.parseString(testString);
	}

}
