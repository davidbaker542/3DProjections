package src.main.base;


import java.awt.*;
import java.util.*;

/**
 * Created by David on 4/3/2015.
 */
public class Cube extends Shape {

	private double length, width, height;

	private Color nodeColor = Color.PINK;

	public Cube(double x, double y, double z, double l, double w, double h) {

		super(x, y, z);

		this.length = l;
		this.width = w;
		this.height = h;

		Node[] nodes = new Node[8];

		nodes[0] = new Node(this.getX() - (length / 2d), this.getY() - (width / 2d), this.getZ() - (height / 2d));
		nodes[1] = new Node(this.getX() - (length / 2d), this.getY() - (width / 2d), this.getZ() + (height / 2d));
		nodes[2] = new Node(this.getX() - (length / 2d), this.getY() + (width / 2d), this.getZ() - (height / 2d));
		nodes[3] = new Node(this.getX() - (length / 2d), this.getY() + (width / 2d), this.getZ() + (height / 2d));
		nodes[4] = new Node(this.getX() + (length / 2d), this.getY() - (width / 2d), this.getZ() - (height / 2d));
		nodes[5] = new Node(this.getX() + (length / 2d), this.getY() - (width / 2d), this.getZ() + (height / 2d));
		nodes[6] = new Node(this.getX() + (length / 2d), this.getY() + (width / 2d), this.getZ() - (height / 2d));
		nodes[7] = new Node(this.getX() + (length / 2d), this.getY() + (width / 2d), this.getZ() + (height / 2d));

		globalNodes.addAll(Arrays.asList(nodes));

		Face f1a = new Face(nodes[2], nodes[6], nodes[4]);
		Face f1b = new Face(nodes[2], nodes[0], nodes[4]);

		//Face f1 = new Face(new Node[] { nodes[2], nodes[6], nodes[4], nodes[0] });
		Face f2 = new Face(new Node[] { nodes[1], nodes[5], nodes[4], nodes[0] });
		Face f3 = new Face(new Node[] { nodes[1], nodes[5], nodes[7], nodes[3] });
		Face f4 = new Face(new Node[] { nodes[2], nodes[3], nodes[7], nodes[6] });
		Face f5 = new Face(new Node[] { nodes[4], nodes[5], nodes[7], nodes[6] });
		Face f6 = new Face(new Node[] { nodes[0], nodes[1], nodes[3], nodes[2] });

		f1a.setColor(Color.MAGENTA);
		f1b.setColor(Color.MAGENTA);

		f2.setColor(Color.RED);
		f3.setColor(Color.BLUE);
		f4.setColor(Color.ORANGE);
		f5.setColor(Color.GREEN);
		f6.setColor(Color.CYAN);

		f1a.setEdgesVisible(true);
		f1b.setEdgesVisible(true);

		f2.setEdgesVisible(true);
		f3.setEdgesVisible(true);
		f4.setEdgesVisible(true);
		f5.setEdgesVisible(true);
		f6.setEdgesVisible(true);

		faces.add(f1a);
		faces.add(f1b);

		faces.add(f2);
		faces.add(f3);
		faces.add(f4);
		faces.add(f5);
		faces.add(f6);
	}

	public void render(Camera camera) {

		Graphics2D g2 = camera.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//Find Approximate order of faces to render
		ArrayList<Face> faceOrder = getCorrectFaceOrder(camera);

		//The stack is backwards, go through and render each face in correct order.
		for (int i = faceOrder.size() - 1; i >= 0; i--) {
			Face face = faceOrder.get(i);
			face.renderFace(camera);
		}

		//Draw the camera nodes and their XYZ position.
		for (Node global : globalNodes) {
			Node camNode = global.getRelativeNode(camera);

			g2.setColor(nodeColor);
			g2.fillOval((int) (camNode.getX() - 4d), (int) (camNode.getY() - 4), 8, 8);
			g2.setColor(Color.WHITE);

			double xr = Math.round(camNode.getX() * 1000) / 1000;
			double yr = Math.round(camNode.getY() * 1000) / 1000;
			double zr = Math.round(camNode.getZ() * 1000) / 1000;

			g2.drawString("Relative - X: " + xr  + ", Y: " + yr + ", Z: " + zr, (int) (camNode.getX() - 4d), (int) (camNode.getY() - 4));
		}
	}

}