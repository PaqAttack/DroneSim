package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Graph {

	private static final int GRAPH_LENGTH_IN_PLOTS = 100; // Number of columns in grid

	private static final int GRAPH_X_START_POSITION = 275; // X offset location for grid displayed position
	private static final int GRAPH_Y_START_POSITION = 75; // Y offset location for grid displayed position
	private static final int GRAPH_DISPLAY_LENGTH = 500; // Width of displayed grid

	private static final int GRAPH_PLOT_LENGTH = GRAPH_DISPLAY_LENGTH / GRAPH_LENGTH_IN_PLOTS;

	// Displayed mapItems.Plot radius in pixels
	public static final int MAP_ITEM_DIAMETER = 7;

	// Distance from center point of square to edges in plot squares excluding
	// center grid box (2 equals a 5x5 square)
	public static final int MAP_OBS_DISPLAY_RANGE = 3;

	// Displayed mapItems.Drone diameter in pixels
	public static final int MAP_DRONE_DIAMETER = 10;

	// Spaces around plots that are declared offlimits to additional
	// plots/Obstacles.
	public static final int MAP_PLOT_SPACE = 4;

	// Determines if lines will show destination of drones with destinations set.
	public static boolean showLine = false;

	// Creates a int array to store plot state information.
	private static int[][] graph = new int[GRAPH_LENGTH_IN_PLOTS][GRAPH_LENGTH_IN_PLOTS];

	// Bounds of the grid on the screen.
	private static Rectangle bounds = new Rectangle(GRAPH_X_START_POSITION, GRAPH_Y_START_POSITION,
			GRAPH_PLOT_LENGTH * GRAPH_LENGTH_IN_PLOTS, GRAPH_LENGTH_IN_PLOTS * GRAPH_PLOT_LENGTH);

	/**
	 * Sets graph ID of a node under the mouse X,Y position to the given graph ID.
	 * 
	 * @param newID  New ID for selected graph coordinates.
	 * @param mouseX X Position of Mouse click.
	 * @param mouseY Y Position of Mouse click.
	 */
	public static void changeGraphID(int newID, int mouseX, int mouseY) {
		if (bounds.contains(mouseX, mouseY)) {
			int gPosX = screenXtoGraphX(mouseX);
			int gPosY = screenYtoGraphY(mouseY);
			graph[gPosX][gPosY] = newID;
		}
	}

	/**
	 * Sets graph ID for a given coordinate position
	 * 
	 * @param newID New ID for selected graph coordinates.
	 * @param x     Grid X Coordinate to be changed.
	 * @param y     Grid Y Coordinate to be changed.
	 */
	public static void changeGraphIDbyCoord(int newID, int x, int y) {
		if (x >= 0 && x < GRAPH_LENGTH_IN_PLOTS) {
			if (y >= 0 && y < GRAPH_LENGTH_IN_PLOTS) {
				graph[x][y] = newID;
			}
		}
	}

	/**
	 * Gets main.Graph ID value from coordinates of grid position
	 * 
	 * @param x X position of grid coordinate.
	 * @param y Y position of grid coordinate.
	 * @return integer representing the graph ID from the grid.
	 */
	public static int getGraphValue(int x, int y) {
		return graph[x][y];
	}

	/**
	 * Converts graph coordinate X to the screen position of the center point of the
	 * tile at grid position X .
	 * 
	 * @param graphCoord An X graph coordinate.
	 * @return The X of the screen center point of the tile represented by the given
	 *         grid X position.
	 */
	public static int graphXtoScreenX(int graphCoord) {
		return GRAPH_X_START_POSITION + (graphCoord * GRAPH_PLOT_LENGTH) + (GRAPH_PLOT_LENGTH / 2);
	}

	/**
	 * Converts graph coordinate Y to the screen position of the center point of the
	 * tile at grid position Y .
	 * 
	 * @param graphCoord An Y graph coordinate.
	 * @return The Y of the screen center point of the tile represented by the given
	 *         grid Y position.
	 */
	public static int graphYtoScreenY(int graphCoord) {
		return GRAPH_Y_START_POSITION + GRAPH_DISPLAY_LENGTH - (graphCoord * GRAPH_PLOT_LENGTH);
	}

	/**
	 * Converts a screen X coordinate to a grid X coordinate.
	 * 
	 * @param screenCoord The screen X position to be converted.
	 * @return The X coordinate within the graph.
	 */
	public static int screenXtoGraphX(int screenCoord) {
		return (screenCoord - GRAPH_X_START_POSITION) / GRAPH_PLOT_LENGTH;
	}

	/**
	 * Converts a screen Y coordinate to a grid Y coordinate.
	 * 
	 * @param screenCoord The screen Y position to be converted.
	 * @return The Y coordinate within the graph.
	 */
	public static int screenYtoGraphY(int screenCoord) {
		return GRAPH_LENGTH_IN_PLOTS - (screenCoord - GRAPH_Y_START_POSITION) / GRAPH_PLOT_LENGTH;
	}

	/**
	 * Sets all main.Graph IDs to 0. As written this code is unnecessary but was created
	 * in case changes required a different initialized ID.
	 */
	public static void initGraph() {
		for (int x = 0; x < GRAPH_LENGTH_IN_PLOTS; x++) {
			for (int y = 0; y < GRAPH_LENGTH_IN_PLOTS; y++) {
				graph[x][y] = 0;
			}
		}
	}

	public static Node getNextPoint(Node start, Node end) {
		List<Node> path = Node.FindPath(start, end);
		for (Node p : path) {
			System.out.println("point X: " + p.getX() + ", Y: " + p.getY());
		}

		if (path != null) {
			return path.get(1);
		}
		return start;
	}

	/**
	 * Draws the Grid on screen
	 * 
	 * @param g Graphics Object
	 */
	public static void renderGraph(Graphics g) {
		// Draw border
		renderBorder(g);

		// Draw Interior
		renderInterior(g);
	}

	/**
	 * Checks if a given coordinate point exists in the grid.
	 * 
	 * @param x X coordinate of point to be checked.
	 * @param y Y coordinate of point to be checked.
	 * @return True/false determination of the existence of the point in question.
	 */
	public static boolean doesExist(int x, int y) {
		if (x >= 0 && x < GRAPH_LENGTH_IN_PLOTS) {
			if (y >= 0 && y < GRAPH_LENGTH_IN_PLOTS) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a given coordinate point exists in the grid.
	 * 
	 * @param point This point will be checked to see if it exists.
	 * @return True/false determination of the existence of the point in question.
	 */
	public static boolean doesExist(Node point) {
		return doesExist(point.getX(), point.getY());
	}

	/**
	 * Draws a 2 pixel wide border for the grid. TODO: Cast to Graphics 2D and
	 * adjust stroke size to reduce draws to 1 rectangle.
	 * 
	 * @param g Graphics Object
	 */
	private static void renderBorder(Graphics g) {
		g.setColor(Simulator.getGraphColor());
		g.drawRect(GRAPH_X_START_POSITION - 1, GRAPH_Y_START_POSITION - 1, (GRAPH_DISPLAY_LENGTH) + 2,
				(GRAPH_DISPLAY_LENGTH) + 2);
		g.drawRect(GRAPH_X_START_POSITION - 2, GRAPH_Y_START_POSITION - 2, (GRAPH_DISPLAY_LENGTH) + 4,
				(GRAPH_DISPLAY_LENGTH) + 4);
	}

	/**
	 * For each node in the grid check its value and draw it based on that. 
	 * 0 = Blank 
	 * 1 = Obstacle 
	 * 2 = mapItems.Plot A
	 * 3 = mapItems.Plot B
	 * 
	 * @param g Graphics Object.
	 */
	private static void renderInterior(Graphics g) {
		for (int x = 0; x < GRAPH_LENGTH_IN_PLOTS; x++) {
			for (int y = 0; y < GRAPH_LENGTH_IN_PLOTS; y++) {
				switch (graph[x][y]) {
				case 0: { // Blank
					g.setColor(Color.BLACK);
					break;
				}
				case 1: { // Obstacle
					g.setColor(Simulator.getObsColor());
					break;
				}
				case 2: { // PLOT Target A
					g.setColor(Color.BLACK);
					break;
				}
				case 3: { // PLOT Target B
					g.setColor(Color.BLACK);
					break;
				}

				}
				g.drawRect(GRAPH_X_START_POSITION + (x * GRAPH_PLOT_LENGTH),
						GRAPH_Y_START_POSITION + (GRAPH_DISPLAY_LENGTH) - (y * GRAPH_PLOT_LENGTH) - 5,
						GRAPH_PLOT_LENGTH, GRAPH_PLOT_LENGTH);

				g.fillRect(GRAPH_X_START_POSITION + (x * GRAPH_PLOT_LENGTH),
						GRAPH_Y_START_POSITION + GRAPH_DISPLAY_LENGTH - (y * GRAPH_PLOT_LENGTH) - 5, GRAPH_PLOT_LENGTH,
						GRAPH_PLOT_LENGTH);

			}
		}
	}

	/**
	 * Get Grid bounds as a Rectangle Object.
	 * 
	 * @return Rectangle Object representing the grid.
	 */
	public static Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Get main.Graph 2D integer array.
	 * 
	 * @return main.Graph 2D integer array.
	 */
	public static int[][] getGraph() {
		return graph;
	}

	/**
	 * Gets the length in pixels of the entire grid. Width and height will always be
	 * the same. Only a square is supported by this program.
	 * 
	 * @return Length in pixels of the entire grid.
	 */
	public static int getGraphDisplayLength() {
		return GRAPH_DISPLAY_LENGTH;
	}

	/**
	 * Gets the X coordinate to display the grid at.
	 * 
	 * @return The X coordinate to display the grid at.
	 */
	public static int getGraphXStartPosition() {
		return GRAPH_X_START_POSITION;
	}

	/**
	 * Gets the Y coordinate to display the grid at.
	 * 
	 * @return The Y coordinate to display the grid at.
	 */
	public static int getGraphYStartPosition() {
		return GRAPH_Y_START_POSITION;
	}

	/**
	 * Gets the Predetermined Map Item diameter in Pixels.
	 * 
	 * @return Map Item diameter in Pixels.
	 */
	public static int getMapItemDiameter() {
		return MAP_ITEM_DIAMETER;
	}

	/**
	 * Gets displayed range in grid plots of each obstacle plot. This value
	 * represents how many plots outward from a selected center plot (not pixel)
	 * will be turned into obstacles.
	 * 
	 * @return Displayed range in grid plots of each obstacle plot.
	 */
	public static int getMapObsDisplayRange() {
		return MAP_OBS_DISPLAY_RANGE;
	}

	/**
	 * Gets the Predetermined drone diameter in pixels.
	 * 
	 * @return Map Item diameter in Pixels.
	 */
	public static int getMapDroneDiameter() {
		return MAP_DRONE_DIAMETER;
	}

	/**
	 * Returns the amount of protected tiles around a mapItems.Plot. This is the number of
	 * tiles that get turned into untouchable tiles on the grid excluding the
	 * centerpoint. A value of 2 would result in a 5/5 square of restricted area
	 * around the plot.
	 * 
	 * @return The amount of protected tiles around a mapItems.Plot.
	 */
	public static int getMapPlotSpace() {
		return MAP_PLOT_SPACE;
	}

	/**
	 * Get the number of plots in a length of the grid
	 * 
	 * @return The number of plots in a length of the grid
	 */
	public static int getGraphLengthInPlots() {
		return GRAPH_LENGTH_IN_PLOTS;
	}

	/**
	 * Get the number of pixels in length of each plot in the graph. Height and
	 * Width will always be equal.
	 * 
	 * @return The number of pixels in length of each plot in the graph.
	 */
	public static int getGraphPlotLength() {
		return GRAPH_PLOT_LENGTH;
	}

}
