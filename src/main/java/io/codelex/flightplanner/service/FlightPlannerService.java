package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.repository.FlightPlannerRepository;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.response.FlightResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class FlightPlannerService {
    private final FlightPlannerRepository flightPlannerRepository;

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

    public Flight deleteFlight(int id) {
        return flightPlannerRepository.deleteFlight(id);
    }

    public List<Airport> searchAirports(String phrase) {
        List<Flight> flights = flightPlannerRepository.getFlights();
        List<Airport> airportList = new ArrayList<>();

        for (Flight flight : flights) {
            airportList.add(flight.getFrom());
            airportList.add(flight.getTo());
        }

        String regex = ".*" + phrase + ".*";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        return airportList.stream()
                .filter(a -> pattern.matcher(a.getCountry()).matches() ||
                        pattern.matcher(a.getCity()).matches() ||
                        pattern.matcher(a.getAirport()).matches())
                .toList();
    }
}
