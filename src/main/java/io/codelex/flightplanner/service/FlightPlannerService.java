package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.TimeDTO;
import io.codelex.flightplanner.repository.FlightPlannerRepository;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightRequest;
import io.codelex.flightplanner.response.FlightResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Service
public class FlightPlannerService {
    private final FlightPlannerRepository flightPlannerRepository;

    public FlightPlannerService(FlightPlannerRepository flightPlannerRepository) {
        this.flightPlannerRepository = flightPlannerRepository;
    }

    public FlightResponse fetchFlight(int id) {
        return new FlightResponse(flightPlannerRepository.fetchFlight(id));
    }

    public void clear() {
        flightPlannerRepository.clear();
    }

    public synchronized ResponseEntity<Flight> addFlight(AddFlightRequest flightRequest) throws Exception {
        TimeDTO flightTimeDTO = new TimeDTO(flightRequest.getDepartureTime(), flightRequest.getArrivalTime());

        String fromCountry = flightRequest.getFrom().getCountry().toLowerCase();
        String toCountry = flightRequest.getTo().getCountry().toLowerCase();
        String fromCity = flightRequest.getFrom().getCity().toLowerCase();
        String toCity = flightRequest.getTo().getCity().toLowerCase();
        String fromAirport = flightRequest.getFrom().getAirport().toLowerCase();
        String toAirport = flightRequest.getTo().getAirport().toLowerCase();

        if (fromAirport.equals(toAirport) || fromCountry.equals(toCountry) || fromCity.equals(toCity)) {
            return ResponseEntity.badRequest().build();
        }

        if (!flightTimeDTO.isBefore()) {
            return ResponseEntity.badRequest().build();
        }

        Flight flight = new Flight(flightRequest.getFrom(), flightRequest.getTo(),flightRequest.getCarrier(),flightRequest.getDepartureTime(),flightRequest.getArrivalTime());
        flightPlannerRepository.addFlight(flight);

        return ResponseEntity.status(HttpStatus.CREATED).body(flight);
    }

    public boolean deleteFlight(int id) {
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

    public List<Flight> searchFlight(SearchFlightRequest searchFlightRequest) {
        List<Flight> flights = flightPlannerRepository.getFlights();

        Airport from = searchAirports(searchFlightRequest.getFrom()).stream().findFirst().orElse(null);
        Airport to = searchAirports(searchFlightRequest.getTo()).stream().findFirst().orElse(null);

            return flights.stream()
                    .filter(a -> a.getFrom().equals(from) &&
                            a.getTo().equals(to) &&
                            a.getDepartureTime()
                                    .equals(searchFlightRequest.getDepartureDate()))
                    .toList();
    }
}