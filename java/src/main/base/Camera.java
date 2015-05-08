package src.main.base;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

/**
 * Created by 15bakerd on 4/6/2015.
 */
public class Camera extends Pointer {

	private BufferedImage render;

	private Graphics2D graphics;

	private AffineTransform reset;

	World world;

	private ArrayList<Face> orderedFaces;

	private double viewWidth, viewHeight;
	private double focalLength;

	public Camera(World w, double x, double y, double z,  double yaw, double pitch, double roll, double viewWidth, double viewHeight, double f) {
		super(x, y, z, yaw, pitch, roll);

		this.world = w;

		orderedFaces = (ArrayList<Face>) world.getFaces().clone();

		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
		focalLength = f;

		render = new BufferedImage((int) viewWidth, (int) viewHeight, BufferedImage.TYPE_INT_ARGB);

		graphics = render.createGraphics();
		reset = graphics.getTransform();
	}

	private void correctFaceOrder() {

		orderedFaces.clear();

		for (Face face : world.getFaces()) {
			double dist = face.getCenter().getDistance(this);
			for (int i = 0; i < orderedFaces.size(); i++) {
				double d = orderedFaces.get(i).getCenter().getDistance(this);
				if(dist < d) {
					orderedFaces.add(i, face);
					break;
				}
			}
			if(!orderedFaces.contains(face))
				orderedFaces.add(face);
		}

		Collections.reverse(orderedFaces);
	}

	public void drawRender(Graphics g) {

		for (Face face : world.getFaces())
			face.updateFace(this);

		correctFaceOrder();

		graphics.setTransform((AffineTransform) reset.clone());
		graphics.clearRect(0, 0, (int) viewWidth, (int) viewHeight);
		graphics.translate(-(x - (viewWidth / 2)), -(y - (viewHeight / 2)));

		for (Face f : orderedFaces)
			f.renderFace(this);

		g.drawImage(render, 0, 0, null);
	}

	public Graphics2D getGraphics() {
		return graphics;
	}

	public void setFocalLength(double focalLength) {

		this.focalLength = focalLength;
	}

	public double getFocalLength() {
		return focalLength;
	}

	public Camera clone() {
		return new Camera(world, x, y, z, yaw, pitch, roll, viewWidth, viewHeight, focalLength);
	}
}
