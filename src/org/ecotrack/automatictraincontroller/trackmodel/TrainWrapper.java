package org.ecotrack.automatictraincontroller.trackmodel;

import java.util.ArrayList;

import org.ecotrack.automatictraincontroller.trainmodel.Train;

public class TrainWrapper {

	private Train train;
	private Block currentBlock = null;
	private ArrayList<Block> blockArray;
	private double distance;
	private double xLoc;
	private double yLoc;


	public TrainWrapper(Train train, ArrayList<Block> blockArray, double xLoc, double yLoc, Block currentBlock){
		this.train = train;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.currentBlock = currentBlock;
		this.distance = 0;
		this.blockArray = blockArray;
	}

	public void update(int CLOCK) {
		train.update(CLOCK, currentBlock.getSpeed(), currentBlock.getAuthority(), currentBlock.getGrade());
		currentBlock.setOccupied(true);
		double distanceTraveled = (this.train.getCurrentSpeed() * (CLOCK / 1000.0));
		if(!currentBlock.getIsReversed()){
			distance += distanceTraveled;
		}else{
			distance -= distanceTraveled;
		}


		if(distance > currentBlock.getLength() && !currentBlock.getIsReversed() || distance < 0 && currentBlock.getIsReversed()){			//on to next block

			currentBlock.setOccupied(false);
			currentBlock.setSpeed(0);
			currentBlock.setAuthority(0);


			int nextBlock;

			if(currentBlock.getIsSwitch() && currentBlock.getSwitchPosition()){
				if(!currentBlock.getIsReversed()){
					nextBlock = currentBlock.getSwitchBlock();
				}else{
					if(currentBlock.getInvertedSwitch()){
						nextBlock = currentBlock.getNextBlock();
					}else{
						nextBlock = currentBlock.getPrevBlock();
					}
				}
			}else{
				if(!currentBlock.getIsReversed()){
					nextBlock = currentBlock.getNextBlock();
				}else{
					nextBlock = currentBlock.getPrevBlock();
				}
			}

			if(blockArray.get(nextBlock).getPrevBlock() == currentBlock.getId()){
				System.out.println("here -1");
				blockArray.get(nextBlock).setIsReversed(false);
			}else if(blockArray.get(nextBlock).getNextBlock() == currentBlock.getId()){
				System.out.println("here 0");
				if(blockArray.get(nextBlock).getInvertedSwitch() && blockArray.get(nextBlock).getSwitchPosition()){
					System.out.println("here 1");
					blockArray.get(nextBlock).setIsReversed(false);
				}else{
					blockArray.get(nextBlock).setIsReversed(true);
				}
			}else if(blockArray.get(nextBlock).getIsSwitch() && blockArray.get(nextBlock).getSwitchBlock() == currentBlock.getId()){
					System.out.println("here 2");
					blockArray.get(nextBlock).setIsReversed(true);
			}else{
				System.out.println("Error with reverse direction.");
			}

			//gets layover distance traveled between clock ticks
			if(!currentBlock.getIsReversed()){
				distance = distance - currentBlock.getLength(); //is a negative distance
			}else{
				distance = -distance;
			}

			//calculates distance along blocks
			if(!blockArray.get(nextBlock).getIsReversed()){
				//distance = distance - currentBlock.getLength(); //is a negative distance
			}else{
				distance = blockArray.get(nextBlock).getLength() - distance;
			}

			currentBlock = blockArray.get(nextBlock);
			currentBlock.setOccupied(true);


		}
		//System.out.println(currentBlock.getId());
		//System.out.println(distance);
		int reversed = 1;
		if(currentBlock.getIsReversed()){
			reversed = 1;
		}
		//updates actual train coordinate
		if(currentBlock.getIsSwitch() && currentBlock.getSwitchPosition()){
			double slope = currentBlock.getSlopeSwitch();
			double radians = Math.atan(slope);

			xLoc = currentBlock.getX3() + distance * Math.cos(radians) * currentBlock.getNegateXSwitch() * reversed;
			yLoc = currentBlock.getY3() + distance * Math.sin(radians) * currentBlock.getNegateYSwitch() * reversed;
		}else{
			double slope = currentBlock.getSlope();
			double radians = Math.atan(slope);
			if(reversed == -1){
				xLoc = currentBlock.getX() + (currentBlock.getLength() + distance) * Math.cos(radians) * currentBlock.getNegateX() * reversed;
				yLoc = currentBlock.getY() + (currentBlock.getLength() + distance) * Math.sin(radians) * currentBlock.getNegateY() * reversed;
			}else{
				xLoc = currentBlock.getX() + distance * Math.cos(radians) * currentBlock.getNegateX() * reversed;
				yLoc = currentBlock.getY() + distance * Math.sin(radians) * currentBlock.getNegateY() * reversed;
			}
		}
    }

	public Train getTrain(){
		return this.train;
	}


	public void setCurrentBlock(Block currentBlock){
		this.currentBlock = currentBlock;
	}

	public Block getCurrentBlock(){
		return this.currentBlock;
	}

	public void setXLoc(double xLoc){
		this.xLoc = xLoc;
	}

	public double getXLoc(){
		return this.xLoc;
	}

	public void setYLoc(double yLoc){
		this.yLoc = yLoc;
	}

	public double getYLoc(){
		return this.yLoc;
	}

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return this.distance;
	}


}
