package com.example.ItmoProject.service;

import com.example.ItmoProject.model.dto.request.CarInfoReq;
import com.example.ItmoProject.model.dto.response.CarInfoResp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {
    CarInfoResp getCar(Long id);

    CarInfoResp addCar(CarInfoReq req);

    CarInfoResp updateCar(Long id, CarInfoReq req);

    void deleteCar(Long id);

    List<CarInfoResp> getAllCars();

    CarInfoResp getCar(String brand, Integer year);
}
