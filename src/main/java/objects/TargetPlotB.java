package objects;

import java.awt.Color;
import java.util.ArrayList;

import main.Graph;
import main.Node;
import main.Simulator;
import mapItems.Plot;

public class TargetPlotB extends Plot {

	private static ArrayList<TargetPlotB> targets = new ArrayList<>();

	public TargetPlotB(Node location, Color color, String name) {
		super(location, color, name);
	}

	@Override
	public void update() {

	}

	@Override
	public void activate() {

	}

	public static void createPlotB(int x, int y) {
		if (Graph.getGraphValue(x, y) == 0) {
			Graph.changeGraphIDbyCoord(3, x, y);
			targets.add(new TargetPlotB(new Node(x, y, null), Simulator.getPlotBColor(), Simulator.getPlotBName()));
			for (int a = x - Graph.getMapPlotSpace(); a <= x + Graph.getMapPlotSpace(); a++) {
				for (int b = y - Graph.getMapPlotSpace(); b <= y + Graph.getMapPlotSpace(); b++) {
					if (Graph.doesExist(a, b)) {
						if (Graph.getGraphValue(a, b) == 0) {
							Graph.changeGraphIDbyCoord(3, a, b);
						}
					}
				}
			}
			targets.clear();
		} else {
			System.out.println("Invalid plot location");
		}

	}

}
