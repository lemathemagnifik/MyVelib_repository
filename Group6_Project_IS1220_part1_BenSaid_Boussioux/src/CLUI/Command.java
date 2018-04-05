package CLUI;

import java.util.ArrayList;

import javax.sql.rowset.spi.SyncFactoryException;

import myVelib.*;
import myVelib.Station.NoBikesAvailableException;
import myVelib.Station.OfflineStationException;
import myVelib.User.AlreadyHasABikeException;



public abstract class Command {

	private MyVelib myVelib;
	private ArrayList<String> args = new ArrayList<String>();
	
	public Command(MyVelib myVelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super();
		this.myVelib = myVelib;
		this.args = args;
		check();
	}

	public abstract void execute() throws SyntaxErrorException, MisuseException;	
	
	public abstract void check() throws SyntaxErrorException;
	
	public void checkNumOfArgs(int num) throws SyntaxErrorException{
		if(args.size() != num) {
			throw new SyntaxErrorException("This cmd requires "+num+" arguments.");
		}
	}
	
	public void checkNumOfArgs(int num, int num2) throws SyntaxErrorException{
		if(args.size() != num || args.size() != num2) {
			throw new SyntaxErrorException("This cmd requires "+num+" or " + num2+ " arguments.");
		}
	}

	public static int stringToInt(String str, String arg) throws SyncFactoryException {
		try {
			return Integer.parseInt(str); 
		}
		catch(NumberFormatException e) {
			throw new SyncFactoryException("The argument "+arg+ " should be an integer.");
		}
			
		
	}
	
	public static double stringToDouble(String str, String arg) throws SyncFactoryException {
		try {
			return Double.parseDouble(str); 
		}
		catch(NumberFormatException e) {
			throw new SyncFactoryException("The argument "+arg+ " should be a double.");
		}
			
		
	}

	public MyVelib getMyVelib() {
		return myVelib;
	}

	public void setMyVelib(MyVelib myVelib) {
		this.myVelib = myVelib;
	}

	public ArrayList<String> getArgs() {
		return args;
	}

	public void setArgs(ArrayList<String> args) {
		this.args = args;
	}

	public static void main(String[] args) {
		
		try {
			System.out.println(stringToDouble("A", "bla"));
		} catch (SyncFactoryException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
}

