package io.codelex.flightplanner.response;

import io.codelex.flightplanner.domain.Flight;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightResponse {
    private Flight flight;
    public FlightResponse(Flight flight) {
        this.flight = flight;
    }
}
