package ru.ivashkevich.meteorological_sensor_rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ivashkevich.meteorological_sensor_rest.models.Sensor;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
}
