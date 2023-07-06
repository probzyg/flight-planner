package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.service.FlightPlannerService;
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
    public ResponseEntity<Flight> getFlight(@PathVariable("id") int id) {
        try {
            Flight flight = flightPlannerService.getFlight(id);
            return ResponseEntity.ok(flight);}
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
