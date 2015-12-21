package org.ecotrack.automatictraincontroller.traincontroller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JPasswordField;
import java.util.*;

public class TrainControllerGui {

	private JFrame frame;
	private JTextField next;
	private JTextField username;
	private JPasswordField password;
	private JTextField trainLine;
	private JTextField trainID;

	private TrainController currentTrain;
	//private TrainController controller[];
	private ArrayList<TrainController> list = new ArrayList<TrainController>();

	public void addTrainController(TrainController train)
	{
		list.add(train);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainControllerGui window = new TrainControllerGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TrainControllerGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 766, 579);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		final JLabel lblTrainNum = new JLabel("Train #:");
		lblTrainNum.setVisible(false);
		lblTrainNum.setBounds(10, 14, 46, 14);
		frame.getContentPane().add(lblTrainNum);

		final JSpinner trainIDIn = new JSpinner();
		trainIDIn.setVisible(false);
		trainIDIn.setBounds(54, 11, 98, 20);
		frame.getContentPane().add(trainIDIn);

		final JPanel panelTrain = new JPanel();
		panelTrain.setVisible(false);
		panelTrain.setBounds(10, 269, 730, 260);
		frame.getContentPane().add(panelTrain);
		panelTrain.setLayout(null);

		final JSpinner speed = new JSpinner();
		speed.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		speed.setBounds(49, 232, 103, 20);
		panelTrain.add(speed);

		JLabel lblSpeed = new JLabel("Speed:");
		lblSpeed.setBounds(10, 235, 46, 14);
		panelTrain.add(lblSpeed);

		JLabel lblNext = new JLabel("Next Stop:");
		lblNext.setBounds(297, 73, 64, 14);
		panelTrain.add(lblNext);

		next = new JTextField();
		next.setHorizontalAlignment(SwingConstants.CENTER);
		next.setText("Station Square");
		next.setEditable(false);
		next.setBounds(371, 70, 349, 20);
		panelTrain.add(next);
		next.setColumns(10);

		JPanel panelFaults = new JPanel();
		panelFaults.setBounds(297, 140, 423, 109);
		panelTrain.add(panelFaults);
		panelFaults.setLayout(null);

		JLabel lblFaults = new JLabel("Faults:");
		lblFaults.setBounds(10, 11, 46, 14);
		panelFaults.add(lblFaults);

		final JToggleButton trainEngineFailure = new JToggleButton("Train Engine Failure");
		trainEngineFailure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				currentTrain.setTrainEngineFailure(trainEngineFailure.isSelected());
				trainEngineFailure.setSelected(trainEngineFailure.isSelected());
			}
		});
		trainEngineFailure.setBounds(66, 7, 347, 23);
		panelFaults.add(trainEngineFailure);

		final JToggleButton signalPickupFailure = new JToggleButton("Signal Pickup Failure");
		signalPickupFailure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentTrain.setSignalPickupFailure(signalPickupFailure.isSelected());
				signalPickupFailure.setSelected(signalPickupFailure.isSelected());
			}
		});
		signalPickupFailure.setBounds(66, 41, 347, 23);
		panelFaults.add(signalPickupFailure);

		final JToggleButton brakeFailure = new JToggleButton("Brake Failure");
		brakeFailure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentTrain.setBrakeFailure(brakeFailure.isSelected());
				brakeFailure.setSelected(brakeFailure.isSelected());
			}
		});
		brakeFailure.setBounds(66, 75, 347, 23);
		panelFaults.add(brakeFailure);

		JLabel lblTrainLine = new JLabel("Train Line: ");
		lblTrainLine.setBounds(297, 45, 64, 14);
		panelTrain.add(lblTrainLine);

		trainLine = new JTextField();
		trainLine.setEditable(false);
		trainLine.setBounds(371, 42, 103, 20);
		panelTrain.add(trainLine);
		trainLine.setColumns(10);

		final JLabel doorStatus = new JLabel("Closed");
		doorStatus.setBounds(123, 45, 46, 14);
		panelTrain.add(doorStatus);

		final JLabel brakeStatus = new JLabel("On");
		brakeStatus.setBounds(123, 162, 46, 14);
		panelTrain.add(brakeStatus);

		final JLabel lightStatus = new JLabel("Off");
		lightStatus.setBounds(123, 103, 46, 14);
		panelTrain.add(lightStatus);

		JButton btnOpenDoors = new JButton("Open Doors");
		btnOpenDoors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doorStatus.setText("Open");
				currentTrain.setDoors(false);
			}
		});
		btnOpenDoors.setBounds(10, 11, 103, 23);
		panelTrain.add(btnOpenDoors);

		JButton btnCloseDoors = new JButton("Close Doors");
		btnCloseDoors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doorStatus.setText("Closed");
				currentTrain.setDoors(true);
			}
		});
		btnCloseDoors.setBounds(123, 11, 103, 23);
		panelTrain.add(btnCloseDoors);

		JLabel lblDoorStatus = new JLabel("Door Status:");
		lblDoorStatus.setBounds(10, 45, 74, 14);
		panelTrain.add(lblDoorStatus);

		JButton btnLightsOn = new JButton("Lights On");
		btnLightsOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lightStatus.setText("On");
				currentTrain.setLights(false);
			}
		});
		btnLightsOn.setBounds(10, 69, 103, 23);
		panelTrain.add(btnLightsOn);

		JButton btnLightsOff = new JButton("Lights Off");
		btnLightsOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lightStatus.setText("Off");
				currentTrain.setLights(true);
			}
		});
		btnLightsOff.setBounds(123, 69, 103, 23);
		panelTrain.add(btnLightsOff);

		JLabel lblLightsStatus = new JLabel("Lights Status:");
		lblLightsStatus.setBounds(10, 103, 74, 14);
		panelTrain.add(lblLightsStatus);

		JButton btnBrakeOn = new JButton("Brake On");
		btnBrakeOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				brakeStatus.setText("On");
				currentTrain.setServiceBrake(true);
			}
		});
		btnBrakeOn.setBounds(10, 128, 103, 23);
		panelTrain.add(btnBrakeOn);

		JButton btnBrakeOff = new JButton("Brake Off");
		btnBrakeOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				brakeStatus.setText("Off");
				currentTrain.setServiceBrake(false);
			}
		});
		btnBrakeOff.setBounds(123, 128, 103, 23);
		panelTrain.add(btnBrakeOff);

		JLabel lblBrakeStatus = new JLabel("Brake Status:");
		lblBrakeStatus.setBounds(10, 162, 74, 14);
		panelTrain.add(lblBrakeStatus);

		JLabel lblTrainId = new JLabel("Train ID:");
		lblTrainId.setBounds(297, 15, 64, 14);
		panelTrain.add(lblTrainId);

		trainID = new JTextField();
		trainID.setEditable(false);
		trainID.setBounds(371, 12, 103, 20);
		panelTrain.add(trainID);
		trainID.setColumns(10);

		JButton setSpeed = new JButton("Set");
		setSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentTrain.setSpeedLimit((double)speed.getValue());
			}
		});
		setSpeed.setBounds(162, 231, 89, 23);
		panelTrain.add(setSpeed);

		final JButton btnTrainNum = new JButton("Select");
		btnTrainNum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//pick new train
				for(int i = 0; i < list.size(); i++) {
					if(list.get(i).getID() == (Integer) trainIDIn.getValue()) {
						currentTrain = list.get(i);
					}
				}

				//Update Train

				if(currentTrain.getDoors())
					doorStatus.setText("Closed");
				else
					doorStatus.setText("Open");

				if(currentTrain.getLights())
					brakeStatus.setText("Off");
				else
					brakeStatus.setText("On");

				if(currentTrain.getEmergencyBrake())
					lightStatus.setText("On");
				else
					lightStatus.setText("Off");

				speed.setValue((double) currentTrain.getSpeed());
				//trainLine.setText(currentTrain.getLine());
				trainID.setText(trainIDIn.getValue().toString());

				trainEngineFailure.setSelected(currentTrain.getTrainEngineFailure());
				signalPickupFailure.setSelected(currentTrain.getSignalPickupFailure());
				brakeFailure.setSelected(currentTrain.getBrakeFailure());
			}
		});
		btnTrainNum.setVisible(false);
		btnTrainNum.setBounds(162, 10, 89, 23);
		frame.getContentPane().add(btnTrainNum);


		final JPanel panelLogin = new JPanel();
		panelLogin.setBounds(10, 39, 730, 220);
		frame.getContentPane().add(panelLogin);
		panelLogin.setLayout(null);

		final JLabel lblInvalid = new JLabel("Invalid username or password.");
		lblInvalid.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvalid.setVisible(false);
		lblInvalid.setForeground(Color.RED);
		lblInvalid.setBounds(260, 36, 240, 14);
		panelLogin.add(lblInvalid);

		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(username.getText().equals("admin") && new String(password.getPassword()).equals("password"))
				{
					panelTrain.setVisible(true);
					btnTrainNum.setVisible(true);
					lblTrainNum.setVisible(true);
					trainIDIn.setVisible(true);

					panelLogin.setVisible(false);
				}
				else
				{
					lblInvalid.setVisible(true);
				}

			}
		});
		btnLogin.setBounds(260, 136, 89, 23);
		panelLogin.add(btnLogin);

		username = new JTextField();
		username.setBounds(260, 61, 240, 20);
		panelLogin.add(username);
		username.setColumns(10);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblUsername.setBounds(260, 46, 81, 14);
		panelLogin.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblPassword.setBounds(260, 92, 58, 14);
		panelLogin.add(lblPassword);

		password = new JPasswordField();
		password.setBounds(260, 108, 240, 20);
		panelLogin.add(password);


	}
}
