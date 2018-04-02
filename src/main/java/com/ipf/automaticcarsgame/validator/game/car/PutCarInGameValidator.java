package com.ipf.automaticcarsgame.validator.game.car;

import com.ipf.automaticcarsgame.domain.Position;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.repository.RoadmapRepository;
import com.ipf.automaticcarsgame.service.roadmap.RoadmapPositionService;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.ipf.automaticcarsgame.dto.Result.Error;

@Component
public class PutCarInGameValidator implements GameCarValidator {

    private final RoadmapRepository roadmapRepository;
    private final GameCarBasicValidator gameCarBasicValidator;
    private final RoadmapPositionService roadmapPositionService;

    public PutCarInGameValidator(RoadmapRepository roadmapRepository, GameCarBasicValidator gameCarBasicValidator, RoadmapPositionService roadmapPositionService) {
        this.roadmapRepository = roadmapRepository;
        this.gameCarBasicValidator = gameCarBasicValidator;
        this.roadmapPositionService = roadmapPositionService;
    }

    @Override
    public Result validate(GameCarRequest gameCarRequest) {
        Result result = gameCarBasicValidator.validate(gameCarRequest);
        validatePosition(gameCarRequest, result);

        return result;
    }

    private void validatePosition(GameCarRequest gameCarRequest, Result result) {
        if (result.isValid()) {
            Position position = gameCarRequest.getPosition();
            if (position != null && position.getCol() != null && position.getRow() != null) {
                validateCcnstraints(gameCarRequest, result);
            } else {
                result.addError(new Error("POSITION_INCORRECT", "Position is incorrect"));
            }
        }
    }

    private void validateCcnstraints(GameCarRequest gameCarRequest, Result result) {
        Optional<Roadmap> roadmapOpt = roadmapRepository.findByNameIgnoreCaseAndDeleted(gameCarRequest.getRoadmap(), false);

        if (roadmapOpt.isPresent()) {
            Roadmap roadmap = roadmapOpt.get();
            boolean fieldIsCorrect = roadmapPositionService.checkIfFieldIsCorrect(roadmap, gameCarRequest.getPosition());

            if (!fieldIsCorrect) {
                result.addError(new Error("FIELD_INVALID", "Pointed position is invalid"));
            } else {
                boolean fieldIsOccupied = roadmapPositionService.checkIfFieldIsOccupied(roadmap, gameCarRequest.getPosition());

                if (!fieldIsOccupied) {
                    result.addError(new Error("FIELD_OCCUPIED", "Pointed position is occupied"));
                }
            }
        }
    }
}
