package io.codelex.flightplanner.response;

import io.codelex.flightplanner.domain.Flight;

public class FlightResponse {
    private Flight flight;

    public FlightResponse(Flight flight) {
        this.flight = flight;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
