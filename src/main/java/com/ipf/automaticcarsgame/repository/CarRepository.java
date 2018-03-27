package com.ipf.automaticcarsgame.repository;

import com.ipf.automaticcarsgame.domain.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
}

