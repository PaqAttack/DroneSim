package main;

public class StateManager {

	private static State myState = State.PLOT_A_TARGETS;
	private static String message1;
	private static String message2;

	/**
	 * Update message to display based on current simulation state.
	 */
	public static void update() {
		updateMsg();
	}

	/**
	 * Update message to display based on current simulation state.
	 */
	private static void updateMsg() {
		switch (StateManager.getState()) {
		case PLOT_A_TARGETS: {
			message1 = "Click the field to plot targets of type A.";
			message2 = "Press Enter when done.";
			break;
		}
		case PLOT_B_TARGETS: {
			message1 = "Click the field to plot targets of type B.";
			message2 = "Press Enter when done.";
			break;
		}
		case PLOT_OBSTACLES: {
			message1 = "Click the field to plot obstacles.";
			message2 = "Press Enter when done.";
			break;
		}
		case WAITING: {
			message1 = "Press Enter to start.";
			message2 = "";
			break;
		}
		case PLAYING: {
			message1 = "Simulation in progress";
			message2 = "Press Enter to pause.";
			break;
		}
		case PAUSED: {
			message1 = "Simulation paused";
			message2 = "Press Enter to resume.";
			break;
		}
		}
	}

	/**
	 * Returns top line of message to display.
	 * 
	 * @return String top line of message to display.
	 */
	public static String getMessage1() {
		return message1;
	}

	/**
	 * Returns bottom line of message to display.
	 * 
	 * @return String bottom line of message to display.
	 */
	public static String getMessage2() {
		return message2;
	}

	/**
	 * Returns Simulation Current main.State.
	 * 
	 * @return Simulation Current main.State.
	 */
	public static State getState() {
		return myState;
	}

	/**
	 * Sets the simulation state.
	 * 
	 * @param state The state that will be set as new current simulation state.
	 */
	public static void setState(State state) {
		myState = state;
	}

}
