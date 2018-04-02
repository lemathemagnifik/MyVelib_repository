package CLUI;

import java.util.ArrayList;

import fr.ecp.is1220.project.core.SimErgy;

public class CommandFactory {
	
	private SimErgy simergy;
	
	public CommandFactory(SimErgy simergy) {
		super();
		this.simergy = simergy;
	}

	public Command createCommand(String cmd, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		
		if(cmd == null) {
			return null;
		}
		
		if(cmd.equalsIgnoreCase("help")) {
			return new CommandHelp(simergy, args);
		}
		
		if(cmd.equalsIgnoreCase("createED")) {
			return new CommandCreateED(simergy, args);
		}
		
		
		else if(cmd.equalsIgnoreCase("addRoom")) {
			return new CommandAddRoom(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("generateRooms")) {
			return new CommandGenerateRooms(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("addNurse")) {
			return new CommandAddNurse(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("generateNurses")) {
			return new CommandGenerateNurses(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("addPhysi")) {
			return new CommandAddPhysi(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("generatePhysis")) {
			return new CommandGeneratePhysis(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("addTransporter")){
			return new CommandAddTransporter(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("GenerateTransporters")) {
			return new CommandGenerateTransporters(simergy, args);
		}
		
		
		else if (cmd.equalsIgnoreCase("addMRI")) {
			return new CommandAddMRI(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("addBloodTest")) {
			return new CommandAddBloodTest(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("addXRay")) {
			return new CommandAddXRay(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("addVisit")) {
			return new CommandAddVisit(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("addVisitVerdict")) {
			return new CommandAddVisitVerdict(simergy, args);
		}
		
		
		else if(cmd.equalsIgnoreCase("addL1Arrival")) {
			return new CommandAddL1Arrival(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("addL2Arrival")) {
			return new CommandAddL2Arrival(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("addL3Arrival")) {
			return new CommandAddL3Arrival(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("addL4Arrival")) {
			return new CommandAddL4Arrival(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("addL5Arrival")) {
			return new CommandAddL5Arrival(simergy, args);
		}
		
		else if(cmd.equalsIgnoreCase("execute")) {
			return new CommandExecute(simergy, args);
		}
		else if(cmd.equalsIgnoreCase("display")) {
			return new CommandDisplay(simergy, args);
		}
		
		throw new SyntaxErrorException("No such command.");
	}

	public SimErgy getSimergy() {
		return simergy;
	}

	public void setSimergy(SimErgy simergy) {
		this.simergy = simergy;
	}
	
}

