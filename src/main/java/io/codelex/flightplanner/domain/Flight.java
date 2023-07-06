package io.codelex.flightplanner.domain;

import org.springframework.stereotype.Component;

@Component
public class Flight {
    private int id;
    private String name;

    public Flight() {
    }

    public Flight(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
