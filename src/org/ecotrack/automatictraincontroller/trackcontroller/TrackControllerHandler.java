/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackcontroller;

//package org.ecotrack.automatictraincontroller.trackcontroller;
//import org.ecotrack.automatictraincontroller.trackmodel.Block;
//import org.ecotrack.automatictraincontroller.trackmodel.Track;
//import org.ecotrack.automatictraincontroller.trackmodel.TrainWrapper;
//import org.ecotrack.automatictraincontroller.trackmodel.Line;
//import org.ecotrack.automatictraincontroller.trackcontroller.TrackControllerInterface;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Christian
 */
public class TrackControllerHandler implements TrackControllerInterface {

    private TrackControllerUI trackControllerUI;
    private final Track track;
    public ArrayList<Line> lines;
    public Line redLine;
    public Line greenLine;
    private final int GREEN_LINE_ID = 0;
    private final int RED_LINE_ID = 1;
    private final ArrayList<Block> redBlocks;
    private final ArrayList<Block> redSwitches;
    private final ArrayList<Block> redCrossings;
    private final ArrayList<Block> greenBlocks;
    private final ArrayList<Block> greenSwitches;
    private final ArrayList<Block> greenCrossings;
    private final int redBlockIds[];
    private final int greenBlockIds[];
    public ArrayList<TrackController> redTrackControllers;
    public ArrayList<TrackController> greenTrackControllers;
    private final int numRedTrackControllers = 2;
    private final int numGreenTrackControllers = 4;
    private final int beforeOverlap = 2;
    private final int afterOverlap = 2;
    private final int sizeTrackControllerOverlap = beforeOverlap + afterOverlap;
    public int trainClock = 10;

    public TrackControllerHandler(Track track) {
        //Store Track
        this.track = track;

        //Store Lines - Red & Green
        lines = this.track.getLine();
        for (int i = 0; i < lines.size(); i++) {
            if (i == GREEN_LINE_ID) {
                greenLine = lines.get(i);
            } else {
                redLine = lines.get(i);
            }
        }

        //REMOVE LATER
        if (lines.size() == 1) {
            redLine = lines.get(0);
            greenLine = lines.get(0);
            lines.add(greenLine);
            redLine.getBlocks().get(7).setCrossing(true);
        }

        //Store Blocks, Crossings, & Switches for the Red Line
        redBlocks = redLine.getBlocks();
        redCrossings = new ArrayList<>();
        redSwitches = new ArrayList<>();
        redBlockIds = new int[redBlocks.size()];
        for (int i = 0; i < redBlocks.size(); i++) {
            Block currentBlock = redBlocks.get(i);
            redBlockIds[i] = currentBlock.getId();
            if (currentBlock.getCrossing()) {
                redCrossings.add(currentBlock);
            } else if (currentBlock.getIsSwitch()) {
                redSwitches.add(currentBlock);
            }
        }

        //Create each Track Controller for the Red Line given a set of Blocks, Crossings, Switches
        int numBlocksInRedTrackController = (redBlocks.size() / numRedTrackControllers) + sizeTrackControllerOverlap;
        redTrackControllers = new ArrayList<>();
        for (int i = 0; i < numRedTrackControllers; i++) {
            HashMap<Integer, Block> blocks = new HashMap<>();
            HashMap<Integer, Block> crossings = new HashMap<>();
            HashMap<Integer, Block> switches = new HashMap<>();
            int offset = numBlocksInRedTrackController - sizeTrackControllerOverlap;
            for (int j = (i * offset - beforeOverlap); j < (numBlocksInRedTrackController + (i * offset - 2)); j++) {
                int mod = j % redBlocks.size();
                if (mod < 0) {
                    mod += redBlocks.size();
                }
                Block currentBlock = redBlocks.get(mod);
                blocks.put(currentBlock.getId(), currentBlock);
                if (currentBlock.getCrossing()) {
                    crossings.put(currentBlock.getId(), currentBlock);
                } else if (currentBlock.getIsSwitch()) {
                    //If current block is a switch add it and the 2 blocks following the switch.
                    switches.put(currentBlock.getId(), currentBlock);
                    int switchId = currentBlock.getSwitchBlock();
                    Block switchBlock = redBlocks.get(switchId);
                    int afterSwitchId = switchBlock.getNextBlock();
                    Block afterSwitchBlock = redBlocks.get(afterSwitchId);
                    int upcomingAfterSwitchId = afterSwitchBlock.getNextBlock();
                    blocks.put(switchId, switchBlock);
                    blocks.put(afterSwitchId, afterSwitchBlock);
                    blocks.put(upcomingAfterSwitchId, redBlocks.get(upcomingAfterSwitchId));
                }
            }
            TrackController redTrackController = new TrackController(i, RED_LINE_ID, blocks, crossings, switches);
            redTrackControllers.add(redTrackController);
            redTrackController.loadPLC("resources/booleanlogicfile.plc");
        }

        //Store Blocks, Crossings, & Switches for the Green Line
        greenBlocks = greenLine.getBlocks();
        greenCrossings = new ArrayList<>();
        greenSwitches = new ArrayList<>();
        greenBlockIds = new int[greenBlocks.size()];
        for (int i = 0; i < greenBlocks.size(); i++) {
            Block currentBlock = greenBlocks.get(i);
            greenBlockIds[i] = currentBlock.getId();
            if (currentBlock.getCrossing()) {
                greenCrossings.add(currentBlock);
            } else if (currentBlock.getIsSwitch()) {
                greenSwitches.add(currentBlock);
            }
        }

        //Create each Track Controller for the Green Line given a set of Blocks, Crossings, Switches
        int numBlocksInGreenTrackController = (greenBlocks.size() / numGreenTrackControllers) + sizeTrackControllerOverlap;
        greenTrackControllers = new ArrayList<>();
        for (int i = 0; i < numGreenTrackControllers; i++) {
            HashMap<Integer, Block> blocks = new HashMap<>();
            HashMap<Integer, Block> crossings = new HashMap<>();
            HashMap<Integer, Block> switches = new HashMap<>();
            int offset = numBlocksInGreenTrackController - sizeTrackControllerOverlap;
            for (int j = (i * offset - beforeOverlap); j < (numBlocksInGreenTrackController + (i * offset - 2)); j++) {
                int mod = j % redBlocks.size();
                if (mod < 0) {
                    mod += redBlocks.size();
                }
                Block currentBlock = greenBlocks.get(mod);
                blocks.put(currentBlock.getId(), currentBlock);
                if (currentBlock.getCrossing()) {
                    crossings.put(currentBlock.getId(), currentBlock);
                } else if (currentBlock.getIsSwitch()) {
                    //If current block is a switch add it and the 2 blocks following the switch.
                    switches.put(currentBlock.getId(), currentBlock);
                    int switchId = currentBlock.getSwitchBlock();
                    Block switchBlock = greenBlocks.get(switchId);
                    int afterSwitchId = switchBlock.getNextBlock();
                    Block afterSwitchBlock = greenBlocks.get(afterSwitchId);
                    int upcomingAfterSwitchId = afterSwitchBlock.getNextBlock();
                    blocks.put(switchId, switchBlock);
                    blocks.put(afterSwitchId, afterSwitchBlock);
                    blocks.put(upcomingAfterSwitchId, greenBlocks.get(upcomingAfterSwitchId));
                }
            }
            TrackController greenTrackController = new TrackController(i, GREEN_LINE_ID, blocks, crossings, switches);
            greenTrackControllers.add(greenTrackController);
            greenTrackController.loadPLC("resources/booleanlogicfile.plc");
        }

        //Testing Jexl & Jython
        TrackController test = redTrackControllers.get(0);
        System.out.println("proceed " + test.vitalPLC.vitalProceed(null, null));
        System.out.println("close " + test.vitalPLC.vitalClose(null, null));
        System.out.println("switch " + test.vitalPLC.vitalSwitch(null, null));
        System.out.println("crossing " + test.vitalPLC.vitalCrossing(null));
        System.out.println("crossing state " + test.vitalPLC.vitalCrossingState(null, null));
        System.out.println("monitor " + test.vitalPLC.vitalMonitor(null, null));
        //Test should ALWAYS be true
        System.out.println("test " + test.vitalPLC.vitalTest());
        TrackControllerUILogin trackControllerLogin = new TrackControllerUILogin(null, true, track, this);
        trackControllerLogin.setVisible(true);
    }

    public void loadTrackControllerUI() {
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
        this.trackControllerUI = new TrackControllerUI(track, this);
        trackControllerUI.setVisible(true);
    }

    private TrackController getTrackController(int line, ArrayList<Integer> blocks) {

        ArrayList<TrackController> colorTrackControllers;

        switch (line) {
            case GREEN_LINE_ID:
                colorTrackControllers = greenTrackControllers;
                break;
            case RED_LINE_ID:
                colorTrackControllers = redTrackControllers;
                break;
            default:
                return null;
        }

        for (TrackController tc : colorTrackControllers) {
            boolean found = true;
            for (Integer block : blocks) {
                if (tc.getBlock(block) == null) {
                    found = false;
                }
            }
            if (found) {
                return tc;
            }
        }

        return null;
    }

    public void monitorTrack() {
        for (Line line : lines) {
            TrackController tc = (line.getId() == RED_LINE_ID) ? redTrackControllers.get(0) : greenTrackControllers.get(0);
            ArrayList<TrainWrapper> trains = new ArrayList<>(line.getTrains());
            for (TrainWrapper tw : trains) {
                int block = tw.getCurrentBlock().getId();
                int firstBlock = 1;
                int lastBlock = (line.getBlocks().size() - 2);
                if (block > firstBlock && block < lastBlock) {
                    Block current = line.getBlocks().get(block);
                    Block next;
                    Block upcoming;
                    Block previous;
                    Block before;
                    
                    //Special Case if block occupied is a switch.
                    if (current.getIsSwitch()) {
                        boolean toggled = current.getSwitchPosition();
                        int nextId = toggled ? current.getSwitchBlock() : current.getNextBlock();
                        next = line.getBlocks().get(nextId);
                        int upcomingId = next.getNextBlock() != current.getId() ? next.getNextBlock() : next.getPrevBlock();
                        upcoming = (upcomingId < 0) ? next : line.getBlocks().get(upcomingId);
                        if(current.getInvertedSwitch() && toggled){
                            previous = line.getBlocks().get(current.getNextBlock());
                            before = line.getBlocks().get(previous.getNextBlock());
                        }
                        else{
                            previous = line.getBlocks().get(current.getPrevBlock());
                            before = line.getBlocks().get(previous.getPrevBlock());
                        }
                    } 
                    else {
                        previous = line.getBlocks().get(current.getPrevBlock());
                        if(previous.getIsSwitch()){
                            boolean toggled = previous.getSwitchPosition();
                            boolean inverted = previous.getInvertedSwitch();
                            int beforeId = previous.getSwitchBlock() == current.getId() ? previous.getNextBlock() : previous.getSwitchBlock();
                            before = (toggled && !inverted) ? line.getBlocks().get(beforeId) : line.getBlocks().get(previous.getPrevBlock());
                        }
                        else{
                            before = line.getBlocks().get(previous.getPrevBlock());    
                        }
                        next = line.getBlocks().get(current.getNextBlock());
                        if(next.getIsSwitch()){
                            boolean toggled = next.getSwitchPosition();
                            boolean inverted = next.getInvertedSwitch();
                            int upcomingId = next.getSwitchBlock() == current.getId() ? next.getPrevBlock() : next.getSwitchBlock();
                            upcoming = (toggled && !inverted) ? line.getBlocks().get(upcomingId) : line.getBlocks().get(next.getNextBlock());
                        }
                        else{
                            upcoming = line.getBlocks().get(next.getNextBlock());
                        }
                    }
                    boolean vitalLookAhead = tc.vitalPLC.vitalMonitor(next, upcoming);
                    boolean vitalLookBehind = tc.vitalPLC.vitalMonitor(previous, before);
                    if(!vitalLookAhead || !vitalLookBehind){
                        current.setSpeed(0);
                        current.setAuthority(0);
                        //trackControllerUI.theLog.append("\nPLC DETECTED POTENTIAL CRASH AT BLOCK : " + current.getId());
                    }
                }
            }
        }
    }

    public void monitorCrossings() {
        for (Line line : lines) {
            TrackController tc = (line.getId() == RED_LINE_ID) ? redTrackControllers.get(0) : greenTrackControllers.get(0);
            ArrayList<Block> crossings = (line.getId() == RED_LINE_ID) ? redCrossings : greenCrossings;
            for (Block crossing : crossings) {
                Block afterCrossing = line.getBlocks().get(crossing.getNextBlock());
                int beforeBlockId = crossing.getPrevBlock();
                Block beforeCrossing = (beforeBlockId >= 0) ? line.getBlocks().get(beforeBlockId) : null;
                if (afterCrossing != null) {
                    Block followingAfterCrossing = line.getBlocks().get(afterCrossing.getNextBlock());
                    //Attempt to activate crossing if railroad crossing has been passed.
                    if (afterCrossing.getOccupancy()) {
                        boolean activeRequired = tc.vitalPLC.vitalCrossingState(afterCrossing, crossing);
                        crossing.setCrossingActivated(activeRequired);
                    } //Attempt to deactivate crossing if railroad crossing has been passed.
                    else if (followingAfterCrossing != null && followingAfterCrossing.getOccupancy()) {
                        boolean activeRequired = tc.vitalPLC.vitalCrossingState(afterCrossing, crossing);
                        crossing.setCrossingActivated(activeRequired);
                    }
                }
                if (beforeCrossing != null) {
                    int beforeBeforeCrossingId = beforeCrossing.getPrevBlock();
                    Block beforeBeforeCrossing = (beforeBeforeCrossingId >= 0) ? line.getBlocks().get(beforeBeforeCrossingId) : null;
                    //Attempt to activate crossing if railroad crossing is upcoming.
                    if (beforeCrossing.getOccupancy()) {
                        boolean activeRequired = tc.vitalPLC.vitalCrossingState(beforeCrossing, crossing);
                        crossing.setCrossingActivated(activeRequired);
                    } //Attempt to deactivate crossing if railroad crossing is upcoming.
                    else if (beforeBeforeCrossing != null && beforeBeforeCrossing.getOccupancy()) {
                        boolean activeRequired = tc.vitalPLC.vitalCrossingState(beforeCrossing, crossing);
                        crossing.setCrossingActivated(activeRequired);
                    }
                }
            }
        }
    }

    @Override
    public boolean proceedRequest(int line, int currentBlock, int nextBlock, int upcomingBlock, double speed, double authority) {
        TrackController calledTrackController;
        ArrayList<Integer> blocks = new ArrayList<>();
        Line colorLine = lines.get(line);
        Block current = colorLine.getBlocks().get(currentBlock);
        boolean reverse = current.getPrevBlock() == nextBlock;
        int previousBlock = reverse ? current.getNextBlock() : current.getPrevBlock();
        if (previousBlock >= 0) {
            blocks.add(previousBlock);
        }
        blocks.add(currentBlock);
        blocks.add(nextBlock);
        blocks.add(upcomingBlock);
        calledTrackController = getTrackController(line, blocks);

        if (calledTrackController != null) {
            return calledTrackController.proceedRequest(line, currentBlock, nextBlock, upcomingBlock, speed, authority);
        }

        return false;
    }

    @Override
    public boolean maintenanceRequest(int line, int block, boolean request) {
        TrackController calledTrackController;
        ArrayList<Integer> blocks = new ArrayList<>();
        Line colorLine = lines.get(line);
        Block current = colorLine.getBlocks().get(block);
        int nextBlock = current.getNextBlock();
        int previousBlock = current.getPrevBlock();
        if (nextBlock >= 0) {
            blocks.add(nextBlock);
        }
        if (previousBlock >= 0) {
            blocks.add(previousBlock);
        }

        blocks.add(block);
        calledTrackController = getTrackController(line, blocks);

        if (calledTrackController != null) {
            return calledTrackController.maintenanceRequest(line, block, request);
        }

        return false;
    }

    @Override
    public String blockStatusRequest(int line, int block) {
        TrackController calledTrackController;
        ArrayList<Integer> blocks = new ArrayList<>();
        blocks.add(block);
        calledTrackController = getTrackController(line, blocks);
        if (calledTrackController != null) {
            return calledTrackController.blockStatusRequest(line, block);
        }
        return "";
    }

    @Override
    public int trainStatusRequest(int line, int train) {
        Line colorLine = lines.get(line);
        ArrayList<TrainWrapper> Trains;

        if (colorLine == null) {
            return -1;
        }

        Trains = colorLine.getTrains();
        for (TrainWrapper tw : Trains) {
            if (tw.getTrain().getID() == train) {
                return tw.getCurrentBlock().getId();
            }
        }

        return -1;
    }

    @Override
    public boolean crossingStatusRequest(int line, int block) {
        TrackController calledTrackController;
        ArrayList<Integer> blocks = new ArrayList<>();
        blocks.add(block);
        calledTrackController = getTrackController(line, blocks);
        if (calledTrackController != null) {
            return calledTrackController.crossingStatusRequest(line, block);
        }
        return false;
    }

    @Override
    public int switchStatusRequest(int line, int switchNumber) {
        TrackController calledTrackController;
        ArrayList<Integer> blocks = new ArrayList<>();
        blocks.add(switchNumber);
        calledTrackController = getTrackController(line, blocks);
        if (calledTrackController != null) {
            return calledTrackController.switchStatusRequest(line, switchNumber);
        }
        return -1;
    }

    @Override
    public boolean crossingRequest(int line, int block, boolean activate) {
        TrackController calledTrackController;
        ArrayList<Integer> blocks = new ArrayList<>();
        Line colorLine = lines.get(line);
        Block current = colorLine.getBlocks().get(block);
        int nextBlock = current.getNextBlock();
        int previousBlock = current.getPrevBlock();
        if (nextBlock >= 0) {
            blocks.add(nextBlock);
        }
        if (previousBlock >= 0) {
            blocks.add(previousBlock);
        }

        blocks.add(block);
        calledTrackController = getTrackController(line, blocks);

        if (calledTrackController != null) {
            return calledTrackController.crossingRequest(line, block, activate);
        }

        return false;
    }

    @Override
    public boolean switchRequest(int line, int switchNumber, int destinationBlock) {
        TrackController calledTrackController;
        ArrayList<Integer> blocks = new ArrayList<>();
        Line colorLine = lines.get(line);
        int nextBlock = colorLine.getBlocks().get(switchNumber).getNextBlock();
        int switchBlock = colorLine.getBlocks().get(switchNumber).getSwitchBlock();

        blocks.add(nextBlock);
        blocks.add(switchBlock);
        blocks.add(switchNumber);
        blocks.add(destinationBlock);
        calledTrackController = getTrackController(line, blocks);

        if (calledTrackController != null) {
            return calledTrackController.switchRequest(line, switchNumber, destinationBlock);
        }

        return false;

    }

    @Override
    public int dispatchTrain(int line) {
        Line colorLine = lines.get(line);
        if (colorLine == null) {
            return -1;
        }
        Block initialBlock = colorLine.getBlocks().get(0);
        Block nextBlock = colorLine.getBlocks().get(initialBlock.getNextBlock());
        Block upcomingBlock = colorLine.getBlocks().get(nextBlock.getNextBlock());

        //Do NOT dispatch a new train if train is waiting to be routed.
        if (initialBlock.getOccupancy()) {
            return -1;
        }

        if (proceedRequest(line, initialBlock.getId(), nextBlock.getId(), upcomingBlock.getId(), 0, 0)) {
            TrainWrapper newTrain = colorLine.addTrain();
            return newTrain.getTrain().getID();
        } else {
            return -1;
        }
    }

    @Override
    public void update(int clock) {
        track.update(clock, trainClock);
        monitorTrack();
        monitorCrossings();
        trackControllerUI.update();
    }

}
