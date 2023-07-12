package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.response.FlightResponse;
import io.codelex.flightplanner.service.FlightPlannerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/admin-api")
public class AdminController {
    private final FlightPlannerService flightPlannerService;

    public AdminController(FlightPlannerService flightPlannerService) {
        this.flightPlannerService = flightPlannerService;
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<FlightResponse> fetchFlight(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(flightPlannerService.fetchFlight(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody AddFlightRequest request) {
        try {
            return flightPlannerService.addFlight(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteFlight(@PathVariable int id) {
        try {
            return ResponseEntity.ok(flightPlannerService.deleteFlight(id));
        } catch (Exception e) {
            return ResponseEntity.status(200).build();
        }
    }
}
