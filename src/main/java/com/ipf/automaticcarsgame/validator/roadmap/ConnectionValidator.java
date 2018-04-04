package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ConnectionValidator implements RoadmapValidator {

    private static final String UNLINKED_ROADS = "UNLINKED_ROADS";
    private static final String UNLINKED_ROADS_MESSAGE = "Unlinked roads";

    public Result validate(RoadmapRequest gameMap) {
        final int[][] fields = gameMap.getFields();
        final int[][] roads = new int[fields.length][fields.length];

        int areaNumber = 1;
        for (int row = 0; row < fields.length; row++) {
            for (int col = 0; col < fields[row].length; col++) {
                if (fields[row][col] == 0) {
                    roads[row][col] = 0;
                } else {
                    int top = getTopRoadNumber(row, col, roads);
                    int left = getLeftRoadNumber(row, col, roads);
                    if (top == 0 && left == 0) {
                        roads[row][col] = areaNumber;
                        areaNumber++;
                    } else if (top == 0 && left > 0) {
                        roads[row][col] = left;
                    } else if (top > 0 && left == 0) {
                        roads[row][col] = top;
                    } else if (top < left) {
                        roads[row][col] = top;
                        connectRoads(roads, left, top);
                    } else if (top > left) {
                        roads[row][col] = left;
                        connectRoads(roads, top, left);
                    }
                }
            }

        }

        if (checkIfExistOnlyOneRoad(roads)) {
            return createSuccessValidation();
        } else {
            return createError(UNLINKED_ROADS, UNLINKED_ROADS_MESSAGE);
        }

    }

    private boolean checkIfExistOnlyOneRoad(int[][] roads) {
        for (int row = 0; row < roads.length; row++) {
            for (int col = 0; col < roads[row].length; col++) {
                if (roads[row][col] > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getLeftRoadNumber(int row, int col, int[][] roads) {
        if (col > 0) {
            return roads[row][col - 1];
        } else {
            return 0;
        }
    }

    private int getTopRoadNumber(int row, int col, int[][] roads) {
        if (row > 0) {
            return roads[row - 1][col];
        } else {
            return 0;
        }
    }

    private void connectRoads(int[][] roads, int roadNumberToChange, int newRoadNumber) {
        for (int i = 0; i < roads.length; i++) {
            for (int k = 0; k < roads[i].length; k++) {
                if (roads[i][k] == roadNumberToChange) {
                    roads[i][k] = newRoadNumber;
                }
            }
        }
    }

}
