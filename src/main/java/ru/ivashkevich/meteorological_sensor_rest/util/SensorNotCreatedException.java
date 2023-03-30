package ru.ivashkevich.meteorological_sensor_rest.util;

public class SensorNotCreatedException extends RuntimeException{

    public SensorNotCreatedException(String message) {
        super(message);
    }
}
