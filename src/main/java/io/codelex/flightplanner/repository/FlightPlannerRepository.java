package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.IdGen;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Repository
public class FlightPlannerRepository {

    private List<Flight> flights;

    public FlightPlannerRepository() {
        this.flights = new ArrayList<>();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void addFlight(Flight flight) throws Exception {
        if (flights.contains(flight)) {
            throw new Exception();
        }
        this.flights.add(flight);
    }

    public Flight fetchFlight(int id) {
        return flights.stream()
                .filter(flight -> flight.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void clear() {
        flights.clear();
        IdGen.setId(0);
    }

    public boolean deleteFlight(int id) {
        return this.flights.removeIf(flight -> flight.getId() == id);
    }

}
