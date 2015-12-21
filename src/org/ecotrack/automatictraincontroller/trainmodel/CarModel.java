package org.ecotrack.automatictraincontroller.trainmodel;

/**
 * The CarModel class is used as a wrapper for train car data. Any real-world
 * train car can be modeled with the Automated Train Controller by hard-coding
 * values to the given static final fields. Here, we use the Bombardier
 * Flexity 2 tram.
 * <p>
 * Note the private constructor. This class exists to store constants for use
 * by the TrainModel.
 *
 * @author Tim Glorioso
 * @version 0.1.0
 */
class CarModel {

    /*
     * Dimensions and Weight
     */
    static final double LENGTH = 32.2; // m
    static final double HEIGHT = 3.42; // m
    static final double WIDTH = 2.65; // m
    static final double EMPTY_MASS = 40.9; // t

    /*
     * Performance and Capacity
     */
    static final double MAX_SPEED = 70.0; // km/h
    static final double MAX_ACCELERATION = 0.5; // m/s^2
    static final double MAX_SERVICE_DECELERATION = 1.2; // m/s^2
    static final double MAX_EMERGENCY_DECELERATION = 2.73; // m/s^2
    static final int MAX_PASSENGERS = 222;

    /* This class should not be instantiated */
    private CarModel() {}
}
