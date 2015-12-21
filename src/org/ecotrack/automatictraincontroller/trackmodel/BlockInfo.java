package org.ecotrack.automatictraincontroller.trackmodel;

/**
 * @brief All possible block information.
 * <p>All possible block information packed into one packet.</p>
 * @brief John C. Matty
 */
public class BlockInfo {
		public double length;
		public double slope;
		public boolean heater;
		public boolean state;
		public boolean open;
		public boolean occupied;
		public double speed;
		public double authority;
		public boolean crossing;
		public boolean crossingActivated;
		public boolean direction;
		public boolean isSwitch;
		public boolean switchPosition;
		public double slopeSwitch;
		public double switchBlockLength;
		public boolean isReversed;

		/**
		 * @brief Default constructor.
		 * Default constructor for a <code>BlockInfo</code> packet.
		 */
		public BlockInfo() {
				this.length = -1;
				this.slope = -1;
				this.heater = false;
				this.state = false;
				this.open = false;
				this.occupied = false;
				this.speed = -1;
				this.authority = -1;
				this.crossing = false;
				this.crossingActivated = false;
				this.direction = false;
				this.isSwitch = false;
				this.switchPosition = false;
				this.slopeSwitch = -1;
				this.switchBlockLength = -1;
				this.isReversed = false;
		}
}
