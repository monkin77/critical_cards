package com.criticalsoftware.cards.dtos;

import java.util.ArrayList;
import java.util.List;

public class RetroDTO {
    private long id;
    private String name;
    private final List<RetroLaneDTO> lanes = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RetroLaneDTO> getLanes() {
        return lanes;
    }

    public void addLane(RetroLaneDTO lane) {
        lanes.add(lane);
    }

    public String toJSON() {
        return toStringBuilder().toString();
    }

    public StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":" + id + ",");
        sb.append("\"name\":\"" + name + "\",");
        sb.append("\"lanes\":[ ");
        for (RetroLaneDTO lane : lanes) {
            sb.append(lane.toJSON() + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.append("}");
    }
}
