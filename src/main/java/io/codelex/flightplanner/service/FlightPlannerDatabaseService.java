package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.repository.AirportDatabaseRepository;
import io.codelex.flightplanner.repository.FlightDatabaseRepository;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightRequest;
import io.codelex.flightplanner.response.FlightResponse;
import io.codelex.flightplanner.response.PageResult;

import java.util.List;

public class FlightPlannerDatabaseService implements FlightPlannerService{
    private FlightDatabaseRepository flightDatabaseRepository;
    private AirportDatabaseRepository airportDatabaseRepository;

    public FlightPlannerDatabaseService(FlightDatabaseRepository flightDatabaseRepository, AirportDatabaseRepository airportDatabaseRepository) {
        this.flightDatabaseRepository = flightDatabaseRepository;
        this.airportDatabaseRepository = airportDatabaseRepository;
    }

    @Override
    public FlightResponse fetchFlight(long id) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public Flight addFlight(AddFlightRequest flightRequest) {
        return null;
    }

    @Override
    public Flight isValidAddFlightRequest(AddFlightRequest flightRequest) {
        return null;
    }

    @Override
    public void isValidAirport(AddFlightRequest flightRequest) {

    }

    @Override
    public void isValidTime(AddFlightRequest flightRequest) {

    }

    @Override
    public void isValidFlightRequest(AddFlightRequest flightRequest) {

    }

    @Override
    public void deleteFlight(long id) {

    }

    @Override
    public Flight createFlight(AddFlightRequest addFlightRequest) {
        return null;
    }

    @Override
    public List<Airport> searchAirports(String phrase) {
        return null;
    }

    @Override
    public List<Airport> createAirportList(List<Flight> flights) {
        return null;
    }

    @Override
    public PageResult<Flight> searchFlight(SearchFlightRequest searchFlightRequest) {
        return null;
    }
}
