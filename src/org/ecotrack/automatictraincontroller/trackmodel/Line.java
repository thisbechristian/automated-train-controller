package org.ecotrack.automatictraincontroller.trackmodel;

import java.util.ArrayList;

import org.ecotrack.automatictraincontroller.trainmodel.Train;

public class Line{

	final int CARCOUNT = 5;
	private static int currid = 0;
	private ArrayList<Block> blockArray;
	private ArrayList<TrainWrapper> trainArray;
	private Yard yard;

	private double startX;
	private double startY;

	public Line(){
		yard = new Yard(400,200);
		blockArray = new ArrayList<Block>();
		trainArray = new ArrayList<TrainWrapper>();
		this.currid = currid;
		currid++;
		importBlockData();
	}

	public TrainWrapper addTrain(){
		TrainWrapper train = new TrainWrapper(new Train(), blockArray, yard.getXLoc()+ yard.getWidth(), yard.getYLoc() + yard.getWidth(), blockArray.get(0));
		trainArray.add(train);
		return train;
	}

	public ArrayList<Block> getBlocks(){
		return this.blockArray;
	}

	public ArrayList<TrainWrapper> getTrains(){
		return this.trainArray;
	}

	public int getId(){
		return this.currid;
	}

	public void setTrainMotion(double speed, int trainId){
		//trainArray.get(trainId).getTrain().setSpeedLimit(speed); //WILL NOT WORK AFTER TRAINS HAVE BEEN REMOVED
	}

	public void update(int CLOCK){
		for(int i = 0; i < trainArray.size(); i++){
			trainArray.get(i).update(CLOCK);
		}
	}

	public Yard getYard(){
		return this.yard;
	}

	private void importBlockData(){
		//Would be read form a database

		startX = yard.getXLoc()+ yard.getWidth();
		startY = yard.getYLoc()+ yard.getWidth();

		//YY
		buildBlock(-1, 	1, 		0, 		50, 	false, 	"S", 	false, 	-2, 	"", false, false);
		//K
		buildBlock(0, 	2, 		1, 		100, 	false, 	"S", 	true, 	151, 	"W", false, true);
		buildBlock(1, 	3, 		2, 		100,	false,	"S", 	false, 	-2,		"", false, false);
		buildBlock(2, 	4, 		3, 		200,	false,	"S", 	false, 	-2,		"", false, false);
		buildBlock(3, 	5, 		4, 		200,	false,	"S", 	false, 	-2,		"", false, false);
		buildBlock(4, 	6, 		5, 		100,	false,	"S", 	false, 	-2,		"", false, false);
		buildBlock(5, 	7, 		6, 		100,	false,	"S", 	false, 	-2,		"", false, false);
		//L
		buildBlock(6, 	8, 		7, 		100,	false,	"SW", 	false, 	-2,		"", false, false);
		buildBlock(7, 	9, 		8, 		100,	false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(8, 	10, 	9, 		100,	false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(9, 	11, 	10, 	100,	false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(10, 	12, 	11, 	100,	false,	"W", 	false, 	-2,		"", false, false);
		//M
		buildBlock(11, 	13, 	12, 	100,	false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(12, 	14, 	13, 	100,	false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(13, 	15, 	14, 	100,	false,	"W", 	false, 	-2,		"", false, false);
		//N
		buildBlock(14, 	16, 	15, 	300,		true,	"W", 	true, 	39,		"NE", false, true);
		buildBlock(15, 	17, 	16, 	300,		true,	"W", 	false, 	-2,		"", false, false);
		buildBlock(16, 	18, 	17, 	300,		true,	"W", 	false, 	-2,		"", false, false);
		buildBlock(17, 	19, 	18, 	300,		true,	"W", 	false, 	-2,		"", false, false);
		buildBlock(18, 	20, 	19, 	300,		true,	"W", 	false, 	-2,		"", false, false);
		buildBlock(19, 	21, 	20, 	300,		true,	"W", 	false, 	-2,		"", false, false);
		buildBlock(20, 	22, 	21, 	300,		true,	"W", 	false, 	-2,		"", false, false);
		buildBlock(21, 	23, 	22, 	300,		true,	"W", 	false, 	-2,		"", false, false);
		buildBlock(22, 	24, 	23, 	300,		true,	"W", 	true, 	38,	"NW", false, false);
		//O
		buildBlock(23, 	25, 	24, 	100,		true,	"W", 	false, 	-2,		"", false, false);
		buildBlock(24, 	26, 		25, 	86.6,		true,	"W", 	false, 	-2,		"", false, false);
		buildBlock(25, 	27, 		26, 	100,		true,	"W", 	false, 	-2,		"", true, false);
		//P
		buildBlock(26, 	28, 		27, 	75,		false,	"N", 	false, 	-2,		"", false, false);
		buildBlock(27, 	29, 		28, 	75,		false,	"N", 	false, 	-2,		"", false, false);
		buildBlock(28, 	30, 		29, 	75,		false,	"N", 	false, 	-2,		"", false, false);
		buildBlock(29, 	31, 		30, 	75,		false,	"N", 	false, 	-2,		"", false, false);
		buildBlock(30, 	32, 		31, 	75,		false,	"N", 	false, 	-2,		"", false, false);
		buildBlock(31, 	33, 		32, 	75,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(32, 	34, 		33, 	75,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(33, 	35, 		34, 	75,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(34, 	36, 		35, 	75,		false,	"E", 	false, 	-2,		"", false, false);
		//Q
		buildBlock(35, 	37, 		36, 	75,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(36, 	38, 		37, 	75,		false,	"S", 	false, 	-2,		"", false, false);
		buildBlock(37, 	23, 		38, 	75,		false,	"S", 	false, 	-2,		"", true, false);

		//R
		buildBlock(15, 	40, 	39, 	35,		false,	"NE", 	false, 	-2,		"", true, false);

		//S
		buildBlock(39, 	41, 	40, 	100,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(40, 	42, 	41, 	100,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(41, 	43, 	42, 	80,		false,	"E", 	false, 	-2,		"", false, false);
		//T
		buildBlock(42, 	44, 	43, 	100,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(43, 	45, 	44, 	100,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(44, 	46, 	45, 	90,			false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(45, 	47, 	46, 	100,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(46, 	48, 	47, 	100,		false,	"NE", 	false, 	-2,		"", false, false);
		//U
		buildBlock(47, 	49, 	48, 	100,		false,	"N", 	false, 	-2,		"", false, false);
		buildBlock(48, 	50, 	49, 	100,		false,	"N", 	false, 	-2,		"", false, false);
		buildBlock(49, 	51, 	50, 	100,		false,	"N", 	false, 	-2,		"", false, false);
		buildBlock(50, 	52, 	51, 	100,		false,	"N", 	false, 	-2,		"", false, false);
		buildBlock(51, 	53, 	52, 	100,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(52, 	54, 	53, 	100,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(53, 	55, 	54, 	100,		false,	"W", 	false, 	-2,		"", false, false);
		//V
		buildBlock(54, 	56, 	55, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(55, 	57, 	56, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(56, 	58, 	57, 	40,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(57, 	59, 	58, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(58, 	60, 	59, 	50,		false,	"N", 	false, 	-2,		"", false, false);
		//W
		buildBlock(59, 	61, 	60, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(60, 	62, 	61, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(61, 	63, 	62, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(62, 	64, 	63, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(63, 	65, 	64, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(64, 	66, 	65, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(65, 	67, 	66, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(66, 	68, 	67, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(67, 	69, 	68, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(68, 	70, 	69, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(69, 	71, 	70, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(70, 	72, 	71, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(71, 	73, 	72, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(72, 	74, 	73, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(73, 	75, 	74, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(74, 	76, 	75, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(75, 	77, 	76, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(76, 	78, 	77, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(77, 	79, 	78, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(78, 	80, 	79, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(79, 	81, 	80, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(80, 	82, 	81, 	50,		false,	"W", 	false, 	-2,		"", false, false);
		//X
		buildBlock(81, 	83, 	82, 	50,		false,	"NW", 	false, 	-2,		"", false, false);
		buildBlock(82, 	84, 	83, 	50,		false,	"NW", 	false, 	-2,		"", false, false);
		buildBlock(83, 	85, 	84, 	50,		false,	"NW", 	false, 	-2,		"", false, false);
		//Y
		buildBlock(84, 	86, 	85, 	50,		false,	"N", 	false, 	-2,		"", false, false);
		buildBlock(85, 	87, 	86, 	40,		false,	"N", 	false, 	-2,		"", false, false);
		buildBlock(86, 	88, 	87, 	40,		false,	"N", 	false, 	-2,		"", false, false);
		//Z
		buildBlock(87, 	89, 	88, 	35,		false,	"NE", 	false, 	-2,		"", false, false);
		//F
		buildBlock(88, 	90, 	89, 	50,		true,	"NE", 	true, 	117,		"E", false, true);
		buildBlock(89, 	91, 	90, 	50,		true,	"N", 	false, 	-2,		"", false, false);
		buildBlock(90, 	92, 	91, 	100,		true,	"N", 	false, 	-2,		"", false, false);
		buildBlock(91, 	93, 	92, 	200,		true,	"NW", 	false, 	-2,		"", false, false);
		buildBlock(92, 	94, 	93, 	300,		true,	"W", 	false, 	-2,		"", false, false);
		buildBlock(93, 	95, 	94, 	300,		true,	"W", 	false, 	-2,		"", false, false);
		buildBlock(94, 	96, 	95, 	300,		true,	"NW", 	false, 	-2,		"", false, false);
		buildBlock(95, 	97, 	96, 	300,		true,	"N", 	false, 	-2,		"", false, false);
		//E
		buildBlock(96, 	98, 	97, 	150,		true,	"NE", 	false, 	-2,		"", false, false);
		buildBlock(97, 	99, 	98, 	150,		true,	"NE", 	false, 	-2,		"", false, false);
		buildBlock(98, 	100, 	99, 	150,		true,	"NE", 	false, 	-2,		"", false, false);
		buildBlock(99, 	101, 	100, 	150,		true,	"NE", 	false, 	-2,		"", false, false);
		//D
		buildBlock(100, 	102, 	101, 	150,		true,	"E", 	false, 	-2,		"", false, false);
		buildBlock(101, 	103, 	102, 	150,		true,	"E", 	false, 	-2,		"", false, false);
		buildBlock(102, 	104, 	103, 	150,		true,	"E", 	false, 	-2,		"", false, false);
		buildBlock(103, 	105, 	104, 	150,		true,	"E", 	true, 	116,		"SE", false, false);
		//C
		buildBlock(104, 	106, 	105, 	100,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(105, 	107, 	106, 	100,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(106, 	108, 	107, 	100,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(107, 	109, 	108, 	100,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(108, 	110, 	109, 	100,		false,	"SE", 	false, 	-2,		"", false, false);
		buildBlock(109, 	111, 	110, 	100,		false,	"S", 	false, 	-2,		"", false, false);
		//B
		buildBlock(110, 	112, 	111, 	100,		false,	"SW", 	false, 	-2,		"", false, false);
		buildBlock(111, 	113, 	112, 	100,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(112, 	114, 	113, 	100,		false,	"NW", 	false, 	-2,		"", false, false);
		//A
		buildBlock(113, 	115, 	114, 	100,		false,	"NW", 	false, 	-2,		"", false, false);
		buildBlock(114, 	116, 	115, 	100,		false,	"W", 	false, 	-2,		"", false, false);
		buildBlock(115, 	104, 	116, 	100,		false,	"W", 	false, 	-2,		"", true, false);

		//G
		buildBlock(89, 		118, 	117, 	50,			false,	"SE", 	false, 	-2,		"", true, false);
		buildBlock(117, 	119, 	118, 	50,		false,	"SE", 	false, 	-2,		"", false, false);
		buildBlock(118, 	120, 	119, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(119, 	121, 	120, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		//H
		buildBlock(120, 	122, 	121, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(121, 	123, 	122, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(122, 	124, 	123, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		//I
		buildBlock(123, 	125, 	124, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(124, 	126, 	125, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(125, 	127, 	126, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(126, 	128, 	127, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(127, 	129, 	128, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(128, 	130, 	129, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(129, 	131, 	130, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(130, 	132, 	131, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(131, 	133, 	132, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(132, 	134, 	133, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(133, 	135, 	134, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(134, 	136, 	135, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(135, 	137, 	136, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(136, 	138, 	137, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(137, 	139, 	138, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(138, 	140, 	139, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(139, 	141, 	140, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(140, 	142, 	141, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(141, 	143, 	142, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(142, 	144, 	143, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(143, 	145, 	144, 	50,		false,	"E", 	false, 	-2,		"", false, false);
		buildBlock(144, 	146, 	145, 	50,		false,	"E", 	true, 	147,		"SE", false, false);
		//ZZ
		buildBlock(145, 	-1, 		146, 	150,		false,	"E", 	false, 	-2,		"", false, false);
		//J
		buildBlock(145, 	148, 		147, 	50,		false,	"SE", 	false, 	-2,		"", true, false);
		buildBlock(147, 	149, 		148, 	50,		false,	"SE", 	false, 	-2,		"", false, false);
		buildBlock(148, 	150, 		149, 	50,		false,	"SE", 	false, 	-2,		"", false, false);
		buildBlock(149, 	151, 		150, 	50,		false,	"SE", 	false, 	-2,		"", false, false);
		buildBlock(150, 	1, 		151, 		50,		false,	"SE", 	false, 	-2,		"", true, false);


		for(int i = 0; i < blockArray.size(); i++){
			System.out.println("Block: " + blockArray.get(i).getId() + " Prev: " + blockArray.get(i).getPrevBlock() + " Next: " + blockArray.get(i).getNextBlock() + " Switch: " + blockArray.get(i).getSwitchBlock());
			blockArray.get(i).setIsReversed(false);
		}

	}


	private void buildBlock(int prevId, int nextId, int id, double length, boolean isReversed, String direction, boolean isSwitch, int switchBlockId, String switchDirection, boolean secondSwitchPosition, boolean invertedSwitch){

		double x = 0;
		double y = 0;
		double x2 = 0;
		double y2 = 0;
		double x3 = 0;
		double y3 = 0;
		double x4 = 0;
		double y4 = 0;

		if(prevId == -1){
			x = startX;
			y = startY;
		}else if(secondSwitchPosition && blockArray.get(prevId).getIsSwitch()){
			x = blockArray.get(prevId).getX4();
			y = blockArray.get(prevId).getY4();
		}else{
			x = blockArray.get(prevId).getX2();
			y = blockArray.get(prevId).getY2();
		}


		if(direction.equals("N")){
			if(invertedSwitch && isSwitch){
				x2 = x;
				y2 = y - length;
			}else{
				x2 = x;
				y2 = y - length;
			}

		}else if(direction.equals("NE")){
			if(invertedSwitch && isSwitch){
				x2 = x + length / Math.sqrt(2);
				y2 = y - length / Math.sqrt(2);
			}else{
				x2 = x + length / Math.sqrt(2);
				y2 = y - length / Math.sqrt(2);
			}

		}else if(direction.equals("E")){
			if(invertedSwitch && isSwitch){
				x2 = x + length;
				y2 = y;
			}else{
				x2 = x + length;
				y2 = y;
			}

		}else if(direction.equals("SE")){
			if(invertedSwitch && isSwitch){
				x2 = x + length / Math.sqrt(2);
				y2 = y + length / Math.sqrt(2);
			}else{
				x2 = x + length / Math.sqrt(2);
				y2 = y + length / Math.sqrt(2);
			}

		}else if(direction.equals("S")){
			if(invertedSwitch && isSwitch){
				x2 = x;
				y2 = y + length;
			}else{
				x2 = x;
				y2 = y + length;
			}

		}else if(direction.equals("SW")){
			if(invertedSwitch && isSwitch){
				x2 = x - length / Math.sqrt(2);
				y2 = y + length / Math.sqrt(2);
			}else{
				x2 = x - length / Math.sqrt(2);
				y2 = y + length / Math.sqrt(2);
			}

		}else if(direction.equals("W")){
			if(invertedSwitch && isSwitch){
				x2 = x - length;
				y2 = y;
			}else{
				x2 = x - length;
				y2 = y;
			}

		}else if(direction.equals("NW")){
			if(invertedSwitch && isSwitch){
				x2 = x - length / Math.sqrt(2);
				y2 = y - length / Math.sqrt(2);
			}else{
				x2 = x - length / Math.sqrt(2);
				y2 = y - length / Math.sqrt(2);
			}

		}else{
			System.out.println("Error in direction");
		}

		if(invertedSwitch && isSwitch){
			x3 = x2;
			y3 = y2;
		}else{
			x3 = x;
			y3 = y;
		}

		if(isSwitch){
			if(switchDirection.equals("N")){
				if(invertedSwitch){
					x4 = x3;
					y4 = y3 - length;
				}else{
					x4 = x;
					y4 = y - length;
				}

			}else if(switchDirection.equals("NE")){
				if(invertedSwitch){
					x4 = x3 + length / Math.sqrt(2);
					y4 = y3 - length / Math.sqrt(2);
				}else{
					x4 = x + length / Math.sqrt(2);
					y4 = y - length / Math.sqrt(2);
				}

			}else if(switchDirection.equals("E")){
				if(invertedSwitch){
					x4 = x3 + length;
					y4 = y3;
				}else{
					x4 = x + length;
					y4 = y;
				}

			}else if(switchDirection.equals("SE")){
				if(invertedSwitch){
					x4 = x3 + length / Math.sqrt(2);
					y4 = y3 + length / Math.sqrt(2);
				}else{
					x4 = x + length / Math.sqrt(2);
					y4 = y + length / Math.sqrt(2);
				}

			}else if(switchDirection.equals("S")){
				if(invertedSwitch){
					x4 = x3;
					y4 = y3 + length;
				}else{
					x4 = x;
					y4 = y + length;
				}

			}else if(switchDirection.equals("SW")){
				if(invertedSwitch){
					x4 = x3 - length / Math.sqrt(2);
					y4 = y3 + length / Math.sqrt(2);
				}else{
					x4 = x - length / Math.sqrt(2);
					y4 = y + length / Math.sqrt(2);
				}

			}else if(switchDirection.equals("W")){
				if(invertedSwitch){
					x4 = x3 - length;
					y4 = y3;
				}else{
					x4 = x - length;
					y4 = y;
				}

			}else if(switchDirection.equals("NW")){
				if(invertedSwitch){
					x4 = x3 - length / Math.sqrt(2);
					y4 = y3 - length / Math.sqrt(2);
				}else{
					x4 = x - length / Math.sqrt(2);
					y4 = y - length / Math.sqrt(2);
				}

			}else{
				System.out.println("Error in direction");
			}
		}else{
			x3 = 0;
			y3 = 0;
			x4 = 0;
			y4 = 0;
		}

		addBlock(x, y, x2, y2, length, isReversed, prevId, nextId, id, switchBlockId, isSwitch, x3, y3, x4, y4, length, secondSwitchPosition, invertedSwitch);

	}

	private void addBlock(double x, double y, double x2, double y2, double length, boolean isReversed, int prevBlockId, int nextBlockId, int id, int switchBlock, boolean isSwitch, double x3, double y3, double x4, double y4, double switchBlockLength, boolean secondSwitchPosition, boolean invertedSwitch){
		blockArray.add(new Block(blockArray, x, y, x2, y2, length, isReversed, prevBlockId, nextBlockId, id, switchBlock, isSwitch, x3, y3, x4, y4, switchBlockLength, secondSwitchPosition, invertedSwitch));
	}




	//interaface
	public int[] getBlockIdArray(){
		int length = blockArray.size();
		int[] blockIntArray = new int[length];
		for(int i = 0; i < length; i++){
			blockIntArray[i] = blockArray.get(i).getId();
		}
		return blockIntArray;
	}





}
