package org.ecotrack.automatictraincontroller.traincontroller;

import org.ecotrack.automatictraincontroller.trainmodel.Train;

public class TrainController {
	private static boolean initialized = false;
	private Train train;
	private TrainControllerGui g;
	private double speed_limit = 0;

	private double prev_power = 0;
	private double prev_integral = 0;
	private double prev_error = 0;
	private double dt = 1;
	private int kp = -24000;
	private int ki = 1;

	//private final double PASS_WEIGHT = 68.0389; //150lbs
	//private final double TRAIN_WEIGHT = 37103.86; //40.9tons
	private final double MAX_POWER = 120000;
	private final double BRAKE_SPEED = 1.2; //m/s^2

	public TrainController(Train train) {
		if(!initialized)
		{
			g = new TrainControllerGui();
			g.main(null);
			initialized = true;
		}

		this.train = train;
		g.addTrainController(this);
	}

		public double update(double set_speed, double authority, int clock)
	{
	    this.dt = clock / 1000;
		this.speed_limit = set_speed;

		double current_speed = this.getSpeed();
		double stop_distance = current_speed*current_speed/(2*BRAKE_SPEED);
		if(stop_distance >= authority - 5)
		{
			this.setSpeedLimit(0);
			this.setServiceBrake(true);
			prev_power = 0;
			return 0;
		}

		double error = current_speed - set_speed;

		double integral;
		if(prev_power < MAX_POWER)
			integral = prev_integral + dt/2 * (error + prev_error);
		else
			integral = prev_integral;


		double power = kp*error + ki*integral;

		prev_error = error;
		prev_integral = integral;
		prev_power = power;

		if(power > MAX_POWER)
			power = MAX_POWER;

        if(power <= 0)
		{
		    power = 0;
			train.setServiceBrake(true);
		}
		else
		{
			train.setServiceBrake(false);
		}

 /*
		//Calculations not necessary, acting as train model
		double mass = passengers * PASS_WEIGHT + TRAIN_WEIGHT;

		double a = power / mass / current_speed;
		current_speed = current_speed + dt * a;
 */

		//System.out.println("Speed: " + current_speed + ", Acceleration: " + a + ", Power: " + power);
		return power;
	}

	public int getID()
	{
		return this.train.getID();
	}

	public boolean getDoors() {
		return !this.train.hasDoorsOpen();
	}

	public void setDoors(boolean closed) {
		this.train.setDoorsOpen(!closed);
	}

	public boolean getLights() {
		return !this.train.hasLightsOn();
	}

	public void setLights(boolean off) {
		this.train.setLightsOn(!off);
	}

	public void setServiceBrake(boolean on) {
		this.train.setServiceBrake(on);
	}

	public boolean getEmergencyBrake() {
		return this.train.isEmergencyBrakePulled();
	}

	public void setEmergencyBrake(boolean on) {
		this.train.setEmergencyBrake(on);
	}

	public void setSpeedLimit(double speed) {
		this.speed_limit = speed;
	}

	public double getSpeedLimit() {
		return this.speed_limit;
	}

	public double getSpeed() {
		return this.train.getCurrentSpeed();
	}

//	public String getLine() {
//		return this.train.getLine();
//	}

	public boolean getTrainEngineFailure() {
		return this.train.getEngineFailure();
	}

	public void setTrainEngineFailure(boolean failure) {
		this.train.setEngineFailure(failure);
	}

	public boolean getSignalPickupFailure() {
		return this.train.getSignalPickupFailure();
	}

	public void setSignalPickupFailure(boolean failure) {
		this.train.setSignalPickupFailure(failure);
	}

	public boolean getBrakeFailure() {
		return this.train.getBrakeFailure();
	}

	public void setBrakeFailure(boolean failure) {
		this.train.setBrakeFailure(failure);
	}
}
