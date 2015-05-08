package src.main.base;

import java.util.*;

public abstract class Shape extends Pointer {

	protected ArrayList<Node> globalNodes;
	protected ArrayList<Face> faces;

	public Shape(double x, double y, double z) {
		super(x, y, z, 0, 0, 0);

		globalNodes = new ArrayList<>();
		faces = new ArrayList<>();
	}

	public ArrayList<Face> getFaces() {
		return faces;
	}

	public void setRoll(double radian) {
		shiftRoll(this.getRoll() - radian, globalNodes);
		super.setRoll(radian);
	}

	protected void shiftRoll(double radian, ArrayList<Node> nodes) {
		if(radian > Math.PI * 2d) {
			shiftRoll(radian - Math.PI * 2, nodes);
		}
		else if(radian < 0d) {
			shiftRoll(radian + Math.PI * 2d, nodes);
		}
		else {
			for (Node node : nodes) {
				node.shiftRoll(radian, this);
			}
		}
	}

	public void setYaw(double radian) {
		shiftYaw(this.getYaw() - radian, globalNodes);
		super.setYaw(radian);
	}

	protected void shiftYaw(double radian,  ArrayList<Node> nodes) {

		if(radian > Math.PI * 2d) {
			shiftYaw(radian - Math.PI * 2, nodes);
		}
		else if(radian < 0d) {
			shiftYaw(radian + Math.PI * 2, nodes);
		}
		else {
			for (Node node : nodes) {
				node.shiftYaw(radian, this);
			}
		}
	}

	//x
	public void setPitch(double radian) {
		shiftPitch(this.getPitch() - radian, globalNodes);
		super.setPitch(radian);
	}

	protected void shiftPitch(double radian, ArrayList<Node> nodes) {

		if(radian > Math.PI * 2d) {
			shiftPitch(radian - Math.PI * 2d, nodes);
		}
		else if(radian < 0d) {
			shiftPitch(radian + Math.PI * 2d, nodes);
		}
		else {
			for (Node node : nodes) {
				node.shiftPitch(radian, this);
			}
		}
	}

	protected ArrayList<Face> getCorrectFaceOrder(Camera camera) {

		ArrayList<Face> faceOrder = new ArrayList<>();
		for (Face face : faces) {
			double dist = face.getCenter().getDistance(camera);
			for (int i = 0; i < faceOrder.size(); i++) {
				double d = faceOrder.get(i).getCenter().getDistance(camera);
				if(dist < d) {
					faceOrder.add(i, face);
					break;
				}
			}
			if(!faceOrder.contains(face))
				faceOrder.add(face);
		}

		return faceOrder;
	}
}
