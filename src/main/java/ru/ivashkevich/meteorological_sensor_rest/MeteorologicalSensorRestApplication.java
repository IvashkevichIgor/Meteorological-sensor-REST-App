package ru.ivashkevich.meteorological_sensor_rest;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MeteorologicalSensorRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeteorologicalSensorRestApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
