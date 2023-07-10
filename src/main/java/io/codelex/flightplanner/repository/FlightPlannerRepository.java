package io.codelex.flightplanner.repository;

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

    public void addFlight(Flight flight) throws Exception {
        if (flights.contains(flight)) {
            throw new Exception();
        }
        this.flights.add(flight);
    }

    public Flight getFlight(int id) {
        return flights.get(id-1);
    }

    public void clear() {
        flights.clear();
    }
}
