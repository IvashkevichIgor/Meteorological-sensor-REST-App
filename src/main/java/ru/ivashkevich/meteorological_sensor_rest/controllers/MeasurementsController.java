package ru.ivashkevich.meteorological_sensor_rest.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.ivashkevich.meteorological_sensor_rest.dto.MeasurementDTO;
import ru.ivashkevich.meteorological_sensor_rest.dto.RainyDaysCountDTO;
import ru.ivashkevich.meteorological_sensor_rest.models.Measurement;
import ru.ivashkevich.meteorological_sensor_rest.services.MeasurementService;
import ru.ivashkevich.meteorological_sensor_rest.util.MeasurementDTOValidator;
import ru.ivashkevich.meteorological_sensor_rest.util.MeasurementErrorResponse;
import ru.ivashkevich.meteorological_sensor_rest.util.MeasurementNotCreatedException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;
    private final MeasurementDTOValidator measurementDTOValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, MeasurementDTOValidator measurementDTOValidator, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementDTOValidator = measurementDTOValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MeasurementDTO> getAllMeasurements(){
        return measurementService.getAllMeasurements()
                .stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> save(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult){
        measurementDTOValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()){
            StringBuilder errorMessages = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessages.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotCreatedException(errorMessages.toString());
        }

        measurementService.save(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/rainyDaysCount")
    public RainyDaysCountDTO countRainyDays(){
        return new RainyDaysCountDTO(measurementService.countRainyDays());
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException ex){
        MeasurementErrorResponse response = new MeasurementErrorResponse(ex.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }
}
