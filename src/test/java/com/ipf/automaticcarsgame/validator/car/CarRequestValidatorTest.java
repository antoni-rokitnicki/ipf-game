package com.ipf.automaticcarsgame.validator.car;


import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.dto.car.CarType;
import com.ipf.automaticcarsgame.dto.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarRequestValidatorTest {

    @Autowired
    CarRequestValidator carRequestValidator;

    @Test
    public void shouldBeValidCarRequest(){
        CarRequest toyota = new CarRequest("Toyota", CarType.NORMAL_CAR.name());

        Result result = carRequestValidator.validate(toyota);

        assertThat(result.isValid()).isTrue();
    }


    @Test
    public void shouldBeInValidCarRequest(){
        CarRequest car1 = new CarRequest();
        Result result = carRequestValidator.validate(car1);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getErrors().size() == 2).isTrue();

        CarRequest car2 = new CarRequest("Toyota", null);
        result = carRequestValidator.validate(car2);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getErrors().size() == 1).isTrue();

        CarRequest car3 = new CarRequest("Toyota", "ABC");
        result = carRequestValidator.validate(car3);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getErrors().size() == 1).isTrue();

        CarRequest car4 = new CarRequest("", "ABC");
        result = carRequestValidator.validate(car4);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getErrors().size() == 2).isTrue();
    }
}
