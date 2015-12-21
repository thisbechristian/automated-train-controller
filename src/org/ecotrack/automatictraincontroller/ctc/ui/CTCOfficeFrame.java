package org.ecotrack.automatictraincontroller.ctc.ui;

/**
 * @brief CTCOfficeFrame user interface.
 * <p>This class implements the main user interface for the CTC office.</p>
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.ecotrack.automatictraincontroller.ctc.CTC;
import org.ecotrack.automatictraincontroller.ctc.ui.AboutDialog;
import org.ecotrack.automatictraincontroller.ctc.ui.SchedulePanel;
import org.ecotrack.automatictraincontroller.trackmodel.GUIPanel;
import org.ecotrack.automatictraincontroller.trackmodel.Track;

/**
 * @brief CTCOfficeFrame main UI.
 * <p>This class is a subclass of JFrame, and implements the main UI for the
 * CTC office.</p>
 * @author John C. Matty
 * @version 0.1a
 */
public class CTCOfficeFrame extends JFrame {
		// Menu bar.
		private JMenuBar menuBar;

		private JMenu fileMenu;
		private JMenu trainMenu;
		private JMenu trackMenu;
		private JMenu scheduleMenu;
		private JMenu helpMenu;

		private ImageIcon exitIcon;
		private ImageIcon aboutIcon;
		private ImageIcon helpIcon;

		private JMenuItem exit;
		// private JMenuItem viewTrain;
		private JMenuItem dispatchTrain;
		private JMenuItem setTrainSpeedAndAuthority;
		private JMenuItem viewTrack;
		private JMenuItem schedule;
		private JMenuItem about;
		private JMenuItem help;

		// Tool bar.
		private JToolBar toolBar;

		private JButton exitButton;
		private JButton helpButton;

		// UI.

		private JTabbedPane trainPane;
		private JTabbedPane trackPane;
		private JTabbedPane schedulePane;
		//private SchedulePanel schedulePanel;
		private GUIPanel trackViewPanel;

		private JTextField speedTextField;
		private JTextField authorityTextField;
		private JTextField trainIDTextField;

		// Main icon.
		private ImageIcon mainIcon;

		// Track.
		private Track track;

		// CTC.
		private CTC ctc;

		/**
		 * @brief Default constructor.
		 * <p>Construct and initialize a <code>CTCOfficeFrame</code>.
		 */
		public CTCOfficeFrame(CTC ctc, Track t) {
				this.ctc = ctc;
				this.track = t;

				// Set the layout.
				setLayout(new BorderLayout());

				// Build the menu bar and tool bar.
				buildMenuBar();
				buildToolBar();

        // Set some properties for the frame.

				// Set the icon for the application.
				setIconImage(new ImageIcon("images/CTCOffice.png").getImage());
				// Set the title.
				setTitle("CTC Office");
				// Set the size.
				pack();
				// Do nothing upon pressing close button.
				addWindowListener(new HandleCloseListener());
				setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

				// Show it.
				setVisible(true);
		}

		// Update

		public void update(int clk) {
				// First, update the track.
				ctc.update(clk);
				track.update(clk);
				trackViewPanel.update(clk);
		}

		/*
		 * Helper functions to build the CTCOfficeFrame menu bar.
		 */

		private void buildMenuBar() {
				menuBar = new JMenuBar();
				setJMenuBar(menuBar);

				buildFileMenu();
				buildTrainMenu();
				buildTrackMenu();
				buildScheduleMenu();
				buildHelpMenu();

				initUI();
		}

		private void buildFileMenu() {
        // Create the icons for the menu.
				exitIcon = new ImageIcon(getClass().getResource("/images/icons/file/exit.png"));

				fileMenu = new JMenu("File");
				menuBar.add(fileMenu);

				exit = new JMenuItem("Exit", exitIcon);

				exit.setMnemonic(KeyEvent.VK_Q);

				exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
																									 ActionEvent.CTRL_MASK));

				exit.setToolTipText("Exit CTCOffice");

				exit.addActionListener(new ExitListener());

				fileMenu.add(exit);
				//fileMenu.addSeparator();
		}

		private void buildTrainMenu() {
        // Create the icons for the menu.
				//viewTrainIcon = new ImageIcon(getClass().getResource("/images/icons/train/viewTrain.png"));
				//setTrainSpeedAndAuthorityIcon = new ImageIcon(getClass().getResource("/images/icons/train/setTrainSpeedAndAuthority.png"));

				trainMenu = new JMenu("Train");
				menuBar.add(trainMenu);

				// viewTrain = new JMenuItem("View train status");//, viewTrainIcon);
				dispatchTrain = new JMenuItem("Dispatch Train");
				setTrainSpeedAndAuthority = new JMenuItem("Set train speed and authority");//, setTrainSpeedAndAuthorityIcon);

				// viewTrain.setMnemonic(KeyEvent.VK_V);
				dispatchTrain.setMnemonic(KeyEvent.VK_D);
				setTrainSpeedAndAuthority.setMnemonic(KeyEvent.VK_A);

				// viewTrain.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				// 																					 ActionEvent.CTRL_MASK));
				dispatchTrain.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
																														ActionEvent.CTRL_MASK));
				setTrainSpeedAndAuthority.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
																																				ActionEvent.CTRL_MASK));

				// viewTrain.setToolTipText("View the status of a selected train.");
				dispatchTrain.setToolTipText("Dispatch a train at the Yard.");
				setTrainSpeedAndAuthority.setToolTipText("Set the speed and authority of a selected train.");

				// viewTrain.addActionListener(new ViewTrainListener());
				dispatchTrain.addActionListener(new DispatchTrainActionListener());
				setTrainSpeedAndAuthority.addActionListener(new SetTrainSpeedAndAuthorityActionListener());

				// trainMenu.add(viewTrain);
				trainMenu.add(dispatchTrain);
				trainMenu.addSeparator();
				trainMenu.add(setTrainSpeedAndAuthority);
		}

		private void buildTrackMenu() {
        // Create the icons for the menu.
				//viewTrackIcon = new ImageIcon(getClass().getResource("/images/icons/track/viewTrack.png"));

				trackMenu = new JMenu("Track");
				menuBar.add(trackMenu);

				// viewTrack = new JMenuItem("View track status");//, viewTrackIcon);

				// viewTrack.setMnemonic(KeyEvent.VK_V);

				// viewTrack.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				// 																					 ActionEvent.CTRL_MASK));

				// viewTrack.setToolTipText("View the status of a selected block.");

				// viewTrack.addActionListener(new ViewTrackListener());

				// trackMenu.add(viewTrack);
				//trackMenu.addSeparator();
		}

		private void buildScheduleMenu() {
        // Create the icons for the menu.
				//scheduleIcon = new ImageIcon(getClass().getResource("/images/icons/schedule/schedule.png"));

				scheduleMenu = new JMenu("Schedule");
				menuBar.add(scheduleMenu);

				schedule = new JMenuItem("Schedule");//, scheduleIcon);

				schedule.setMnemonic(KeyEvent.VK_S);

				schedule.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
																									 ActionEvent.CTRL_MASK));

				schedule.setToolTipText("Schedule");

				schedule.addActionListener(new ScheduleListener());

				scheduleMenu.add(schedule);
				//scheduleMenu.addSeparator();
		}

    private void buildHelpMenu() {
				aboutIcon = new ImageIcon(getClass().getResource("/images/icons/help/about.png"));
				helpIcon = new ImageIcon(getClass().getResource("/images/icons/help/help.png"));

				helpMenu = new JMenu("Help");
				menuBar.add(helpMenu);
				about = new JMenuItem("About", aboutIcon);
				help = new JMenuItem("Help", helpIcon);

				about.setMnemonic(KeyEvent.VK_A);
				help.setMnemonic(KeyEvent.VK_H);

				help.setAccelerator(KeyStroke.getKeyStroke("F1"));

				about.setToolTipText("Provides information about CTCOffice");
				help.setToolTipText("Provides help for CTCOffice");

				about.addActionListener(new AboutListener());
				help.addActionListener(new HelpListener());

				helpMenu.add(about);
				helpMenu.addSeparator();
				helpMenu.add(help);
		}

		/*
		 * Helper functions to build the CTCOfficeFrame tool bar.
		 */

		private void buildToolBar() {
				// build toolbar
				toolBar = new JToolBar();
				add(toolBar, BorderLayout.NORTH);

				// add the buttons
				addFileButtonsToToolBar();
				addTrainButtonsToToolBar();
				addTrackButtonsToToolBar();
				addScheduleButtonsToToolBar();
				addHelpButtonsToToolBar();
		}

		private void addFileButtonsToToolBar() {
				exitButton = new JButton(exitIcon);

				exitButton.setToolTipText("Exit CTCOffice");

				exitButton.addActionListener(new ExitListener());

				toolBar.add(exitButton);
		}

		private void addTrainButtonsToToolBar() {
		}

		private void addTrackButtonsToToolBar() {
		}

		private void addScheduleButtonsToToolBar() {
		}

    private void addHelpButtonsToToolBar() {
				helpButton = new JButton(helpIcon);

				helpButton.setToolTipText("Provides help for MapEditor");

				helpButton.addActionListener(new HelpListener());

				toolBar.add(helpButton);
		}

		private void initUI() {
				// create table of contents split panel
				trainPane = new JTabbedPane();
				trackPane = new JTabbedPane();
				schedulePane = new JTabbedPane();

				// column headers
				String[] cols = {"Property", "Value"};
				// data
				Object[][] train_rows = {
						{"Length", ""},
						{"Width", ""},
						{"Height", ""},
						{"Number of Cars", ""},
						{"Mass", ""},
						{"Crew Count", ""},
						{"Passenger Count", ""},
						{"Current Block Number", ""},
						{"Total Distance Traveled", ""},
						{"Time to Next Break", ""},
						{"Doors", ""},
						{"Lights", ""}
				};
				JTable trainStatsTable = new JTable(train_rows, cols)
						{
								public boolean isCellEditable(int row, int column) {
										return false;
								}
						};
				trainPane.addTab("", null, trainStatsTable, "TRAIN 0");
				trainPane.setMnemonicAt(0, KeyEvent.VK_0);

				//trainPane.setMinimumSize(new Dimension(100, 300));

				//schedulePanel = new SchedulePanel();
				// column headers
				String[] schedule_cols = {"Times", "Mile", "Departure/Arrival", "Destination"};
				// data
				Object[][] schedule_rows = {
						{"", "", "", ""}
				};
				JTable scheduleTable = new JTable(schedule_rows, schedule_cols)
						{
								public boolean isCellEditable(int row, int column) {
										return false;
								}
						};

				schedulePane.addTab("", null, scheduleTable, "TRACK 0");
				schedulePane.setMnemonicAt(0, KeyEvent.VK_0);


				trackViewPanel = new GUIPanel(track);

				JScrollPane scheduleScrollPane = new JScrollPane(schedulePane);
				scheduleScrollPane.setHorizontalScrollBarPolicy(
						JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scheduleScrollPane.setVerticalScrollBarPolicy(
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				//scheduleScrollPane.setMinimumSize(new Dimension(150, 500));

				JSplitPane trainTrackSplitPane = new JSplitPane
						(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(trainPane),
						 new JScrollPane(trackPane));
				JSplitPane trainModelScheduleSplitPane = new JSplitPane
						(JSplitPane.HORIZONTAL_SPLIT, trackViewPanel,
						 new JScrollPane(schedulePane));
				JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
																							trainModelScheduleSplitPane,
																											trainTrackSplitPane);
				splitPane.setOneTouchExpandable(true);
				splitPane.setContinuousLayout(true);

				add(splitPane, BorderLayout.CENTER);
				pack();
		}

    private int exitCTCOffice() {
				int res = -1;
				try {
						res = JOptionPane.showConfirmDialog(null, "Are you sure that you wish to exit?");
						if (res == JOptionPane.YES_OPTION) {
								// ....
						}
				} catch (Exception e) {
				} finally {
						return res;
				}
		}

    private void showAboutDialog() {
				AboutDialog adlg = new AboutDialog();
				adlg.addArtists("John Matty");

				adlg.setCopyright("(C) 2015 John C. Matty");

				ArrayList<String> descript = new ArrayList<String>();
				descript.add("Description will go here.  ");
				adlg.setDescription(descript);

				ArrayList<String> devers = new ArrayList<String>();
				devers.add("John Matty");
				adlg.addDevelopers(devers);

				adlg.addDocWriters("John Matty");

				adlg.setImageIcon(mainIcon);

				adlg.setName("CTCOffice");

				adlg.setVersion(0.0);

				// show it
				adlg.updateUI();
				adlg.show(true);
		}

		private void showHelpDialog() {
				// do nothing for now
				return;
		}

    private class HandleCloseListener extends WindowAdapter {
				public void windowClosing(WindowEvent e) {
						if (exitCTCOffice() == JOptionPane.YES_OPTION) {
								CTCOfficeFrame.this.dispose();
						}
						else return;
				}
		}

    /**
     * ActionListener to handle proper exiting of the application
     */
		private class ExitListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
						if (exitCTCOffice() == JOptionPane.YES_OPTION) {
								CTCOfficeFrame.this.dispose();
						}
						else return;
				}
		}

    /**
     * ActionListener to handle dispatching trains.
     */
		private class DispatchTrainActionListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
						/// @todo Dialog to enter in line.
						ctc.dispatchTrain(0);
				}
		}

    /**
     * ActionListener to handle setting the speed and authority of a train..
     */
		private class SetTrainSpeedAndAuthorityActionListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
						JDialog dlg = new JDialog(CTCOfficeFrame.this,
																			"Set Train Speed/Authority.", true);

						GridLayout gl = new GridLayout(4, 2);
						JPanel panel = new JPanel();
						panel.setLayout(gl);

						panel.add(new JLabel("Train ID:"));
						trainIDTextField = new JTextField();
						panel.add(trainIDTextField);

						panel.add(new JLabel("Speed:"));
						speedTextField = new JTextField();
						panel.add(speedTextField);

						panel.add(new JLabel("Authority:"));
						authorityTextField = new JTextField();
						panel.add(authorityTextField);

						JButton cancelButton = new JButton("Cancel");
						cancelButton.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
												dlg.dispose();
												// JDialog dlg = ((JButton)e.getComponent()).getParent()).getParent();
												// // Just hide.
												// setVisible(false);
										}
								});
						JButton okButton = new JButton("OK");
						okButton.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
												int trainID = Integer.parseInt(trainIDTextField.getText());
												double speed = Double.parseDouble(speedTextField.getText());
												double authority = Double.parseDouble(authorityTextField.getText());

												// Dispatch the train.
												ctc.setTrainSpeedAndAuthority(trainID, speed, authority);

												// Now we want to dispose like above.
												dlg.dispose();
										}
								});

						panel.add(cancelButton);
						panel.add(okButton);

						dlg.setContentPane(panel);
						dlg.pack();
						dlg.setDefaultCloseOperation(
								JDialog.DO_NOTHING_ON_CLOSE);
						// dlg.addWindowListener(new WindowAdapter() {
						// 				public void windowClosing(WindowEvent we) {
						// 				}
						// 		});
						dlg.setVisible(true);
				}
		}

    /**
     * ActionListener to handle scheduling of a train.
     */
		private class ScheduleListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
						ctc.routeTrains();
				}
		}

    /**
     * ActionListener to display information about the application.
		 */
		private class AboutListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
						showAboutDialog();
				}
		}

		/**
     * handles showing the help pages to the user.
		 */
		private class HelpListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
						showHelpDialog();
				}
		}
}
