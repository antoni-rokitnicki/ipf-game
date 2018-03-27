package com.ipf.automaticcarsgame.service;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.mapper.CarMapper;
import com.ipf.automaticcarsgame.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarService(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Transactional
    public void createCar(CarRequest carRequest){
        Car car = carMapper.map(carRequest);
        carRepository.save(car);
    }

    public List<Car> findAll() {
        return (List<Car>) this.carRepository.findAll();
    }
}
