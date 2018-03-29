package com.ipf.automaticcarsgame.validator.car;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.dto.car.CarType;
import com.ipf.automaticcarsgame.repository.CarRepository;
import com.ipf.automaticcarsgame.validator.Result;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CarAlreadyExistsValidatorTest {

    private static final String NAME_EXISTED = "Toyota";
    private static final String NAME_NOT_EXISTED = "Opel";

    private CarRepository carRepository = mock(CarRepository.class);
    private CarAlreadyExistsValidator carAlreadyExistsValidator;

    @Before
    public void createRoadmapValidatorProcessor() {
        Car car = new Car();
        car.setName(NAME_EXISTED);

        when(carRepository.findByName(NAME_EXISTED)).thenReturn(Optional.of(car));
        when(carRepository.findByName(NAME_NOT_EXISTED)).thenReturn(Optional.empty());

        carAlreadyExistsValidator = new CarAlreadyExistsValidator(carRepository);
    }


    @Test
    public void shouldReturnFalse() {
        // given
        CarRequest toyota = new CarRequest(NAME_EXISTED, CarType.RACER.name());

        // when
        Result result = carAlreadyExistsValidator.validate(toyota);

        // then
        assertThat(result.isValid()).isFalse();
    }

    @Test
    public void shouldReturnTrue() {
        // given
        CarRequest opel = new CarRequest(NAME_NOT_EXISTED, CarType.RACER.name());

        // when
        Result result = carAlreadyExistsValidator.validate(opel);

        // then
        assertThat(result.isValid()).isTrue();
    }
}
