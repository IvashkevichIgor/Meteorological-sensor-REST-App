package ru.ivashkevich.meteorological_sensor_rest.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.ivashkevich.meteorological_sensor_rest.dto.SensorDTO;
import ru.ivashkevich.meteorological_sensor_rest.models.Sensor;
import ru.ivashkevich.meteorological_sensor_rest.services.SensorService;
import ru.ivashkevich.meteorological_sensor_rest.util.SensorErrorResponse;
import ru.ivashkevich.meteorological_sensor_rest.util.SensorNotCreatedException;
import ru.ivashkevich.meteorological_sensor_rest.util.SensorDTOValidator;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorService sensorService;
    private final SensorDTOValidator sensorDTOValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorService sensorService, SensorDTOValidator sensorDTOValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorDTOValidator = sensorDTOValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){
        sensorDTOValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()){
            StringBuilder errorMessages = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessages.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotCreatedException(errorMessages.toString());
        }
        sensorService.save(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException ex){
        SensorErrorResponse response = new SensorErrorResponse(ex.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
