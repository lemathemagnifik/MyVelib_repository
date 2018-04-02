package CLUI;

import fr.ecp.is1220.project.core.SimErgy;
import fr.ecp.is1220.project.distributions.WrongParametersException;
import fr.ecp.is1220.project.humanresources.NotReleasedYetException;
import fr.ecp.is1220.project.humanresources.NotVisitedYetException;
import fr.ecp.is1220.project.resources.NotProbability;

public class CLUITest {
	
	public static
	void main(String[] args) throws InstantiationException, IllegalAccessException, WrongParametersException, NotProbability, NotReleasedYetException, NotVisitedYetException, BadTimeFormatException {
		SimErgy mySimErgy = new SimErgy();
		CLUI myCLUI = new CLUI(mySimErgy);
		myCLUI.execute();
	}

}

