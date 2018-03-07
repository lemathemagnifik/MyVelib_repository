package myVelib;

public class Bicycle {
	static int counter = 0 ; 
	private BicycleType type;
	private boolean isUsed;
	private int ID;
	
	public enum BicycleType{Electrical, Mechanical};
	
	public Bicycle(BicycleType type) {
		super();
		counter++;
		this.type = type;
		this.ID = counter;
		this.isUsed = false;
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
	
	
	public int getID() {
		return ID;
	}
	
	
	public void setID(int iD) {
		ID = iD;
	}

	

}