package myVelib;



public class PlannedRide extends Ride{
	private TripPreference preference;
	private GPS departure;
	private GPS arrival;
	private Network network;
	private boolean plus;
	private boolean uniformity;
	private boolean fastest;
	private boolean alreadyHaveBicycle;
	private Station[] path;
	
	public PlannedRide(Network network, GPS departure, GPS arrival, boolean plus, boolean uniformity, boolean fastest, boolean alreadyHaveBicycle)  {
		super();
		this.network = network;
		this.departure = departure;
		this.arrival = arrival;
		this.fastest = fastest;
		this.plus = plus;
		this.uniformity = uniformity;
		if (alreadyHaveBicycle) {
			preference = new RecalculatePath();
		}
		else {
			if (fastest) {
				preference = new FastestPath();
				FastestPath f =  new FastestPath();
				try{f.setPath(network, departure, arrival, uniformity, plus);} catch (Exception e) {}
				this.bicycleType = f.getBicycleType();
				this.duration = f.getDuration();
			}
			else {
				preference = new ShortestPath();
			}
		}

			this.path = preference.setPath(network, departure, arrival, uniformity, plus);
			

		this.departureStation=this.path[0];
		this.arrivalStation=this.path[1];
		

	}

	public TripPreference getPreference() {
		return preference;
	}

	public void setPreference(TripPreference preference) {
		this.preference = preference;
	}

	public GPS getDeparture() {
		return departure;
	}

	public void setDeparture(GPS departure) {
		this.departure = departure;
	}

	public GPS getArrival() {
		return arrival;
	}

	public void setArrival(GPS arrival) {
		this.arrival = arrival;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public boolean isPlus() {
		return plus;
	}

	public void setPlus(boolean plus) {
		this.plus = plus;
	}

	public boolean isUniformity() {
		return uniformity;
	}

	public void setUniformity(boolean uniformity) {
		this.uniformity = uniformity;
	}

	public boolean isFastest() {
		return fastest;
	}

	public void setFastest(boolean fastest) {
		this.fastest = fastest;
	}

	public boolean isAlreadyHaveBicycle() {
		return alreadyHaveBicycle;
	}

	public void setAlreadyHaveBicycle(boolean alreadyHaveBicycle) {
		this.alreadyHaveBicycle = alreadyHaveBicycle;
	}

	public Station[] getPath() {
		return path;
	}

	public void setPath(Station[] path) {
		this.path = path;
	}
	
	
	
	

	

	
	
	@Override
	public String toString() {
		String str="";
		str+="====== Planned Ride ======\n\n";
		str+="You are now at "+ this.departure.str()+ " and want to go to "+ this.arrival.str() + "using ";
		if (this.fastest) {
			str+= "the fastest path. \n";
		}
		else str+="the shortest path. \n ";
		if (this.plus) {
			str+="Arrival station should be a plus station if possible. \n";
		}
		if (this.uniformity) {
			str+= "Thank you for helping us unifromize our stations !";
		}
		if (this.alreadyHaveBicycle) {
			str+="\n You have already rented a bicycle.";
		}
		str+= "\n\n To reach your destination you need to :";
		if (this.alreadyHaveBicycle==false) {
			str+= "\n* Walk to the "+this.departureStation.getName()+ " station whose ID is "+this.departureStation.getId()+". It is located at : "+ this.departureStation.getPosition().str()+"."
					+ "\n* Rent the "+Bicycle.bicycleTypeString(this.bicycleType)+ " bike of your choice.";
		}
		str+= "\n* Cycle towards the " + this.arrivalStation.getName()+ " station whose ID is "+this.arrivalStation.getId()+". It is located at : "+ this.arrivalStation.getPosition().str()+".";
		if (fastest) {
			str+= "\n  You will get there in "+this.duration.toMinutes()+" minutes.";
		}
		str+= "\n  You will be notified if the station goes offline.";
		str+= "\n* Drop your bike there and walk to your destination : "+this.arrival.str()+".";
		str+="\n  Enjoy your trip with Velib !\n";
		 
		return str;
	}

	public static void main(String[] args)  {


	}
	
	
}
	
