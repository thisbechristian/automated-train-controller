package org.ecotrack.automatictraincontroller.ctc;

import java.util.HashMap;
import java.util.Iterator;

import org.ecotrack.automatictraincontroller.ctc.Router;
import org.ecotrack.automatictraincontroller.ctc.TrainInfoPacket;
import org.ecotrack.automatictraincontroller.trainmodel.TrainInfo;
import org.ecotrack.automatictraincontroller.trainmodel.TrainCarInfo;
import org.ecotrack.automatictraincontroller.trackcontroller.TrackControllerInterface;
import org.ecotrack.automatictraincontroller.trackmodel.Block;
import org.ecotrack.automatictraincontroller.trackmodel.BlockInfo;
import org.ecotrack.automatictraincontroller.trackmodel.Track;
import org.ecotrack.automatictraincontroller.trackmodel.TrainWrapper;

/**
 * @brief Backend implementation for the CTC office.
 * <p>This class implements the backend functionality for the CTC Office.  this
 * includes things such as setting train speed and authority, dispatch trains,
 * rerouting trains, and closing track sections.</p>
 * @author John C. Matty
 */
public class CTC {
		private Track track;
		private TrackControllerInterface trackController;
		private Router router;
		private HashMap<Integer, TrainInfoPacket> trains;

		public CTC(Track track, TrackControllerInterface trackController) {
				this.track = track;
				this.trackController = trackController;
				this.trains = new HashMap<Integer, TrainInfoPacket>();
				this.router = new Router();
		}

		public void update(int clk) {
				trackController.update(clk);
				// routeTrains();
		}

		public void routeTrains() {
				for (Integer id : trains.keySet()) {
						System.out.print("Key:\t");
						System.out.println(id);
						TrainInfoPacket tip = trains.get(id);
						System.out.print("Speed:\t");
						System.out.println(tip.speed);
						System.out.print("Authority:\t");
						System.out.println(tip.authority);
				}

				HashMap<Integer, TrainInfoPacket> newSchedule
						= router.routeTrains(trains, track);

				/*
				 * Now that we have the route, try to update it foreach of the
				 * trains, and update the values in <code>trains</code> if we can.
				 */

				Iterator it = newSchedule.entrySet().iterator();
				while (it.hasNext()) {
						HashMap.Entry pair = (HashMap.Entry)it.next();
						Integer trainID = (Integer) pair.getKey();
						TrainInfoPacket suggestedPacket = (TrainInfoPacket) pair.getValue();
						double speed = suggestedPacket.speed;
						double authority = suggestedPacket.authority;
						// avoids a ConcurrentModificationException
						it.remove();

						TrainInfoPacket thisTrainPacket
								= trains.get(trainID);
						/// @todo Keep track of another block.
						boolean couldDo = trackController.proceedRequest(thisTrainPacket.currentLine,
																														 thisTrainPacket.currentBlockID,
																														 thisTrainPacket.nextBlockID,
																														 thisTrainPacket.upcomingBlockID,
																														 speed,
																														 authority);
						if (couldDo) {
								thisTrainPacket.currentLine = suggestedPacket.currentLine;
								thisTrainPacket.currentBlockID
										= suggestedPacket.currentBlockID;
								thisTrainPacket.currentDistance
										= suggestedPacket.currentDistance;
								thisTrainPacket.nextBlockID = suggestedPacket.nextBlockID;
								thisTrainPacket.upcomingBlockID
										= suggestedPacket.upcomingBlockID;
								thisTrainPacket.speed = suggestedPacket.speed;
								thisTrainPacket.authority = suggestedPacket.authority;
						}
				}
		}

		public boolean setTrainSpeedAndAuthority(int trainID, double speed,
																						 double authority) {
				TrainInfoPacket thisTrainPacket = trains.get(trainID);
				boolean couldDo = trackController.proceedRequest(thisTrainPacket.currentLine,
																												 thisTrainPacket.currentBlockID,
																												 thisTrainPacket.nextBlockID,
																												 thisTrainPacket.upcomingBlockID,
																												 speed,
																												 authority);

				System.out.println(couldDo);
				if (couldDo) {
						thisTrainPacket.setSpeed(speed);
						thisTrainPacket.setAuthority(authority);
						thisTrainPacket.currentBlockID++;
						thisTrainPacket.nextBlockID++;
						thisTrainPacket.upcomingBlockID++;
						// thisTrainPacket.speed = speed;
						// thisTrainPacket.authority = authority;
						trains.remove(trainID);
						trains.put(trainID, thisTrainPacket);
				}

				return couldDo;
		}

		// public TrainInfo getTrainInfo(int trainID) {
		// 		TrainInfoPacket tip = trains.get(trainID);
		// 		return track.getLine().get(tip.currentLine).getTrains().get(trainID).getTrain().getTrainInfo();
		// }

		public BlockInfo getBlockInfo(int line, int blockID) {
				BlockInfo bi = new BlockInfo();
				Block b
						= track.getLine().get(line).getBlocks().get(blockID);
				// FIll it up.

				bi.length = b.getLength();
				bi.slope = b.getSlope();
				bi.heater = b.getHeater();
				bi.state = b.getState();
				bi.open = b.getOpen();
				bi.occupied = b.getOccupancy();
				bi.speed = b.getSpeed();
				bi.authority = b.getAuthority();
				bi.crossing = b.getCrossing();
				bi.crossingActivated = b.getCrossingActivated();
				bi.direction = b.getDirection();
				bi.isSwitch = b.getIsSwitch();
				bi.switchPosition = b.getSwitchPosition();
				bi.slopeSwitch = b.getSlopeSwitch();
				bi.switchBlockLength = b.getSwitchBlockLength();
				bi.isReversed = b.getIsReversed();

				// Return it.
				return bi;
		}

		public boolean dispatchTrain(int line) {
				int newTrainID = trackController.dispatchTrain(line);
				if (newTrainID >= 0) {
						// Create a new packet for the new train and add it to the
						// hashtable.
						TrainInfoPacket newTrainPacket = new TrainInfoPacket();
						newTrainPacket.currentLine = line;
						/// @todo Is this correct?
						newTrainPacket.currentBlockID = 0;
						newTrainPacket.currentDistance = 1000000;
						newTrainPacket.nextBlockID = 1;
						newTrainPacket.upcomingBlockID = 2;
						newTrainPacket.speed = 0;
						newTrainPacket.authority = 0;
						trains.put(newTrainID, newTrainPacket);
						return true;
				}
				else {
						return false;
				}
		}

		public boolean toggleSwitch(int lineID, int switchID) {
				return trackController.switchRequest(lineID, switchID);
		}

		public boolean shutdownBlock(int lineID, int blockID) {
				return trackController.maintenanceRequest(lineID, blockID, false);
		}

		public boolean startupBlock(int lineID, int blockID) {
				return trackController.maintenanceRequest(lineID, blockID, true);
		}
}
