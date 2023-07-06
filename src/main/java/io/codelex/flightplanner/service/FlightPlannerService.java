package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.repository.FlightPlannerRepository;
import org.springframework.stereotype.Service;


@Service
public class FlightPlannerService {
    private FlightPlannerRepository flightPlannerRepository;

    public FlightPlannerService(FlightPlannerRepository flightPlannerRepository) {
        this.flightPlannerRepository = flightPlannerRepository;
    }

    public Flight getFlight(int id) {
        return flightPlannerRepository.getFlight(id);
    }
}
