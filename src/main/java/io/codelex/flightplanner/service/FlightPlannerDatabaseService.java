package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.TimeDTO;
import io.codelex.flightplanner.repository.AirportDatabaseRepository;
import io.codelex.flightplanner.repository.FlightDatabaseRepository;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightRequest;
import io.codelex.flightplanner.response.FlightResponse;
import io.codelex.flightplanner.response.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class FlightPlannerDatabaseService implements FlightPlannerService{
    private final FlightDatabaseRepository flightDatabaseRepository;
    private final AirportDatabaseRepository airportDatabaseRepository;
    private final Airport from = new Airport();
    private final Airport to = new Airport();

    public FlightPlannerDatabaseService(FlightDatabaseRepository flightDatabaseRepository, AirportDatabaseRepository airportDatabaseRepository) {
        this.flightDatabaseRepository = flightDatabaseRepository;
        this.airportDatabaseRepository = airportDatabaseRepository;
    }

    @Override
    public FlightResponse fetchFlight(long id) {
        Optional<Flight> flight = flightDatabaseRepository.findById(id);
        return new FlightResponse(flight.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Override
    public void clear() {
        flightDatabaseRepository.deleteAll();
        airportDatabaseRepository.deleteAll();
    }

    @Override
    public synchronized Flight addFlight(AddFlightRequest flightRequest) {
        Flight flight = isValidAddFlightRequest(flightRequest);
        this.flightDatabaseRepository.save(flight);
        return flight;
    }

    @Override
    public Flight isValidAddFlightRequest(AddFlightRequest flightRequest) {
        if (isValidAirport(flightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!isValidTime(flightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (isValidFlightRequest(flightRequest)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return createFlight(flightRequest);
    }

    @Override
    public boolean isValidAirport(AddFlightRequest flightRequest) {
        String from = flightRequest.getFrom().getAirport().toLowerCase().trim();
        String to = flightRequest.getTo().getAirport().toLowerCase().trim();

        return from.equals(to);
    }

    @Override
    public boolean isValidTime(AddFlightRequest flightRequest) {
        TimeDTO flightTimeDTO = new TimeDTO(flightRequest.getDepartureTime(), flightRequest.getArrivalTime());
        return flightTimeDTO.isBefore();
    }

    @Override
    public boolean isValidFlightRequest(AddFlightRequest flightRequest) {
        Flight flight = createFlight(flightRequest);
        return flightDatabaseRepository.findAll().contains(flight);
    }

    @Override
    public void deleteFlight(long id) {
        flightDatabaseRepository.deleteById(id);
    }

    public Flight createFlight(AddFlightRequest addFlightRequest) {
        addAirports(addFlightRequest);

        Flight flight = new Flight();
        flight.setFrom(this.from);
        flight.setTo(this.to);
        flight.setCarrier(addFlightRequest.getCarrier());
        flight.setDepartureTime(addFlightRequest.getDepartureTime());
        flight.setArrivalTime(addFlightRequest.getArrivalTime());
        return flight;
    }

    @Override
    public List<Airport> searchAirports(String phrase) {
        return airportDatabaseRepository.searchAirportsByPhrase(phrase.trim().toLowerCase());
    }

    public void addAirports(AddFlightRequest addFlightRequest) {

        Airport fromRequest = addFlightRequest.getFrom();
        this.from.setCountry(fromRequest.getCountry());
        this.from.setCity(fromRequest.getCity());
        this.from.setAirport(fromRequest.getAirport());

        Airport toRequest = addFlightRequest.getTo();
        this.to.setCountry(toRequest.getCountry());
        this.to.setCity(toRequest.getCity());
        this.to.setAirport(toRequest.getAirport());

        airportDatabaseRepository.save(this.from);
        airportDatabaseRepository.save(this.to);
    }

    @Override
    public PageResult<Flight> searchFlight(SearchFlightRequest searchFlightRequest) {
        String from = searchFlightRequest.getFrom().trim().toUpperCase();
        String to = searchFlightRequest.getTo().trim().toUpperCase();
        String departureDate = searchFlightRequest.getDepartureDate();

        if (from.equals(to)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Flight> foundFlights = flightDatabaseRepository.searchFlightsByFromToAndDepartureDate(from, to, departureDate);

        return new PageResult<>(0, foundFlights.size(), foundFlights);
    }
}
