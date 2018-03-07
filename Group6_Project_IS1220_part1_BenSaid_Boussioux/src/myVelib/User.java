package myVelib;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentSkipListMap;

import MyVelib.Bicycle.BicycleType;



public class User implements CardVisitor, Observer{
	public User() {
		super();
	}

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
	
	
	
	
	
	/** Observers functions
	 * 
	 * @param s
	 */
	
	public void destinationStation(Station s){
		s.addObserver(this);
		DestinationStation.add(s);
	}
	
	public void dropOff(Station s){
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
	public User(Id id, Card card, String name, ArrayList<Message> messageBox) {
		super();
		this.id = id;
		this.card = card;
		this.name = name;
		this.messageBox = messageBox;
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
			return  (tripTime.toHours()+1)*2;
		}
		if (type==Bicycle.BicycleType.Mechanical){
			return tripTime.toHours()+1;
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
	public double visit(VmaxCard vmaxCard,  Duration tripTime) {
		long numberOfHours= tripTime.toHours();
		
		return numberOfHours;
	}
	

		
	

}
