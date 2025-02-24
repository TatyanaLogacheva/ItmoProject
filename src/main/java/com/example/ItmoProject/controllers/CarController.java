package com.example.ItmoProject.controllers;

import com.example.ItmoProject.model.dto.request.CarInfoReq;
import com.example.ItmoProject.model.dto.response.CarInfoResp;
import com.example.ItmoProject.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@Tag(name="Автомобили")
public class CarController {
    private final CarService carService;


    @GetMapping("/{id}")
    @Operation(summary = "Получить автомобиль по id")
    public CarInfoResp getCar(@PathVariable Long id){
        return carService.getCar(id);
    }

    @PostMapping
    @Operation(summary = "Добавить автомобиль")
    public CarInfoResp addCar(@RequestBody CarInfoReq req){
        return carService.addCar(req);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные автомобиля по id")
    public CarInfoResp updateCar (@PathVariable Long id, @RequestBody CarInfoReq req){
        return carService.updateCar(id, req);
    }

    @DeleteMapping ("/{id}")
    @Operation(summary = "Удалить автомобиль по id")
    public void deleteCar (@PathVariable Long id){
        carService.deleteCar(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список всех автомобилей")
    public List<CarInfoResp> getAllCars(){
        return carService.getAllCars();
    }

    @PostMapping("/linkUserAndDriver/{carId}/{userId}")
    @Operation(summary = "Добавить пользователю автомобиль по id")
    public CarInfoResp linkUserAndDriver(@PathVariable Long carId, @PathVariable Long userId){
        return carService.linkCarAndDriver(carId, userId);
    }

    @GetMapping("/userCars/{userId}")
    @Operation(summary = "Получить список автомобилей пользователя по его id")
    public List <CarInfoResp> userCars(@PathVariable Long userId) {
        return carService.getUserCars(userId);
    }
    @GetMapping("/pages")
    @Operation(summary = "Получить постраничный список всех автомобилей")
    Page<CarInfoResp> getAllCarsOnPages(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer perPage,
                                        @RequestParam(defaultValue = "brand") String sort,
                                        @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                        @RequestParam(required = false) String filter){
        return carService.getAllCarsOnPages(page, perPage, sort, order, filter);
    }

}
