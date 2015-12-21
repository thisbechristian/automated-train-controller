package trackcontroller;

//package org.ecotrack.automatictraincontroller.trackcontroller;
//import org.ecotrack.automatictraincontroller.trackcontroller.TrackControllerInterface;
//import org.ecotrack.automatictraincontroller.trackmodel.Block;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 *
 * @author Christian Boni
 */
public class TrackController implements TrackControllerInterface {

    public HashMap<Integer,Block>  blocks;
    public HashMap<Integer,Block> switches;
    public HashMap<Integer,Block> crossings;
    public PLC vitalPLC;
    private final int line;
    private final int id;
    
    /**
     * Sole Constructor for TrackController
     * @param id An int value denoting the specific number of this track controller.
     * @param line An int value denoting which line this track controller corresponds to.
     * @param blocks A HashMap of all the blocks that this track controller controls.
     * @param crossings A HashMap of all the crossings that this track controller controls.
     * @param switches A HashMap of all the switches that this track controller controls.
     */
    public TrackController(int id, int line, HashMap<Integer,Block> blocks, HashMap<Integer,Block> crossings, HashMap<Integer,Block> switches){
        this.id = id;
        this.line = line;
        this.blocks = blocks;
        this.switches = switches;
        this.crossings = crossings;
    }
    
    /**
     * Gets the track controller id number
     * @return The track controllers id number as an int
     */
    public int getId(){
        return id;
    }
    
    /**
     * Gets the line 'red' or 'green'
     * @return The line the track controller belongs to as an int
     */
    public int getLine(){
        return line;
    }
    
    public Block getBlock(int blockId){
        return blocks.get(blockId);
    }
    
    public Block getSwitch(int blockId){
        return switches.get(blockId);
    }
    
    public Block getCrossing(int blockId){
        return crossings.get(blockId);
    }
    
    public String getBlockState(int blockId){
        Block block = blocks.get(blockId);
        if(block == null){
            return null;
        }
        boolean closed = !block.getOpen();
        boolean occupied = block.getOccupancy();
        boolean broken = !block.getState();
        if(broken){
            return "broken";
        }
        if(occupied){
            return "occupied";
        }
        if(closed){
            return "closed";
        }
        return "open";
    }
    
    /**
     * This method loads the PLC boolean logic program with all of the vital expressions 
     * @param path The path to where the PLC file is located.
     * @return True: the file could be loaded, False: otherwise.
     */
    public boolean loadPLC(String path){
        try{
            //Load the file
            FileReader file = new FileReader(path);
            BufferedReader reader = new BufferedReader(file);
            
            //Store vital expressions as Strings to be used in Jexl Expression Evaluator
            String proceedExpression = null;
            String switchExpression = null;
            String maintenanceExpression = null;
            String crossingExpression = null;
            String crossingActivateExpression = null;
            String monitorExpression = null;
            String currentline;
            
            while((currentline = reader.readLine()) != null){
                String[] expression = currentline.split("-");
                switch (expression[0].toLowerCase()) {
                    case "proceed":
                        proceedExpression = expression[1];
                        break;
                    case "switch":
                        switchExpression = expression[1];
                        break;
                    case "maintenance":
                        maintenanceExpression = expression[1];
                        break;
                    case "crossing":
                        crossingExpression = expression[1];
                        break;
                    case "crossingactivate":
                        crossingActivateExpression = expression[1];
                        break;
                    case "monitor":
                        monitorExpression = expression[1];
                        break;
                }
            }
            
            this.vitalPLC = new PLC(proceedExpression, switchExpression, maintenanceExpression, crossingExpression, crossingActivateExpression, monitorExpression);
            return true;
        }
        
        catch(Exception e){
            System.out.println("Could not read PLC file.");
            return false;
        }
    }

    @Override
    public boolean proceedRequest(int line, int currentBlock, int nextBlock, int upcomingBlock, double speed, double authority) {
        Block current = getBlock(currentBlock);
        Block next = getBlock(nextBlock);
        Block upcoming = getBlock(upcomingBlock);
        //Zero out old speed and authority
        current.setAuthority(0);
        current.setSpeed(0);    
        //Check if going in reverse.
        boolean reverse = current.getPrevBlock() == nextBlock;
        int previousBlock = reverse ? current.getNextBlock() : current.getPrevBlock();
        Block previous = getBlock(previousBlock);
        
        //Validation if next block is a crossing
        if(next.getCrossing()){
            //Always activate railroad crossing.
            next.setCrossingActivated(true);
            if(!vitalPLC.vitalCrossing(next)){
                current.setAuthority(0);
                current.setSpeed(0);
                return false;  
            }   
        }
        
        //Validation if next block is a switch
        if(next.getIsSwitch()){
            boolean toggled = next.getSwitchPosition();
            boolean reversed = false;
            boolean onSwitch = currentBlock == next.getSwitchBlock();
            int nextBlockId = toggled ? next.getSwitchBlock() : next.getNextBlock();
            //If switch needs to be toggled
            if((upcoming.getId() != nextBlockId) || (onSwitch && (currentBlock != nextBlockId))){
                
                //If train is going into a switch in reverse
                if(upcoming.getId() == next.getPrevBlock()){
                    reversed = true;
                    //If switch needs to be toggled
                    if(current.getId() != nextBlockId){
                        if(!switchRequest(line, next.getId(), upcomingBlock)){
                            current.setAuthority(0);
                            current.setSpeed(0);
                            return false; 
                        }
                    }
                }
                
                //If switch toggle fails use current switch config
                else if(!switchRequest(line, next.getId(), nextBlockId)){
                    upcoming = getBlock(nextBlockId);
                }
            }
        
            //Perform vital switch
            if(!vitalPLC.vitalSwitch(next, upcoming)){
                //If vital switch fails, attempt toggling switch
                if(!reversed && switchRequest(line, next.getId(), upcomingBlock)){
                    toggled = next.getSwitchPosition();
                    nextBlockId = toggled ? next.getSwitchBlock() : next.getNextBlock();
                    upcoming = getBlock(nextBlockId);
                    //If all fails zero out speed and authority 
                    if(!vitalPLC.vitalSwitch(next, upcoming)){
                         current.setAuthority(0);
                        current.setSpeed(0);
                        return false; 
                    }
                }
                else{
                    current.setAuthority(0);
                    current.setSpeed(0);
                    return false; 
                } 
            }
        }
        
        //Validation if current block is a switch
        if(current.getIsSwitch()){
            boolean toggled = current.getSwitchPosition();
            int nextBlockId = toggled ? current.getSwitchBlock() : current.getNextBlock();
            //If switch has been toggled
            if(next.getId() != nextBlockId){
                next = getBlock(nextBlockId);
                upcomingBlock = reverse ? next.getPrevBlock() : next.getNextBlock();
                upcoming = getBlock(upcomingBlock);
            }
        }
        
        if(!vitalPLC.vitalProceed(next, upcoming)){
            current.setAuthority(0);
            current.setSpeed(0);
            return false;   
        }
        
        current.setAuthority(authority);
        current.setSpeed(speed);       
        return true;
    }

    @Override
    public boolean maintenanceRequest(int line, int block, boolean request) {
        Block maintenanceBlock = getBlock(block);
        Block previousBlock = getBlock(maintenanceBlock.getPrevBlock());
        Block nextBlock = getBlock(maintenanceBlock.getNextBlock());
        
        //Always Open a requested block (no need for vital check)
        if(request){
            maintenanceBlock.setOpen(request);
            return true;
        }
        
        //Check for train occupancies in both directions
        boolean vitalCloseRegular = vitalPLC.vitalClose(previousBlock, maintenanceBlock);
        boolean vitalCloseReverse = vitalPLC.vitalClose(nextBlock, maintenanceBlock);
        boolean vitalClose = vitalCloseRegular && vitalCloseReverse;
        
        //Vital PLC check if block can be Closed
        if(vitalClose){
            maintenanceBlock.setOpen(request);
            return true;
        }
        return false;
    }

    @Override
    public String blockStatusRequest(int line, int block) {
        return getBlockState(block);
    }

    @Override
    //Implemented in Track Controller Handler only 
    public int trainStatusRequest(int line, int train) {
        return -1;
    }

    @Override
    public boolean crossingStatusRequest(int line, int block) {
        Block crossing = getCrossing(block);
        if(crossing != null){
            return crossing.getState() && crossing.getCrossingActivated();
        }
        return false;
    }

    @Override
    public int switchStatusRequest(int line, int switchNumber) {
        Block theSwitch = getSwitch(switchNumber);
        if(theSwitch != null && theSwitch.getState()){
            return theSwitch.getSwitchPosition() ? theSwitch.getSwitchBlock() : theSwitch.getNextBlock();
        }
        return -1;
    }

    @Override
    public boolean crossingRequest(int line, int block, boolean activate) {
        Block crossingBlock = getBlock(block);
        Block previousBlock = getBlock(crossingBlock.getPrevBlock());
        Block nextBlock = getBlock(crossingBlock.getNextBlock());
       
       //Always Activate a requested crossing (no need for vital check)
       if(activate){
           crossingBlock.setCrossingActivated(true);
           return true;
       }
       
       //Check for train occupancies in both directions
       boolean vitalCrossingRegular = vitalPLC.vitalCrossingState(previousBlock, crossingBlock);
       boolean vitalCrossingReverse = vitalPLC.vitalCrossingState(nextBlock, crossingBlock);
       //True if crossing should be Activated, False if crossing can be Deactivated 
       boolean vitalCrossing = vitalCrossingRegular || vitalCrossingReverse;
       
       //Vital PLC check if crossing can be Deactivated
       if(!vitalCrossing){
           crossingBlock.setCrossingActivated(false);
           return true;
       }
        
        return false;
    }

    @Override
    public boolean switchRequest(int line, int switchNumber, int destinationBlock) {
        Block switchBlock = getBlock(switchNumber);
        Block nextBlock = getBlock(destinationBlock);
        boolean toggled = switchBlock.getSwitchPosition();
        
        //Vital PLC check if Switch can be Toggled
        if(vitalPLC.vitalSwitch(switchBlock, nextBlock)){
            switchBlock.setSwitchPosition(!toggled);
            return true;
        }
        return false;
    }
    
    //Implemented in Track Controller Handler only 
    @Override
    public int dispatchTrain(int line) {
        return -1;
    }

    //Implemented in Track Controller Handler only
    @Override
    public void update(int clock) {
    }
    
}
