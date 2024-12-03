package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
	// Holds node main.Graph Coordinates
	private Point location;

	// Holds the parent node for pathfinding.
	public Node parent;

	/**
	 * Creates a node at column x and row y.
	 * 
	 * @param x      column to create node in.
	 * @param y      row to create node in.
	 * @param parent node that contains parent node for pathfinding
	 */
	public Node(int x, int y, Node parent) {
		location = new Point(x, y);
		this.parent = parent;
	}

	/**
	 * Implements equals functionality for nodes.
	 * 
	 * @Override
	 */
	public boolean equals(Object o) {
		Node point;
		if (o != null) {
			point = (Node) o;
		} else {
			return false;
		}
		
		return (int) location.getX() == (int) point.location.getX() && (int) location.getY() == (int) point.location.getY();
	}

	/**
	 * Implements toString functionality for nodes.
	 * 
	 * @Override
	 */
	public String toString() {
		return "X: " + location.getX() + ", Y: " + location.getY();
	}

	/**
	 * Implements hashCode functionality for nodes.
	 * 
	 * @Override
	 */
	public int hashCode() {
		return Objects.hash(location.getX(), location.getY());
	}

	/**
	 * Returns custom offset node.
	 * 
	 * @param ox offset along X axis.
	 * @param oy offset along Y axis.
	 * @return New node at offset location.
	 */
	public Node offset(int ox, int oy) {
		return new Node((int) (location.getX() + ox), (int) (location.getY() + oy), this);
	}

	/**
	 * Determines if a node is passable for pathfinding.
	 * 
	 * @param node main.Node to be checked
	 * @return true/False if provided node is passable.
	 */
	public static boolean IsWalkable(Node node) {
		int[][] map = Graph.getGraph();

		if (Graph.doesExist(node)) {
			return map[(int) node.location.getX()][(int) node.location.getY()] != 1;
		} else {
			return false;
		}
	}

	/**
	 * Checks left right up and down to determine neighbors of the provided node.
	 * 
	 * @param node provided node that will be the source of all identified
	 *             neighbors.
	 * @return ArrayList of Nodes representing valid neighbors of the provided node.
	 */
	public static List<Node> FindNeighbors(Node node) {
		List<Node> neighbors = new ArrayList<>();
		if (Graph.doesExist(node.offset(0, 1))) {
			Node up = node.offset(0, 1);
			if (IsWalkable(up))
				neighbors.add(up);
		}
		if (Graph.doesExist(node.offset(0, -1))) {
			Node down = node.offset(0, -1);
			if (IsWalkable(down))
				neighbors.add(down);
		}
		if (Graph.doesExist(node.offset(-1, 0))) {
			Node left = node.offset(-1, 0);
			if (IsWalkable(left))
				neighbors.add(left);
		}
		if (Graph.doesExist(node.offset(1, 0))) {
			Node right = node.offset(1, 0);
			if (IsWalkable(right))
				neighbors.add(right);
		}
		return neighbors;
	}

	/**
	 * Pathfinding method based on BFS to determine a path between two points. This
	 * method will return a null list if no path has been found after all graph
	 * elements have bee checked. This should be replaced with A* or at least DFS
	 * pathfinding when the chance arises.
	 * 
	 * @param start node to start pathfinding at.
	 * @param end   node to end pathfinding at.
	 * @return List of nodes that lead from start to end point.
	 */
	public static ArrayList<Node> FindPath(Node start, Node end) {
		boolean finished = false;

		if (start.equals(end)) {
			return null;
		}
		
		// list of points that have been reviewed
		List<Node> used = new ArrayList<>();
		used.add(start);

		// Main Loop
		while (!finished) {
			List<Node> newOpen = new ArrayList<>();
			// For each node thats been added to the list get all the valid neighbors
			// On the first pass this is just the start node.
			for (int i = 0; i < used.size(); ++i) {
				Node point = used.get(i);

				// for each point thats been used find their neighbors
				// If the neighbor hasn't been checked already (by being in used array) or
				// already added to newOpen then add them to newOpen.
				for (Node neighbor : FindNeighbors(point)) {
					if (!used.contains(neighbor) && !newOpen.contains(neighbor)) {
						newOpen.add(neighbor);
					}
				}
			}

			// go through the newly opened nodes and move them to the checked nodes (in used
			// array)
			for (Node point : newOpen) {
				used.add(point);

				// If any of them are the end point then we are done.
				if (end.equals(point)) {
					finished = true;
					break;
				}
			}

			// If the end hasn't been found and everything has been checked (moved to used
			// array) then no path exists.
			if (!finished && newOpen.isEmpty()) {
				System.out.println("No Path Possible");
				return null;
			}
		}

		ArrayList<Node> path = new ArrayList<>();
		// The last added node on the path will be the end point
		Node point = used.get(used.size() - 1);

		// Adds the used nodes to the path at the beginning of the list.
		// This results in path starting at start node and ending at end node.
		while (point.parent != null) {
			path.add(0, point);
			point = point.parent;
		}
		path.add(end);
		return path;
	}

	/**
	 * Get X coordinate of this node's location on the grid
	 * 
	 * @return X coordinate of this node's location on the grid
	 */
	public int getX() {
		return (int) location.getX();
	}

	/**
	 * Get Y coordinate of this node's location on the grid
	 * 
	 * @return Y coordinate of this node's location on the grid
	 */
	public int getY() {
		return (int) location.getY();
	}

}
