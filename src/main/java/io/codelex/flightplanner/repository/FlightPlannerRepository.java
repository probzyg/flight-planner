package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightPlannerRepository {
    private final List<Flight> flightsList;

    public FlightPlannerRepository() {
        this.flightsList = new ArrayList<>();
    }

    public void saveFlight(Flight flight) {
        flightsList.add(flight);
    }

    public Flight getFlight(int id) {
        return flightsList.get(id);
    }
}
