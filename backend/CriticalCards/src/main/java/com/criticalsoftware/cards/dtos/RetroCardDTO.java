package com.criticalsoftware.cards.dtos;

import com.criticalsoftware.cards.Color;

public class RetroCardDTO {
    private long id;
    private long retro_lane_id;
    private String retro_card_text;
    private int retro_card_color;
    private int retro_votes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRetro_lane_id() {
        return retro_lane_id;
    }

    public void setRetro_lane_id(long retro_lane_id) {
        this.retro_lane_id = retro_lane_id;
    }

    public String getRetro_card_text() {
        return retro_card_text;
    }

    public void setRetro_card_text(String retro_card_text) {
        this.retro_card_text = retro_card_text;
    }

    public int getRetro_card_color() {
        return retro_card_color;
    }

    public void setRetro_card_color(int retro_card_color) {
        this.retro_card_color = retro_card_color;
    }

    public int getRetro_votes() {
        return retro_votes;
    }

    public void setRetro_votes(int retro_votes) {
        this.retro_votes = retro_votes;
    }

    public String toJSON() {
        return toStringBuilder().toString();
    }

    public StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":" + id + ",");
        sb.append("\"text\":\"" + retro_card_text + "\",");
        sb.append("\"color\":\"" + Color.intToWeb(retro_card_color) + "\",");
        sb.append("\"votes\":" + retro_votes);
        return sb.append("}");
    }
}
