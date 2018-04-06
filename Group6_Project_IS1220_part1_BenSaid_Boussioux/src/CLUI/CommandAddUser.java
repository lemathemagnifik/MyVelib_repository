package CLUI;

import java.time.Duration;
import java.util.ArrayList;

import myVelib.*;



public class CommandAddUser extends Command {

	public CommandAddUser(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
	}



	@Override
	public void execute() throws SyntaxErrorException {
		User user = null;
		MyVelib myVelib = this.getMyVelib();
		String userName = getArgs().get(0);
		String cardType = getArgs().get(1);
		String velibnetworkName = getArgs().get(2);
		Network network = myVelib.getNetwork(velibnetworkName);
		if (network==null) {
			throw new SyntaxErrorException("Please check the network name.");
		}
		if (cardType.equalsIgnoreCase("Vlibre")){
			 user = new User(userName, new VlibreCard(user, Duration.ZERO), network,myVelib);
		 }
		 else if (cardType.equalsIgnoreCase("VMax")) {
			 user = new User(userName, new VmaxCard(user, Duration.ZERO), network,myVelib);
		 }
		 else if (cardType.equalsIgnoreCase("CreditCard")){
			 user = new User(userName, new CreditCard(user), network,myVelib);
		 }
		 else {
			 throw new SyntaxErrorException("Please check the card type.");
			 }
		 network.addUser(user); ;
		 System.out.println("The user "+userName+" has been added to "+velibnetworkName+".\n");
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(3);
	}
}