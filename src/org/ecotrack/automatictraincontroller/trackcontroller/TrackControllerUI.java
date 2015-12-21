package trackcontroller;

//package org.ecotrack.automatictraincontroller.trackcontroller;
//import org.ecotrack.automatictraincontroller.trackmodel.Block;
//import org.ecotrack.automatictraincontroller.trackmodel.Track;
//import org.ecotrack.automatictraincontroller.trackcontroller.TrackControllerHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Boni
 */
public class TrackControllerUI extends javax.swing.JFrame {

    public TrackControllerHandler TrackController;
    private TrackController currentTrackController;
    private ArrayList<Block> currentTrackControllerBlocks;
    private final int GREEN_LINE_ID = 0;
    private final int RED_LINE_ID = 1;
    private final Track track;
    //Simulating CTC Scheduling
    private final ArrayList<Queue> routes = new ArrayList<>();
    private final ArrayList<Integer> lastOccupiedBlock = new ArrayList<>();

    /**
     * Creates new form TrackControllerUI
     *
     * @param track The track.
     * @param trackController The track controller.
     */
    public TrackControllerUI(Track track, TrackControllerHandler trackController) {
        initComponents();
        this.track = track;
        this.TrackController = trackController;
        theLog.setEditable(false);
        theLog.append("Welcome to the Track Controller. All of its outputs will be recorded here.");
        greenLineRadioButton.setSelected(true);
        updateTrackControllerComboBox();
        updateCommandComboBoxes();
    }

    public void update() {
        refreshBlockTable();
        simulateCTC();
    }

    private void simulateCTC() {
        int count = 0;
        for (Queue route : routes) {
            int last = lastOccupiedBlock.get(count);
            if (!route.isEmpty() && !TrackController.greenLine.getBlocks().get(last).getOccupancy()) {
                ArrayList<Integer> block = (ArrayList<Integer>) route.remove();
                int current = block.get(0);
                int next = block.get(1);
                int upcoming = block.get(2);
                boolean proceed = TrackController.proceedRequest(0, current, next, upcoming, 300, 100000);
                theLog.append("\nProceed " + current + " for Train " + count + " " + proceed);
                last = current;
                if (!proceed) {
                    route.clear();
                }
            }
            lastOccupiedBlock.set(count, last);
            count++;
        }
    }

    private void refreshBlockTable() {
        DefaultTableModel theModel = (DefaultTableModel) blockTable.getModel();
        theModel.setRowCount(currentTrackControllerBlocks.size());
        int counter = 0;
        for (Block block : currentTrackControllerBlocks) {

            double authority = (block.getAuthority() < 0) ? (double) theModel.getValueAt(counter, 3) : block.getAuthority();
            String blockState = currentTrackController.getBlockState(block.getId());

            String crossingState;
            if (block.getCrossing()) {
                crossingState = block.getCrossingActivated() ? "Activated" : "Deactivated";
            } else {
                crossingState = "";
            }

            String switchNumber = block.getIsSwitch() ? "" + block.getId() : "";
            String switchDestination = block.getIsSwitch()
                    ? block.getSwitchPosition() ? "" + block.getSwitchBlock() : "" + block.getNextBlock() : "";

            theModel.setValueAt(block.getId(), counter, 0);
            theModel.setValueAt(blockState, counter, 1);
            theModel.setValueAt(block.getSpeed(), counter, 2);
            theModel.setValueAt(authority, counter, 3);
            theModel.setValueAt(block.getCrossing(), counter, 4);
            theModel.setValueAt(crossingState, counter, 5);
            theModel.setValueAt(block.getIsSwitch(), counter, 6);
            theModel.setValueAt(switchNumber, counter, 7);
            theModel.setValueAt(switchDestination, counter, 8);
            counter++;
        }
    }

    private void updateTrackControllerComboBox() {
        trackControllerComboBox.removeAllItems();
        if (greenLineRadioButton.isSelected()) {
            for (TrackController tc : TrackController.greenTrackControllers) {
                trackControllerComboBox.addItem(tc.getId());
            }
        } else {
            for (TrackController tc : TrackController.redTrackControllers) {
                trackControllerComboBox.addItem(tc.getId());
            }
        }
        trackControllerComboBox.setSelectedIndex(0);
    }

    private void updateCommandComboBoxes() {
        int trackControllerID = trackControllerComboBox.getSelectedIndex();
        if (trackControllerID < 0) {
            return;
        }
        trackMaintenanceComboBox.removeAllItems();
        railroadCrossingComboBox.removeAllItems();
        switchComboBox.removeAllItems();
        railroadCrossingComboBox.setEnabled(true);
        switchComboBox.setEnabled(true);

        currentTrackController = greenLineRadioButton.isSelected()
                ? TrackController.greenTrackControllers.get(trackControllerID) : TrackController.redTrackControllers.get(trackControllerID);

        ArrayList<Block> blocks = new ArrayList<>(currentTrackController.blocks.values());
        ArrayList<Block> crossings = new ArrayList<>(currentTrackController.crossings.values());
        ArrayList<Block> switches = new ArrayList<>(currentTrackController.switches.values());

        for (Block block : blocks) {
            trackMaintenanceComboBox.addItem(block.getId());
        }
        for (Block crossing : crossings) {
            railroadCrossingComboBox.addItem(crossing.getId());
        }
        for (Block aswitch : switches) {
            switchComboBox.addItem(aswitch.getId());
        }
        if (switches.isEmpty()) {
            switchComboBox.setEnabled(false);
        }
        if (crossings.isEmpty()) {
            railroadCrossingComboBox.setEnabled(false);
        }

        currentTrackControllerBlocks = blocks;
        refreshBlockTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lineButtonGroup = new javax.swing.ButtonGroup();
        fileChooser = new javax.swing.JFileChooser();
        controllerTabbedPane = new javax.swing.JTabbedPane();
        controlPanel = new javax.swing.JPanel();
        trackControllerSelectionLabel = new javax.swing.JLabel();
        greenLineRadioButton = new javax.swing.JRadioButton();
        redLineRadioButton = new javax.swing.JRadioButton();
        lineLabel = new javax.swing.JLabel();
        trackControllerLabel = new javax.swing.JLabel();
        trackControllerComboBox = new javax.swing.JComboBox();
        PLCLabel = new javax.swing.JLabel();
        PLCTextField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        enterButton = new javax.swing.JButton();
        blockCommandLabel = new javax.swing.JLabel();
        trackMaintenanceComboBox = new javax.swing.JComboBox();
        trackMaintenanceLabel = new javax.swing.JLabel();
        railroadCrossingLabel = new javax.swing.JLabel();
        railroadCrossingComboBox = new javax.swing.JComboBox();
        trackMaintenanceButton = new javax.swing.JButton();
        railroadCrossingButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        switchLabel = new javax.swing.JLabel();
        switchComboBox = new javax.swing.JComboBox();
        switchButton = new javax.swing.JButton();
        proceedAlpha = new javax.swing.JButton();
        proceedBeta = new javax.swing.JButton();
        simulateCTCLabel = new javax.swing.JLabel();
        toggleAcceleration = new javax.swing.JButton();
        logPanel = new javax.swing.JPanel();
        logScrollPane = new javax.swing.JScrollPane();
        theLog = new javax.swing.JTextArea();
        gridPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        blockTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        trackControllerSelectionLabel.setText("Track Controller Selection");

        lineButtonGroup.add(greenLineRadioButton);
        greenLineRadioButton.setText("Green");
        greenLineRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                greenLineRadioButtonItemStateChanged(evt);
            }
        });

        lineButtonGroup.add(redLineRadioButton);
        redLineRadioButton.setText("Red");
        redLineRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                redLineRadioButtonItemStateChanged(evt);
            }
        });

        lineLabel.setText("Line");

        trackControllerLabel.setText("Track Controller");

        trackControllerComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                trackControllerComboBoxItemStateChanged(evt);
            }
        });

        PLCLabel.setText("PLC Logic File");

        PLCTextField.setText("Enter Filename or Browse...");

        browseButton.setText("Browse");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        enterButton.setText("Enter");
        enterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterButtonActionPerformed(evt);
            }
        });

        blockCommandLabel.setText("Block Commands");

        trackMaintenanceLabel.setText("Track Maintenance at Block:");

        railroadCrossingLabel.setText("Railroad Crossing at Block:");

        trackMaintenanceButton.setText("Toggle");
        trackMaintenanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trackMaintenanceButtonActionPerformed(evt);
            }
        });

        railroadCrossingButton.setText("Toggle");
        railroadCrossingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                railroadCrossingButtonActionPerformed(evt);
            }
        });

        switchLabel.setText("Track Switch at Switch:");

        switchButton.setText("Toggle");
        switchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchButtonActionPerformed(evt);
            }
        });

        proceedAlpha.setText("Dispatch Alpha");
        proceedAlpha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proceedAlphaActionPerformed(evt);
            }
        });

        proceedBeta.setText("Dispatch Beta");
        proceedBeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proceedBetaActionPerformed(evt);
            }
        });

        simulateCTCLabel.setText("Simulate CTC Routing:");

        toggleAcceleration.setText("Fast Acceleration");
        toggleAcceleration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleAccelerationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(controlPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(controlPanelLayout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lineLabel)
                                            .addComponent(greenLineRadioButton)
                                            .addComponent(redLineRadioButton))
                                        .addGap(18, 18, 18)
                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(trackControllerLabel)
                                            .addGroup(controlPanelLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(trackControllerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(PLCLabel)
                                            .addGroup(controlPanelLayout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(enterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(controlPanelLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(PLCTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(trackControllerSelectionLabel)
                                    .addComponent(blockCommandLabel)))
                            .addGroup(controlPanelLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(trackMaintenanceLabel)
                                    .addComponent(switchLabel)
                                    .addGroup(controlPanelLayout.createSequentialGroup()
                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(switchComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 150, Short.MAX_VALUE)
                                            .addComponent(trackMaintenanceComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(trackMaintenanceButton, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                            .addComponent(switchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(38, 38, 38)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(railroadCrossingLabel)
                                    .addGroup(controlPanelLayout.createSequentialGroup()
                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(railroadCrossingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(toggleAcceleration, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(proceedAlpha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(railroadCrossingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(proceedBeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(simulateCTCLabel))))
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1)))
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trackControllerSelectionLabel)
                .addGap(18, 18, 18)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lineLabel)
                    .addComponent(trackControllerLabel)
                    .addComponent(PLCLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(greenLineRadioButton)
                    .addComponent(trackControllerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PLCTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(enterButton))
                    .addComponent(redLineRadioButton))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(blockCommandLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trackMaintenanceLabel)
                    .addComponent(railroadCrossingLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trackMaintenanceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trackMaintenanceButton)
                    .addComponent(railroadCrossingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(railroadCrossingButton))
                .addGap(18, 18, 18)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(switchLabel)
                    .addComponent(simulateCTCLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(switchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(switchButton)
                    .addComponent(proceedAlpha)
                    .addComponent(proceedBeta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(toggleAcceleration)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        controllerTabbedPane.addTab("Control", controlPanel);

        theLog.setColumns(20);
        theLog.setRows(5);
        logScrollPane.setViewportView(theLog);

        javax.swing.GroupLayout logPanelLayout = new javax.swing.GroupLayout(logPanel);
        logPanel.setLayout(logPanelLayout);
        logPanelLayout.setHorizontalGroup(
            logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                .addContainerGap())
        );
        logPanelLayout.setVerticalGroup(
            logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addContainerGap())
        );

        controllerTabbedPane.addTab("Log", logPanel);

        blockTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Block Id", "State", "Speed", "Authority", "isRailroadCrossing", "Crossing Activated", "isSwitch", "Switch Id", "Switch Destination"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(blockTable);

        javax.swing.GroupLayout gridPanelLayout = new javax.swing.GroupLayout(gridPanel);
        gridPanel.setLayout(gridPanelLayout);
        gridPanelLayout.setHorizontalGroup(
            gridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gridPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                .addContainerGap())
        );
        gridPanelLayout.setVerticalGroup(
            gridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gridPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                .addContainerGap())
        );

        controllerTabbedPane.addTab("Track", gridPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(controllerTabbedPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(controllerTabbedPane)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void trackMaintenanceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trackMaintenanceButtonActionPerformed

        Integer blockId = (Integer) trackMaintenanceComboBox.getSelectedItem();
        Block block = currentTrackController.getBlock(blockId);
        int line = currentTrackController.getLine();
        String theLine = (line == GREEN_LINE_ID) ? "green" : "red";
        boolean open = block.getOpen();
        String state = open ? "Closing" : "Opening";
        if (currentTrackController.maintenanceRequest(line, blockId, !open)) {
            theLog.append("\n" + state + " the track at block " + blockId + " on the " + theLine + " line");
            refreshBlockTable();
        }
    }//GEN-LAST:event_trackMaintenanceButtonActionPerformed

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            PLCTextField.setText(f.getPath());
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    private void greenLineRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_greenLineRadioButtonItemStateChanged
        updateTrackControllerComboBox();
    }//GEN-LAST:event_greenLineRadioButtonItemStateChanged

    private void trackControllerComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_trackControllerComboBoxItemStateChanged
        updateCommandComboBoxes();
    }//GEN-LAST:event_trackControllerComboBoxItemStateChanged

    private void redLineRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_redLineRadioButtonItemStateChanged
        updateTrackControllerComboBox();
    }//GEN-LAST:event_redLineRadioButtonItemStateChanged

    private void enterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterButtonActionPerformed
        String plcFile = PLCTextField.getText();
        boolean invalid = false;
        //Return if file DNE
        if (!(new File(plcFile).exists())) {
            theLog.append("\nThe file '" + PLCTextField.getText() + "' does not Exist. Try again with a valid .plc file");
            JOptionPane.showMessageDialog(this, "The file '" + PLCTextField.getText() + "' does not Exist. Try again with a valid .plc file", "Invalid File", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (TrackController greenTrackController : TrackController.greenTrackControllers) {
            if (greenTrackController.loadPLC(plcFile)) {
                theLog.append("\nSuccessfully loaded in '" + PLCTextField.getText() + "' for Green Line Track Controller " + greenTrackController.getId());
            } else {
                theLog.append("\nCould not loaded in '" + PLCTextField.getText() + "' for Green Line Track Controller " + greenTrackController.getId());
                invalid = true;
            }
        }
        for (TrackController redTrackController : TrackController.redTrackControllers) {
            if (redTrackController.loadPLC(plcFile)) {
                theLog.append("\nSuccessfully loaded in '" + PLCTextField.getText() + "' for Red Line Track Controller " + redTrackController.getId());
            } else {
                theLog.append("\nCould not loaded in '" + PLCTextField.getText() + "' for Red Line Track Controller " + redTrackController.getId());
                invalid = true;
            }
        }

        if (invalid) {
            JOptionPane.showMessageDialog(this, "The file '" + PLCTextField.getText() + "' does not Exist. Try again with a valid .plc file", "Invalid File", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Successfully loaded in '" + PLCTextField.getText() + "' for all the Track Controllers", "PLC Load Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_enterButtonActionPerformed

    private void railroadCrossingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_railroadCrossingButtonActionPerformed
        if (railroadCrossingComboBox.isEnabled()) {
            Integer blockId = (Integer) railroadCrossingComboBox.getSelectedItem();
            Block block = currentTrackController.getBlock(blockId);
            int line = currentTrackController.getLine();
            String theLine = (line == GREEN_LINE_ID) ? "green" : "red";
            if (block.getCrossing()) {
                boolean activated = block.getCrossingActivated();
                String state = activated ? "Deactivating" : "Activating";
                if (currentTrackController.crossingRequest(line, blockId, !activated)) {
                    theLog.append("\n" + state + " the railroad crossing at block " + blockId + " on the " + theLine + " line");
                    refreshBlockTable();
                }
            }
        }
    }//GEN-LAST:event_railroadCrossingButtonActionPerformed

    private void switchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchButtonActionPerformed
        if (switchComboBox.isEnabled()) {
            Integer switchBlock = (Integer) switchComboBox.getSelectedItem();
            Block aswitch = currentTrackController.getSwitch(switchBlock);
            int line = currentTrackController.getLine();
            String theLine = (line == GREEN_LINE_ID) ? "green" : "red";
            boolean state = aswitch.getSwitchPosition();
            int oldBlock = state ? aswitch.getSwitchBlock() : aswitch.getNextBlock();
            int newBlock = state ? aswitch.getNextBlock() : aswitch.getSwitchBlock();
            if (TrackController.switchRequest(line, switchBlock, switchBlock)) {
                theLog.append("\nSwitch at block " + switchBlock + " on the " + theLine + " line changed from block " + oldBlock + " to block " + newBlock);
                refreshBlockTable();
            }
        }
    }//GEN-LAST:event_switchButtonActionPerformed


    private void proceedAlphaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proceedAlphaActionPerformed
        theLog.append("\nDispatching Train " + TrackController.dispatchTrain(0));
        theLog.append("\nProceed 0 for Train 0 " + TrackController.proceedRequest(0, 0, 1, 2, 300, 100000));

        Queue routeAlpha = new LinkedList();
        for (int count = 1; count < 36; count++) {
            //theLog.append("\nProceed " + i + " " + TrackController.proceedRequest(0, i, i+1, i+2, 300, 100000));
            ArrayList<Integer> x = new ArrayList<>(Arrays.asList(count, count + 1, count + 2));
            routeAlpha.add(x);
        }

//        theLog.append("\nProceed 36 " + TrackController.proceedRequest(0, 36, 37, 38, 300, 100));
//        theLog.append("\nProceed 37 " + TrackController.proceedRequest(0, 37, 38, 23, 300, 100));
//        theLog.append("\nProceed 38 " + TrackController.proceedRequest(0, 38, 23, 22, 300, 100));
//        theLog.append("\nProceed 23 " + TrackController.proceedRequest(0, 23, 22, 21, 300, 100));
//        theLog.append("\nProceed 22 " + TrackController.proceedRequest(0, 22, 21, 20, 300, 100));
//        theLog.append("\nProceed 21 " + TrackController.proceedRequest(0, 21, 20, 19, 300, 100));
//        theLog.append("\nProceed 20 " + TrackController.proceedRequest(0, 20, 19, 18, 300, 100));
//        theLog.append("\nProceed 19 " + TrackController.proceedRequest(0, 19, 18, 17, 300, 100));
//        theLog.append("\nProceed 18 " + TrackController.proceedRequest(0, 18, 17, 16, 300, 100));
//        theLog.append("\nProceed 17 " + TrackController.proceedRequest(0, 17, 16, 15, 300, 100));
//        theLog.append("\nProceed 16 " + TrackController.proceedRequest(0, 16, 15, 39, 300, 100));
//        theLog.append("\nProceed 15 " + TrackController.proceedRequest(0, 15, 39, 40, 300, 100));
//        theLog.append("\nProceed 39 " + TrackController.proceedRequest(0, 39, 40, 41, 300, 100));
        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(36, 37, 38));
        ArrayList<Integer> b = new ArrayList<>(Arrays.asList(37, 38, 23));
        ArrayList<Integer> c = new ArrayList<>(Arrays.asList(38, 23, 22));
        ArrayList<Integer> d = new ArrayList<>(Arrays.asList(23, 22, 21));
        ArrayList<Integer> e = new ArrayList<>(Arrays.asList(22, 21, 20));
        ArrayList<Integer> f = new ArrayList<>(Arrays.asList(21, 20, 19));
        ArrayList<Integer> g = new ArrayList<>(Arrays.asList(20, 19, 18));
        ArrayList<Integer> h = new ArrayList<>(Arrays.asList(19, 18, 17));
        ArrayList<Integer> i = new ArrayList<>(Arrays.asList(18, 17, 16));
        ArrayList<Integer> j = new ArrayList<>(Arrays.asList(17, 16, 15));
        ArrayList<Integer> k = new ArrayList<>(Arrays.asList(16, 15, 39));
        ArrayList<Integer> l = new ArrayList<>(Arrays.asList(15, 39, 40));
        ArrayList<Integer> m = new ArrayList<>(Arrays.asList(39, 40, 41));
        routeAlpha.add(a);
        routeAlpha.add(b);
        routeAlpha.add(c);
        routeAlpha.add(d);
        routeAlpha.add(e);
        routeAlpha.add(f);
        routeAlpha.add(g);
        routeAlpha.add(h);
        routeAlpha.add(i);
        routeAlpha.add(j);
        routeAlpha.add(k);
        routeAlpha.add(l);
        routeAlpha.add(m);
        
        for (int count = 40; count < 115; count++) {
            ArrayList<Integer> x = new ArrayList<>(Arrays.asList(count, count + 1, count + 2));
            routeAlpha.add(x);
        }
        
        ArrayList<Integer> n = new ArrayList<>(Arrays.asList(115, 116, 104));
        ArrayList<Integer> o = new ArrayList<>(Arrays.asList(116, 104, 103));
        routeAlpha.add(n);
        routeAlpha.add(o);
        
        for (int count = 104; count > 91; count--) {
            ArrayList<Integer> x = new ArrayList<>(Arrays.asList(count, count - 1, count - 2));
            routeAlpha.add(x);
        }
        
        ArrayList<Integer> p = new ArrayList<>(Arrays.asList(91, 90, 89));
        ArrayList<Integer> q = new ArrayList<>(Arrays.asList(90, 89, 117));
        ArrayList<Integer> r = new ArrayList<>(Arrays.asList(89, 117, 118));
        routeAlpha.add(p);
        routeAlpha.add(q);
        routeAlpha.add(r);
        
        for (int count = 117; count < 144; count++) {
            ArrayList<Integer> x = new ArrayList<>(Arrays.asList(count, count + 1, count + 2));
            routeAlpha.add(x);
        }
        
        ArrayList<Integer> s = new ArrayList<>(Arrays.asList(144, 145, 147));
        ArrayList<Integer> t = new ArrayList<>(Arrays.asList(145, 147, 148));
        ArrayList<Integer> u = new ArrayList<>(Arrays.asList(147, 148, 149));
        ArrayList<Integer> v = new ArrayList<>(Arrays.asList(148, 149, 150));
        ArrayList<Integer> w = new ArrayList<>(Arrays.asList(149, 150, 151));
        ArrayList<Integer> x = new ArrayList<>(Arrays.asList(150, 151, 1));
        ArrayList<Integer> y = new ArrayList<>(Arrays.asList(151, 1, 2));
        ArrayList<Integer> z = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> aa = new ArrayList<>(Arrays.asList(2, 3, 4));
        routeAlpha.add(s);
        routeAlpha.add(t);
        routeAlpha.add(u);
        routeAlpha.add(v);
        routeAlpha.add(w);
        routeAlpha.add(x);
        routeAlpha.add(y);
        routeAlpha.add(z);
        routeAlpha.add(aa);

        routes.add(routeAlpha);
        lastOccupiedBlock.add(0, 0);

    }//GEN-LAST:event_proceedAlphaActionPerformed

    private void proceedBetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proceedBetaActionPerformed
        theLog.append("\nDispatching Train " + TrackController.dispatchTrain(0));
        theLog.append("\nProceed 0 for Train 1 " + TrackController.proceedRequest(0, 0, 1, 2, 300, 100000));

        Queue routeBeta = new LinkedList();
        for (int i = 1; i < 20; i++) {
            //theLog.append("\nProceed " + i + " " + TrackController.proceedRequest(0, i, i+1, i+2, 300, 100000));
            ArrayList<Integer> x = new ArrayList<>(Arrays.asList(i, i + 1, i + 2));
            routeBeta.add(x);
        }
        routes.add(routeBeta);
        lastOccupiedBlock.add(1, 0);
    }//GEN-LAST:event_proceedBetaActionPerformed

    private void toggleAccelerationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleAccelerationActionPerformed
        if (toggleAcceleration.getText().equals("Fast Acceleration") ){
            toggleAcceleration.setText("Slow Acceleration");
            TrackController.trainClock = 100;
        }
        
        else{
            toggleAcceleration.setText("Fast Acceleration");
            TrackController.trainClock = 10;
        }
    }//GEN-LAST:event_toggleAccelerationActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TrackControllerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrackControllerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrackControllerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrackControllerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrackControllerUI(null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PLCLabel;
    private javax.swing.JTextField PLCTextField;
    private javax.swing.JLabel blockCommandLabel;
    private javax.swing.JTable blockTable;
    private javax.swing.JButton browseButton;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JTabbedPane controllerTabbedPane;
    private javax.swing.JButton enterButton;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JRadioButton greenLineRadioButton;
    private javax.swing.JPanel gridPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.ButtonGroup lineButtonGroup;
    private javax.swing.JLabel lineLabel;
    private javax.swing.JPanel logPanel;
    private javax.swing.JScrollPane logScrollPane;
    private javax.swing.JButton proceedAlpha;
    private javax.swing.JButton proceedBeta;
    private javax.swing.JButton railroadCrossingButton;
    private javax.swing.JComboBox railroadCrossingComboBox;
    private javax.swing.JLabel railroadCrossingLabel;
    private javax.swing.JRadioButton redLineRadioButton;
    private javax.swing.JLabel simulateCTCLabel;
    private javax.swing.JButton switchButton;
    private javax.swing.JComboBox switchComboBox;
    private javax.swing.JLabel switchLabel;
    public javax.swing.JTextArea theLog;
    private javax.swing.JButton toggleAcceleration;
    private javax.swing.JComboBox trackControllerComboBox;
    private javax.swing.JLabel trackControllerLabel;
    private javax.swing.JLabel trackControllerSelectionLabel;
    private javax.swing.JButton trackMaintenanceButton;
    private javax.swing.JComboBox trackMaintenanceComboBox;
    private javax.swing.JLabel trackMaintenanceLabel;
    // End of variables declaration//GEN-END:variables
}
