package mapItems;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Graph;
import main.Node;
import main.Simulator;

public abstract class Plot {
	private Node location;
	private Color color;
	private String name;

	// List holding reference to all Plots that are in the game.
	private static ArrayList<Plot> plots = new ArrayList<>();

	/**
	 * Created a plot on the grid.
	 * 
	 * @param node  main.Node represents the location on the grid.
	 * @param color Color that will represent the plot on the grid.
	 * @param name  name of the plot item.
	 */
	protected Plot(Node node, Color color, String name) {
		super();
		this.location = node;
		this.color = color;
		this.name = name;

		// Stores reference to the new plot in the static plots list.
		plots.add(this);
	}

	/**
	 * This method is available if needed.
	 */
	public abstract void activate();

	/**
	 * This is called every update and allows support for looping behavior.
	 */
	public abstract void update();

	/**
	 * Renders mapItems.Plot on the grid.
	 * 
	 * @param g Graphics Object used to render graphics.
	 */
	public void render(Graphics g) {
		g.setColor(color);
		g.drawOval(Graph.graphXtoScreenX(location.getX()) - (Graph.getMapItemDiameter() / 2),
				Graph.graphYtoScreenY(location.getY()) - (Graph.getMapItemDiameter() / 2), Graph.getMapItemDiameter(),
				Graph.getMapItemDiameter());
		g.fillOval(Graph.graphXtoScreenX(location.getX()) - (Graph.getMapItemDiameter() / 2),
				Graph.graphYtoScreenY(location.getY()) - (Graph.getMapItemDiameter() / 2), Graph.getMapItemDiameter(),
				Graph.getMapItemDiameter());

		g.setColor(Simulator.getGraphColor());
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		g.drawString(name, Graph.graphXtoScreenX(location.getX()) - (g.getFontMetrics().stringWidth(name) / 2),
				Graph.graphYtoScreenY(location.getY()) + Graph.getMapItemDiameter() + 15);
	}

	/**
	 * Removes the sole reference to this plot and effectively deletes it from
	 * existence.
	 */
	public void remove() {
		plots.remove(this);
	}

	/**
	 * Gets a reference to a plot based on its location on the grid.
	 * 
	 * @param p main.Node to compare position with.
	 * @return Located plot or null if no plot is at the provided main.Node's location.
	 */
	public static Plot getPlotByLocation(Node p) {
		for (Plot plot : plots) {
			if (plot.getLocation().equals(p)) {
				return plot;
			}
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		Plot plot = (Plot) obj;
		if (this.getLocation().equals(plot.getLocation())) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the location (as a node) for the current plot.
	 * 
	 * @return Location as a main.Node.
	 */
	public Node getLocation() {
		return location;
	}

	/**
	 * Sets the location on the plot.
	 * 
	 * @param node Provided node gives location data to this plot.
	 */
	public void setNode(Node node) {
		this.location = node;
	}

	/**
	 * Static method to get the arrayList of all Plots.
	 * 
	 * @return arrayList containing all mapItems.Plot references.
	 */
	public static ArrayList<Plot> getPlots() {
		return plots;
	}

}
