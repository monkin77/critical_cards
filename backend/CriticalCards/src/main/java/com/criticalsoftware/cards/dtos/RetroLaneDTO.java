package com.criticalsoftware.cards.dtos;

import com.criticalsoftware.cards.Color;

import java.util.ArrayList;
import java.util.List;

public class RetroLaneDTO {
    private long id;
    private long cards_session_id;
    private String retro_lane_name;
    private int retro_lane_color;
    private final List<RetroCardDTO> cards = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCards_session_id() {
        return cards_session_id;
    }

    public void setCards_session_id(long cards_session_id) {
        this.cards_session_id = cards_session_id;
    }

    public String getRetro_lane_name() {
        return retro_lane_name;
    }

    public void setRetro_lane_name(String retro_lane_name) {
        this.retro_lane_name = retro_lane_name;
    }

    public int getRetro_lane_color() {
        return retro_lane_color;
    }

    public void setRetro_lane_color(int retro_lane_color) {
        this.retro_lane_color = retro_lane_color;
    }

    public List<RetroCardDTO> getCards() {
        return cards;
    }

    public void addCard(RetroCardDTO card) {
        cards.add(card);
    }

    public String toJSON() {
        return toStringBuilder().toString();
    }

    public StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":" + id + ",");
        if (retro_lane_name != null)
            sb.append("\"name\":\"" + retro_lane_name + "\",");
        sb.append("\"color\":\"" + Color.intToWeb(retro_lane_color) + "\",");
        sb.append("\"cards\":[ ");
        for (RetroCardDTO card : cards) {
            sb.append(card.toJSON() + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.append("}");
    }
}
