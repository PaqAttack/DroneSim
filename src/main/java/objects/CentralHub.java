package objects;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import main.Graph;
import main.Node;
import mapItems.CommInterface;
import mapItems.DataType;
import mapItems.Drone;
import mapItems.Message;
import studentDrones.ChrisDrone;

public class CentralHub implements CommInterface {

	private boolean active = false;

	// Simulation specific variables
	private ArrayList<Node> fires = new ArrayList<>();
	private ArrayList<Node> people = new ArrayList<>();
	private ArrayList<Drone> chrisDrones = new ArrayList<>();
	private ArrayList<Node> peopleSaved = new ArrayList<>();

	// Home main.Node
	private static Node home = new Node(4, 4, null);
	
	private boolean richDroneDeployed = false;
	private boolean fireDetected = false;

	private static ArrayList<CentralHub> HUBs = new ArrayList<>();

	public CentralHub() {
		HUBs.add(this);
	}

	public void InitCentralHUB() {
		// Execute this on startup after plots & Drones created
		active = true;
		for (Drone drone : Drone.getDrones()) {
			drone.activate();
		}

		System.out.println("Central HUB directs Fire finder drone to deploy");
		transmit((CommInterface) Drone.getDroneByStudentName("FOSTER"), new Message("START", null, null));

		System.out.println("Central HUB directs Human finder drone to deploy");
		transmit((CommInterface) Drone.getDroneByStudentName("JUDE"), new Message("START", null, null));

	}

	public void update() {
		if (!richDroneDeployed && people.size() > 0) {
			System.out.println("Central HUB directs Camper Check in drone to deploy as " + people.size()
					+ " people have been spotted.");
			transmit((CommInterface) Drone.getDroneByStudentName("RICHARD"),
					new Message("START", DataType._POINT_ARRAY, people));

			richDroneDeployed = true;
		}

		if (fireDetected && people.size() != peopleSaved.size()) {
			ArrayList<Node> goTo = new ArrayList<>();
			Node home = new Node(4, 4, null);

			for (Node p : people) {
				if (!peopleSaved.contains(p)) {
					System.out.println("Deploying HID drone to rescue camper at " + p.getX() + ", " + p.getY() + ".");
					chrisDrones.add(new ChrisDrone(new Point(Graph.graphXtoScreenX(4), Graph.graphYtoScreenY(4)), "HID mapItems.Drone", "Chris", Color.CYAN, true, 30));
					peopleSaved.add(p);
					chrisDrones.get(chrisDrones.size() - 1).addDestinationPoint(p);
					chrisDrones.get(chrisDrones.size() - 1).activate();
				}
			}
		}

	}

	public void transmit(CommInterface reciever, Message msg) {
		reciever.recieve(this, msg);
	}

	public void recieve(CommInterface transmitter, Message msg) {
		// Add newly detected fires to list
		if (msg.getMsg().equalsIgnoreCase("FIRE")) {
			if (!fires.contains((Node) msg.getO())) {
				fires.add((Node) msg.getO());
				fireDetected = true;
			}

		}

		if (msg.getMsg().equalsIgnoreCase("HUMAN")) {
			if (!people.contains((Node) msg.getO())) {
				people.add((Node) msg.getO());

				if (richDroneDeployed) {
					System.out.println("Central retrasnmits new human location to Camper Check in drone. "
							+ people.size() + " people have been spotted total now.");
					transmit((CommInterface) Drone.getDroneByStudentName("RICHARD"),
							new Message("ADD", DataType._POINT, (Node) msg.getO()));
				}
			}

		}

	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public static ArrayList<CentralHub> getHUBs() {
		return HUBs;
	}

	public static Node getHome() {
		return home;
	}

	public static void setHome(Node home) {
		CentralHub.home = home;
	}

	
}
