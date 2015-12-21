package org.ecotrack.automatictraincontroller.trainmodel;

import org.ecotrack.automatictraincontroller.traincontroller.TrainController;

/**
 * The Train class provides a simple model of a real train.
 *
 * @author timglorioso
 */
public class Train {

    private static final double ROOM_TEMPERATURE = 22; // °C
    private static final double GRAVITATIONAL_ACCELERATION = 9.8; // m/s^2
    private static final double PASSENGER_MASS = 68; // kg

    private static int trainCount = 0;

    private final int id;                   // +get
    private final int carCount;             // +get
    private final int crewCount;            // +get
    private final TrainCar trainCarData;    // +get
    private final double length;            // +get
    private final double emptyMass;
    private final int maxPassengerCount;

    private double timestep;
    private double mass;
    private double speedLimit;              // +get
    private double authority;               // +get
    private double grade;
    private double acceleration;
    private double powerCommand;            //      +set
    private double currentSpeed;            // +get
    private boolean engineFailure;          // +get ~set
    private boolean signalPickupFailure;    // +get ~set
    private boolean brakeFailure;           // +get ~set
    private boolean emergencyBrakePulled;   // +get ~set
    private boolean doorsOpen;              // +get +set
    private boolean lightsOn;               // +get +set
    private double interiorTemperature;     // +get +set
    private int passengerCount;             // +get +set
    private TrainController tc;

    /*
     * PUBLIC methods
     */

    public void update(int clock, double speed, double authority,
                       double grade) {
        if (!signalPickupFailure) {
            powerCommand = tc.update(speed, authority, clock);
            speedLimit = speed;
            this.authority = authority;
            this.grade = grade;
            timestep = (double) clock / 1000;
            calculateSpeed();
        }
    }

    /**
     * @return the unique ID of this Train.
     */
    public int getID() {
        return id;
    }

    /**
     * @return the number of cars in this Train.
     */
    public int getCarCount() {
        return carCount;
    }

    /**
     * @return the number of crew members on this Train.
     */
    public int getCrewCount() {
        return crewCount;
    }

    /**
     * @return the train car data for this Train. See the TrainCar class at
     * the end of this file for the fields which can be accessed with dot
     * notation, e.g., trainCarData.length
     */
    public TrainCar getTrainCarData() {
        return trainCarData;
    }

    /**
     * @return the length of this Train.
     */
    public double getLength() {
        return length;
    }

    /**
     * @return the speed limit of this Train, as given by the CTC.
     */
    public double getSpeedLimit() {
        return speedLimit;
    }

    /**
     * @return the authority of this Train, as given by the CTC.
     */
    public double getAuthority() {
        return authority;
    }

    /**
     * This method allows this Train's TrainController to set its power
     * command. Using this power, the new speed is calculated and returned.
     *
     * @param power  the power for this Train to use
     *
     * @return the new speed calculated from the given power.
     */
    public double setPowerCommand(double power) {
        powerCommand = power;
        return currentSpeed;
    }

    /**
     * @return the current speed of this Train.
     */
    public double getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * @return <code>true</code> when this Train's engine isn't working.
     */
    public boolean getEngineFailure() {
        return engineFailure;
    }

    /**
     * @return <code>true</code> when this Train's signal pickup isn't working.
     */
    public boolean getSignalPickupFailure() {
        return signalPickupFailure;
    }

    /**
     * @return <code>true</code> when this Train's service brake isn't working.
     */
    public boolean getBrakeFailure() {
        return brakeFailure;
    }

    // need to figure out how emergency brake stuff works
    public boolean isEmergencyBrakePulled() {
        return emergencyBrakePulled;
    }

    /**
     * @return <code>true</code> when this Train's doors are open.
     */
    public boolean hasDoorsOpen() {
        return doorsOpen;
    }

    /**
     * This method allows this Train's TrainController to open or close its
     * doors.
     *
     * @param state  <code>true</code> to open the doors
     *               <code>false</code> to close the doors
     */
    public void setDoorsOpen(boolean state) {
        doorsOpen = state;
    }

    /**
     * @return <code>true</code> when this Train's lights are on.
     */
    public boolean hasLightsOn() {
        return lightsOn;
    }

    /**
     * This method allows this Train's TrainController to turn its lights on
     * or off.
     *
     * @param state  <code>true</code> to turn the lights on
     *               <code>false</code> to turn the lights off
     */
    public void setLightsOn(boolean state) {
        lightsOn = state;
    }

    /**
     * @return the interior temperature of this Train.
     */
    public double getInteriorTemperature() {
        return interiorTemperature;
    }

    /**
     * This method allows this Train's TrainController to set its interior
     * temperature.
     *
     * @param temperature  the new interior temperature
     */
    public void setInteriorTemperature(double temperature) {
        interiorTemperature = temperature;
    }

    /**
     * @return the number of passengers on this Train.
     */
    public int getPassengerCount() {
        return passengerCount;
    }

    /**
     * This method allows the TrackModel to set this Train's passenger count
     * while at a station.
     *
     * @param count  the new passenger count
     *
     * @return <code>true</code> when there is enough room on this Train for
     *         the given number of passengers.
     */
    public boolean setPassengerCount(int count) {
        if (count <= maxPassengerCount) {
            passengerCount = count;
            calculateMass();
            return true;
        }
        return false;
    }

    public void setServiceBrake(boolean active) {
        // consider case when brakeFailure is true
        if (active) {
            acceleration = trainCarData.serviceDecel;
        } else {
            acceleration = 0;
        }
    }

    /*
     * If the engine is failing and we're not braking, make sure acceleration
     * is set to 0.
     */
    public void setEngineFailure(boolean isFailure) {
        if (isFailure && acceleration > 0) {
            acceleration = 0;
        }
        engineFailure = isFailure;
    }

    public void setSignalPickupFailure(boolean isFailure) {
        signalPickupFailure = isFailure;
    }

    public void setBrakeFailure(boolean isFailure) {
        brakeFailure = isFailure;
    }

    /*
     * If the emergency brake is pulled, set the acceleration to the
     * emergency brake deceleration.
     */
    public void setEmergencyBrake(boolean pulled) {
        if (pulled) {
            acceleration = trainCarData.emergencyDecel;
        }
        emergencyBrakePulled = pulled;
    }

    /*
     * NON-PUBLIC methods
     */

    private void calculateMass() {
        mass = emptyMass + (passengerCount * PASSENGER_MASS);
    }

    private void calculateSpeed() {
        double accel;
        double angle = Math.atan(grade / 100);
        if (currentSpeed == 0) {
            accel = trainCarData.normalAccel;
        } else {
            if (powerCommand <= 0) {
                accel = 0;
            } else {
                accel = powerCommand / (mass * currentSpeed);
            }
        }
        accel += acceleration;
        currentSpeed = currentSpeed +
            timestep * accel * Math.cos(angle);
        if (currentSpeed < 0) {
            currentSpeed = 0;
        }
    }

    /*
     * PUBLIC constructors
     */

    /**
     * Initialize a stationary, empty Train with one train car.
     */
    public Train() {
        this(1);
        tc = new TrainController(this);
    }

    /*
     * PRIVATE constructors
     */

    /*
     * This private constructor initializes the Train object so that the
     * public constructor can pass 'this' to the TrainController constructor.
     */
    private Train(int carCount) {
        id = trainCount;
        this.carCount = carCount;
        crewCount = 1;
        trainCarData = new TrainCar();
        length = carCount * trainCarData.length;
        emptyMass = carCount * trainCarData.emptyMass;
        maxPassengerCount = carCount * trainCarData.maxPassengerCount;
        mass = emptyMass;
        interiorTemperature = ROOM_TEMPERATURE;
        trainCount++;
    }
}

/*
 * The TrainCar class stores data for the Bombardier Flexity 2.
 */
class TrainCar {

    /* dimensions and weight */
    public final double length = 32.2; // m
    public final double height = 3.42; // m
    public final double width = 2.65; // m
    public final double emptyMass = 40900.0; // kg

    /* performance and capacity */
    public final double maxSpeed = 70000.0 / 3600.0; // m/s (70 km/h)
    public final double normalAccel = 0.5; // m/s^2
    public final double serviceDecel = -1.2; // m/s^2
    public final double emergencyDecel = -2.73; // m/s^2
    public final int maxPassengerCount = 222;
}
