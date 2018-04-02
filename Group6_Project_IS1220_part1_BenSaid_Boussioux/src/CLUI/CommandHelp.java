package CLUI;

import java.util.ArrayList;

import fr.ecp.is1220.project.core.SimErgy;
import fr.ecp.is1220.project.distributions.WrongParametersException;
import fr.ecp.is1220.project.humanresources.NotReleasedYetException;
import fr.ecp.is1220.project.humanresources.NotVisitedYetException;
import fr.ecp.is1220.project.resources.NotProbability;

public class CommandHelp extends Command {

	public CommandHelp(SimErgy simergy, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(simergy, args);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute()
			throws MisuseException, WrongParametersException, InstantiationException, IllegalAccessException,
			SyntaxErrorException, NotProbability, NotReleasedYetException, NotVisitedYetException {
		String help = "Commands available:\n";
		
		help += "\n- createED <EDname>:\n";
		help += "create an Emergency Department system with assigned name.\n";
		
		help += "\n- addRoom <RoomType> <RoomName> (<RoomSize>):\n";
		help += "add one room in ED system. Room types available:\n";
		help += "Box- Shock- BloodTest- MRI- Radio- WaitingExam- WaitingRoom\n";
		help += "(If you choose WaitingExamRoom or WaitingRoom, you need also to precise its size.)\n";
		
		help += "\n- generateRooms <RoomType> <RoomNumber> (<RoomSize>):\n";
		help += "generate a certain number of rooms in ED system. Room types available:\n";
		help += "Box- Shock- BloodTest- MRI- Radio- WaitingExam- WaitingRoom\n";
		help += "(If you choose WaitingExamRoom or WaitingRoom, you need also to precise its size).\n";
		
		help += "\n- addBloodTest <DistributionType> <DistributionParameter1> (<DistributionParameter2>):\n";
		help += "choose the distribution of the duration of a bloodtest.\n";
		help += "Distribution types:\n";
		help += "Dirac: 1 parameter, double a>0\n";
		help += "Exp: 1 parameter, double lambda>0\n";
		help += "Gamma: 2 parameters, integer alpha>0, double beta>0\n";
		help += "LogNorm: 2 parameters, double mean, double sigma>0\n";
		help += "Normal: 2 parameters, double mean, double sigma>0\n";
		help += "Poisson: 1 parameter, alpha>0\n";
		help += "Uniform: 2 parameters, double a, double b, a<b\n";
		
		help += "\n- addMRI <DistributionType> <DistributionParameter1> (<DistributionParameter2>):\n";
		help += "choose the distribution of the duration of a MRI test.\n";
		
		help += "\n- addXRay <DistributionType> <DistributionParameter1> (<DistributionParameter2>):\n";
		help += "choose the distribution of the duration of a X-ray test.\n";
		
		help += "\n- addL1Arrival <DistributionType> <DistributionParameter1> (<DistributionParameter2>):\n";
		help += "choose the distribution of L1-priority arrivals.\n";
		
		help += "\n- addL2Arrival <DistributionType> <DistributionParameter1> (<DistributionParameter2>):\n";
		help += "choose the distribution of L2-priority arrivals.\n";
		
		help += "\n- addL3Arrival <DistributionType> <DistributionParameter1> (<DistributionParameter2>):\n";
		help += "choose the distribution of L3-priority arrivals.\n";
		
		help += "\n- addL4Arrival <DistributionType> <DistributionParameter1> (<DistributionParameter2>):\n";
		help += "choose the distribution of L4-priority arrivals.\n";
	
		help += "\n- addL5Arrival <DistributionType> <DistributionParameter1> (<DistributionParameter2>):\n";
		help += "choose the distribution of L5-priority arrivals.\n";
		
		help += "\n- addVisit <DistributionType> <DistributionParameter1> (<DistributionParameter2>):\n";
		help += "choose the distribution of the duration of a visit.\n";
		
		help += "\n- addVisitverdict <no_exam_proportion> <x_ray_proportion> <blood_test_proportion>:\n";
		help += "choose the visit's verdict proportion (no exam, X-ray, bloodtest, MRI needed or not).\n";
		help += "The 3 parameters should be of type double and respect no_exam_proportion+x_ray_proportion+blood_test_proportion<1.\n";
		
		help += "\n- addNurse <NurseName> <NurseSurname> <NurseUserName>:\n";
		help += "add one nurse with a name, surname and username.\n";
		
		help += "\n- generateNurses <Number>:\n";
		help += "add a certain number of nurses.\n";
		
		help += "\n- addPhysis <PhysisName> <PhysisSurname> <PhysisUserName>:\n";
		help += "add one physician with a name, surname and username.\n";
		
		help += "\n- generatePhysis <Number>:\n";
		help += "add a certain number of physicians.\n";
		
		help += "\n- addTransporter <TransporterName> <TransporterSurname> <TransporterUserName>:\n";
		help += "add one transporter with a name and surname.\n";
		
		help += "\n- generateTransporters <Number>:\n";
		help += "add a certain number of transporters.\n";
		
		help += "\n- execute <hour> <minute>:\n";
		help += "execute the simulation of the system up to a certain time. We must have 0<a<24 and 0<b<60.\n";
		
		help += "\n- display :\n";
		help += "display the number of released and visited patients of the simulation.\n";
		
		help += "\n- help:\n";
		help += "get help with commands.\n";
		
	    System.out.println(help);
		
	}

	@Override
	public void check() throws SyntaxErrorException {
		// TODO Auto-generated method stub

	}

}

