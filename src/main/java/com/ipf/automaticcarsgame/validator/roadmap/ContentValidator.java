package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@Order(2)
public class ContentValidator implements RoadmapValidator {

    private static final String INVALID_CONTENT = "INVALID_CONTENT";

    public Result validate(RoadmapRequest gameMap) {
        final boolean incorrectContent = Stream.of(gameMap.getFields()).flatMapToInt(IntStream::of).filter(invalidContent()).findFirst().isPresent();
        if (incorrectContent) {
            return createError(INVALID_CONTENT, "Invalid content. Supported only 0 or 1");
        }
        return createSuccessValidation();
    }

    private IntPredicate invalidContent() {
        return digit -> digit != 0 && digit != 1;
    }

}
