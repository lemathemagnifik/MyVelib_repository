package myVelib;

import java.sql.Timestamp;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentSkipListMap;

import Tests.Test;
import myVelib.Bicycle.BicycleType;
import myVelib.ParkingSlot.UnavailableSlotException;
import myVelib.Station.NoMoreBikeException;



public class User implements CardVisitor, Observer {
	
//*****************************************************************//
//							Attributes 							   //
//*****************************************************************//
		
	static int IDuserCounter=0;
	final static double walkingSpeed = 4;
	
	private Network network;
	protected int id;
	private String name;
	private Card card;
	private GPS position;
	private Ride ride;
	private UserBalance userBalance;
	private Bicycle bicycle; //Pas sûr de l'utilité de Bicycle

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
		this.card = null;
		this.name = name;
		this.messageBox = new ArrayList <Message>();
		this.position = new GPS(0,0);
		this.userBalance = new UserBalance();
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
//Pas sûr de l'utilité de Bicycle
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
			this.recieveMessage(new Message("The Station "+station.getName()+" is now "+station.getStatus()));
			System.out.println("The Station "+station.getName()+" is now "+station.getStatus());
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
			System.out.println("Error, do not enter a time in the past.");
		}
		else{
			userHistory.put(t,ride);
			//System.out.println("The user's history is updated: the user "+ride.toString()+" the bicycle "+ bicycle.toString() + "at time "+t.toString());
		}
	}
	
	
	//bleu 1ï¿½ /heure-> mï¿½canique 2ï¿½/h-> ï¿½lectrique
	//velibr 0ï¿½ premiï¿½re heure et 1ï¿½ le reste -> mï¿½canique; 1ï¿½ 1ere h 2ï¿½ les autres h.->electrique
	//vmax 0ï¿½premiï¿½re h 1ï¿½ pour les heures suivantes.
	
	
	/**
	 * cette classe compute le temps final. C'est ï¿½ dire que tripTime est le temps moins le crï¿½dit en temps.
	 * On facture par heures entiï¿½res.
	 * @throws Exception 
	 */
	
	//TODO Coder le duration trip
	@Override
	public double visit(BlueCard blueCard, Duration tripTime, Bicycle.BicycleType type) throws Exception {
		// toHours() retourne le nombre d'heures tronque
		if (type==Bicycle.BicycleType.Electrical){
			return  (tripTime.toHours()+1)*blueCard.getCostH1electrical();
		}
		if (type==Bicycle.BicycleType.Mechanical){
			return (tripTime.toHours()+1)*blueCard.getCostH1mechanical();
		}
		else{
			throw new Exception("bicycle type not found!");
		}
	}
	@Override
	public double visit(VlibreCard vlibreCard, Duration tripTime, Bicycle.BicycleType type) throws Exception {	
		// contient le nombre d'heures moins un !
		long numberOfHours=tripTime.toHours();
		
		if(type==Bicycle.BicycleType.Mechanical){
			return numberOfHours;
		}
		if(type==Bicycle.BicycleType.Electrical){
			if(numberOfHours==1){
				return 1;
			}
			else{
				return (numberOfHours-1)*2+1;
			}
		}
		else {
			throw new Exception("bicycle type error!");
		}
	}
	
	@Override
	public double visit(VmaxCard vmaxCard,  Duration tripTime, BicycleType type) {
		long numberOfHours= tripTime.toHours();
		
		return numberOfHours;
	}
	
	

//-----------------------------------------------------------------//

	/**
	 * This function allows the User to drop Off his bicycle.
	 * @param u
	 * @param s
	 */
	
	public void returnBike(Station s, Timestamp t) throws UnavailableStationException, NoMoreAvailableSlotsException {
		
		if (s.getStatus()==Station.Status.Offline)
			throw new UnavailableStationException();
		if (s.getStatus()==Station.Status.Full)
			throw new NoMoreAvailableSlotsException();
		
		else {
			for (int i=0; i<s.getParkingSlots().size(); i++) {
				if (s.getParkingSlots().get(i).getStatus() == ParkingSlot.Status.Free)
				{
					System.out.println("Please, put your bicycle at slot "+ i);
					//On gÃ¨re l'exception pour des questions de compilation
					
					try {
						s.getParkingSlots().get(i).addBicycle(this.getBicycle(),t);
						}
					//le println ??
					catch(UnavailableSlotException e) {System.out.println("no electrical: "  + e.toString());
					}
					
				
					// On signale Ã  la station qu'on a rendu un vÃ©lo.	
					s.addEntryToStationHistory(t);
					
					//On met du crÃ©dit si c'est une plus station
					if (s.getStationType()==Station.StationType.Plus) {
						this.getCard().creditTime();
						//TODO remplacer tout les 300000 par une constante et défiinir la constante.
						this.ride.getTimeCredit().plusMillis(300000);
						this.userBalance.getTotalTimeCredit().plusMillis(300000);
					}
					 
					//We compute the duration of the trip in ms.
					Duration duration = Duration.ZERO;
					duration.plusMillis(t.getTime()-this.ride.getDepartureTime().getTime());
					//We update the user's history
					s.setNumberOfReturns(s.getNumberOfReturns()+1);
					//We compute the cost of the trip
					Double cost = (double) 0;
					try {
					cost = this.visit((BlueCard) this.card, duration, this.getBicycle().getType());
					System.out.println("You have to pay" + cost + "euros.");
					}
					catch(Exception e) {System.out.println("no blueCard: "  + e.toString());
					}
					this.setBicycle(null);
					
					this.userBalance.setTotalCharges(this.userBalance.getTotalCharges() + cost);
					this.userBalance.getTotalTime().plus(duration);
					
					this.ride.setArrivalTime(t);
					this.ride.setArrivalStation(s);
					this.ride.setDuration(duration);
					this.ride.setCost(cost);
					this.updateUserHistory(this.ride.getDepartureTime(), this.ride);
					this.ride = new Ride();
					// TODO Stocker ce coÃ»t dans Trip pour le retrouver ou bien dans Card
					// TODO Faire sortir l'utilisateur des observateurs.
					this.unsuscribe(s);
				}
			}
		}
	}
	
	
				
	/**
	 * This function allows the User to drop on an electrical bicycle.
	 * @param t
	 * @param s
	 * @throws NoMoreElectricalException 
	 */
//	public void rentBikeElectrical(Station s, Timestamp t) throws NoMoreElectricalException, AlreadyHasABikeException {
//		if (s.slotsOccupiedByElectrical()==0)
//			throw new NoMoreElectricalException(); 
//		if (this.getBicycle()!=null)
//			throw new AlreadyHasABikeException();
//		try {
//		int i = s.selectBicycleElectrical();
//		//We get the bicycle
//				Bicycle bicycle = s.getParkingSlots().get(i).getBicycle();
//				// We set free the slot
//				s.getParkingSlots().get(i).becomesFree(t);
//				this.setBicycle(bicycle);
//				// start counter for the user
//				this.updateUserHistory(t, UserAction.dropped_on);
//				//We need to begin the riding time and put something in the TimeStamp
//				s.addEntryToStationHistory(t);
//				s.setNumberOfRentals(s.getNumberOfRentals()+1);
//		}
//		catch(Station.NoMoreElectricalException e){System.out.println("no electrical: "  + e.toString());
//		}	
//	}
	
	/**
	 * This function allows the User to drop on a mechanical bicycle.
	 * @param u
	 * @param s
	 * @throws NoMoreMechanicalException 
	 */
	
//	
//	public void rentBikeMechanical(Station s, Timestamp t) throws NoMoreMechanicalException, AlreadyHasABikeException {
//		if (s.slotsOccupiedByMechanical()==0)
//			throw new NoMoreMechanicalException(); 
//		if (this.getBicycle()!=null)
//			throw new AlreadyHasABikeException();
//		try {
//		int i = s.selectBicycleMechanical();
//		//We get the bicycle
//				Bicycle bicycle = s.getParkingSlots().get(i).getBicycle();
//				// We set free the slot
//				s.getParkingSlots().get(i).becomesFree(t);
//				this.setBicycle(bicycle);
//				// start counter for the user
//				this.updateUserHistory(t, UserAction.dropped_on);
//				//We need to begin the riding time and put something in the TimeStamp
//				s.addEntryToStationHistory(t);
//				s.setNumberOfRentals(s.getNumberOfRentals()+1);
//		}
//		catch(Station.NoMoreMechanicalException e){System.out.println("no electrical: "  + e.toString());
//		}	
//	}
	
	public void rentBike(Station s, Bicycle.BicycleType bType, Timestamp t) throws NoMoreBikesException, AlreadyHasABikeException {
		if (this.getBicycle()!=null) {
			throw new AlreadyHasABikeException();
		}
		else if (s.slotsOccupiedByMechanical()==0 & bType==Bicycle.BicycleType.Mechanical || s.slotsOccupiedByElectrical()==0 & bType==Bicycle.BicycleType.Electrical) {
			throw new NoMoreBikesException();
		}
		
		else {
			try {
				int i = s.selectBicycle(bType);
				Bicycle bicycle = s.getParkingSlots().get(i).getBicycle();
				// We set free the slot
				s.getParkingSlots().get(i).becomesFree(t);
				this.setBicycle(bicycle);
				// start counter for the user
				this.ride=new Ride();
				this.updateUserHistory(t, this.ride);
				
				this.userBalance.setNumberOfRides(this.userBalance.getNumberOfRides()+1);
				
				//We need to begin the riding time and put something in the TimeStamp
				s.addEntryToStationHistory(t);
				s.setNumberOfRentals(s.getNumberOfRentals()+1);
				this.ride.setDepartureStation(s);
				this.ride.setDepartureTime(t);
				this.ride.setBicycle(bicycle);
			}
			catch (NoMoreBikeException e) {
				e.toString();
			}
		}
	}
	

	public void userBalance() {
		
	}
	
	public void planRide(GPS destination,  boolean plus, boolean uniformity, boolean fastest) {
		if (this.ride == null) {
			this.ride = new PlannedRide(this.network, new GPS(0,0), new GPS(5,5), true, true, false, false);
			System.out.println("We are finding the best path");
		}
		else {
			this.ride = new PlannedRide(this.network, this.position, destination, plus, uniformity, fastest, true);
			System.out.println("You haven't reached your destination yet. We are looking for a new path.");
			
		}
	}
	

//-----------------------------------------------------------------//

// toString METHOD
		
		public String toString() {
			return "User [id=" + id + ", name=" + name + ", card="
					+ card + "]";
		}


	
//*****************************************************************//
//							EXCEPTIONS 							   //
//*****************************************************************//	
	
	public class NoMoreElectricalException extends Exception{
		public NoMoreElectricalException(){
		    System.out.println("Sorry, no more electrical bikes available.");
		  }  
	}
	
	public class NoMoreMechanicalException extends Exception{
		public NoMoreMechanicalException(){
		    System.out.println("Sorry, no more electrical bikes available.");
		  }  
	}
	
	public class NoMoreBikesException extends Exception{
		public NoMoreBikesException(){
		    System.out.println("Sorry, no more bikes of the desired type available.");
		  }  
	}
	
	public class AlreadyHasABikeException extends Exception{
		public AlreadyHasABikeException(){
		    System.out.println("Sorry, you already have a bike.");
		  }  
	}
	
	public class UnavailableStationException extends Exception{
		public UnavailableStationException(){
		    System.out.println("Sorry, this station is unavailable to drop off your bicycle.");
		  }  
	}
	
	public class NoMoreAvailableSlotsException extends Exception{
		public NoMoreAvailableSlotsException(){
		    System.out.println("Sorry, this station has no more available slots.");
		  }  
	}
	
	
	
//*****************************************************************//
//								Main 							   //
//*****************************************************************//	
	
	public static void main(String[] args) throws UnavailableSlotException {
		
		Network myNetwork = Test.CreateTestNetwork();
		User userTest = new User("Anis");
		userTest.setNetwork(myNetwork);
		userTest.setRide( new PlannedRide(myNetwork, userTest.getPosition(), new GPS(5,5), true, true, false, false));
		System.out.println(userTest.ride.getDepartureStation());
		System.out.println(userTest.ride.getArrivalStation());
		
		
		
	}



	
}