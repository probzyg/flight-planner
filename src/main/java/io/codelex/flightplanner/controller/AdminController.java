package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.response.FlightResponse;
import io.codelex.flightplanner.service.FlightPlannerInMemoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/admin-api")
public class AdminController {
    private final FlightPlannerInMemoryService flightPlannerInMemoryService;

    public AdminController(FlightPlannerInMemoryService flightPlannerInMemoryService) {
        this.flightPlannerInMemoryService = flightPlannerInMemoryService;
    }

    @GetMapping("/flights/{id}")
    public FlightResponse fetchFlight(@PathVariable("id") long id) {
            return flightPlannerInMemoryService.fetchFlight(id);
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody AddFlightRequest request){
            return flightPlannerInMemoryService.addFlight(request);
    }

    @DeleteMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlight(@PathVariable long id) {
        flightPlannerInMemoryService.deleteFlight(id);
    }
}
