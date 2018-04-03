package com.ipf.automaticcarsgame.domain;

public enum DirectionType {
    NORTH, EAST, SOUTH, WEST;

    public DirectionType nextLeftDirection() {
        DirectionType type = null;
        switch (this) {
            case EAST:
                type = NORTH;
                break;
            case NORTH:
                type = WEST;
                break;
            case WEST:
                type = SOUTH;
                break;
            case SOUTH:
                type = EAST;
                break;
        }
        return type;
    }

    public DirectionType nextRightDirection() {
        DirectionType type = null;
        switch (this) {
            case EAST:
                type = SOUTH;
                break;
            case SOUTH:
                type = WEST;
                break;
            case WEST:
                type = NORTH;
                break;
            case NORTH:
                type = EAST;
                break;
        }
        return type;
    }
}
