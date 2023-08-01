package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.service.FlightPlannerInMemoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestingController {
    private final FlightPlannerInMemoryService flightPlannerInMemoryService;

    public TestingController(FlightPlannerInMemoryService flightPlannerInMemoryService) {
        this.flightPlannerInMemoryService = flightPlannerInMemoryService;
    }

    @PostMapping("/clear")
    public void clearFlights() {
        flightPlannerInMemoryService.clear();
    }
}
