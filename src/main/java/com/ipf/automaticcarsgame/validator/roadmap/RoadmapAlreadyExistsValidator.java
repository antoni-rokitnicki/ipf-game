package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import com.ipf.automaticcarsgame.repository.RoadmapRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(4)
public class RoadmapAlreadyExistsValidator implements RoadmapValidator {
    private static final String ALREADY_EXISTS = "ALREADY_EXISTS";
    private final RoadmapRepository roadmapRepository;

    public RoadmapAlreadyExistsValidator(RoadmapRepository roadmapRepository) {
        this.roadmapRepository = roadmapRepository;
    }

    @Override
    public Result validate(RoadmapRequest createRoadmapRequest) {
        final Optional<Roadmap> roadmapOptional = roadmapRepository.findByNameIgnoreCaseAndDeleted(createRoadmapRequest.getName(), false);
        if (roadmapOptional.isPresent()) {
            return Result.ResultBuilder.builder()
                    .addError(new Result.Error(ALREADY_EXISTS, "Roadmap " + createRoadmapRequest.getName() + " already exists"))
                    .build();
        } else {
            return Result.ResultBuilder.builder().build();
        }
    }

}
