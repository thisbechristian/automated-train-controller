package org.ecotrack.automatictraincontroller.ctc;

/**
 * @brief Information for train during routing.
 *
 * @author John C. Matty
 */
public class TrainInfoPacket {
		/** The train ID for this train. */
		public int trainID;
		/** The block ID for this train's current block. */
		public int currentBlockID;
		/**
		 * The current distance that this train has traveled on the current
		 * block.
		 */
		public double currentDistance;
		/** The block ID for this train's next block. */
		public int nextBlockID;
		/** The block ID for this train's upcoming block. */
		public int upcomingBlockID;
		/** The current line for this train. */
		public int currentLine;
		/** The desired speed for this train. */
		public double speed;
		/** The authority this train. */
		public double authority;

		/**
		 * @brief Initalize this <code>TrainInfoPacket<code>'s values to all
		 *        invalid values (<code>-1</code> or <code>""</code> for strings.
		 */
		public TrainInfoPacket() {
				trainID = -1;
				currentBlockID = -1;
				currentDistance = 1000000;
				nextBlockID = -1;
				upcomingBlockID = -1;
				currentLine = -1;
				speed = -1;
				authority = -1;
		}

		public void setSpeed(double s) {
				this.speed = s;
		}

		public void setAuthority(double a) {
				this.authority = a;
		}
}
