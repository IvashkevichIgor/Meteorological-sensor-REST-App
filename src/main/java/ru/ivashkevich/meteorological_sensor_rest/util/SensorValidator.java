package ru.ivashkevich.meteorological_sensor_rest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ivashkevich.meteorological_sensor_rest.models.Sensor;
import ru.ivashkevich.meteorological_sensor_rest.services.SensorService;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Sensor.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorService.hasSensorWithName(sensor.getName())){
            errors.rejectValue("name", "", "Sensor with such name already exists");
        }
    }
}
