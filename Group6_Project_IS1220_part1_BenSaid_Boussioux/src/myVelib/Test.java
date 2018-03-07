package myVelib;

public class Test {
	public static void main(String[] args) {
		Network myNetwork= new Network();
		for (Integer i=1 ; i<=10; i++) {
			for (Integer j=1; i<=10; j++) {
				Station.StationType stationtype = Station.StationType.Normal; 
				if (i%2==0){stationtype= Station.StationType.Plus;}
				myNetwork.addStation(new Station(i.toString()+j.toString(), stationtype, new GPS(i,j), myNetwork ));
			}
		}
		
		
		System.out.println(myNetwork.getStations());
	}
}
