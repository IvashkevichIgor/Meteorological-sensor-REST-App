package ru.ivashkevich.meteorological_sensor_rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivashkevich.meteorological_sensor_rest.models.Measurement;
import ru.ivashkevich.meteorological_sensor_rest.repositories.MeasurementsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementsRepository measurementsRepository;

    @Autowired
    public MeasurementService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }

    public List<Measurement> getAllMeasurements(){
        return measurementsRepository.findAll();
    }

    public int countRainyDays(){
        return measurementsRepository.countByRainingIsTrue();
    }

    @Transactional
    public void save(Measurement measurement){
        measurement.setCreatedAt(LocalDateTime.now());
        measurementsRepository.save(measurement);
    }
}
