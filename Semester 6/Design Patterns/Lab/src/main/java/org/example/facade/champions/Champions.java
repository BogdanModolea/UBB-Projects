package org.example.facade.champions;

import org.example.domain.Champion;

import java.util.List;

public class Champions {
    public List<Champion> champions;

    public Champions(List<Champion> champions) {
        this.champions = champions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Champion champion : champions) {
            sb.append("Name: ").append(champion.getName()).append("\n");
            sb.append("Role: ").append(champion.getRole()).append("\n");
            sb.append("Type: ").append(champion.getType()).append("\n");
            sb.append("\n");
        }

        return sb.toString();
    }
}
