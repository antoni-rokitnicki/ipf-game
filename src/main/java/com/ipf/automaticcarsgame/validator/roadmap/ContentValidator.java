package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.service.roadmap.CreateRoadmapRequest;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@Order(2)
public class ContentValidator implements RoadmapValidator {

    private static final String INVALID_CONTENT = "INVALID_CONTENT";

    public ValidationResult validate(CreateRoadmapRequest gameMap) {
        if (gameMap == null || gameMap.getFields() == null || gameMap.getFields().length == 0) {
            return createError();
        }

        final boolean incorrectContent = Stream.of(gameMap.getFields()).flatMapToInt(IntStream::of).filter(invalidContent()).findFirst().isPresent();
        if (incorrectContent) {
            return createError();
        }

        return createSuccessValidation();
    }

    private IntPredicate invalidContent() {
        return digit -> digit != 0 && digit != 1;
    }

    private ValidationResult createError() {
        final ValidationResult validation = new ValidationResult();
        final ValidationResult.Error error = new ValidationResult.Error();
        error.setCode(INVALID_CONTENT);
        error.setMessage("Invalid content. Supported only 0 or 1");
        validation.addError(error);
        return validation;
    }
}
