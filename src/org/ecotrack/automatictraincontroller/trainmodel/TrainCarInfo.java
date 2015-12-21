package org.ecotrack.automatictraincontroller.trainmodel;

/**
 * @brief A "POD" to store all of the data for a single train car.
 * <p><code>TrainCarInfo</code> is a simple class used to pack together all
 * data specific to a single train car, and transmit it between the various
 * submodules of the Automatic Train Controller.  It is primarily used by the
 * class <code>TrainInfo</code> to store information for each of a train's
 * train cars.</p>
 * @author John C. Matty
 * @see {TrainInfo}
 */
public class TrainCarInfo {
		public double length = 32.2;
		public double width = 2.65;
		public double height = 3.42;
		public double mass = 40.9;
		public int passengerCount;
		public int crewCount;

		public TrainCarInfo() {
		}

		public TrainCarInfo(double length, double width, double height,
												double mass, int passengerCount, int crewCount) {
				this.length = length;
				this.width = width;
				this.height = height;
				this.mass = mass;
				this.passengerCount = passengerCount;
				this.crewCount = crewCount;
		}
}
