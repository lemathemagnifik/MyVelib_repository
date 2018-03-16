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



public class User implements CardVisitor, Observer{
	

	public enum UserAction {dropped_on, dropped_off}; 
	
	/**A map representing a user's history: with Timestamps as keys and UserStates as values.
	 * This type of map stores the key-value pairs in a specific order. This way it is easy to get the last user's state.
	 * 
	 */
	private ConcurrentSkipListMap <Timestamp, UserAction> userHistory = new ConcurrentSkipListMap<Timestamp, User.UserAction>();
	private GPS position;
	private GPS destination;
	protected Id id;
	private Card card;
	private String name;
	static int IDuserCounter=0;
	final static double walkingSpeed = 4;


	private ArrayList<Message> messageBox;
	private ArrayList<Station> DestinationStation;
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
	public void setPosition(GPS position) {
		this.position = position;
	}
	
	public void setPosition(GPS departure, GPS arrival) {
		this.position = arrival;
		
	}
	
	public GPS getDestination() {
		return destination;
	}
	public void setDestination(GPS destination) {
		this.destination = destination;
	}
	public Id getId() {
		return id;
	}
	
	/**
	 * TODO
	 * @param id
	 */
	public void setId(Id id) {
		this.id = id;
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
	
	public void destinationStation(Station s){
		s.addObserver(this);
		DestinationStation.add(s);
	}
	
	public void unsuscribe(Station s){
		s.deleteObserver(this);
		DestinationStation.remove(s);
		//// ligne à rajouter dans Timestamp treatedPatients.add(s);
	}
	

	public void recieveMessage(Message m){
		messageBox.add(m);
	}
	public void displayMessage(Message m){
		m.setRead(true);
	}
	public void removeMessage(Message m){
		messageBox.remove(m);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Station s = (Station) o;
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
	
	public double computeCharge(){
		double charge = 0;
		//TODO Attention il faut enlever le crédit sur le temps avant de visiter.
		return charge;
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
	
	public void dropOff(Station s, Timestamp t) throws UnavailableStationException {
		
		if (s.getStatus()==Station.Status.Full || s.getStatus()==Station.Status.Offline)
			throw new UnavailableStationException();
		
		else {
			for (int i=0; i<s.getParkingSlots().size(); i++) {
				if (s.getParkingSlots().get(i).getStatus() == ParkingSlot.Status.Free)
				{
					System.out.println("Go put your bicycle at slot "+ i);
					//On gère l'exception pour des questions de compilation
					
					try {
						s.getParkingSlots().get(i).addBicycle(this.getBicycle());
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
	public void dropOnElectrical(Station s, Timestamp t) throws NoMoreElectricalException, AlreadyHasABikeException {
		if (s.slotsOccupiedByElectrical()==0)
			throw new NoMoreElectricalException(); 
		if (this.getBicycle()!=null)
			throw new AlreadyHasABikeException();
		int i = this.selectBicycleElectrical(s);
		
		//We get the bicycle
		Bicycle bicycle = s.getParkingSlots().get(i).getBicycle();
		// We set free the slot
		s.getParkingSlots().get(i).becomesFree();
		this.setBicycle(bicycle);
		// start counter for the user
		this.updateUserHistory(t, UserAction.dropped_on);
		//We need to begin the riding time and put something in the TimeStamp
		s.addEntryToStationHistory(t);
		
	}
	
	/**
	 * This function allows the User to drop on a mechanical bicycle.
	 * @param u
	 * @param s
	 * @throws NoMoreMechanicalException 
	 */
	public void dropOnMechanical(Station s, Timestamp t) throws NoMoreMechanicalException, AlreadyHasABikeException {
		if (s.slotsOccupiedByMechanical()==0)
			throw new NoMoreMechanicalException();
		if (this.getBicycle()!=null)
			throw new AlreadyHasABikeException();
		
		int i = this.selectBicycleMechanical(s);
		
		//We get the bicycle
		Bicycle bicycle = s.getParkingSlots().get(i).getBicycle();
		// We set free the slot
		s.getParkingSlots().get(i).becomesFree();
		this.setBicycle(bicycle);
		// start counter
		//We need to begin the riding time and put something in the TimeStamp
		s.addEntryToStationHistory(t);
		
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
		    System.out.println("Sorry, this stations is unavailable to drop off your bicycle.");
		  }  
	}
	
	
	public int selectBicycleElectrical  (Station s) throws NoMoreElectricalException{

		if (s.slotsOccupiedByElectrical() == 0){
				throw new NoMoreElectricalException();}
		else {
			for (int i=0; i<=s.getParkingSlots().size(); i++) {
				if (s.getParkingSlots().get(i).getStatus() == ParkingSlot.Status.OccupiedByElectrical)
				{
					System.out.println("Go take electrical bicycle at slot "+ i);
					return i;
					
				}
			}
		}
		return 0;//for debugging purposes
		
	}
	
	/** 
	 * This function tells the User in which slot he should take his mechanical bicycle.
	 * @param s
	 * @return i
	 * i is the parking slot where the user can take the bicycle.
	 */

	public int selectBicycleMechanical (Station s) throws NoMoreMechanicalException{
		if (s.slotsOccupiedByMechanical() == 0)
				throw new NoMoreMechanicalException(); 
		else {
			for (int i=0; i<=s.getParkingSlots().size(); i++) {
				if (s.getParkingSlots().get(i).getStatus() == ParkingSlot.Status.OccupiedByMechanical)
				{
					System.out.println("Go take mechanical bicycle at slot "+ i);
					return i;	}}}return 0;}
	
	
	
	

}
