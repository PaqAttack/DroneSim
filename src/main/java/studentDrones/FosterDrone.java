package studentDrones;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import main.Node;
import mapItems.CommInterface;
import mapItems.DataType;
import mapItems.Drone;
import mapItems.DroneScannerA;
import mapItems.Message;
import mapItems.Plot;
import objects.CentralHub;

public class FosterDrone  extends Drone implements CommInterface{

	DroneScannerA scanner;
	Node fire;
	ArrayList<Plot> ignoreList;
	
	public FosterDrone(Point location, String name, String studentName, Color color, boolean returnHome, int speedMPH) {
		super(location, name, studentName, color, returnHome, speedMPH);
		ignoreList = new ArrayList<>();
		scanner = new DroneScannerA(this, 1);
	}

	@Override
	public void transmit(CommInterface reciever, Message msg) {
		reciever.recieve(this, msg);
	}

	@Override
	public void recieve(CommInterface transmitter, Message msg) {
		if (msg.getMsg().equalsIgnoreCase("START")) {
			List<Node> destPoints = new ArrayList<>();
			destPoints.add(new Node(20, 80, null));
			destPoints.add(new Node(40, 80, null));
			destPoints.add(new Node(40, 20, null));
			destPoints.add(new Node(60, 20, null));
			destPoints.add(new Node(60, 80, null));
			destPoints.add(new Node(80, 80, null));
			destPoints.add(new Node(80, 20, null));
			
			for (Node n : destPoints) {
				addDestinationPoint(n);
			}
			
			moving = true;
			System.out.println("Fire Finder mapItems.Drone Deployed.");
		}
	}

	@Override
	public void loop() {
		if (scanner.scan() > 0) {
			//found fire
			for (Plot p : scanner.getScannedItemList()) {
				if (!ignoreList.contains(p)) {
					transmit(CentralHub.getHUBs().get(0), new Message("Fire", DataType._POINT, p.getLocation()));
					ignoreList.add(p);
					System.out.println("Fire finder drone sends detected fire location to central HUB.");
				}
			}
			
		}
	}

	@Override
	public void activate() {
		
	}

	@Override
	public void arrived() {
		// TODO Auto-generated method stub
		
	}




}
