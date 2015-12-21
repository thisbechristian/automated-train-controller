package org.ecotrack.automatictraincontroller.trackmodel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.ArrayList;

public class GUIPanel extends JPanel{

	int SQUARE_SIZE = 15;
	double zoom = 0.35;
	int shiftX = 3300;
	int shiftY = 1400;
	Track track;
	JButton ctcFakeInput;
	JButton toggleSwitch;
	JButton toggleSwitch2;
	JButton toggleSwitch3;
	JButton toggleSwitch4;
	JButton toggleSwitch5;
	JButton toggleSwitch6;
	JButton plus;
	JButton minus;
	JButton left;
	JButton right;
	JButton up;
	JButton down;


	public GUIPanel(Track track){
		this.track = track;

		ctcFakeInput = new JButton("Pull Train With Velocity");
		toggleSwitch = new JButton("Switch 1");
		toggleSwitch2 = new JButton("Switch 2");
		toggleSwitch3 = new JButton("Switch 3");
		toggleSwitch4 = new JButton("Switch 4");
		toggleSwitch5 = new JButton("Switch 5");
		toggleSwitch6 = new JButton("Switch 6");
		plus = new JButton("+");
		minus = new JButton("-");
		left = new JButton("<");
		right = new JButton(">");
		up = new JButton("^");
		down = new JButton("v");


		JTextField speedInput = new JTextField(3);
		HandlerClass handler = new HandlerClass(speedInput);
		ctcFakeInput.addActionListener(handler);
		toggleSwitch.addActionListener(handler);
		toggleSwitch2.addActionListener(handler);
		toggleSwitch3.addActionListener(handler);
		toggleSwitch4.addActionListener(handler);
		toggleSwitch5.addActionListener(handler);
		toggleSwitch6.addActionListener(handler);
		plus.addActionListener(handler);
		minus.addActionListener(handler);
		left.addActionListener(handler);
		right.addActionListener(handler);
		up.addActionListener(handler);
		down.addActionListener(handler);
		this.add(speedInput);
		this.add(ctcFakeInput);
		this.add(plus);
		this.add(minus);
		this.add(left);
		this.add(right);
		this.add(up);
		this.add(down);
		this.add(toggleSwitch);
		this.add(toggleSwitch2);
		this.add(toggleSwitch3);
		this.add(toggleSwitch4);
		this.add(toggleSwitch5);
		this.add(toggleSwitch6);
		this.setMinimumSize(new Dimension(700, 500));
	}


	public void paintComponent(Graphics g){

		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		ArrayList<Line> lineArray = track.getLine();
		Yard yard = lineArray.get(0).getYard();
		g.setColor(Color.GREEN);
		g.drawRect((int) ((yard.getXLoc()+shiftX)*zoom), (int)((yard.getYLoc()+shiftY)*zoom), (int)(yard.getWidth()*zoom), (int)(yard.getWidth()*zoom));
		//g.drawString("YARD", (int) ((yard.getXLoc() + 10)*zoom), (int) ((yard.getYLoc() + (yard.getWidth()/2))*zoom));

		//Assuming 1 line for now lineArray.get(0)
		ArrayList<Block> blockArray = lineArray.get(0).getBlocks();
		ArrayList<TrainWrapper> trainArray = lineArray.get(0).getTrains();
		for(int i = 0; i < blockArray.size(); i++){
			paintBlock(g, blockArray.get(i));
			paintBlockInfo(g, i, blockArray.get(i));
		}

		for(int i = 0; i < trainArray.size(); i++){
			//paintTrain(g,trainArray.get(i).getX(),trainArray.get(i).getY(), trainArray.get(i).getLength(), trainArray.get(i).getId(), trainArray.get(i).getSpeed());
			paintTrain(g, trainArray.get(i));
			paintTrainInfo(g, i, trainArray.get(i));
		}

	}

	public void paintBlock(Graphics g, Block block){
		g.setColor(Color.RED);
		g.drawString(block.getId() + "", (int) ((block.getX()+shiftX)*zoom), (int) ((block.getY() - SQUARE_SIZE + shiftY)*zoom));

		g.setColor(Color.GREEN);
		//g.fillRect((int)block.getX(), (int) block.getY() - SQUARE_SIZE / 2, SQUARE_SIZE, SQUARE_SIZE);
		//g.drawLine((int)block.getX(), (int) block.getY(), (int) (block.getX() + block.getLength()), (int) block.getY());


		if(block.getIsSwitch()){
			g.setColor(Color.MAGENTA);
			if(block.getSwitchPosition()){
				g.setColor(Color.GRAY);
			}
			g.drawLine((int)((block.getX()+shiftX)*zoom), (int) ((block.getY()+shiftY)*zoom), (int) ((block.getX2()+shiftX)*zoom), (int) ((block.getY2()+shiftY)*zoom));
			if(!block.getSwitchPosition()){
				g.setColor(Color.GRAY);
			}else{
				g.setColor(Color.MAGENTA);
			}
			g.drawLine((int)((block.getX3()+shiftX)*zoom), (int) ((block.getY3()+shiftY)*zoom), (int) ((block.getX4()+shiftX)*zoom), (int) ((block.getY4()+shiftY)*zoom));
			g.setColor(Color.GREEN);
		}else{
			g.drawLine((int)((block.getX()+shiftX)*zoom), (int) ((block.getY()+shiftY)*zoom), (int) ((block.getX2()+shiftX)*zoom), (int) ((block.getY2()+shiftY)*zoom));
		}

		if(block.getDirection()){
			//g.fillRect((int)(block.getX() + block.getLength()), (int) block.getY() - SQUARE_SIZE / 2, SQUARE_SIZE, SQUARE_SIZE);
			g.fillRect((int)((block.getX2() - (SQUARE_SIZE) / 2 +shiftX)*zoom), (int) ((block.getY2() - (SQUARE_SIZE / 2) +shiftY)*zoom), (int)(SQUARE_SIZE*zoom), (int) (SQUARE_SIZE*zoom));
		}else{
			g.drawRect((int)((block.getX2() - (SQUARE_SIZE/ 2) +shiftX)*zoom), (int) ((block.getY2() - (SQUARE_SIZE / 2) +shiftY)*zoom), (int)(SQUARE_SIZE*zoom), (int)(SQUARE_SIZE*zoom));
		}
		if(block.getOccupancy()){
			g.setColor(Color.RED);
		}else{
			g.setColor(Color.GREEN);
		}
		g.fillOval((int) ((block.getX() + SQUARE_SIZE +shiftX)*zoom), (int) ((block.getY() + SQUARE_SIZE +shiftY)*zoom), (int)(SQUARE_SIZE*zoom), (int)(SQUARE_SIZE*zoom));
		if(block.getHeater()){
			g.setColor(Color.RED);
			g.drawString("H", (int) ((block.getX() + 2 * SQUARE_SIZE +shiftX)*zoom), (int) ((block.getY() + SQUARE_SIZE +shiftY)*zoom));
		}


	}

	public void paintBlockInfo(Graphics g, int i, Block block){
		/*
		g.setColor(Color.GREEN);
		String string = "BLOCK ID:" + block.getId() + "  Length: " + block.getLength() + " X: " + block.getX() + " Y: " + block.getY();
		g.drawString(string, 0, 400 + 20 * i );
		*/

	}

	public void paintTrainInfo(Graphics g, int i, TrainWrapper train){
		/*
		g.setColor(Color.BLUE);
		String string = "TRAIN ID: 0 Length: " + (int) train.getTrain().getLength() + " X: " + (int) train.getXLoc() + " Y: " + train.getYLoc();
		//String string = "TRAIN ID:" + train.getId() + "  Length: " + (int) train.getLength() + " X: " + (int) train.getX() + " Y: " + train.getY();
		g.drawString(string, 400, 400 + 20 * i );
		*/
	}

	//public void paintTrain(Graphics g, double x, double y, double length, int id, double speed){
	public void paintTrain(Graphics g, TrainWrapper train){
		g.setColor(Color.BLUE);
		g.fillRect((int) ((train.getXLoc() - (SQUARE_SIZE/2) +shiftX)*zoom), (int) ((train.getYLoc() - (SQUARE_SIZE/3) +shiftY)*zoom), (int)(SQUARE_SIZE*zoom), (int)((SQUARE_SIZE - (SQUARE_SIZE/5))*zoom));
		g.drawString(((int)train.getTrain().getCurrentSpeed()*10)/10 + "", (int)((train.getXLoc() - train.getTrain().getLength() +shiftX)*zoom), (int) ((train.getYLoc() - (2 * SQUARE_SIZE) +shiftY)*zoom));
	}



	public void update(int CLOCK){
		repaint();
	}

	private class HandlerClass implements ActionListener{

		JTextField speedInput;

		public HandlerClass(JTextField speedInput){
			this.speedInput = speedInput;
		}

		////////INPUT FROM OTHER MODULES
		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			try{
				if(event.getSource().equals(ctcFakeInput)){
					double speed = Double.parseDouble(speedInput.getText());
					ArrayList<Line> lineArray = track.getLine();
					TrainWrapper train = lineArray.get(0).addTrain();


					//train.getTrain().setSpeed(speed); //for individual train speeds

					ArrayList<Block> blocks = lineArray.get(0).getBlocks(); //for sending speeds to blocks
					for(int i = 0; i < blocks.size(); i++){
						blocks.get(i).setSpeed(speed);
						blocks.get(i).setAuthority(10000);
					}
				//15, 20, 86, 101, 141,1
				}else if(event.getSource().equals(toggleSwitch)){
					int switchNumber = 1;
					boolean position = track.getLine().get(0).getBlocks().get(switchNumber).getSwitchPosition();
					track.getLine().get(0).getBlocks().get(switchNumber).setSwitchPosition(!position);
				}else if(event.getSource().equals(toggleSwitch2)){
					int switchNumber = 15;
					boolean position = track.getLine().get(0).getBlocks().get(switchNumber).getSwitchPosition();
					track.getLine().get(0).getBlocks().get(switchNumber).setSwitchPosition(!position);
				}else if(event.getSource().equals(toggleSwitch3)){
					int switchNumber = 23;
					boolean position = track.getLine().get(0).getBlocks().get(switchNumber).getSwitchPosition();
					track.getLine().get(0).getBlocks().get(switchNumber).setSwitchPosition(!position);
				}else if(event.getSource().equals(toggleSwitch4)){
					int switchNumber = 89;
					boolean position = track.getLine().get(0).getBlocks().get(switchNumber).getSwitchPosition();
					track.getLine().get(0).getBlocks().get(switchNumber).setSwitchPosition(!position);
				}else if(event.getSource().equals(toggleSwitch5)){
					int switchNumber = 104;
					boolean position = track.getLine().get(0).getBlocks().get(switchNumber).getSwitchPosition();
					track.getLine().get(0).getBlocks().get(switchNumber).setSwitchPosition(!position);
				}else if(event.getSource().equals(toggleSwitch6)){
					int switchNumber = 145;
					boolean position = track.getLine().get(0).getBlocks().get(switchNumber).getSwitchPosition();
					track.getLine().get(0).getBlocks().get(switchNumber).setSwitchPosition(!position);
				}else if(event.getSource().equals(plus)){
					zoom += 0.025;
				}else if(event.getSource().equals(minus)){
					zoom -= 0.025;
				}else if(event.getSource().equals(left)){
					shiftX += 100;
				}else if(event.getSource().equals(right)){
					shiftX -= 100;
				}else if(event.getSource().equals(up)){
					shiftY += 100;
				}else if(event.getSource().equals(down)){
					shiftY -= 100;
				}else{
					System.out.println("Error reading user input");
				}
				repaint();
			}catch(Exception e){}

		}

	}


}
