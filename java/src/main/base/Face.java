package src.main.base;

import java.awt.*;
import java.util.*;

/**
 * Created by David on 4/4/2015.
 */
public class Face {

	private Node[] globalNodes;
	private ArrayList<Node> camNodes;


	private Color color;

	private boolean visibleEdges = false;

	public Face(Node[] globalNodes) {

		this.globalNodes = globalNodes;
		camNodes = new ArrayList<>();
		color = Color.GRAY;
	}

	public Face(Node n1, Node n2, Node n3) {

		this.globalNodes = new Node[] {n1, n2, n3};
		color = Color.GRAY;
	}

	public void setEdgesVisible(boolean edgesVisible) {

		visibleEdges = edgesVisible;
	}

	public void setColor(Color c) {

		this.color = c;
	}

//	public boolean isInCameraView(Camera cam) {
//
//
//	}

	public Node getCenter() {

		double minX = globalNodes[0].getX(), maxX = globalNodes[0].getX(), minY = globalNodes[0].getY(), maxY = globalNodes[0].getY(), minZ = globalNodes[0].getZ(), maxZ = globalNodes[0].getZ();

		for (Node n : globalNodes) {

			if(n.getX() > maxX)
				maxX = n.getX();
			if(n.getY() > maxY)
				maxY = n.getY();
			if(n.getZ() > maxZ)
				maxZ = n.getZ();

			if(n.getX() < minX)
				minX = n.getX();
			if(n.getY() < minY)
				minY = n.getY();
			if(n.getZ() < minZ)
				minZ = n.getZ();
		}

		return new Node((minX + maxX) / 2d, (minY + maxY) / 2d, (minZ + maxZ) / 2d);
	}

	public void updateFace(Camera c) {

		camNodes = new ArrayList<>();

		for (Node node : globalNodes) {
			Node camNode = node.getRelativeNode(c);
			camNodes.add((camNode));
		}
	}

	public void renderFace(Camera camera) {

		Graphics2D g = camera.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Polygon poly = new Polygon();

		for (Node node : camNodes) {
			poly.addPoint((int) (node.getX()), (int) (node.getY()));
		}

//		for (Node node : globalNodes) {
//			Node camNode = node.getRelativeNode(camera);
//			poly.addPoint((int) (camNode.getX()), (int) (camNode.getY()));
//		}

		g.setColor(color);
		g.fillPolygon(poly);
		g.setColor(Color.BLACK);
		g.drawPolygon(poly);

//		if(visibleEdges) {
//			g.setColor(Color.BLACK);
//			g.drawPolygon(poly);
//		}
	}

}
