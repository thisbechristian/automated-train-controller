package org.ecotrack.automatictraincontroller.trackmodel;

public class Yard {

	private double xLoc;
	private double yLoc;
	private double WIDTH = 100;

	public Yard(double xLoc, double yLoc){

		this.xLoc = xLoc;
		this.yLoc = yLoc;
	}

	public double getXLoc(){
		return this.xLoc;
	}

	public double getYLoc(){
		return this.yLoc;
	}

	public double getWidth(){
		return this.WIDTH;
	}

}
