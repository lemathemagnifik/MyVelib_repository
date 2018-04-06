package myVelib;

import java.sql.Timestamp;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListMap;

import CLUI.MisuseException;
import myVelib.Bicycle.BicycleType;
import myVelib.Station.NoAvailableFreeSlotsException;
import myVelib.Station.NoBikesAvailableException;
import myVelib.Station.OfflineStationException;




public class User implements Observer {
	
//*****************************************************************//
//							Attributes 							   //
//*****************************************************************//
		
	static int IDuserCounter=0;
	final static double walkingSpeed = 4;
	
	private MyVelib myVelib;
	private Network network;
	protected int id;
	private String name;
	private CreditCard creditCard;
	private Card card;
	private GPS position;
	private Ride ride;
	public PlannedRide plannedRide;
	private UserBalance userBalance;
	private Bicycle bicycle; //Pas sï¿½r de l'utilitï¿½ de Bicycle
	private ArrayList<Message> messageBox;
	private ArrayList<Observable> observedStations = new ArrayList<Observable>();
	
	/**A map representing a user's history: with Timestamps as keys and UserStates as values.
	 * This type of map stores the key-value pairs in a specific order. This way it is easy to get the last user's state.
	 * 
	 */
	private ConcurrentSkipListMap <Timestamp, Ride> userHistory = new ConcurrentSkipListMap<Timestamp, Ride>();

	
	
	
//*****************************************************************//
//						Constructor 							   //
//*****************************************************************//
	
	/**
	 * Constructor 
	 * @param id
	 * @param card
	 * @param name
	 * @param messageBox
	 */
	public User(String name) {
		super();
		IDuserCounter++;
		this.id=IDuserCounter;
		this.creditCard = new CreditCard(this);
		this.card = this.creditCard;
		this.name = name;
		this.messageBox = new ArrayList <Message>();
		this.position = new GPS(0,0);
		this.userBalance = new UserBalance();
	}
	
	public User(String name, Card card, Network network, MyVelib myVelib) {
		super();
		IDuserCounter++;
		this.id=IDuserCounter;
		this.network= network;
		this.creditCard = new CreditCard(this);
		this.card = card;
		this.name = name;
		this.messageBox = new ArrayList <Message>();
		this.position = new GPS(0,0);
		this.userBalance = new UserBalance();
		this.myVelib = myVelib;
	}
	
	
	public User() {
		super();
	}
	
	
//*****************************************************************//
//						Getters and Setters 					   //
//*****************************************************************//
	
	
	public int getId() {
		return id;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public GPS getPosition() {
		return position;
	}

	public void setPosition(GPS position) {
		this.position = position;
	}
//Pas sï¿½r de l'utilitï¿½ de Bicycle
	public Bicycle getBicycle() {
		return bicycle;
	}

	public void setBicycle(Bicycle bicycle) {
		this.bicycle = bicycle;
	}

	public ArrayList<Message> getMessageBox() {
		return messageBox;
	}

	public void setMessageBox(ArrayList<Message> messageBox) {
		this.messageBox = messageBox;
	}

	public Ride getRide() {
		return ride;
	}
	
	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public ConcurrentSkipListMap<Timestamp, Ride> getUserHistory() {
		return userHistory;
	}

	public void setUserHistory(ConcurrentSkipListMap<Timestamp, Ride> userHistory) {
		this.userHistory = userHistory;
	}

	public static int getIDuserCounter() {
		return IDuserCounter;
	}

	public static void setIDuserCounter(int iDuserCounter) {
		IDuserCounter = iDuserCounter;
	}

	public static double getWalkingspeed() {
		return walkingSpeed;
	}
	
	


//*****************************************************************//
//							Methods 							   //
//*****************************************************************//	

	
// OBSERVER PATTERN
	/** Observers functions
	 * 
	 * @param s
	 */
	
	public void subscribeStation(Observable s){
		s.addObserver(this);
		this.observedStations.add(s);
	}
	
	public void unsuscribe(Observable s){
		s.deleteObserver(this);
		observedStations.remove(s);
	}
	

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Station) {
			Station station = (Station) o;
			this.recieveMessage(new Message("Your arrival station "+station.getName()+" with ID "+station.getId()+" is now "+station.getStatus()+"."));
			System.out.println("Your arrival station "+station.getName()+" with ID "+station.getId()+" is now "+station.getStatus()+".");
			Scanner sc = new Scanner(System.in);
			char str = ' ';
			while (str != 'Y' || str!='N') {
				System.out.println("Would you want to recalculate your path ? (Y/N)");
				str = sc.nextLine().charAt(0);
				if (str =='Y') {
					GPS destination = this.plannedRide.getArrival();
					boolean plus = this.plannedRide.isPlus();
					boolean uniformity = this.plannedRide.isUniformity();
					boolean fastest = this.plannedRide.isFastest();
		 			this.planRide(destination, plus, uniformity, fastest);
		 			break;
				}
				else if (str =='N') {
					System.out.println("No new path calculation.");
					break;
				}
				else{	System.out.println("Invalide Synthaxe : please enter Y or N.");}

			}
	
		}

	}


	public void recieveMessage(Message m){
		messageBox.add(m);
	}
	
	public void displayMessage(){
		for (Message m : this.messageBox) {
			m.setRead(true);
			System.out.println(m.getText());}
	}
	
	public void removeMessage(Message m){
		messageBox.remove(m);
	}
	

//-----------------------------------------------------------------//

// VISITOR PATTERN	
		
	/**
	 * adds an event to the user's history. Also checks that the events are entered in chronological order.
	 * @param t
	 * @param ps
	 */
	public void updateUserHistory(Timestamp t, Ride ride){
		if(!userHistory.isEmpty()&& userHistory.lastKey().compareTo(t)>0){
//			System.out.println("Error, do not enter a time in the past.");
		}
		else{
			userHistory.put(t,ride);
			//System.out.println("The user's history is updated: the user "+ride.toString()+" the bicycle "+ bicycle.toString() + "at time "+t.toString());
		}
	}
	

	

//-----------------------------------------------------------------//

	/**
	 * This function allows the User to drop Off his bicycle.
	 * @param u
	 * @param s
	 * @throws NoAvailableFreeSlotsException 
	 * @throws OfflineStationException 
	 */
	
	public void returnBike(Station s, Duration tripDuration) throws OfflineStationException, NoAvailableFreeSlotsException   {
		Timestamp myVelibCurrentTime = myVelib.getCurrentTime();
		Timestamp rentTime = this.ride.getDepartureTime();
		Timestamp returnTime = new Timestamp(rentTime.getTime()+tripDuration.toMillis());
		
		if (myVelibCurrentTime.getTime() > returnTime.getTime()) {
			Duration minDuration = Duration.ofMillis(myVelibCurrentTime.getTime() - rentTime.getTime());
			System.out.println("Wrong duration :");
			System.out.println("The user "+this.name+" rented his bike on : "+ MyVelib.timeToString(rentTime)+".");
			System.out.println("Current time is : "+ MyVelib.timeToString(myVelibCurrentTime)+".");
			System.out.println("So the minimum trip duration should be : "+ minDuration+".");
			System.out.println("Consequently, the user's trip duration has been sat to the minimum allowed duration");
			System.out.println("and return time has been set to current time.");
			tripDuration = minDuration;
			returnTime = new Timestamp(myVelibCurrentTime.getTime());
		}
		else {
			this.myVelib.setCurrentTime(returnTime);
			myVelib.printCurrentTime();
		}

		this.position = s.getPosition();
		// return the bike to an available ParkingSlot
		s.returnBicycle(this.bicycle, returnTime);
		// On signale ï¿½ la station qu'on a rendu un vï¿½lo.	
		s.addEntryToStationHistory(returnTime);
		s.setNumberOfReturns(s.getNumberOfReturns()+1);

		this.setBicycle(null);
		
		//We compute the duration of the trip in ms.
		this.ride.setDuration(tripDuration);

			
		//If the user has a VelibCard, if the Station is Plus we add timeCredit to the card, 
		//then we reduce the number of hours to pay using that timeCredit.
		if (this.card instanceof VelibCard) {
			VelibCard vCard = (VelibCard) this.card;
			if (s.getStationType()==Station.StationType.Plus) {
				vCard.creditTime();
				this.ride.setTimeCredit(this.ride.getTimeCredit().plus(Station.plusTimeCredit));
				this.userBalance.setTotalTimeCredit(this.userBalance.getTotalTimeCredit().plus(Station.plusTimeCredit));
			}
		}
				 		
		
		CardVisitor visitor = new ConcreteCardVisitor();
			
		Double cost = (double) 0;
		try {
			cost = this.card.accept(visitor, tripDuration, this.ride.getBicycle().getType());
		} catch (Exception e) {
		}
		
		System.out.println(this.name +" paid "+ cost +"€.");
		
		this.userBalance.setTotalCharges(this.userBalance.getTotalCharges() + cost);
		this.userBalance.getTotalTime().plus(tripDuration);
		
		this.ride.setArrivalTime(returnTime);
		this.ride.setArrivalStation(s);
		this.ride.setCost(cost);
		this.updateUserHistory(this.ride.getDepartureTime(), this.ride);
		this.ride = new Ride();
		
		this.unsuscribe(s);
	}
	
	
				
	/**
	 * This function allows the User to drop on a bicycle.
	 * @param t
	 * @param s
	 * @throws NoMoreElectricalException 
	 */

	
	public void rentBike(Station s, Bicycle.BicycleType bType) throws AlreadyHasABikeException, OfflineStationException, NoBikesAvailableException {
		Timestamp t = new Timestamp(myVelib.getCurrentTime().getTime());
		if (this.getBicycle()!=null) {
			throw new AlreadyHasABikeException();
		}
		else {
			Bicycle bicycle = s.retrieveBicycle(bType, t);
			this.setBicycle(bicycle);
			// start counter for the user
			//this.updateUserHistory(t, this.ride);				
			this.ride = new Ride();	
			this.userBalance.setNumberOfRides(this.userBalance.getNumberOfRides()+1);
				
			//We need to begin the riding time and put something in the TimeStamp
			s.addEntryToStationHistory(t);
			s.setNumberOfRentals(s.getNumberOfRentals()+1);
			this.ride.setDepartureStation(s);
			this.ride.setDepartureTime(t);
			this.ride.setBicycle(bicycle);		
		}
	}
	

	
	
	public void planRide(GPS destination,  boolean plus, boolean uniformity, boolean fastest) {
		if (this.bicycle == null) {
			this.plannedRide = new PlannedRide(this.network, this.position, destination, plus, uniformity, fastest, false);
		}
		else {
			this.plannedRide = new PlannedRide(this.network, this.position, destination, plus, uniformity, fastest, true);
		}
		System.out.println(this.plannedRide);
		this.ride = this.plannedRide;
		this.plannedRide.getArrivalStation().addObserver(this);
	}
	
	public int getNumberOfRides() {
		return this.userBalance.getNumberOfRides();
	}
	
	public Duration getTotalTime() {
		return this.userBalance.getTotalTime();
	}
	
	public Double getTotalCharges() {
		return this.userBalance.getTotalCharges();
	}
	
	public Duration getTotalTimeCredit() {
		return this.userBalance.getTotalTimeCredit();
	}
	


//-----------------------------------------------------------------//

// toString METHOD
		
		public String toString() {
			String str="";
			String strCard ="";
			String strTime="";
			if (this.card instanceof VelibCard) {
				VelibCard vCard = (VelibCard) this.card;
				strTime = String.format("%-20s %1s", "Time Credit", " : ")+ vCard.getTimeCredit().toMinutes() +"\n";
				if (card instanceof VlibreCard) {strCard = "Vlibre";}
				else if (card instanceof VmaxCard) {strCard = "Vmax";}
			}
			else {strCard="Credit Card";};

			str+= "========= User Infos =========" +"\n";
			str+= String.format("%-20s %1s", "User Name", " : ")+ this.name +"\n";
			str+= String.format("%-20s %1s", "User Id", " : ")+ this.id +"\n";
			str+= String.format("%-20s %1s", "Network Name", " : ")+ this.network.getName() +"\n";
			str+= String.format("%-20s %1s", "User Position", " : ")+ this.position.str() +"\n";
			str+= String.format("%-20s %1s", "Card Type", " : ")+ strCard +"\n";
			str+= strTime;
			str+= "======== User Balance ========" +"\n";
			str+= String.format("%-20s %1s", "Number of rides", " : ")+ this.getNumberOfRides() +"\n";
			str+= String.format("%-20s %1s", "Total trips duration", " : ")+ this.getTotalTime().toMinutes() +" minutes \n";
			str+= String.format("%-20s %1s", "Total time credit", " : ")+ this.getTotalTimeCredit().toMinutes() +" minutes \n";
			return str;

		}

		public String toArray() {
			String strCard ="";
			String strTime="";
			if (this.card instanceof VelibCard) {
				VelibCard vCard = (VelibCard) this.card;
				strTime = vCard.getTimeCredit().toMinutes() +" min";
				if (card instanceof VlibreCard) {strCard = "Vlibre";}
				else if (card instanceof VmaxCard) {strCard = "Vmax";}
			}
			else {strCard="Credit Card";};
			
			return String.format("%-7s %1s %-20s %1s %-25s %1s %-11s %1s %-11s %1s %-15s %1s %-20s %1s %-17s %1s", id, "|",name, "|",this.position ,"|",strCard, "|", strTime, "|", this.getNumberOfRides(),"|", this.getTotalTime().toMinutes()+" min", "|", this.getTotalTimeCredit().toMinutes() +" min", "|"  );
		}
	
//*****************************************************************//
//							EXCEPTIONS 							   //
//*****************************************************************//	
	


	
	public class AlreadyHasABikeException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8282649192773856116L;

		public AlreadyHasABikeException(){
		    System.out.println("This user has already a bike ! Only one bike can be rented at a time.");
		  }  
	}




	
	

	
	
//*****************************************************************//
//								Main 							   //
//*****************************************************************//	
	

		
		
}


