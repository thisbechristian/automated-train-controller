package trackcontroller;

//package org.ecotrack.automatictraincontroller.trackcontroller;

/**
 *
 * @author Christian Boni
 */
public interface PLCInterface {
    
    /**
     * A vital Method that determines if the train is allow to proceed to the next block; checks for occupancies, broken blocks, and closed blocks.
     * @param nextBlock The object of the next block the train will proceed to.
     * @param upcomingBlock The object of the block immediately after nextBlock.
     * @return True if Train can safely proceed to nextBlock, False if Train cannot safely proceed to nextBlock.
     */
    public boolean vitalProceed(Block nextBlock, Block upcomingBlock);
    
    /**
     * A vital Method that determines if the train is allow to proceed on the current Switch path; checks for broken Switch.
     * @param switchBlock The object of the switch block which will be the next block the train will proceed to.
     * @param destinationBlock The object of the block immediately after switchBlock on the desired route.
     * @return True if Train can safely proceed to switchBlock, False if Train cannot safely proceed to switchBlock
     */
    public boolean vitalSwitch(Block switchBlock, Block destinationBlock);
    
    /**
     * A vital Method that determines if the selected block can be closed for maintenance.
     * @param previousBlock The object of the block that is immediately before the block to be closed.
     * @param closeBlock The object of the block to be closed.
     * @return True if closeBlock can be safely closed for maintenance, False if closeBlock cannot be safely closed for maintenance.
     */
    public boolean vitalClose(Block previousBlock, Block closeBlock);
    
    /**
     * A vital Method that determines if the Train can proceed onto the Railroad Crossing block; checks for broken Railroad Crossing.
     * @param crossingBlock The object of the block with the crossing that should activated.
     * @return True if Train can safely proceed to crossingBlock, False if Train cannot safely proceed to crossingBlock.
     */
    public boolean vitalCrossing(Block crossingBlock);
    
     /**
     * A vital Method that determines if the Railroad Crossing should be activated or deactivated
     * @param previousBlock The object of the block that is immediately before the crossing to be activated/deactivated.
     * @param crossingBlock The object of the block with the crossing to be activated/deactivated.
     * @return True if Railroad Crossings state should be activated on the crossingBlock, False otherwise.
     */
    public boolean vitalCrossingState(Block previousBlock, Block crossingBlock);
    
     /**
     * A vital Method that determines if the train is allow to proceed to the next block; checks for occupancies only.
     * @param nextBlock The object of the next block the train will proceed to.
     * @param upcomingBlock The object of the block immediately after nextBlock.
     * @return True if Train can safely proceed to nextBlock, False if Train cannot safely proceed to nextBlock.
     */
    public boolean vitalMonitor(Block nextBlock, Block upcomingBlock);
    
}