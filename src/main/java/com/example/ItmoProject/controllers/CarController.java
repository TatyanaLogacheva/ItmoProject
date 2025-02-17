package com.example.ItmoProject.controllers;

import com.example.ItmoProject.model.dto.request.CarInfoReq;
import com.example.ItmoProject.model.dto.response.CarInfoResp;
import com.example.ItmoProject.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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

    @PostMapping("/linkUserAndDriver/{carId}/{userId}")
    public CarInfoResp linkUserAndDriver(@PathVariable Long carId, @PathVariable Long userId){
        return carService.linkCarAndDriver(carId, userId);
    }

    @GetMapping("/linkUserCars/{userId}")
    public List <CarInfoResp> linkUserCars(@PathVariable Long userId) {
        return carService.getUserCars(userId);
    }
    @GetMapping("/pages")
    Page<CarInfoResp> getAllCarsOnPages(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer perPage,
                                        @RequestParam(defaultValue = "brand") String sort,
                                        @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                        @RequestParam(required = false) String filter){
        return carService.getAllCarsOnPages(page, perPage, sort, order, filter);
    }

}
