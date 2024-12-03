package objects;

import java.awt.Color;
import java.util.ArrayList;

import main.Graph;
import main.Node;
import main.Simulator;
import mapItems.Plot;

public class TargetPlotA extends Plot {

	private static ArrayList<TargetPlotA> targets = new ArrayList<>();

	public TargetPlotA(Node location, Color color, String name) {
		super(location, color, name);
	}

	@Override
	public void update() {

	}

	@Override
	public void activate() {

	}

	public static void createPlotA(int x, int y) {
		if (Graph.getGraphValue(x, y) == 0) {
			Graph.changeGraphIDbyCoord(2, x, y);
			targets.add(new TargetPlotA(new Node(x, y, null), Simulator.getPlotAColor(), Simulator.getPlotAName()));
			for (int a = x - Graph.getMapPlotSpace(); a <= x + Graph.getMapPlotSpace(); a++) {
				for (int b = y - Graph.getMapPlotSpace(); b <= y + Graph.getMapPlotSpace(); b++) {
					if (Graph.doesExist(a, b)) {
						if (Graph.getGraphValue(a, b) == 0) {
							Graph.changeGraphIDbyCoord(2, a, b);
						}
					}
				}
			}
			targets.clear();
		} else {
			System.out.println("Invalid location for plot.");
		}

	}

}
