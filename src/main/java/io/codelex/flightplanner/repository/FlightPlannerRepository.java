package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.IdGen;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;

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
        return flights.get((id-1));
    }

    public void clear() {
        flights.clear();
        IdGen.setId(0);
    }

    public Flight deleteFlight(int id) {
        return flights.remove(id-1);
    }

}
