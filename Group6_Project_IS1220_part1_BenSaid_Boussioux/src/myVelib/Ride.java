package myVelib;

import java.sql.Timestamp;


import javafx.util.Duration;

public class Ride {
	
	
	
	/**
	 * This function allows the User to drop Off his bicycle.
	 * @param u
	 * @param s
	 */
	
	public void dropOff(User u, Station s, Timestamp t) {
		if (s.getStatus()==Station.Status.Full || s.getStatus()==Station.Status.Offline)
			System.out.println("Sorry, no more Electrical bicyles available");
		else {
			for (int i=0; i<=s.getParkingSlots().size(); i++) {
				if (s.getParkingSlots().get(i).getStatus() == ParkingSlot.Status.Free)
				{
					System.out.println("Go put your bicycle at slot "+ i);
					s.getParkingSlots().get(i).addBicycle(u.getBicycle());
				// On signale à la station qu'on a rendu un vélo.	
					s.addEntryToStationHistory(t);
					
					//On met du crédit si c'est une plus station
					if (s.getStationType()==Station.StationType.Plus) {
						u.getCard().creditTime();
					}
		
					//On calcule duration of the Trip
					Duration duration = new Duration(t.getTime()-u.getUserHistory().lastKey().getTime());
					
					//TODO Enfin on calcule le coût
					u.visit(u.getCard(), duration, u.getBicycle());

					//TODO Décider à qui appartient dropOff et dropOn
					
					// TODO Stocker ce coût dans Trip pour le retrouver ou bien dans Card
					// TODO Faire sortir l'utilisateur des observateurs.
					u.unsuscribe(s);
				}
			}
		}
	}
	
	
				
	/**
	 * This function allows the User to drop on an electrical bicycle.
	 * @param u
	 * @param s
	 * @throws NoMoreElectricalException 
	 */
	public void dropOnElectrical(Station s, Timestamp t) throws NoMoreElectricalException {
		int i = this.selectBicycleElectrical(s);
		
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

	public int selectBicycleMechanical (Station s) {
		if (s.slotsOccupiedByMechanical() == 0)
				System.out.println("Sorry, no more Mechanical bicyles available");
		else {
			for (int i=0; i<=s.getParkingSlots().size(); i++) {
				if (s.getParkingSlots().get(i).getStatus() == ParkingSlot.Status.OccupiedByMechanical)
				{
					System.out.println("Go take mechanical bicycle at slot "+ i);
					return i;	}}}return 0;}
	
				/**
				 * This function allows the User to drop on an electrical bicycle.
				 * @param u
				 * @param s
				 */
				public void dropOnMechanical(User u, Station s) {
					// Je n'ai pas pris en compte l'exception ici.
					int i = this.selectBicycleMechanical(s);
					
					//We get the bicycle
					Bicycle bicycle = s.getParkingSlots().get(i).getBicycle();
					// We set free the slot
					s.getParkingSlots().get(i).becomesFree();
					
					// start counter
					//We need to begin the riding time and put something in the TimeStamp
					
					}
				}
	
				
				
				
				
				
				
	
				
	
	