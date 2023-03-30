package ru.ivashkevich.meteorological_sensor_rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivashkevich.meteorological_sensor_rest.models.Sensor;
import ru.ivashkevich.meteorological_sensor_rest.repositories.SensorsRepository;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorsRepository sensorRepository;

    @Autowired
    public SensorService(SensorsRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public boolean hasSensorWithName(String name){
        return sensorRepository.existsByName(name);
    }

    @Transactional
    public void save(Sensor sensor){
        sensor.setRegisteredAt(LocalDateTime.now());
        sensorRepository.save(sensor);
    }
}
