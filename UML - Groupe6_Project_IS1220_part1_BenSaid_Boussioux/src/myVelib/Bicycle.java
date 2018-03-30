package myVelib;

public class Bicycle {
	static int counter = 0 ; 
	private Network network;
	private BicycleType type;
	private boolean isUsed;  	//TODO is it needed ?
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
		this.isUsed = false; //TODO is it needed ?
		if (type==BicycleType.Electrical) {
			this.speed=electricalSpeed;
		}

		else if (type==BicycleType.Mechanical) {
			this.speed = mechanicalSpeed;
			}
	}
	
	public boolean isUsed() { //TODO is it needed ?
		return isUsed;
	}
	
	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
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
	
	/** 
	 * @param bType bicycle type
	 * @return a String containing the bicycle Type
	 */
	public static String bicycleTypeString(Bicycle.BicycleType bType) {
		String str = "";
		if (bType==Bicycle.BicycleType.Electrical) {
			str = "electrical";
		}
		else if (bType==Bicycle.BicycleType.Mechanical) {
			str="mechanical";
		}
		return str;
	}

}