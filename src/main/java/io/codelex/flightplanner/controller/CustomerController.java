package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.request.SearchFlightRequest;
import io.codelex.flightplanner.response.PageResult;
import io.codelex.flightplanner.service.FlightPlannerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final FlightPlannerService flightPlannerService;

    public CustomerController(FlightPlannerService flightPlannerService) {
        this.flightPlannerService = flightPlannerService;
    }

    @GetMapping("/airports")
    public ResponseEntity<List<Airport>> searchAirports(@RequestParam("search") String phrase) {
        return ResponseEntity.ok(this.flightPlannerService.searchAirports(phrase.trim()));
    }

    @PostMapping("/flights/search")
    public ResponseEntity<PageResult<Flight>> searchFlights(@RequestBody @Valid SearchFlightRequest searchFlightRequest) {
        if (searchFlightRequest.getFrom().equals(searchFlightRequest.getTo())) {
            return ResponseEntity.status(400).build();
        }
        List<Flight> foundFlights = flightPlannerService.searchFlight(searchFlightRequest);
        try {
            return ResponseEntity.ok(new PageResult<>(0, foundFlights.size(), foundFlights));
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> findFlightById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(flightPlannerService.fetchFlight(id).getFlight());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
