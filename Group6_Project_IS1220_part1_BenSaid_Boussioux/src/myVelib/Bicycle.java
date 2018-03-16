package myVelib;

public class Bicycle {
	static int counter = 0 ; 
	private BicycleType type;
	private boolean isUsed;
	private int ID;
	private double speed;
	final static double electricalSpeed = 20;
	final static double mechanicalSpeed = 15;

	
	public enum BicycleType{Electrical, Mechanical};
	
	
	public Bicycle(BicycleType type) {
		super();
		counter++;
		this.type = type;
		this.ID = counter;
		this.isUsed = false;
		if (type==BicycleType.Electrical) {
			this.speed=electricalSpeed;
		}

		else if (type==BicycleType.Mechanical) {
			this.speed = mechanicalSpeed;
			}
	}
	
	
	public boolean isUsed() {
		return isUsed;
	}
	
	
	public BicycleType getType() {
		return type;
	}

	
	public void setType(BicycleType type) {
		this.type = type;
	}
	
	
	public double getSpeed() {
		return speed;
	}
	
	
	public int getID() {
		return ID;
	}
	


}