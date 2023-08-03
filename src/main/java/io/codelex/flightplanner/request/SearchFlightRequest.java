package io.codelex.flightplanner.request;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.response.PageResult;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class SearchFlightRequest {
    @NotBlank
    private String from;
    @NotBlank
    private String to;
    @NotBlank
    private String departureDate;

    public SearchFlightRequest(String from, String to, String departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public PageResult<Flight> searchFlight(List<Flight> flights, SearchFlightRequest searchFlightRequest) {
        String from = searchFlightRequest.getFrom();
        String to = searchFlightRequest.getTo();

        if (from.equals(to)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Flight> foundFlights = flights.stream()
                .filter(a -> a.getFrom().getAirport().equals(from.trim().toUpperCase()) &&
                        a.getTo().getAirport().equals(to.trim().toUpperCase()) &&
                        a.getDepartureDate().equals(searchFlightRequest.getDepartureDate()))
                .toList();


        return new PageResult<>(0, foundFlights.size(), foundFlights);
    }
}
