package ru.ivashkevich.meteorological_sensor_rest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ivashkevich.meteorological_sensor_rest.dto.SensorDTO;
import ru.ivashkevich.meteorological_sensor_rest.services.SensorService;

@Component
public class SensorDTOValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorDTOValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SensorDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;

        if (sensorService.hasSensorWithName(sensorDTO.getName())){
            errors.rejectValue("name", "", "Sensor with such name already exists");
        }
    }
}
