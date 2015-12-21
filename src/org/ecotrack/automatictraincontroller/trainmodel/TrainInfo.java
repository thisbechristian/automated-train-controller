package org.ecotrack.automatictraincontroller.trainmodel;

import java.util.ArrayList;

import org.ecotrack.automatictraincontroller.trainmodel.TrainCarInfo;

/**
 * @brief "POD" storing the info of all cars in this train, as well as other
 *        train specific information.
 * <p><code>TrainInfo</code> is a simple class used to pack together all the
 * data for one train and transfer it between various submodlues of the
 * Automatic Train Controller.</p>
 * @author John C. Matty
 */
public class TrainInfo {
		public ArrayList<TrainCarInfo> carInfo;

		public TrainInfo() {
		}

		public TrainInfo(ArrayList<TrainCarInfo> carInfo) {
				this.carInfo = carInfo;
		}
}
