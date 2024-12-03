package mapItems;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import main.Graph;
import main.Node;
import main.Simulator;
import objects.CentralHub;

public abstract class Drone {

	// This stores a reference to all drones all the time.
	private static ArrayList<Drone> drones = new ArrayList<>();

	// Current location of the drone
	private Point location;

	// Given name of the drone
	private String name;

	// Student's name who designed the drone.
	private String studentName;

	// Color to display the drone with
	private Color color;

	// Can be used to block the drone from moving
	protected boolean moving;

	// Speed of drone in MPH. Variable speed not supported
	private int speedMPH;

	// Movement variables
	private double lastCount = 0;
	private double secPerSpot;
	private int pathLoc = 0;

	// Sets if the drone should always return home after travel.
	private boolean returnHome;

	// Current Destination main.Node
	protected Node destination = null;

	// List of nodes from current location to next destination point.
	private ArrayList<Node> path = new ArrayList<>();

	// Nodes to Travel to.
	private Stack<Node> destinationPoints = new Stack<>();

	/**
	 * Constructor for all student created drones.
	 * 
	 * @param location    Point starting position for drone.
	 * @param name        mapItems.Drone name to be displayed in legend.
	 * @param studentName name of student who created the drone.
	 * @param color       color of drone to be displayed.
	 * @param returnHome  sets behavior to always return home when done traveling if
	 *                    this is true.
	 * @param speedMPH    Speed of the drone in Miles Per Hour.
	 */
	protected Drone(Point location, String name, String studentName, Color color, boolean returnHome, int speedMPH) {
		this.location = location;
		this.name = name;
		this.studentName = studentName;
		this.color = color;
		this.returnHome = returnHome;
		this.speedMPH = speedMPH;

		moving = false;
		drones.add(this);

		calculateMoveSpeed();
	}

	/**
	 * Visible loop feature available to students. This is called every update.
	 */
	public abstract void loop();

	/**
	 * Called each time the drone arrives at a waypoint
	 */
	public abstract void arrived();

	/**
	 * Called every update, this method handles movement for drones. This is meant
	 * to be invisible to students.
	 * 
	 * @param elapsedSec Number of seconds that have progressed in the simulation.
	 */
	public void update(int elapsedSec) {

		// If there is no destination set but Waypoints exist then move a
		// destination from Waypoints to the set destination
		if (destination == null && destinationPoints.size() > 0) {
			destination = destinationPoints.pop();
		}

		// If there is a destination but no path then make a path
		if (destination != null && (path == null || path.isEmpty())) {

			// Get path to follow to set destination (which is the next waypoint.)
			path = Node.FindPath(getNodeFromPoint(location), destination);

			// since the path was just set we reset the counter to 0. When the difference
			// between lastcount and elapsedSec is > calculated time needed to reach next
			// point on the path (secPerSpot) the drone will jump to the next path point.
			lastCount = elapsedSec;

			// Represents the next index to move to on the path list.
			pathLoc = 0;
		}

		// if a path exists and you are not at the last step then attempt to progress
		if (path != null && path.size() > 0 && pathLoc < path.size() - 1) {

			// if its been more than secPerSpot
			if (elapsedSec - lastCount > Math.round(secPerSpot)) {

				// If its been longer than secPerSpot move to next pathLoc.
				location.move(Graph.graphXtoScreenX(path.get(pathLoc).getX()),
						Graph.graphYtoScreenY(path.get(pathLoc).getY()));

				// if pathLoc is not at the last index then move to the next index of path
				if (pathLoc + 1 < path.size()) {
					pathLoc++;

					// preserving the double ensures the actual movement speed is as close as
					// possible to intended.
					// TODO: A LERP process would be better. Consider adding it later.
					lastCount += secPerSpot;
				}

			}
		}

		// If you have reached your destination then clear the path.
		if (getNodeFromPoint(location).equals(destination)) {
			if (path != null) {
				path.clear();
			}

			// Set path index variable to 0.
			pathLoc = 0;

			// nulls the destination so a new waypoint gets added from the stack if
			// available next pass.
			destination = null;

			// Call the arrived function to allow the drones to perform any step at each
			// waypoint.
			arrived();
		}

	}

	/**
	 * Converts a Point to a main.Node for use in comparing or displaying desired data.
	 * Point Contains screen location data and resulting main.Node will contain graph
	 * references.
	 * 
	 * @param point Supplies the X, Y position to be used to make a new main.Node
	 * @return A main.Node with the graph X, Y corresponding to the screen X, Y of the
	 *         supplied Point.
	 */
	private Node getNodeFromPoint(Point point) {
		return new Node(Graph.screenXtoGraphX((int) point.getX()), Graph.screenYtoGraphY((int) point.getY()), null);
	}

	/**
	 * This method is called upon drone activation. Activation is normally expected
	 * to come from the Central HUB but this is not required.
	 */
	public abstract void activate();

	/**
	 * Adds a destination point to the waypoint stack. If the returnHome boolean is
	 * true then a home point will always be the final waypoint.
	 * 
	 * @param destination main.Node to be added as a waypoint.
	 */
	public void addDestinationPoint(Node destination) {
		if (destinationPoints.size() == 0 && returnHome) {
			destinationPoints.push(CentralHub.getHome());
		}
		destinationPoints.push(destination);
	}

	/**
	 * Renders the drone on the grid and displays the student name below it.
	 * location point is screen position so no conversion is necessary.
	 * 
	 * @param g Graphics Object
	 */
	public void render(Graphics g) {
		g.setColor(color);
		g.drawOval((int) location.getX() - (Graph.getMapDroneDiameter() / 2),
				(int) location.getY() - (Graph.getMapDroneDiameter() / 2), Graph.getMapDroneDiameter(),
				Graph.getMapDroneDiameter());
		g.fillOval((int) location.getX() - (Graph.getMapDroneDiameter() / 2),
				(int) location.getY() - (Graph.getMapDroneDiameter() / 2), Graph.getMapDroneDiameter(),
				Graph.getMapDroneDiameter());

		g.setColor(Simulator.getGraphColor());
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		g.drawString(getStudentName(), (int) location.getX() - (g.getFontMetrics().stringWidth(getStudentName()) / 2),
				(int) location.getY() + Graph.getMapDroneDiameter() + 15);

		if (destination != null && Graph.showLine) {
			g.setColor(Color.GREEN);
			g.drawLine((int) location.getX(), (int) location.getY(), Graph.graphXtoScreenX(destination.getX()),
					Graph.graphYtoScreenY(destination.getY()));
		}
	}

	/**
	 * Calculates Seconds that must pass to move from one main.Node to the next based on
	 * timescale, maps length in miles and drone's speed.
	 */
	private void calculateMoveSpeed() {
		double feetPerMile = 5280;
		double inchesPerMile = feetPerMile * 12;

		double inchesTotal = inchesPerMile * Simulator.getGridLengthInMiles();
		double inchesPerBlock = inchesTotal / Graph.getGraphLengthInPlots();

		double feetPerHour = speedMPH * feetPerMile;
		double inchesPerHour = feetPerHour * 12;
		double inchesPerMin = inchesPerHour / 60;
		double inchesPerSec = inchesPerMin / 60;

		secPerSpot = inchesPerBlock / inchesPerSec;
	}

	/**
	 * Gets the screen X position of the drone converted to an integer.
	 * 
	 * @return Integer screen X position of the drone.
	 */
	public int getPosX() {
		return (int) Math.round(location.getX());
	}

	/**
	 * Gets the screen Y position of the drone converted to an integer.
	 * 
	 * @return Integer screen Y position of the drone.
	 */
	public int getPosY() {
		return (int) Math.round(location.getY());
	}

	/**
	 * Gets the name of the drone.
	 * 
	 * @return The name of the drone as a string.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the name of the student who designed the drone.
	 * 
	 * @return The name of the student who designed the drone.
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * Gets the set color of the drone.
	 * 
	 * @return The set color of the drone.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Gets a reference to the static drone arrayList
	 * 
	 * @return Reference to the static arrayList containing all created drones.
	 */
	public static ArrayList<Drone> getDrones() {
		return drones;
	}

	/**
	 * Gets the calculated seconds of travel required per main.Node on the grid.
	 * 
	 * @return calculated seconds of travel required per main.Node on the grid.
	 */
	public double getSecPerSpot() {
		return secPerSpot;
	}

	/**
	 * Searches the static drones arrayList for a drone created by the provided
	 * student name. If no drone is found a null reference will be returned.
	 * 
	 * @param name non-case sensitive name of student whose drone we want to find.
	 * @return The first located drone with a student name matching the provided
	 *         name.
	 */
	public static Drone getDroneByStudentName(String name) {
		for (Drone d : drones) {
			if (d.getStudentName().equalsIgnoreCase(name)) {
				return d;
			}
		}
		return null;
	}

	/**
	 * gets the currently set destination for this drone.
	 * 
	 * @return main.Node representing the waypoint the drone is currently traveling
	 *         toward.
	 */
	public Node getDestination() {
		return destination;
	}

}
