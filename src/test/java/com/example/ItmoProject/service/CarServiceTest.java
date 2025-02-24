package com.example.ItmoProject.service;

import com.example.ItmoProject.exeption.CommonBackendException;
import com.example.ItmoProject.model.bd.entity.Car;
import com.example.ItmoProject.model.bd.entity.User;
import com.example.ItmoProject.model.bd.repository.CarRepository;
import com.example.ItmoProject.model.dto.request.CarInfoReq;
import com.example.ItmoProject.model.dto.response.CarInfoResp;
import com.example.ItmoProject.model.dto.response.UserInfoResponse;
import com.example.ItmoProject.model.enums.CarStatus;
import com.example.ItmoProject.utils.PaginationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {
    @InjectMocks
    private CarService carService;
    @Spy
    private ObjectMapper mapper;
    @Mock
    private CarRepository carRepository;
    @Mock
    private UserService userService;

    @Test
    public void addCar() {
        CarInfoReq req = new CarInfoReq();
        req.setBrand("BMW");

        Car car = new Car();
        car.setId(1L);
        car.setBrand(req.getBrand());

        when(carRepository.save(any(Car.class))).thenReturn(car);
        CarInfoResp resp = carService.addCar(req);
        assertEquals(car.getId(),resp.getId());
    }

    @Test (expected = CommonBackendException.class)
    public void addCarExisted() {
        CarInfoReq req = new CarInfoReq();
        req.setBrand("BMW");
        req.setModel("X60");
        req.setPrice(2000L);

        Car car = new Car();
        car.setId(1L);
        car.setBrand(req.getBrand());
        car.setModel(req.getModel());
        car.setPrice(req.getPrice());

        when(carRepository.findCarByBrandAndAndModelAndPrice(car.getBrand(),car.getModel(), car.getPrice()))
                .thenReturn(Optional.of(car));
        carService.addCar(req);
    }

    @Test
    public void getCar() {
        Car car = new Car();
        car.setId(1L);
        car.setBrand("BMW");

        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        CarInfoResp carInfoResp = carService.getCar(car.getId());
        assertEquals(car.getBrand(),carInfoResp.getBrand());
    }

    @Test
    public void getCarFromDB() {
        Car car = new Car();
        car.setId(1L);
        car.setBrand("BMW");

        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        Car carFromDB = carService.getCarFromDB(car.getId());
        assertEquals(car.getBrand(), carFromDB.getBrand());
    }

    @Test (expected = CommonBackendException.class)
    public void getCarFromDBNotFound() {
        carService.getCarFromDB(1L);
    }

    @Test
    public void updateCar() {
        Car car = new Car();
        car.setId(1L);
        car.setBrand("BMW");

        CarInfoReq req = new CarInfoReq();
        req.setBrand("Audi");

        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        carService.updateCar(car.getId(), req);
        assertEquals(req.getBrand(), car.getBrand() );
    }

    @Test
    public void deleteCar() {
        Car car = new Car();
        car.setId(1L);
        car.setBrand("BMW");

        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        carService.deleteCar(car.getId());
        verify(carRepository, times(1)).save(any(Car.class));
        assertEquals(CarStatus.DELETED, car.getStatus());
    }

    @Test
    public void getAllCars() {
        Car car1 = new Car();
        car1.setId(1L);
        car1.setBrand("BMW");
        Car car2 = new Car();
        car2.setId(1L);
        car2.setBrand("KIA");
        List<Car> cars = Arrays.asList(car1,car2);

        when(carRepository.findAll()).thenReturn(List.of(car1,car2));
        List<CarInfoResp> carInfoResp = carService.getAllCars();
        assertEquals(cars.get(0).getId(), carInfoResp.get(0).getId());
        assertEquals(cars.get(1).getId(), carInfoResp.get(1).getId());
    }

    @Test
    public void linkCarAndDriver() {
    }

    @Test
    public void getUserCars() {
        Car car = new Car();
        car.setId(1L);
        car.setBrand("BMW");
        List<Car>cars= List.of(car);

        User user = new User();
        user.setId(1L);
        when(userService.getUserFromDB(1L)).thenReturn(user);
        user.setCars(cars);
        List<CarInfoResp> userCars = carService.getUserCars(user.getId());
        assertEquals(cars.get(0).getBrand(), userCars.get(0).getBrand());
    }

    @Test
    public void getAllCarsOnPagesWithoutFilter() {
        Integer page = 3;
        Integer perPage = 1;
        String sort = "brand";
        Sort.Direction order = Sort.Direction.ASC;
        String filter ="";
        Pageable pageReq = PaginationUtils.getPageRequest(page,perPage,sort, order);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setBrand("BMW");
        Car car2 = new Car();
        car2.setId(1L);
        car2.setBrand("KIA");
        List<Car> cars = Arrays.asList(car1,car2);
        Page<Car> pages = new PageImpl<>(cars, pageReq, cars.size());
        when(carRepository.findAll(any(Pageable.class))).thenReturn(pages);
        Page<CarInfoResp> carInfoResp = carService.getAllCarsOnPages(page, perPage, sort, order, filter);
        assertEquals(pages.getTotalPages(), carInfoResp.getTotalPages());
    }

    @Test
    public void getAllCarsOnPagesWithFilter() {
        Integer page = 3;
        Integer perPage = 1;
        String sort = "brand";
        Sort.Direction order = Sort.Direction.ASC;
        String filter ="id";
        Pageable pageReq = PaginationUtils.getPageRequest(page,perPage,sort, order);

        Car car1 = new Car();
        car1.setId(1L);
        car1.setBrand("BMW");
        Car car2 = new Car();
        car2.setId(1L);
        car2.setBrand("KIA");
        List<Car> cars = Arrays.asList(car1,car2);
        Page<Car> pages = new PageImpl<>(cars, pageReq, cars.size());
        when(carRepository.findAllFiltered(pageReq, filter)).thenReturn(pages);
        Page<CarInfoResp> carInfoResp = carService.getAllCarsOnPages(page, perPage, sort, order, filter);
        assertEquals(pages.getTotalPages(), carInfoResp.getTotalPages());
    }
}