package src.main.base;

public class Node {

	protected double x, y, z;

	public Node(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getDistance(Node node) {



		return Math.sqrt(Math.pow(x - node.x, 2d) + Math.pow(y - node.y, 2d) + Math.pow(z - node.z, 2d));
	}

	/**
	 * Finds the Unit Vector counter part of the node's rotation.
	 *
	 * @return Direction Vector
	 */
	public Vector getVectorFromNode(Node n) {

		double dx = n.x - this.x;
		double dy = n.y - this.y;
		double dz = n.z - this.z;

		return new Vector(dx, dy, dz);
	}


	public Node clone() {

		return new Node(x,y,z);
	}

	public Node getRelativeNode(Camera cam) {


		Vector john = this.getVectorFromNode(cam);

		Vector camZ = cam.getUnitVectorZ();
		Vector camY = cam.getUnitVectorY();
		Vector camX = camZ.crossProduct(camY);

		Vector phil = camZ.scaleVector(john.dotProduct(camZ));
		Vector bob =  john.subtractVector(phil);

		Vector vectorDY = camY.scaleVector(bob.dotProduct(camY));
		Vector vectorDX = bob.subtractVector(vectorDY);

		double distX = vectorDX.getMagnitude();
		double distY = vectorDY.getMagnitude();

		if(vectorDY.dotProduct(camY) < 0) {
			distY *= -1;
		}

		if(vectorDX.dotProduct(camX) < 0) {
			distX *= -1;
		}

		double xP = /*distX;*/cam.getFocalLength() * (distX / phil.getMagnitude());
		double yP = /*distY;*/cam.getFocalLength() * (distY / phil.getMagnitude());

		return new Node(x - xP, y - yP, z);


//		double dx = (x - cam.getX());
//		double dy = (y - cam.getY());
//		double dz = (z - cam.getZ());
//
//
//		double newX = cam.getFocalLength() * (dx / dz);
//		double newY = cam.getFocalLength() * (dy / dz);
//
//
//
//		Node newNode = new Node(x - newX, y - newY, z);

//	    return newNode;


	}

	public void setNode(Node set) {
		this.x = set.x;
		this.y = set.y;
		this.z = set.z;
	}


	public void shiftYaw(double radian, Node centerPoint) {

		double sin = Math.sin(radian);
		double cos = Math.cos(radian);

		double x = this.getX() - centerPoint.getX();
		double z = this.getZ() - centerPoint.getZ();

		this.setX(centerPoint.getX() + (x * cos - z * sin));
		this.setZ(centerPoint.getZ() + (z * cos + x * sin));
	}

	public void shiftPitch(double radian, Node centerPoint) {

		double sin = Math.sin(radian);
		double cos = Math.cos(radian);

		double y = this.getY() - centerPoint.getY();
		double z = this.getZ() - centerPoint.getZ();

		this.setY(centerPoint.getY() + (y * cos - z * sin));
		this.setZ(centerPoint.getZ() + (z * cos + y * sin));
	}

	public void shiftRoll(double radian, Node centerPoint) {

		double sin = Math.sin(radian);
		double cos = Math.cos(radian);

		double x = this.getX() - centerPoint.getX();
		double y = this.getY() - centerPoint.getY();

		this.setX(centerPoint.getX() + (x * cos - y * sin));
		this.setY(centerPoint.getY() + (y * cos + x * sin));
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getZ() {
		return z;
	}

	public double getY() {
		return y;
	}

}
