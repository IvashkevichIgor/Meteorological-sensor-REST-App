package ru.ivashkevich.meteorological_sensor_rest.util;

public class MeasurementNotCreatedException extends RuntimeException {

    public MeasurementNotCreatedException(String message) {
        super(message);
    }
}
