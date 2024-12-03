package mapItems;

import java.util.ArrayList;

import main.Graph;
import main.Simulator;
import objects.TargetPlotB;

public class DroneScannerB {

	private ArrayList<Plot> scannedItemList;
	private double range;
	private Drone drone;

	/**
	 * Creates a drone scanner object that scans for objects.TargetPlotB Objects.
	 * 
	 * @param drone        The drone to attach this scanner to.
	 * @param rangeInMiles The range this scanner can detect TargetPlotBs within.
	 */
	public DroneScannerB(Drone drone, double rangeInMiles) {
		this.range = rangeInMiles;
		this.drone = drone;
		scannedItemList = new ArrayList<>();
	}

	/**
	 * Checks all plots and adds any objects.TargetPlotB plots within range to the
	 * scannedItemList arrayList. The scannedItemList arrayList can be retrieved
	 * with getScannedItemList().
	 * 
	 * @return The number of items that have been detected so far by this scanner.
	 */
	public int scan() {
		for (Plot p : Plot.getPlots()) {
			if (p instanceof TargetPlotB) {
				if (getDistance(p) < range / Simulator.getMilesRepresentedByEachGraphBlock()) {
					if (!scannedItemList.contains(p)) {
						scannedItemList.add(p);
					}
				}
			}
		}
		return scannedItemList.size();
	}

	/**
	 * Gets the distance between the drone this scanner is attached to and the
	 * provided mapItems.Plot as a double.
	 * 
	 * @param p The mapItems.Plot from which the distance to is being calculated.
	 * @return Returns a double representing the distance in pixels between the
	 *         scanner and the provided plot.
	 */
	private double getDistance(Plot p) {
		double distX = Math.abs(p.getLocation().getX() - Graph.screenXtoGraphX(drone.getPosX()));
		double distY = Math.abs(p.getLocation().getY() - Graph.screenYtoGraphY(drone.getPosY()));
		return Math.sqrt(((distX * distX) + (distY * distY)));
	}

	/**
	 * Gets a reference to an ArrayList of all TragetPlotB items scanned by this
	 * scanner.
	 * 
	 * @return Reference to scannedItemList array list.
	 */
	public ArrayList<Plot> getScannedItemList() {
		return scannedItemList;
	}

}
