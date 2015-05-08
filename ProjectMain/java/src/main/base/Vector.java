package src.main.base;

/**
 * Created by 15bakerd on 4/8/2015.
 */
public class Vector extends Node {

	public Vector(double x, double y, double z) {
		super(x,y,z);
	}

	public double dotProduct(Vector v) {

		double tx = this.x * v.x;
		double ty = this.y * v.y;
		double tz = this.z * v.z;

		return tx + ty + tz;
	}

	public Vector scaleVector(double scale) {
		return new Vector(x * scale, y * scale, z * scale);
	}

	public Vector subtractVector(Vector v) {

		return new Vector(this.x - v.x, this.y - v.y, this.z - v.z);
	}

	public Vector addVector(Vector v) {

		return new Vector(this.x + v.x, this.y + v.y, this.z + v.z);
	}

	public Vector crossProduct(Vector v) {

		double resX =  ((this.y * v.z) - (this.z * v.y));
		double resY = -((this.x * v.z) - (this.z * v.x));
		double resZ =  ((this.x * v.y) - (this.y * v.x));

		return new Vector(resX, resY, resZ);
	}

	public Vector getParallel(Vector axis) {

		double dot = this.dotProduct(axis);

		return axis.scaleVector(dot);
	}

	public Vector getPerp(Vector axis) {

		Vector parallel = getParallel(axis);

		return this.subtractVector(parallel);
	}

	public double getMagnitude() {

		return Math.sqrt((x * x) + (y * y) + (z * z));
	}
}
