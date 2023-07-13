package io.codelex.flightplanner.service;

import io.codelex.flightplanner.IdGen;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.TimeDTO;
import io.codelex.flightplanner.repository.FlightPlannerRepository;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightRequest;
import io.codelex.flightplanner.response.FlightResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Service
public class FlightPlannerService {
    private final FlightPlannerRepository flightPlannerRepository;

    public FlightPlannerService(FlightPlannerRepository flightPlannerRepository) {
        this.flightPlannerRepository = flightPlannerRepository;
    }

    public synchronized FlightResponse fetchFlight(int id) {
        return new FlightResponse(flightPlannerRepository.getFlights()
                .stream()
                .filter(flight -> flight.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public void clear() {
        this.flightPlannerRepository.getFlights().clear();
        IdGen.setId(0);
    }

    public synchronized Flight addFlight(AddFlightRequest flightRequest){
        TimeDTO flightTimeDTO = new TimeDTO(flightRequest.getDepartureTime(), flightRequest.getArrivalTime());


        String fromAirport = flightRequest.getFrom().getAirport().toLowerCase().trim();
        String toAirport = flightRequest.getTo().getAirport().toLowerCase().trim();

        if (fromAirport.equals(toAirport)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!flightTimeDTO.isBefore()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Flight flight = createFlight(flightRequest);
        if (flightPlannerRepository.getFlights().contains(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        this.flightPlannerRepository.getFlights().add(flight);

        return flight;
    }

    public synchronized void deleteFlight(int id) {
        flightPlannerRepository.getFlights().removeIf(flight -> flight.getId() == id);
    }

    public synchronized Flight createFlight(AddFlightRequest addFlightRequest) {
        Airport from = addFlightRequest.getFrom();
        Airport to = addFlightRequest.getTo();
        String carrier = addFlightRequest.getCarrier();
        String departureTime = addFlightRequest.getDepartureTime();
        String arrivalTime = addFlightRequest.getArrivalTime();

        return new Flight(from, to, carrier, departureTime, arrivalTime);
    }

    public synchronized List<Airport> searchAirports(String phrase) {
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

    public synchronized List<Flight> searchFlight(SearchFlightRequest searchFlightRequest) {
        List<Flight> flights = flightPlannerRepository.getFlights();

        Airport from = searchAirports(searchFlightRequest.getFrom()).stream().findFirst().orElse(null);
        Airport to = searchAirports(searchFlightRequest.getTo()).stream().findFirst().orElse(null);

            return flights.stream()
                    .filter(a -> a.getFrom().equals(from) &&
                            a.getTo().equals(to) &&
                            a.getDepartureDate()
                                    .equals(searchFlightRequest.getDepartureDate()))
                    .toList();
    }
}
