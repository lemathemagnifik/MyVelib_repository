package Tests;
import myVelib.*;
import static org.junit.Assert.*;

import org.junit.Test;

import myVelib.Bicycle;

public class BicycleTest {

	@Test
	public void testBicycleTypeString() {
		assertTrue(Bicycle.bicycleTypeString(Bicycle.BicycleType.Mechanical)=="mechanical");
		assertTrue(Bicycle.bicycleTypeString(Bicycle.BicycleType.Electrical)=="electrical");
	}

}
