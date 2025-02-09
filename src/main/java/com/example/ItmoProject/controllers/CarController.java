package com.example.ItmoProject.controllers;

import com.example.ItmoProject.model.dto.request.CarInfoReq;
import com.example.ItmoProject.model.dto.response.CarInfoResp;
import com.example.ItmoProject.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/{id}")
    public CarInfoResp getCar(@PathVariable Long id){
        return carService.getCar(id);
    }

    @PostMapping
    public CarInfoResp addCar(@RequestBody CarInfoReq req){
        return carService.addCar(req);
    }

    @PutMapping("/{id}")
    public CarInfoResp updateCar (@PathVariable Long id, @RequestBody CarInfoReq req){
        return carService.updateCar(id, req);
    }

    @DeleteMapping ("/{id}")
    public void deleteCar (@PathVariable Long id){
        carService.deleteCar(id);
    }

    @GetMapping("/all")
    public List<CarInfoResp> getAllCars(){
        return carService.getAllCars();
    }

    @GetMapping
    public CarInfoResp getCarWithParams (@RequestParam String brand, @RequestParam (required = false) Integer year){
        return carService.getCar(brand, year);
    }

}
