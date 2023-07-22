package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.service.FlightPlannerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestingController {
    private final FlightPlannerService flightPlannerService;

    public TestingController(FlightPlannerService flightPlannerService) {
        this.flightPlannerService = flightPlannerService;
    }

    @PostMapping("/clear")
    public void clearFlights() {
        flightPlannerService.clear();
    }
}
