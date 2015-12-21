package org.ecotrack.automatictraincontroller.trackmodel;

import java.util.ArrayList;

public class Block {

	//static int currid = 0; //static id will iterate as new blocks are added
	private ArrayList<Block> blockArray; //the list of other blocks on the line that this block is on
	private double length; //
	private double x;
	private double y;
	private double x2;
	private double y2;
	private int negateX = 1;
	private int negateY = 1;
	private double slope;
	private int id;
	private double speed;
    private double authority;
	private boolean isCrossing;
	private boolean crossingActivated;
	private boolean occupied; //train there or not
	private boolean heater;
	private boolean state;  //block is good/broken
	private boolean open;   ///block is open/closed
	private double grade = 0;


	private int nextBlock;
	private int prevBlock;
	private boolean direction; //false means 1 direction, true = means both directions
	private boolean isReversed = false; //false means normal, true means reversed
	private boolean secondSwitchPosition;


	//switch variables
	private boolean isSwitch;
    private boolean switchPosition;
    private int switchBlock = -1;
	private double x3;
	private double y3;
	private double x4;
	private double y4;
	private double slopeSwitch;
	private int negateXSwitch = 1;
	private int negateYSwitch = 1;
	private double switchBlockLength;
	private boolean invertedSwitch;

	public Block(ArrayList<Block> blockArray, double x, double y, double x2, double y2, double length, boolean direction, int prevBlock, int nextBlock, int thisId, int switchBlock, boolean isSwitch, double x3, double y3, double x4, double y4, double switchBlockLength, boolean secondSwitchPosition, boolean invertedSwitch){
		this.id = thisId;
		//currid++;
		this.blockArray = blockArray;
		this.length = length;
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.occupied = false;
		this.heater = false;
		this.state = true;
		this.open = true;
		this.isCrossing = false;
        this.speed = 0;
        this.authority = 0;
        this.direction = direction;
        this.prevBlock = prevBlock;
        this.nextBlock = nextBlock;
        this.secondSwitchPosition = secondSwitchPosition;

        this.switchBlock = switchBlock;
        this.switchPosition = false;
        this.isSwitch = isSwitch;
		this.switchBlockLength = switchBlockLength;
        this.x3 = x3;
        this.y3 = y3;
		this.x4 = x4;
		this.y4 = y4;
		this.invertedSwitch = invertedSwitch;

        //////for calculating slope of main block
        if(x2-x == 0){
			if(y2 - y > 0){
				this.slope = 1000000000;
			}else{
				this.slope = -1000000000;
			}
		}else{
			this.slope = (y2-y)/(x2-x);
		}
		if(x2-x < 0){
			negateX = -1;
			negateY = -1;
		}


		//for calculating slope of switch block
		if(isSwitch){
			if(x4-x3 == 0){
				if(y4 - y3 > 0){
					this.slopeSwitch = 1000000000;
				}else{
					this.slopeSwitch = -1000000000;
				}

			}else{
				this.slopeSwitch = (y4-y3)/(x4-x3);
			}
			if(x4-x3 < 0){
				negateXSwitch = -1;
				negateYSwitch = -1;
			}
		}



	}


	public double getLength(){
		return this.length;
	}

	public double getX(){
		return this.x;
	}

	public double getY(){
		return this.y;
	}

	public double getX2(){
		return x2;
	}

	public double getY2(){
		return y2;
	}

	public int getNegateX(){
		return negateX;
	}

	public int getNegateY(){
		return negateY;
	}

	public double getSlope(){
		return this.slope;
	}

	public int getId(){
		return this.id;
	}

	public boolean getHeater(){
		return this.heater;
	}

	public void setHeater(boolean heater){
		this.heater = heater;
	}

	public boolean getState(){
		return this.state;
	}

	public void setState(boolean state){
		this.state = state;
	}

	public boolean getOpen(){
		return this.open;
	}

	public void setOpen(boolean open){
		this.open = open;
	}

	public void setOccupied(boolean occupied){
		this.occupied = occupied;
	}

	public boolean getOccupancy(){
		return this.occupied;
	}

	public double getSpeed(){
		return this.speed;
	}

	public void setSpeed(double speed){
		this.speed = speed;
	}

	public double getAuthority(){
		return this.authority;
	}

	public void setAuthority(double authority){
		this.authority = authority;
	}

	public boolean getCrossing(){
        return isCrossing;
    }

    public void setCrossing(boolean isCrossing){
        this.isCrossing = isCrossing;
    }

    public boolean getCrossingActivated(){
        return crossingActivated;
    }

    public void setCrossingActivated(boolean crossingActivated){
        this.crossingActivated = crossingActivated;
    }

    public boolean getDirection(){
    	return this.direction;
    }

    public int getNextBlock(){
    	return this.nextBlock;
    }

    public int getPrevBlock(){
    	return this.prevBlock;
    }

    public int getSwitchBlock(){
    	return this.switchBlock;
    }


    public boolean getIsSwitch(){
    	return this.isSwitch;
    }

    public void setSwitchPosition(boolean switchPosition){
    	if(!occupied){
    		this.switchPosition = switchPosition;
    	}
    }

    public boolean getSwitchPosition(){
    	return this.switchPosition;
    }

    public double getSlopeSwitch(){

    	return this.slopeSwitch;
    }

    public int getNegateXSwitch(){
    	return this.negateXSwitch;
    }

    public int getNegateYSwitch(){
    	return this.negateYSwitch;
    }

    public double getX3(){
    	return this.x3;
    }

    public double getY3(){
    	return this.y3;
    }

    public double getX4(){
    	return this.x4;
    }

    public double getY4(){
    	return this.y4;
    }


    public double getSwitchBlockLength(){
    	return this.switchBlockLength;
    }

    public boolean getInvertedSwitch(){
    	return this.invertedSwitch;
    }

    public boolean getIsReversed(){
    	return this.isReversed;
    }

    public void setIsReversed(boolean isReversed){
    	this.isReversed = isReversed;
    }

    public boolean getSecondSwitchPosition(){
    	return this.secondSwitchPosition;
    }

    public double getGrade(){
    	return this.grade;
    }


    @Override
    public boolean equals(Object o){
    	if(o instanceof Block){
    		return (this.getId() == ((Block)o).getId());
    	}
    	return false;
    }

}
