package org.example.decorator;

public class CustomClassImpl implements CustomClass{
    public CustomClassImpl() {
    }

    @Override
    public String buildChampion() {
        return "Champion ";
    }
}
