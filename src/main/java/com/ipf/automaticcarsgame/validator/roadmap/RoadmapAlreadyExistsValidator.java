package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.repository.RoadmapRepository;
import com.ipf.automaticcarsgame.service.roadmap.CreateRoadmapRequest;
import com.ipf.automaticcarsgame.validator.ValidationResult;
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
    public ValidationResult validate(CreateRoadmapRequest createRoadmapRequest) {
        final Optional<Roadmap> roadmapOptional = roadmapRepository.findByNameIgnoreCaseAndDeleted(createRoadmapRequest.getName(), false);
        if (roadmapOptional.isPresent()) {
            return ValidationResult.ValidationResultBuilder.builder()
                    .addError(new ValidationResult.Error(ALREADY_EXISTS, "Roadmap " + createRoadmapRequest.getName() + " already exists"))
                    .build();
        } else {
            return ValidationResult.ValidationResultBuilder.builder().build();
        }
    }

}
