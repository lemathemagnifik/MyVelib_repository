package myVelib;

import java.time.Duration;

public interface CardVisitor {
	public double visit(CreditCard creditCard,Duration tripDuration, Bicycle.BicycleType type) throws Exception;
	public double visit(VlibreCard vlibreCard, Duration tripDuration, Bicycle.BicycleType type) throws Exception;
	public double visit(VmaxCard vmaxCard, Duration tripDuration, Bicycle.BicycleType type) throws Exception;
}
