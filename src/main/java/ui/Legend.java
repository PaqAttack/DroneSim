package ui;

import java.awt.Font;
import java.awt.Graphics;

import main.Graph;
import main.Node;
import main.Simulator;
import mapItems.Drone;

public class Legend {

	private static final int START_X = 25;
	private static final int START_Y = 75;

	private static final int PLOT_TITLE_HEIGHT = 24;
	private static final int PLOT_ENTRY_HEIGHT = 16;
	private static final int PLOT_TITLE_TOP = 500;
	private static final int PLOT_LEGEND_TOP = PLOT_TITLE_TOP + 10;

	private static final int LEGEND_WIDTH = 225;
	private static final int LEGEND_HEIGHT = 60;
	private static final int LEGEND_GAP = 20;

	private static Node indPoint1 = new Node(START_X + 5, PLOT_LEGEND_TOP, null);
	private static Node txtPoint1 = new Node(START_X + 25, PLOT_LEGEND_TOP + 10, null);

	private static Node indPoint2 = new Node(START_X + 5, PLOT_LEGEND_TOP + 20, null);
	private static Node txtPoint2 = new Node(START_X + 25, PLOT_LEGEND_TOP + 30, null);

	private static Node indPoint3 = new Node(START_X + 5, PLOT_LEGEND_TOP + 40, null);
	private static Node txtPoint3 = new Node(START_X + 25, PLOT_LEGEND_TOP + 50, null);

	public Legend() {
		// *pin drops*
	}

	public static void update() {
		// *pin drops*
	}

	public static void render(Graphics g) {
		drawDrones(g, START_Y);
		drawPlots(g);
		drawMapSIze(g);
	}

	private static void drawMapSIze(Graphics g) {
		Node tp = new Node(630, 30, null);
		Node bp = new Node(630, 50, null);

		g.setColor(Simulator.getGraphColor());
		g.setFont(new Font("Times New Roman", Font.BOLD, 16));

//		g.drawRect(START_X, PLOT_ENTRY_HEIGHT, LEGEND_WIDTH, LEGEND_HEIGHT);

		g.drawString("Map Height: " + Simulator.getGridLengthInMiles() + " miles", tp.getX(), tp.getY());
		g.drawString("Map Width:  " + Simulator.getGridLengthInMiles() + " miles", bp.getX(), bp.getY());
	}

	private static void drawDrones(Graphics g, int top) {
		g.setColor(Simulator.getGraphColor());

		if (!Drone.getDrones().isEmpty()) {

			for (int i = 0; i < Math.min(Drone.getDrones().size(), 4); i++) {
				g.setColor(Drone.getDrones().get(i).getColor());
				g.drawRect(START_X, top + ((LEGEND_HEIGHT + LEGEND_GAP) * i), LEGEND_WIDTH, LEGEND_HEIGHT);

				g.setColor(Simulator.getGraphColor());
				g.setFont(new Font("Times New Roman", Font.BOLD, 16));
				g.drawString(Drone.getDrones().get(i).getName(), START_X + 10,
						top + 20 + ((LEGEND_HEIGHT + LEGEND_GAP) * i));

				g.setFont(new Font("Times New Roman", Font.ITALIC, 12));
				g.drawString(Drone.getDrones().get(i).getStudentName(), START_X + 10,
						top + 50 + ((LEGEND_HEIGHT + LEGEND_GAP) * i));

				g.setColor(Drone.getDrones().get(i).getColor());

				if (i < 3) {
					
					g.drawString(
							"Position:       X: " + Graph.screenXtoGraphX(Drone.getDrones().get(i).getPosX()) + ", Y: "
									+ Graph.screenYtoGraphY(Drone.getDrones().get(i).getPosY()),
							START_X + 100, top + 35 + ((LEGEND_HEIGHT + LEGEND_GAP) * i));

					if (Drone.getDrones().get(i).getDestination() != null) {
						g.drawString(
								"Destination: X: " + Drone.getDrones().get(i).getDestination().getX() + ", Y: "
										+ Drone.getDrones().get(i).getDestination().getY(),
								START_X + 100, top + 50 + ((LEGEND_HEIGHT + LEGEND_GAP) * i));
					} else {
						g.drawString("Destination: N/A", START_X + 100, top + 50 + ((LEGEND_HEIGHT + LEGEND_GAP) * i));
					}

				}
			}

		}
	}

	private static void drawPlots(Graphics g) {
		// PLOT HEADER
		g.setColor(Simulator.getGraphColor());
		g.setFont(new Font("Times New Roman", Font.PLAIN, PLOT_TITLE_HEIGHT));
		g.drawString("PLOTS:", START_X, PLOT_TITLE_TOP);

		// PLOT A LEGEND
		g.setColor(Simulator.getPlotAColor());
		g.drawRect(indPoint1.getX(), indPoint1.getY(), 10, 10);
		g.fillRect(indPoint1.getX(), indPoint1.getY(), 10, 10);

		g.setColor(Simulator.getGraphColor());
		g.setFont(new Font("Times New Roman", Font.PLAIN, PLOT_ENTRY_HEIGHT));
		g.drawString(":  " + Simulator.getPlotAName(), txtPoint1.getX(), txtPoint1.getY());

		// PLOT A LEGEND
		g.setColor(Simulator.getPlotBColor());
		g.drawRect(indPoint2.getX(), indPoint2.getY(), 10, 10);
		g.fillRect(indPoint2.getX(), indPoint2.getY(), 10, 10);

		g.setColor(Simulator.getGraphColor());
		g.setFont(new Font("Times New Roman", Font.PLAIN, PLOT_ENTRY_HEIGHT));
		g.drawString(":  " + Simulator.getPlotBName(), txtPoint2.getX(), txtPoint2.getY());

		// OBSTACLE LEGEND
		g.setColor(Simulator.getObsColor());
		g.drawRect(indPoint3.getX(), indPoint3.getY(), 10, 10);
		g.fillRect(indPoint3.getX(), indPoint3.getY(), 10, 10);

		g.setColor(Simulator.getGraphColor());
		g.setFont(new Font("Times New Roman", Font.PLAIN, PLOT_ENTRY_HEIGHT));
		g.drawString(":  Obstacle", txtPoint3.getX(), txtPoint3.getY());

	}

}
