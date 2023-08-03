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

public class FlightPlannerDatabaseService implements FlightPlannerService{
    private FlightDatabaseRepository flightDatabaseRepository;
    private AirportDatabaseRepository airportDatabaseRepository;

    public FlightPlannerDatabaseService(FlightDatabaseRepository flightDatabaseRepository, AirportDatabaseRepository airportDatabaseRepository) {
        this.flightDatabaseRepository = flightDatabaseRepository;
        this.airportDatabaseRepository = airportDatabaseRepository;
    }

    @Override
    public FlightResponse fetchFlight(long id) {
        return new FlightResponse(flightDatabaseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Override
    public void clear() {
        flightDatabaseRepository.deleteAll();
        airportDatabaseRepository.deleteAll();
    }

    @Override
    public Flight addFlight(AddFlightRequest flightRequest) {
        Flight flight = isValidAddFlightRequest(flightRequest);
        return flightDatabaseRepository.save(flight);
    }

    @Override
    public Flight isValidAddFlightRequest(AddFlightRequest flightRequest) {
        if (isValidAirport(flightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (isValidTime(flightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (isValidFlightRequest(flightRequest)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        airportDatabaseRepository.save(flightRequest.getFrom());
        airportDatabaseRepository.save(flightRequest.getTo());
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

    public synchronized Flight createFlight(AddFlightRequest addFlightRequest) {
        Flight flight = new Flight();
        flight.setFrom(addFlightRequest.getFrom());
        flight.setTo(addFlightRequest.getTo());
        flight.setCarrier(addFlightRequest.getCarrier());
        flight.setDepartureTime(addFlightRequest.getDepartureTime());
        flight.setArrivalTime(addFlightRequest.getArrivalTime());
        return flight;
    }

    @Override
    public List<Airport> searchAirports(String phrase) {
        return null;
    }

    public List<Airport> createAirportList(List<Flight> flights) {
        return null;
    }

    @Override
    public PageResult<Flight> searchFlight(SearchFlightRequest searchFlightRequest) {
        return null;
    }
}
