package io.codelex.flightplanner.dto;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeDTO {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public LocalDateTime departureTime;
    public LocalDateTime arrivalTime;

    public TimeDTO(String departureTime, String arrivalTime) {
        this.departureTime = LocalDateTime.parse(departureTime,formatter);
        this.arrivalTime = LocalDateTime.parse(arrivalTime,formatter);
    }

    public boolean isBefore() {
        return this.departureTime.isBefore(this.arrivalTime);
    }
}
