package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.InputManager;

public class Screen extends JPanel{

	private static final long serialVersionUID = 1L;

	// main.Screen Size
	private Dimension size;
	
	// reference to main.Simulator Instance
	private Simulator simulator;
		
	/**
	 * Constructs screen
	 * @param simulator provides object with reference to simulator (Main class)
	 */
	public Screen(Simulator simulator) {
		this.simulator = simulator;
		setPanelSize();
	}
	
	/**
	 * Initializes screen by adding input listeners and setting focus.
	 * @param inputManager Reference to the input manager which implements listener interfaces.
	 */
	public void initScreen(InputManager inputManager) {
		addMouseListener(inputManager);
		addMouseMotionListener(inputManager);
		addKeyListener(inputManager);
		
		requestFocus();
	}
	
	/**
	 * Sets panel size.
	 */
	private void setPanelSize() {
		size = new Dimension(800, 600);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	/**
	 * Starts the chain of drawing everything.
	 * Called automatically by JPanel.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		simulator.render(g);
	}
	
}
