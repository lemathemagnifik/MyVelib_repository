package myVelib;

import java.sql.Timestamp;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentSkipListMap;

import myVelib.Bicycle.BicycleType;
import myVelib.ParkingSlot.UnavailableSlotException;
import myVelib.Ride.NoMoreElectricalException;
import myVelib.User.UnavailableStationException;



public class User implements CardVisitor, Observer {
	

	public enum UserAction {dropped_on, dropped_off}; 
	
	/**A map representing a user's history: with Timestamps as keys and UserStates as values.
	 * This type of map stores the key-value pairs in a specific order. This way it is easy to get the last user's state.
	 * 
	 */
	private ConcurrentSkipListMap <Timestamp, UserAction> userHistory = new ConcurrentSkipListMap<Timestamp, User.UserAction>();
	private GPS position;
	private PlannedRide plannedRide;
	private Network network;
	protected int id;
	private Card card;
	private String name;
	static int IDuserCounter=0;
	final static double walkingSpeed = 4;
	private ArrayList<Message> messageBox;
	private ArrayList<Station> observedStations = new ArrayList<Station>();
	private Bicycle bicycle;
	
	public Bicycle getBicycle() {
		return bicycle;
	}
	public void setBicycle(Bicycle bicycle) {
		this.bicycle = bicycle;
	}
	
	
	
	
	/** The getters and setters
	 * 
	 * @param userActions
	 */

	
	
	public GPS getPosition() {
		return position;
	}
	public PlannedRide getPlannedRide() {
		return plannedRide;
	}
	public void setPlannedRide(PlannedRide plannedRide) {
		this.plannedRide = plannedRide;
	}
	public void setPosition(GPS position) {
		this.position = position;
	}
	
	public void setPosition(GPS departure, GPS arrival) {
		this.position = arrival;
		
	}
		
	public Network getNetwork() {
		return network;
	}
	public void setNetwork(Network network) {
		this.network = network;
	}

	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ConcurrentSkipListMap<Timestamp, UserAction> getUserHistory() {
		return userHistory;
	}
	public void setUserHistory(ConcurrentSkipListMap<Timestamp, UserAction> userHistory) {
		this.userHistory = userHistory;
	}
	
	
	
	/** Observers functions
	 * 
	 * @param s
	 */
	
	public void subscribeStation(Station s){
		s.addObserver(this);
		this.observedStations.add(s);
	}
	
	public void unsuscribe(Station s){
		s.deleteObserver(this);
		observedStations.remove(s);
		//// ligne à rajouter dans Timestamp treatedPatients.add(s);
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
	
	
	public void update(Station s) {//, Object arg) {
		//Station s = (Station) o;
		////String result= (String) arg;
		this.recieveMessage(new Message("The Station "+s.getName()+" is now "+s.getStatus()));

	}
	
	
	
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
	}
	public User() {
		super();
	}
	
	
	
	
	/**
	 * adds an event to the user's history. Also checks that the events are entered in chronological order.
	 * @param t
	 * @param ps
	 */
	public void updateUserHistory(Timestamp t, UserAction ua){
		if(!userHistory.isEmpty()&& userHistory.lastKey().compareTo(t)>0){
			System.out.println("Error, do not enter a time in the past.");
		}
		else{
			userHistory.put(t,ua);
			System.out.println("The user's history is updated: the user "+ua.toString()+" the bicycle "+ bicycle.toString() + "at time "+t.toString());
		}
	}
	
	
			//bleu 1� /heure-> m�canique 2�/h-> �lectrique
			//velibr 0� premi�re heure et 1� le reste -> m�canique; 1� 1ere h 2� les autres h.->electrique
			//vmax 0�premi�re h 1� pour les heures suivantes.
	/**
	 * cette classe compute le temps final. C'est � dire que tripTime est le temps moins le cr�dit en temps.
	 * On facture par heures enti�res.
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
	
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", card="
				+ card + "]";
	}

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
					System.out.println("Go put your bicycle at slot "+ i);
					//On gère l'exception pour des questions de compilation
					
					try {
						s.getParkingSlots().get(i).addBicycle(this.getBicycle(),t);
						}
					catch(UnavailableSlotException e) {System.out.println("no electrical: "  + e.toString());
					}
				
					// On signale à la station qu'on a rendu un vélo.	
					s.addEntryToStationHistory(t);
					
					//On met du crédit si c'est une plus station
					if (s.getStationType()==Station.StationType.Plus) {
						this.getCard().creditTime();
					}
					 
					//We compute the duration of the trip in ms.
					Duration duration = Duration.ZERO;
					duration.plusMillis(t.getTime()-this.getUserHistory().lastKey().getTime());
					//We update the user's history
					this.updateUserHistory(t, UserAction.dropped_off);
					this.setBicycle(null);
					s.setNumberOfReturns(s.getNumberOfReturns()+1);
					//We compute the cost of the trip
					try {
					Double cost = this.visit((BlueCard) this.card, duration, this.getBicycle().getType());
					System.out.println("You have to pay" + cost + "euros.");
					}
					catch(Exception e) {System.out.println("no blueCard: "  + e.toString());
					}					
					
					// TODO Stocker ce coût dans Trip pour le retrouver ou bien dans Card
					// TODO Faire sortir l'utilisateur des observateurs.
					//this.unsuscribe(s);
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
	public void rentBikeElectrical(Station s, Timestamp t) throws NoMoreElectricalException, AlreadyHasABikeException {
		if (s.slotsOccupiedByElectrical()==0)
			throw new NoMoreElectricalException(); 
		if (this.getBicycle()!=null)
			throw new AlreadyHasABikeException();
		try {
		int i = s.selectBicycleElectrical();
		//We get the bicycle
				Bicycle bicycle = s.getParkingSlots().get(i).getBicycle();
				// We set free the slot
				s.getParkingSlots().get(i).becomesFree(t);
				this.setBicycle(bicycle);
				// start counter for the user
				this.updateUserHistory(t, UserAction.dropped_on);
				//We need to begin the riding time and put something in the TimeStamp
				s.addEntryToStationHistory(t);
				s.setNumberOfRentals(s.getNumberOfRentals()+1);
		}
		catch(Station.NoMoreElectricalException e){System.out.println("no electrical: "  + e.toString());
		}	
	}
	
	/**
	 * This function allows the User to drop on a mechanical bicycle.
	 * @param u
	 * @param s
	 * @throws NoMoreMechanicalException 
	 */
	public void rentBikeMechanical(Station s, Timestamp t) throws NoMoreMechanicalException, AlreadyHasABikeException {
		if (s.slotsOccupiedByMechanical()==0)
			throw new NoMoreMechanicalException();
		if (this.getBicycle()!=null)
			throw new AlreadyHasABikeException();
		
		try {
			int i = s.selectBicycleMechanical();
			//We get the bicycle
					Bicycle bicycle = s.getParkingSlots().get(i).getBicycle();
					// We set free the slot
					s.getParkingSlots().get(i).becomesFree(t);
					this.setBicycle(bicycle);
					// start counter for the user
					this.updateUserHistory(t, UserAction.dropped_on);
					//We need to begin the riding time and put something in the TimeStamp
					s.addEntryToStationHistory(t);
					s.setNumberOfRentals(s.getNumberOfRentals()+1);
			}
			catch(Station.NoMoreMechanicalException e){System.out.println("no mechanical: "  + e.toString());
			}
	}
	
	/** 
	 * This function tells the User in which slot he should take his electrical bicycle.
	 * @param s
	 * @return i
	 * i is the parking slot where the user can take the bicycle.
	 */
	public class NoMoreElectricalException extends Exception{
		public NoMoreElectricalException(){
		    System.out.println("Sorry, no more electrical bikes available.");
		  }  
	}
	
	public class NoMoreMechanicalException extends Exception{
		public NoMoreMechanicalException(){
		    System.out.println("Sorry, no more mechanical bikes available.");
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
	
	public void userBalance() {
		
	}
	
	public void planRide(GPS destination,  boolean plus, boolean uniformity, boolean fastest) {
		if (this.plannedRide == null) {
			plannedRide = new PlannedRide(this.network, this.position, destination, plus, uniformity, fastest, false);
			System.out.println("We are finding the best path");
		}
		else {
			plannedRide = new PlannedRide(this.network, this.position, destination, plus, uniformity, fastest, true);
			System.out.println("You haven't reached your destination yet. We are looking for a new path.");
		}


		
	}
	public void deleteCurrentRide() {
		if (plannedRide==null) {
			System.out.println("There is no planned ride.");
		}
		else {
			this.setPlannedRide(null);
			System.out.println("Your ride has been deleted.");
		}
	}
}