package io.codelex.flightplanner.service;

import io.codelex.flightplanner.IdGen;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.TimeDTO;
import io.codelex.flightplanner.repository.FlightPlannerInMemoryRepository;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightRequest;
import io.codelex.flightplanner.response.FlightResponse;
import io.codelex.flightplanner.response.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Service
public class FlightPlannerInMemoryService implements FlightPlannerService {
    private final FlightPlannerInMemoryRepository flightPlannerInMemoryRepository;

    public FlightPlannerInMemoryService(FlightPlannerInMemoryRepository flightPlannerInMemoryRepository) {
        this.flightPlannerInMemoryRepository = flightPlannerInMemoryRepository;
    }
    @Override
    public synchronized FlightResponse fetchFlight(long id) {
        return new FlightResponse(flightPlannerInMemoryRepository.getFlights()
                .stream()
                .filter(flight -> flight.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
    @Override
    public void clear() {
        this.flightPlannerInMemoryRepository.getFlights().clear();
        IdGen.setId(0);
    }
    @Override
    public synchronized Flight addFlight(AddFlightRequest flightRequest){
        Flight flight = isValidAddFlightRequest(flightRequest);
        this.flightPlannerInMemoryRepository.getFlights().add(flight);

        return flight;
    }
    @Override
    public Flight isValidAddFlightRequest(AddFlightRequest flightRequest) {
        isValidAirport(flightRequest);
        isValidTime(flightRequest);
        isValidFlightRequest(flightRequest);

        return createFlight(flightRequest);
    }
    @Override
    public void isValidAirport(AddFlightRequest flightRequest) {
        String fromAirport = flightRequest.getFrom().getAirport().toLowerCase().trim();
        String toAirport = flightRequest.getTo().getAirport().toLowerCase().trim();

        if (fromAirport.equals(toAirport)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public void isValidTime(AddFlightRequest flightRequest) {
        TimeDTO flightTimeDTO = new TimeDTO(flightRequest.getDepartureTime(), flightRequest.getArrivalTime());
        if (!flightTimeDTO.isBefore()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public void isValidFlightRequest(AddFlightRequest flightRequest) {
        Flight flight = createFlight(flightRequest);
        if (flightPlannerInMemoryRepository.getFlights().contains(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    @Override
    public synchronized void deleteFlight(long id) {
        flightPlannerInMemoryRepository.getFlights().removeIf(flight -> flight.getId() == id);
    }
    @Override
    public synchronized Flight createFlight(AddFlightRequest addFlightRequest) {
        Airport from = addFlightRequest.getFrom();
        Airport to = addFlightRequest.getTo();
        String carrier = addFlightRequest.getCarrier();
        String departureTime = addFlightRequest.getDepartureTime();
        String arrivalTime = addFlightRequest.getArrivalTime();

        return new Flight(from, to, carrier, departureTime, arrivalTime);
    }
    @Override
    public List<Airport> searchAirports(String phrase) {
        List<Flight> flights = flightPlannerInMemoryRepository.getFlights();
        List<Airport> airportList = createAirportList(flights);

        String regex = ".*" + phrase + ".*";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        return airportList.stream()
                .filter(a -> pattern.matcher(a.getCountry()).matches() ||
                        pattern.matcher(a.getCity()).matches() ||
                        pattern.matcher(a.getAirport()).matches())
                .toList();
    }
    @Override
    public List<Airport> createAirportList(List<Flight> flights) {
        List<Airport> airportList = new ArrayList<>();

        for (Flight flight : flights) {
            airportList.add(flight.getFrom());
            airportList.add(flight.getTo());
        }
        return airportList;
    }
    @Override
    public PageResult<Flight> searchFlight(SearchFlightRequest searchFlightRequest) {
        if (searchFlightRequest.getFrom().equals(searchFlightRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Flight> flights = flightPlannerInMemoryRepository.getFlights();

        String from = searchFlightRequest.getFrom().trim().toUpperCase();
        String to = searchFlightRequest.getTo().trim().toUpperCase();

        List<Flight> foundFlights = flights.stream()
                    .filter(a -> a.getFrom().getAirport().equals(from) &&
                            a.getTo().getAirport().equals(to) &&
                            a.getDepartureDate()
                                    .equals(searchFlightRequest.getDepartureDate()))
                    .toList();
        return new PageResult<>(0, foundFlights.size(), foundFlights);
    }
}
