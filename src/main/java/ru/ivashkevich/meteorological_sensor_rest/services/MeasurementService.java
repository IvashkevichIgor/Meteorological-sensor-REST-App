package ru.ivashkevich.meteorological_sensor_rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivashkevich.meteorological_sensor_rest.models.Measurement;
import ru.ivashkevich.meteorological_sensor_rest.models.Sensor;
import ru.ivashkevich.meteorological_sensor_rest.repositories.MeasurementsRepository;
import ru.ivashkevich.meteorological_sensor_rest.repositories.SensorsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;

    @Autowired
    public MeasurementService(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }

    public List<Measurement> getAllMeasurements(){
        return measurementsRepository.findAll();
    }

    public int countRainyDays(){
        return measurementsRepository.countByRainingIsTrue();
    }

    @Transactional
    public void save(Measurement measurement){
        Sensor sensor = sensorsRepository.getByName(measurement.getSensor().getName());
        measurement.setSensor(sensor);
        measurement.setCreatedAt(LocalDateTime.now());
        measurementsRepository.save(measurement);
    }
}
