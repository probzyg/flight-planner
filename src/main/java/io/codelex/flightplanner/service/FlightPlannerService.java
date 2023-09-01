package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightRequest;
import io.codelex.flightplanner.response.FlightResponse;
import io.codelex.flightplanner.response.PageResult;

import java.util.List;

public interface FlightPlannerService {
    FlightResponse fetchFlight(long id);
    void clear();
    Flight addFlight(AddFlightRequest flightRequest);
    void deleteFlight(long id);
    List<Airport> searchAirports(String phrase);
    PageResult<Flight> searchFlight(SearchFlightRequest searchFlightRequest);
}
