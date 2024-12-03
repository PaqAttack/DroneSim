package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Graph;
import main.State;
import main.StateManager;
import objects.CentralHub;
import objects.ObstaclePlot;
import objects.TargetPlotA;
import objects.TargetPlotB;

public class InputManager implements MouseListener, MouseMotionListener, KeyListener {

	private CentralHub centralHub;

	/**
	 * Functions as a HUB for all player input. Used due to a low amount of inputs
	 * monitored. If too many get added then this would need to split up.
	 * 
	 * @param centralHub to give a reference for the central HUB. Used to check for
	 *                   active status
	 */
	public InputManager(CentralHub centralHub) {
		this.centralHub = centralHub;
	}

	/*
	 * On mouse press this method plots items on the map
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (Graph.getBounds().contains(e.getX(), e.getY())) {

			// Based on the simulation state different plots will be created.
			switch (StateManager.getState()) {
			case PLOT_A_TARGETS: {
				TargetPlotA.createPlotA(Graph.screenXtoGraphX(e.getX()), Graph.screenYtoGraphY(e.getY()));
				break;
			}
			case PLOT_B_TARGETS: {
				TargetPlotB.createPlotB(Graph.screenXtoGraphX(e.getX()), Graph.screenYtoGraphY(e.getY()));
				break;
			}
			case PLOT_OBSTACLES: {
				ObstaclePlot.createObstacle(Graph.screenXtoGraphX(e.getX()), Graph.screenYtoGraphY(e.getY()));
				break;
			}
			case WAITING: {

				break;
			}
			case PLAYING: {

				break;
			}
			case PAUSED: {

				break;
			}
			}
		}

	}

	/*
	 * On enter press this method changes the game state.
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			// This will swap the state of the game based on the current state.
			switch (StateManager.getState()) {
			case PLOT_A_TARGETS: {
				StateManager.setState(State.PLOT_B_TARGETS);
				break;
			}
			case PLOT_B_TARGETS: {
				StateManager.setState(State.PLOT_OBSTACLES);
				break;
			}
			case PLOT_OBSTACLES: {
				StateManager.setState(State.WAITING);
				break;
			}
			case WAITING: {
				StateManager.setState(State.PLAYING);
				centralHub.InitCentralHUB();
				break;
			}
			case PLAYING: {
				StateManager.setState(State.PAUSED);
				centralHub.setActive(false);
				break;
			}
			case PAUSED: {
				StateManager.setState(State.PLAYING);
				centralHub.setActive(true);
				break;
			}
			}
		}

		// The L key toggles a green line that shows the destination of each drone when
		// it has a destination.
		if (e.getKeyCode() == KeyEvent.VK_L) {
			Graph.showLine = !Graph.showLine;
		}

	}

	/*
	 * ================ UNUSED METHODS ================
	 */

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
