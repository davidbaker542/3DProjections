package src.main.base;

/**
 * Created by David on 4/5/2015.
 */
public class Pointer extends Node {

	protected double yaw, pitch, roll;

	public Pointer(double x, double y, double z, double yaw, double pitch, double roll) {
		super(x, y, z);

		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
	}

	/**
	 * Finds the Unit Vector counter part of the camera's rotation.
	 *
	 * @return Direction Vector
	 */
	public Vector getUnitVectorZ() {

		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.sin(pitch) * Math.sin(yaw);
		double z = Math.cos(pitch);

		return new Vector(x, y, z);
	}

	public Vector getUnitVectorY() {

		double x = Math.sin(pitch - Math.PI / 2) * Math.cos(yaw);
		double y = Math.sin(pitch - Math.PI / 2) * Math.sin(yaw);
		double z = Math.cos(pitch - Math.PI / 2);

		return new Vector(x, y, z);
	}


	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {

		if(yaw > Math.PI * 2)
			setYaw(yaw - Math.PI*2);
		else if(yaw < 0)
			setYaw(yaw + Math.PI*2);
		else
			this.yaw = yaw;
	}

	public double getRoll() {
		return roll;
	}

	public void setRoll(double roll) {
		if(roll > Math.PI * 2)
			setRoll(roll - Math.PI*2);
		else if(roll < 0)
			setRoll(roll + Math.PI*2);
		else
			this.roll = roll;
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		if(pitch > Math.PI * 2)
			setPitch(pitch - Math.PI*2);
		else if(pitch < 0)
			setPitch(pitch + Math.PI*2);
		else
			this.pitch = pitch;
	}

	public Pointer clone() {
		return new Pointer(x, y, z, yaw, pitch, roll);
	}
}
