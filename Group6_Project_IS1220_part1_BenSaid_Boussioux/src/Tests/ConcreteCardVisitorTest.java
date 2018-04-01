package Tests;

import myVelib.*;
import static org.junit.Assert.*;

import java.time.Duration;

import org.junit.Test;

public class ConcreteCardVisitorTest {

	@Test
	public void testApplyVelibBonus() {
		Duration timeCredit = Duration.ZERO;
		Duration tripDuration = Duration.ZERO;
		tripDuration = tripDuration.plusMinutes(125);
		timeCredit = timeCredit.plusMinutes(75);
		Duration[] results = ConcreteCardVisitor.applyVelibBonus(timeCredit, tripDuration);
		System.out.println(timeCredit);
		System.out.println(tripDuration);
		System.out.println(results[0]);
		System.out.println(results[1]);		
		assertTrue(results[0].equals(timeCredit.minusMinutes(65)) & results[1].equals(tripDuration.minusMinutes(65)));
	}

//	@Test
//	public void testVisitCreditCardDurationBicycleType() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testVisitVlibreCardDurationBicycleType() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testVisitVmaxCardDurationBicycleType() {
//		fail("Not yet implemented");
//	}
//
}
