package CLUI;

import java.util.ArrayList;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import myVelib.*;

public class CommandPlanRide extends Command {

	public CommandPlanRide(MyVelib myVelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myVelib, args);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws SyntaxErrorException, MisuseException {
		MyVelib myVelib=getMyVelib();
		myVelib.printCurrentTime();
		User user = myVelib.getUser(stringToInt(getArgs().get(0), "UserID"));
		if (user==null) {
			throw new SyntaxException("Please check argument UserID.");
		}
		GPS destination = new GPS(stringToDouble(getArgs().get(1), "x"), stringToDouble(getArgs().get(2), "y"));
		boolean fastest=false;
		boolean uniformity =false;
		boolean plus=false;
		
		String strPreference = getArgs().get(3);
		if (strPreference.equalsIgnoreCase("FastestPath")) {
			fastest=true;
		}
		else if (strPreference.equalsIgnoreCase("ShortestPath")) {
			fastest=false;
		}
		else throw  new SyntaxException("Please check the pathType argument.");

		if (getArgs().size()==5) {
			String option = getArgs().get(4);
			if (option.equalsIgnoreCase("Plus")) {
				plus=true;
			}
			else if (option.equalsIgnoreCase("Uniformity")){
				uniformity=true;
			}
			else throw  new SyntaxException("Please check the trip preference argument.");
		}
		else if(getArgs().size()==6) {
			String strUniformity =  getArgs().get(4);
			String strPlus =  getArgs().get(5);
			if (strUniformity.equalsIgnoreCase("Uniformity")) {
				uniformity=true;
			}
			else throw  new SyntaxException("Please check the argument uniformity.");
			if (strPlus.equalsIgnoreCase("Plus")) {
				plus=true;
			} 
			else  throw  new SyntaxException("Please check the argument plus.");
		}
		user.planRide(destination, plus, uniformity, fastest);

	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(5, 6);

	}

}
