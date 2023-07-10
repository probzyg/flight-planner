package io.codelex.flightplanner.service;

import io.codelex.flightplanner.IdGen;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.repository.FlightPlannerRepository;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.response.FlightResponse;
import org.springframework.stereotype.Service;


@Service
public class FlightPlannerService {
    private FlightPlannerRepository flightPlannerRepository;

    public FlightPlannerService(FlightPlannerRepository flightPlannerRepository) {
        this.flightPlannerRepository = flightPlannerRepository;
    }

    public FlightResponse getFlight(int id) {
        return new FlightResponse(flightPlannerRepository.getFlight(id));
    }

    public void clear() {
        flightPlannerRepository.clear();
    }

    public synchronized Flight addFlight(AddFlightRequest flightRequest) throws Exception {
        Flight flight = new Flight(flightRequest.getFrom(), flightRequest.getTo(),flightRequest.getCarrier(),flightRequest.getDepartureTime(),flightRequest.getArrivalTime());
        flightPlannerRepository.addFlight(flight);
        return flight;
    }
}
