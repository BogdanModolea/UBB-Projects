package org.example.domain;

public class Champion {
    private String name;
    private String role;
    private String type;

    public Champion(String name, String role, String type) {
        this.name = name;
        this.role = role;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Champion{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
