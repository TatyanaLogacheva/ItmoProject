package com.example.ItmoProject.service.impl;

import com.example.ItmoProject.model.dto.request.CarInfoReq;
import com.example.ItmoProject.model.dto.response.CarInfoResp;
import com.example.ItmoProject.model.enums.CarType;
import com.example.ItmoProject.model.enums.Color;
import com.example.ItmoProject.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CarServiceImpl implements CarService {
    private final ObjectMapper mapper;

    @Override
    public CarInfoResp getCar(Long id) {
        return CarInfoResp.builder()
                .id(1L)
                .brand("Volvo")
                .model("S60")
                .color(Color.BLUE)
                .year(2020)
                .price(3100000L)
                .isNew(false)
                .type(CarType.SEDAN)
                .build();
    }

    @Override
    public CarInfoResp addCar(CarInfoReq req) {
        CarInfoResp carInfoResp = mapper.convertValue(req, CarInfoResp.class);
        carInfoResp.setId(1L);
        return carInfoResp;
    }

    @Override
    public CarInfoResp updateCar(Long id, CarInfoReq req) {
        if (id!=1L){
            log.error("Car with id {} not found", id);
            return null;
        }
        return CarInfoResp.builder()
                .id(1L)
                .brand("Exeed")
                .model("LX")
                .color(Color.GREEN)
                .year(2024)
                .price(280000L)
                .isNew(true)
                .type(CarType.SUV)
                .build();
    }

    @Override
    public void deleteCar(Long id) {
        if (id!=1L){
            log.error("Car with id {} not found", id);
            }
        log.info("Car with id {} deleted", id);
    }

    @Override
    public List<CarInfoResp> getAllCars() {
        return List.of(CarInfoResp.builder()
                .id(1L)
                .brand("Exeed")
                .model("LX")
                .color(Color.GREEN)
                .year(2024)
                .price(280000L)
                .isNew(true)
                .type(CarType.SUV)
                .build(),
                CarInfoResp.builder()
                .id(2L)
                .brand("Volvo")
                .model("S60")
                .color(Color.BLUE)
                .year(2020)
                .price(3100000L)
                .isNew(false)
                .type(CarType.SEDAN)
                .build());
    }

    @Override
    public CarInfoResp getCar(String brand, Integer year) {
        return getCar(1L);
    }


}
