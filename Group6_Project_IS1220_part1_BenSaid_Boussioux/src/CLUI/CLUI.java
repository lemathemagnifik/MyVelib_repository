package CLUI;


import java.util.ArrayList;
import java.util.Scanner;

import fr.ecp.is1220.project.core.SimErgy;
import fr.ecp.is1220.project.distributions.WrongParametersException;
import fr.ecp.is1220.project.humanresources.NotReleasedYetException;
import fr.ecp.is1220.project.humanresources.NotVisitedYetException;
import fr.ecp.is1220.project.resources.NotProbability;

public class CLUI {

	private CommandFactory cmdFactory;
	private CmdParser currentCMD;
	private SimErgy simergy;
	
	public CLUI(SimErgy simergy) throws InstantiationException, IllegalAccessException {
		super();
		this.simergy = simergy;
		this.cmdFactory = new CommandFactory(simergy);
		this.currentCMD = new CmdParser();		
	}
	
	public void execute() throws InstantiationException, IllegalAccessException, WrongParametersException, NotProbability, NotReleasedYetException, NotVisitedYetException, BadTimeFormatException {
		Scanner in = new Scanner(System.in);
		String line;
		System.out.println("Welcome to the Emergency Department Simulation System. Enter help to get help ! ;)");
		
		do {
			line = in.nextLine();
		}while(parseLine(line));
		
		in.close();
		System.out.println("Simulation finished.");
	}
	
	public boolean parseLine(String line) throws InstantiationException, IllegalAccessException, WrongParametersException, NotProbability, NotReleasedYetException, NotVisitedYetException, BadTimeFormatException {
		if (line == null) {
			return true; 
		}
		if (line.trim().length() == 0) {
			return true;
		}
		if (line.trim().equals("exit")) {
			return false;
		}
		currentCMD.parse(line);
		try {
			interpret();
		} catch(SyntaxErrorException e) {
			System.out.println(e.getMessage());
		} catch(MisuseException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	private void interpret() 
			throws SyntaxErrorException, MisuseException, InstantiationException, IllegalAccessException, WrongParametersException, NotProbability, NotReleasedYetException, NotVisitedYetException, BadTimeFormatException {
		
		String cmd = currentCMD.getCmd();
		ArrayList<String> args = currentCMD.getArgs();
		
		if(cmd == null) {
			return;
		}
		
		cmdFactory.createCommand(cmd, args).execute();
		
	}

	public CommandFactory getCmdFactory() {
		return cmdFactory;
	}

	public void setCmdFactory(CommandFactory cmdFactory) {
		this.cmdFactory = cmdFactory;
	}

	public CmdParser getCurrentCMD() {
		return currentCMD;
	}

	public void setCurrentCMD(CmdParser currentCMD) {
		this.currentCMD = currentCMD;
	}

	public SimErgy getSimergy() {
		return simergy;
	}

	public void setSimergy(SimErgy simergy) {
		this.simergy = simergy;
	}

}

