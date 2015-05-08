package src.main;

/**
 * Created by 15bakerd on 4/16/2015.
 */
public class ValOverShoot {

	private double finalValue;
	private double overShootPercent;

	private double startTime;
	private double lapseTime;

	private double curValue = 0;

	private boolean started = false;

	public ValOverShoot(double value, double overShootPercent, double time) {

		finalValue = value;
		this.overShootPercent = overShootPercent;

		this.lapseTime = time;
	}

	public void start() {

		started = true;
		startTime = System.currentTimeMillis() / 1000d;
	}

	public double getCurValue() {

		if(started) {

			double a = lapseTime / 1.5d;
			double s = finalValue * overShootPercent;

			double t = (System.currentTimeMillis() / 1000d) - startTime;

			if(t <= a)
				curValue = (((-s) * Math.cos(t * Math.PI / a)) + s) / 2;
			else if(a <= t && t <= 1.5d * a) {
				curValue = (((s - finalValue) * Math.cos(2 * t * Math.PI / a)) + (s + finalValue)) / 2;
			}
			else {
				curValue = finalValue;
				started = false;
			}
		}

		return curValue;
	}

	public double getFinalValue() {
		return finalValue;
	}


}
