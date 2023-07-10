package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.response.FlightResponse;
import io.codelex.flightplanner.service.FlightPlannerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-api")
public class AdminController {
    private final FlightPlannerService flightPlannerService;

    public AdminController(FlightPlannerService flightPlannerService) {
        this.flightPlannerService = flightPlannerService;
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<FlightResponse> getFlight(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(flightPlannerService.getFlight(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody AddFlightRequest request) {


        String fromCountry = request.getFrom().getCountry().toLowerCase();
        String toCountry = request.getTo().getCountry().toLowerCase();
        String fromCity = request.getFrom().getCity().toLowerCase();
        String toCity = request.getTo().getCity().toLowerCase();
        String fromAirport = request.getFrom().getAirport().toLowerCase();
        String toAirport = request.getTo().getAirport().toLowerCase();

        if (fromAirport.equals(toAirport) || fromCountry.equals(toCountry) || fromCity.equals(toCity)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(flightPlannerService.addFlight(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
