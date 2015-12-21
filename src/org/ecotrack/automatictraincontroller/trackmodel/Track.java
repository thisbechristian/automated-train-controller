package org.ecotrack.automatictraincontroller.trackmodel;

import java.util.ArrayList;

public class Track{

	private ArrayList<Line> lineArray;
	private boolean trackRun = true;

	public Track(){
		lineArray = new ArrayList<Line>();
		importLineData();
	}

	private void importLineData(){
		//Will be from database
		addLine();
	}

	private void addLine(){
		lineArray.add(new Line());
	}

	public ArrayList<Line> getLine(){
		return lineArray;
	}

	public boolean getTrackRun(){
		return this.getTrackRun();
	}

	public void toggleTrackRun(){
		this.trackRun = !this.trackRun;
	}

	public void update(int CLOCK){
		for(int i = 0; i < lineArray.size(); i++){
			lineArray.get(i).update(CLOCK);
		}
	}
	/*
	//public void setTrainMotion(double speed, int lineId, int trainId){ //will implement with ids
	public void setTrainMotion(double speed, int lineId, int trainId){
		lineArray.get(lineId).setTrainMotion(speed, trainId);
	}
	*/

	//interface
	public int[] getlineIdArray(){
		int length = lineArray.size();
		int[] lineIds = new int[length];
		for(int i = 0; i < length; i++){
			lineIds[i] = lineArray.get(i).getId();
		}
		return lineIds;
	}

	public int[] getblockIdArray(int lineId){
		return lineArray.get(lineId).getBlockIdArray();
	}

}
