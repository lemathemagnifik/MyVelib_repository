package CLUI;

import java.util.ArrayList;

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

	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
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
	}
}

