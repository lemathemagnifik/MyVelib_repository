package Tests;

import java.time.Duration;
import java.util.ArrayList;

import myVelib.Bicycle.BicycleType;
import myVelib.Station;
import myVelib.Station.NoMoreBikeException;

public class Test2 {
	public Test2() throws NoMoreAvailableSlotsException {
		throw new NoMoreAvailableSlotsException();
	}


	public class NoMoreAvailableSlotsException extends Exception{
		public NoMoreAvailableSlotsException(){
		    System.out.println("Sorry, this station has no more available slots.");
		  }  
	}
	

	
	public static void main(String[] args) {
		try {
			Test2 t = new Test2();
		} catch (NoMoreAvailableSlotsException e) {

		}
	}

}

