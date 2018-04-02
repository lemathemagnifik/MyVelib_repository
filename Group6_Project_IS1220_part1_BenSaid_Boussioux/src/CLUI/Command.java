package CLUI;

import java.util.ArrayList;

import fr.ecp.is1220.project.core.SimErgy;
import fr.ecp.is1220.project.distributions.WrongParametersException;
import fr.ecp.is1220.project.humanresources.NotReleasedYetException;
import fr.ecp.is1220.project.humanresources.NotVisitedYetException;
import fr.ecp.is1220.project.resources.NotProbability;

public abstract class Command {

	private SimErgy simergy;
	private ArrayList<String> args = new ArrayList<String>();
	
	public Command(SimErgy simergy, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super();
		this.simergy = simergy;
		this.args = args;
		check();
	}

	public abstract void execute() throws MisuseException, WrongParametersException, InstantiationException, IllegalAccessException, SyntaxErrorException, NotProbability, NotReleasedYetException, NotVisitedYetException, BadTimeFormatException;	
	public abstract void check() throws SyntaxErrorException;
	public void checkNumOfArgs(int num) throws SyntaxErrorException{
		if(args.size() != num) {
			throw new SyntaxErrorException("This cmd requires "+num+" arguments.");
		}
	}

	public SimErgy getSimergy() {
		return simergy;
	}

	public void setSimergy(SimErgy simergy) {
		this.simergy = simergy;
	}

	public ArrayList<String> getArgs() {
		return args;
	}

	public void setArgs(ArrayList<String> args) {
		this.args = args;
	}
	
}

