package ru.ivashkevich.meteorological_sensor_rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.ivashkevich.meteorological_sensor_rest.models.Sensor;

public class MeasurementDTO {

    @NotNull(message = "Temperature value should not be null")
    @Min(value = -100, message = "Temperature value shouldn't be less than -100")
    @Max(value = 100, message = "Temperature value shouldn't be greater than 100")
    private double value;

    @NotNull(message = "Raining info should not be null")
    private boolean raining;

    @NotNull(message = "Measurement should have sensor name")
    private Sensor sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
