package org.ecotrack.automatictraincontroller.trainmodel;

/**
 * The TrainModel class is the implementation of the Train interface.
 *
 * @author Tim Glorioso
 * @version 0.1.0
 */
class TrainModel extends Train {

    /** used to get data for the car make/model */
    public final CarModel car;

    /*
     * Location
     */

    private double x;
    private double y;
    private double currentSpeed;

    /*
     * State Attributes
     */

    /** <code>true</code> when train engine is operational */
    private boolean engineStatus;

    /** <code>true</code> when signal pickup is operational */
    private boolean signalPickupStatus;

    /** <code>true</code> when train brake is operational */
    private boolean brakeStatus;

    /** <code>true</code> from pull until emergency is resolved */
    private boolean emergencyBrakePulled;

    /** speed limit of the train as set by the CTC (km/h) */
    private double speedLimit;

    /** acceleration limit of the train as set by the CTC (m/s^2) */
    private double accelerationLimit;

    /** setpoint speed of the train as set by the driver (km/h) */
    private double setpoint;

    /** number of cars in the train */
    private int numberOfCars;

    /** number of crew members on the train */
    private int numberOfCrew;

    /** number of passengers on the train */
    private int numberOfPassengers;

    /** interior temperature of the train */
    private double interiorTemperature;

    /** <code>true</code> when doors are closed */
    private boolean doorsClosed;

    /** <code>true</code> when lights are off */
    private boolean lightsOff;

    /*
     * Physical Attribute Accessors
     */

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    /** @return length of the train (m) */
    public double getLength() {
        return car.LENGTH * numberOfCars;
    }

    /** @return height of the train (m) */
    public double getHeight() {
        return car.HEIGHT;
    }

    /** @return width of the train (m) */
    public double getWidth() {
        return car.WIDTH;
    }

    /** @return mass of the train when empty, i.e., no passengers (t) */
    public double getEmptyMass() {
        return car.EMPTY_MASS * numberOfCars;
    }

    /** @return maximum speed of the train (km/h) */
    public double getMaxSpeed() {
        return car.MAX_SPEED;
    }

    /** @return maximum acceleration of the train (m/s^2) */
    public double getMaxAcceleration() {
        return car.MAX_ACCELERATION;
    }

    /** @return maximum deceleration of the train by service brake (m/s^2) */
    public double getMaxServiceDeceleration() {
        return car.MAX_SERVICE_DECELERATION;
    }

    /** @return maximum deceleration of the train by emergency brake (m/s^2) */
    public double getMaxEmergencyDeceleration() {
        return car.MAX_EMERGENCY_DECELERATION;
    }

    /*
     * State Attribute Accessors
     */

    /** @return <code>true</code> when train engine is operational */
    public boolean getEngineStatus() {
        return engineStatus;
    }

    /** @return <code>true</code> when signal pickup is operational */
    public boolean getSignalPickupStatus() {
        return signalPickupStatus;
    }

    /** @return <code>true</code> when train brake is operational */
    public boolean getBrakeStatus() {
        return brakeStatus;
    }

    /** @return <code>true</code> from pull until emergency is resolved. */
    public boolean isEmergencyBrakePulled() {
        return emergencyBrakePulled;
    }

    /** @return speed limit of the train as set by the CTC (km/h) */
    public double getSpeedLimit() {
        return speedLimit;
    }

    /** @return acceleration limit of the train as set by the CTC (m/s^2) */
    public double getAccelerationLimit() {
        return accelerationLimit;
    }

    /** @return setpoint speed of the train as set by the driver (km/h) */
    public double getSetpoint() {
        return setpoint;
    }

    /** @return number of cars in the train */
    public int getNumberOfCars() {
        return numberOfCars;
    }

    /** @return number of crew members on the train */
    public int getNumberOfCrew() {
        return numberOfCrew;
    }

    /** @return number of passengers on the train */
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    /** @return interior temperature of the train (ºC) */
    public double getInteriorTemperature() {
        return interiorTemperature;
    }

    /** @return <code>true</code> if doors are closed */
    public boolean hasDoorsClosed() {
        return doorsClosed;
    }

    /** @return <code>true</code> if lights are off */
    public boolean hasLightsOff() {
        return lightsOff;
    }

    /*
     * Calculated Attribute Accessors
     */

    /** @return current authority of the train (m) */
    public double getCurrentAuthority() {
        return 0.0;
    }

    /** @return current speed of the train (km/h) */
    public double getCurrentSpeed() {
        return 0.0;
    }

    /** @return current mass of the train (t) */
    public double getCurrentMass() {
        return getEmptyMass() + (numberOfPassengers * 150.0);
    }

    // temporary implementation
    public void setSpeed(double speed) {
        this.currentSpeed = speed;
    }

    public void update(int CLOCK) {
        this.x = this.x + (this.currentSpeed * (CLOCK / 1000.0));
    }

    /*
     * Constructors
     */

    /**
     * Initializes all state attributes for a default, stationary train.
     */
    TrainModel(double x, double y) {
        this.x = x;
        this.y = y;
        this.car = null; // CarModel not instantiable, for data access only
        this.engineStatus = true;
        this.signalPickupStatus = true;
        this.brakeStatus = true;
        this.emergencyBrakePulled = false;
        this.speedLimit = 0.0;
        this.accelerationLimit = 0.0;
        this.setpoint = 0.0;
        this.numberOfCars = 1;
        this.numberOfCrew = 1;
        this.numberOfPassengers = 0;
        this.interiorTemperature = 21.0;
        this.doorsClosed = true;
        this.lightsOff = true;
    }

    // other constructors (with parameters) ...
}
