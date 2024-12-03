package studentDrones;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import main.Node;
import mapItems.CommInterface;
import mapItems.Drone;
import mapItems.Message;

public class RichardDrone extends Drone implements CommInterface {

	ArrayList<Node> visited;
	ArrayList<Node> unVisited;

	public RichardDrone(Point location, String name, String studentName, Color color, boolean returnHome, int speedMPH) {
		super(location, name, studentName, color, returnHome, speedMPH);

	}

	@Override
	public void transmit(CommInterface reciever, Message msg) {
		reciever.recieve(this, msg);
	}

	@Override
	public void recieve(CommInterface transmitter, Message msg) {
		if (msg.getMsg().equalsIgnoreCase("START")) {
			List<Node> nodes = (List<Node>) msg.getO();
			for (Node p : nodes) {
				addDestinationPoint(p);
			}
			moving = true;
			System.out.println("Camper Check-in mapItems.Drone Deployed to " + nodes.size() + " people.");
		}

		if (msg.getMsg().equalsIgnoreCase("ADD")) {
			Node p = (Node) msg.getO();
			addDestinationPoint(p);

			moving = true;
			System.out.println("Camper Check-in mapItems.Drone added 1 person to destination list.");
		}
	}

	@Override
	public void loop() {

	}

	@Override
	public void activate() {

	}

	@Override
	public void arrived() {

	}

}
