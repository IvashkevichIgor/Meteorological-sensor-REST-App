package ru.ivashkevich.meteorological_sensor_rest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ivashkevich.meteorological_sensor_rest.dto.MeasurementDTO;
import ru.ivashkevich.meteorological_sensor_rest.services.SensorService;

@Component
public class MeasurementDTOValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public MeasurementDTOValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MeasurementDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;

        if (!sensorService.hasSensorWithName(measurementDTO.getSensor().getName())){
            errors.rejectValue("sensor", "", "Sensor with such name doesn't exist");
        }
    }
}
