package ru.ivashkevich.meteorological_sensor_rest.dto;

public class RainyDaysCountDTO {
    private int rainyDaysCount;

    public int getRainyDaysCount() {
        return rainyDaysCount;
    }

    public void setRainyDaysCount(int rainyDaysCount) {
        this.rainyDaysCount = rainyDaysCount;
    }

    public RainyDaysCountDTO() {
    }

    public RainyDaysCountDTO(int rainyDaysCount) {
        this.rainyDaysCount = rainyDaysCount;
    }
}
