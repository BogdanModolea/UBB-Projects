package com.example.a1.model;

import java.util.Objects;

public class EsportsTeams {
    String id;
    String top;
    String jungle;
    String mid;
    String bot;
    String support;

    public EsportsTeams(String id, String top, String jungle, String mid, String bot, String support) {
        this.id = id;
        this.top = top;
        this.jungle = jungle;
        this.mid = mid;
        this.bot = bot;
        this.support = support;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getJungle() {
        return jungle;
    }

    public void setJungle(String jungle) {
        this.jungle = jungle;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getBot() {
        return bot;
    }

    public void setBot(String bot) {
        this.bot = bot;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EsportsTeams that = (EsportsTeams) o;
        return id.equals(that.id) && top.equals(that.top) && jungle.equals(that.jungle) && mid.equals(that.mid) && bot.equals(that.bot) && support.equals(that.support);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, top, jungle, mid, bot, support);
    }

    @Override
    public String toString() {
        return "EsportsTeams{" +
                "id='" + id + '\'' +
                ", top='" + top + '\'' +
                ", jungle='" + jungle + '\'' +
                ", mid='" + mid + '\'' +
                ", bot='" + bot + '\'' +
                ", support='" + support + '\'' +
                '}';
    }
}
