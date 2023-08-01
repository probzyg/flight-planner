package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.request.SearchFlightRequest;
import io.codelex.flightplanner.response.PageResult;
import io.codelex.flightplanner.service.FlightPlannerInMemoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final FlightPlannerInMemoryService flightPlannerInMemoryService;

    public CustomerController(FlightPlannerInMemoryService flightPlannerInMemoryService) {
        this.flightPlannerInMemoryService = flightPlannerInMemoryService;
    }

    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> searchAirports(@RequestParam("search") String phrase) {
        return this.flightPlannerInMemoryService.searchAirports(phrase.trim());
    }

    @PostMapping("/flights/search")
    public PageResult<Flight> searchFlights(@RequestBody @Valid SearchFlightRequest searchFlightRequest) {
        return flightPlannerInMemoryService.searchFlight(searchFlightRequest);
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightById(@PathVariable long id) {
            return flightPlannerInMemoryService.fetchFlight(id).getFlight();
    }
}
