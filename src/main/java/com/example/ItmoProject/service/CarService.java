package com.example.ItmoProject.service;

import com.example.ItmoProject.model.bd.entity.Car;
import com.example.ItmoProject.model.bd.entity.User;
import com.example.ItmoProject.model.bd.repository.CarRepository;
import com.example.ItmoProject.model.dto.request.CarInfoReq;
import com.example.ItmoProject.model.dto.response.CarInfoResp;
import com.example.ItmoProject.model.dto.response.UserInfoResponse;
import com.example.ItmoProject.model.enums.CarStatus;
import com.example.ItmoProject.utils.PaginationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class CarService {
    private final ObjectMapper mapper;
    private final CarRepository carRepository;
    private final UserService userService;

    public CarInfoResp addCar(CarInfoReq req) {
        Car car = mapper.convertValue(req, Car.class);
        car.setStatus(CarStatus.CREATED);
        Car save = carRepository.save(car);
        return mapper.convertValue(save, CarInfoResp.class);
    }
    public CarInfoResp getCar(Long id) {
        Car car = getCarFromDB(id);
      return mapper.convertValue(car, CarInfoResp.class);
    }
    public Car getCarFromDB(Long id){
        Optional<Car> optionalCar = carRepository.findById(id);
        return optionalCar.orElse(new Car());
    }

    public CarInfoResp updateCar(Long id, CarInfoReq req) {
        Car carFromDB = getCarFromDB(id);
        if (carFromDB.getId() == null){
            log.error("Car with id {} not found", id);
            return mapper.convertValue(carFromDB, CarInfoResp.class);
        }
        Car carReq = mapper.convertValue(req, Car.class);
        carFromDB.setBrand(carReq.getBrand()==null ? carFromDB.getBrand() : carReq.getBrand());
        carFromDB.setModel(carReq.getModel()==null ? carFromDB.getModel() : carReq.getModel());
        carFromDB.setColor(carReq.getColor() == null ? carFromDB.getColor() : carReq.getColor());
        carFromDB.setYear(carReq.getYear()== null ? carFromDB.getYear() : carReq.getYear() );
        carFromDB.setPrice(carReq.getPrice() == null ? carFromDB.getPrice() : carReq.getPrice());
        carFromDB.setIsNew(carReq.getIsNew() == null ? carFromDB.getIsNew() : carReq.getIsNew());
        carFromDB.setType(carReq.getType() == null ? carFromDB.getType() : carReq.getType());

        carFromDB= carRepository.save(carFromDB);
        return mapper. convertValue(carFromDB, CarInfoResp.class);
    }

    public void deleteCar(Long id) {
        Car carFromDB = getCarFromDB(id);
        if (carFromDB.getId()==null){
            log.error("Car with id {} not found", id);
        }
        carFromDB.setStatus(CarStatus.DELETED);
        carRepository.save(carFromDB);
    }

    public List<CarInfoResp> getAllCars() {
        return carRepository.findAll().stream()
                .map(car -> mapper.convertValue(car,CarInfoResp.class))
                .collect(Collectors.toList());
    }

    public CarInfoResp linkCarAndDriver(Long carId, Long userId ){
        Car carFromDB = getCarFromDB(carId);
        User userFromDB = userService.getUserFromDB(userId);
        {
            if (carFromDB.getId() == null || userFromDB.getId() == null){
                return CarInfoResp.builder().build();
            }

            List<Car> cars = userFromDB.getCars();
            Car existingcar = cars.stream().filter(car -> car.getId().equals(carId)).findFirst().orElse(null);

            if(existingcar!= null){
                return mapper.convertValue(existingcar, CarInfoResp.class);
            }
            cars.add(carFromDB);
            User user = userService.updatedCarList(userFromDB);

            carFromDB.setUser(user);
            carRepository.save(carFromDB);

            CarInfoResp carInfoResp = mapper.convertValue(carFromDB, CarInfoResp.class);
            UserInfoResponse userInfoResponse = mapper.convertValue(user, UserInfoResponse.class);

            carInfoResp.setUser(userInfoResponse);

            return carInfoResp;
        }
    }

    public List <CarInfoResp> getUserCars (Long userId) {
        User user = userService.getUserFromDB(userId);
        if (user.getId() == null){
            log.error("Пользователя с id {} не существует", userId);
            return List.of();
        }

        List<Car> userCars = user.getCars();
        return userCars.stream()
                .map(car -> mapper.convertValue(car, CarInfoResp.class))
                .collect(Collectors.toList());
    }

    public Page<CarInfoResp> getAllCarsOnPages(Integer page, Integer perPage, String sort, Sort.Direction order, String filter) {
        Pageable pageRequest = PaginationUtils.getPageRequest(page, perPage, sort, order);

        Page<Car> cars;

        if(StringUtils.hasText(filter)) {
            cars = carRepository.findAllFiltered(pageRequest, filter);
        } else{
            cars = carRepository.findAll(pageRequest);
        }
        List<CarInfoResp> content = cars.stream()
                .map(c ->mapper.convertValue(c, CarInfoResp.class))
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageRequest, cars.getTotalElements());
    }
}
