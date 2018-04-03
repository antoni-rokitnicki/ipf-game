package com.ipf.automaticcarsgame.dto.roadmap;

import java.util.List;

public class RoadmapResponse {
    private String roadmapName;
    private List<RoadmapPositionDto> positions;

    public RoadmapResponse() {
    }

    public String getRoadmapName() {
        return roadmapName;
    }

    public void setRoadmapName(String roadmapName) {
        this.roadmapName = roadmapName;
    }

    public List<RoadmapPositionDto> getRoadmapPositionResponses() {
        return positions;
    }

    public void setRoadmapPositionResponses(List<RoadmapPositionDto> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoadmapResponse)) return false;

        RoadmapResponse that = (RoadmapResponse) o;

        return roadmapName != null ? roadmapName.equals(that.roadmapName) : that.roadmapName == null;
    }

    @Override
    public int hashCode() {
        return roadmapName != null ? roadmapName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RoadmapResponse{" +
                "roadmapName='" + roadmapName + '\'' +
                ", positions=" + positions +
                '}';
    }
}
