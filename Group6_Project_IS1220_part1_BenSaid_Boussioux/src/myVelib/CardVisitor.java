package myVelib;

import java.time.Duration;

public interface CardVisitor {
	public double visit(VlibreCard vlibreCard, Duration tripTime, Bicycle.BicycleType type) throws Exception;
	public double visit(VmaxCard vmaxCard, Duration tripTime, Bicycle.BicycleType type) ;
}
