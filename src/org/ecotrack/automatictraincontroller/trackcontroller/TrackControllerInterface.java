package trackcontroller;
//package org.ecotrack.automatictraincontroller.trackcontroller;

/**
 *
 * @author Christian Boni
 */
public interface TrackControllerInterface {
    
    /**
     * A Method that is called from the CTC to request to set the speed and authority of a train to a specific block.
     * @param line The line that the block is on: 1 for 'red',  0 for 'green'
     * @param currentBlock The number of the current block the train is on.
     * @param nextBlock The number of the next block the train will proceed to.
     * @param upcomingBlock The number of the block immediately after the next block.
     * @param speed The suggested speed the train should be traveling at.
     * @param authority The suggested authority given to the train.
     * @return True if the proceed request was validated without error, False otherwise.
     */
    public boolean proceedRequest(int line, int currentBlock, int nextBlock, int upcomingBlock, double speed, double authority);
    
    /**
     * A Method that is called from the CTC to either open or close a specific block on a given line.
     * @param line The line that the block is on: 1 for 'red',  0 for 'green'
     * @param block The number of the block to request maintenance on.
     * @param request If true open the block, else close the block.
     * @return True if the maintenance request could be performed, False otherwise.
     */
    public boolean maintenanceRequest(int line, int block, boolean request);
    
     /**
     * A Method that is called from the User to either activate or deactivate a specific railroad crossing on a given line.
     * @param line The line that the block is on: 1 for 'red',  0 for 'green'
     * @param block The number of the railroad crossing block to request activation/deactivation on.
     * @param activate If true activate the railroad crossing, else deactivate the railroad crossing.
     * @return True if the crossing request could be performed, False otherwise.
     */
    public boolean crossingRequest(int line, int block, boolean activate);
    
     /**
     * A Method that is called from the CTC to toggle a specific switch on a given line.
     * @param line The line that the block is on: 1 for 'red',  0 for 'green'
     * @param switchNumber The number of the switch to request a toggle on.
     * @param destinationBlock The number of the block after the switch.
     * @return True if the switch request could be performed, False otherwise.
     */
    public boolean switchRequest(int line, int switchNumber, int destinationBlock);
    
    /**
     * A Method that is called from the CTC to get the physical status of a specific block on a given line.
     * @param line The line that the block is on: 1 for 'red',  0 for 'green'
     * @param block The number of the block to request status on.
     * @return A String message of the block's current status: 'open', 'closed', 'occupied', 'broken'
     */
    public String blockStatusRequest(int line, int block);
    
    /**
     * A Method that is called from the CTC to get the position of a specific train on a given line.
     * @param line The line that the block is on: 1 for 'red',  0 for 'green'
     * @param train The id number of the train to get the block it is currently on.
     * @return The block number that the train is currently on.
     */
    public int trainStatusRequest(int line, int train);
    
    /**
     * A Method that is called from the CTC to get the status of a specific railroad crossing on a given line.
     * @param line The line that the block is on: 1 for 'red',  0 for 'green'
     * @param block The number of the block to request railroad crossing status on.
     * @return True if the railroad crossing is activated, False otherwise. 
     */
    public boolean crossingStatusRequest(int line, int block);
    
    /**
     * A Method that is called from the CTC to get the status of a specific switch on a given line.
     * @param line The line that the block is on: 1 for 'red',  0 for 'green'
     * @param switchNumber The number of the switch to request status on.
     * @return A block number in the current switch configuration, the block currently linked by the switch. 
     * Notes: If the switch is broken -1 will be returned.
     */
    public int switchStatusRequest(int line, int switchNumber);
    
    /**
     * A Method that is called from the CTC to dispatch a train on a given line.
     * @param line The line that the block is on: 1 for 'red',  0 for 'green'
     * @return The train id of the train that was just dispatched
     */
    public int dispatchTrain(int line);
    
     /**
     * A Method that is called from Main in RunProgram to execute tasks at each clock cycle.
     * @param clock The length of a clock tick.
     */
    public void update(int clock);
 
}