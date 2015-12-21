package org.ecotrack.automatictraincontroller.ctc;

import java.util.ArrayList;
import java.util.HashMap;

import org.ecotrack.automatictraincontroller.trackmodel.Track;
import org.ecotrack.automatictraincontroller.trackmodel.TrainWrapper;
import org.ecotrack.automatictraincontroller.trackmodel.Line;
import org.ecotrack.automatictraincontroller.trackmodel.Block;
import org.ecotrack.automatictraincontroller.trainmodel.Train;

/**
 * @brief Scheduling for trains on the tracks.
 * <p>This class implements a basic scheduling algorithm to ensure, given a
 * desired route for the stations, that trains arrive at those stations
 * by the times requested (if such a solution exists).</p>
 * @author John C. Matty
 */
public class Router {
		public Router() {
		}

		// TODO Implement this...
		public static HashMap<Integer, TrainInfoPacket> routeTrains
				(HashMap<Integer, TrainInfoPacket> startSchedule, Track track)
		{
				// The new schedule.
				HashMap<Integer, TrainInfoPacket> newSchedule
						= new HashMap<Integer, TrainInfoPacket>();
				// Get all line info.
				ArrayList<Line> lines = track.getLine();
				for (Integer id : startSchedule.keySet()) {
						TrainInfoPacket tip = startSchedule.get(id);
						Line l = lines.get(tip.currentLine);
						// Get the block with train id <code>id</code> on it.
						System.out.println("ID:\t" + id);
						TrainWrapper trainWrapper = l.getTrains().get(id);
						Train train = trainWrapper.getTrain();
						ArrayList<Block> bs = l.getBlocks();

						int bi = tip.currentBlockID;
						Block b = l.getBlocks().get(tip.currentBlockID);

						// This is stored with previous and next, just set to next unless
						// we are at a switch, in which case we need to look ahead and see
						// which path to take.
						if (b.getIsSwitch()) {
						}
						else {
								TrainInfoPacket newTIP = new TrainInfoPacket();

								// Get the length of the block and the distance it has traveled
								// and update if we moved to a new block.
								// We do this by checking if the distance on the block is less
								// than the distance.
								if (tip.currentDistance > trainWrapper.getDistance()) {
										/// @todo Switch case.
										newTIP.currentBlockID = bi + 1;
										/// @todo Check if next block is a switch, and if so look
										///       ahead.
										newTIP.nextBlockID = bi + 2;
										/// @todo Check if upcoming block is a switch, and if so
										///       look ahead.
										newTIP.upcomingBlockID = bi + 3;
								}
								else {
										newTIP.currentBlockID = tip.currentBlockID;
										newTIP.nextBlockID = tip.nextBlockID;
										newTIP.upcomingBlockID = tip.upcomingBlockID;
								}

								// Update the distance.
								newTIP.currentDistance = trainWrapper.getDistance();
								newTIP.currentLine = tip.currentLine;
								/// @todo Calculate new speed and authority.
								newTIP.speed = tip.speed;
								newTIP.authority = tip.authority;

								System.out.println(newTIP.speed);
								System.out.println(newTIP.authority);
								newSchedule.put(id, newTIP);
								// TrainInfoPacket ttt = newSchedule.get(id);
						}
				}

				return newSchedule;
		}
}
