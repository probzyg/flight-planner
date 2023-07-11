package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.TimeDTO;
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
        TimeDTO flightTimeDTO = new TimeDTO(request.getDepartureTime(), request.getArrivalTime());

        String fromCountry = request.getFrom().getCountry().toLowerCase();
        String toCountry = request.getTo().getCountry().toLowerCase();
        String fromCity = request.getFrom().getCity().toLowerCase();
        String toCity = request.getTo().getCity().toLowerCase();
        String fromAirport = request.getFrom().getAirport().toLowerCase();
        String toAirport = request.getTo().getAirport().toLowerCase();

        if (fromAirport.equals(toAirport) || fromCountry.equals(toCountry) || fromCity.equals(toCity)) {
            return ResponseEntity.badRequest().build();
        }

        if (!flightTimeDTO.isBefore()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(flightPlannerService.addFlight(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Flight> deleteFlight(@PathVariable int id) {
        try {
            return ResponseEntity.ok(flightPlannerService.deleteFlight(id));
        } catch (Exception e) {
            return ResponseEntity.status(200).build();
        }
    }
}
